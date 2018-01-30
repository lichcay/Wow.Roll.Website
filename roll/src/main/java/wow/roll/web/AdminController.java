package wow.roll.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import wow.roll.domain.Epgp;
import wow.roll.domain.EpgpLog;
import wow.roll.domain.Member;
import wow.roll.service.EpgpLogService;
import wow.roll.service.EpgpService;
import wow.roll.service.MemberService;
import wow.roll.util.EpgpUtils;
import wow.roll.util.ResultSet;
import wow.roll.util.Utils;

@Controller
@RequestMapping("/admin")
@EnableAutoConfiguration
public class AdminController {
	protected static Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private EpgpService epgpService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private EpgpLogService epgpLogService;

	@RequestMapping("")
	String admin(Model model) {
		return "admin";
	}

	@RequestMapping(value = "/uploadEpgp", method = RequestMethod.POST)
	@ResponseBody
	String uploadEpgp(Model model, @RequestBody JSONObject jsonObj) {
		JSONObject epgpJson = jsonObj.getJSONObject("epgp");
		String roster = epgpJson.getString("roster");
		List<String[]> epgpList = EpgpUtils.EpgpImportHelp(roster);
		List<Epgp> parsedEpgpList = new ArrayList<Epgp>();
		for (int i = 0; i < epgpList.size(); i++) {
			int ep = Integer.parseInt(epgpList.get(i)[1]);
			int gp = Integer.parseInt(epgpList.get(i)[2]);
			double pr = 1.00 * ep / gp;
			BigDecimal bg = new BigDecimal(pr);
			pr = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			String character = epgpList.get(i)[0];
			Epgp tmpEpgp = new Epgp();
			tmpEpgp.setId(Utils.getUUID());
			tmpEpgp.setEp(ep);
			tmpEpgp.setGp(gp);
			tmpEpgp.setPr(pr);
			tmpEpgp.setCharactername(character);
			tmpEpgp.setCreatetime((new Date()).toString());
			tmpEpgp.setEdittime(tmpEpgp.getCreatetime());
			tmpEpgp.setRemovetag(0);
			parsedEpgpList.add(tmpEpgp);
		}
		boolean rs = epgpService.insertEpgpList(parsedEpgpList);
		ResultSet result = new ResultSet();
		result.setRs(rs);
		if (rs) {
			result.setMessage("Import succeeded!");
		} else {
			result.setMessage("Import failed, try again!");
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/uploadEpgpDB", method = RequestMethod.POST)
	@ResponseBody
	String uploadEpgpDB(@RequestParam("epgp_db") MultipartFile file) {
		try {
			if (file.isEmpty()) {
				return "null";
			}
			// 获取文件名
			String fileName = file.getOriginalFilename();
			logger.info("上传的文件名为：" + fileName);
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			logger.info("文件的后缀名为：" + suffixName);
			InputStream in = file.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				buffer.append(line);
			}
			in.close();
			String epgpDetailsRaw = buffer.toString();
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(epgpDetailsRaw);
			String epgpDetails = m.replaceAll("");
			List<EpgpLog> epgpLog = luaDBHelp(epgpDetails);
			epgpLogService.insertEpgpLogList(epgpLog);
			return "success";
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "fail";
	}

	private List<EpgpLog> luaDBHelp(String epgpDetails) {
		Map<String, String> luaDB = parseLuaDB(epgpDetails);
		String lua_namespace = luaDB.get("namespaces");
		Map<String, String> namesSpace = parseLuaDB(lua_namespace);
		String lua_log = namesSpace.get("log");
		Map<String, String> lua_epgpLog = parseLuaDB(lua_log);
		String epgp_log_profiles = lua_epgpLog.get("profiles");
		Map<String, String> lua_guild = parseLuaDB(epgp_log_profiles);
		String epgp_log_guild = lua_guild.get("ROLL");
		Map<String, String> lua_log_details = parseLuaDB(epgp_log_guild);
		String epgp_log_details = lua_log_details.get("log");
		String[] epgp_log_details_array = epgp_log_details.split("\\},");
		List<EpgpLog> epgpLogList = new ArrayList<EpgpLog>();
		for (int i = 0; i < epgp_log_details_array.length; i++) {
			if (i == 0) {
				String[] tmplog = epgp_log_details_array[i].substring(1, epgp_log_details_array[i].length()).split(",--");
				EpgpLog log = new EpgpLog();
				log.setId(Utils.getUUID());
				log.setTimestamp(tmplog[0]);
				String type = tmplog[1].substring(3, tmplog[1].length()).replaceAll("\"", "");
				log.setType(type);
				if (StringUtils.equals(type, "EP")) {
					log.setReason(tmplog[3].substring(3, tmplog[3].length()).replaceAll("\"", ""));
					log.setGear("");
				} else {
					log.setGear(tmplog[3].substring(3, tmplog[3].length()).replaceAll("\"", ""));
					log.setReason("");
				}
				log.setAmount(tmplog[4].substring(3, tmplog[4].length()).replaceAll("\"", ""));
				log.setCreatetime((new Date()).toString());
				log.setEdittime(log.getCreatetime());
				log.setRemovetag(0);
				String memberNameWithServer = tmplog[2].substring(3, tmplog[2].length()).replaceAll("\"", "");
				String memberName = memberNameWithServer.split("-")[0];
				Member m = memberService.getMemberByNameIncludeRemoved(memberName);
				if (m != null) {
					String memberId = m.getId();
					log.setMemberid(memberId);
					epgpLogList.add(log);
				}
			} else if (i != epgp_log_details_array.length - 1) {
				String[] tmplog = epgp_log_details_array[i].substring(1, epgp_log_details_array[i].length()).split(",--");
				EpgpLog log = new EpgpLog();
				log.setId(Utils.getUUID());
				String timestamp = tmplog[0].split("\\{")[1];
				log.setTimestamp(timestamp);
				String type = tmplog[1].substring(3, tmplog[1].length()).replaceAll("\"", "");
				log.setType(type);
				if (StringUtils.equals(type, "EP")) {
					log.setReason(tmplog[3].substring(3, tmplog[3].length()).replaceAll("\"", ""));
					log.setGear("");
				} else {
					log.setGear(tmplog[3].substring(3, tmplog[3].length()).replaceAll("\"", ""));
					log.setReason("");
				}
				log.setAmount(tmplog[4].substring(3, tmplog[4].length()).replaceAll("\"", ""));
				log.setCreatetime((new Date()).toString());
				log.setEdittime(log.getCreatetime());
				log.setRemovetag(0);
				String memberNameWithServer = tmplog[2].substring(3, tmplog[2].length()).replaceAll("\"", "");
				String memberName = memberNameWithServer.split("-")[0];
				Member m = memberService.getMemberByNameIncludeRemoved(memberName);
				if (m != null) {
					String memberId = m.getId();
					log.setMemberid(memberId);
					epgpLogList.add(log);
				}
			}
		}
		return epgpLogList;
	}

	private Map<String, String> parseLuaDB(String luaDB) {
		int n = 0;
		Map<String, String> luaMap = new HashMap<String, String>();
		while (n < luaDB.length()) {
			String c = String.valueOf(luaDB.charAt(n));
			if (StringUtils.equals(c, "[")) {
				n = n + 1;
				int keycount = 1;
				String key = "";
				while (keycount > 0) {
					c = String.valueOf(luaDB.charAt(n));
					if (StringUtils.equals(c, "[")) {
						keycount = keycount + 1;
					} else if (StringUtils.equals(c, "]")) {
						keycount = keycount - 1;
					} else if (StringUtils.equals(c, "\"")) {
						key = key + "";
					} else {
						key = key + c;
					}
					n = n + 1;
				}
				c = String.valueOf(luaDB.charAt(n));
				if (StringUtils.equals(c, "=")) {
					n = n + 1;
					c = String.valueOf(luaDB.charAt(n));
					if (StringUtils.equals(c, "{")) {
						n = n + 1;
						int valuecount = 1;
						String value = "";
						while (valuecount > 0) {
							c = String.valueOf(luaDB.charAt(n));
							if (StringUtils.equals(c, "{")) {
								valuecount = valuecount + 1;
								value = value + c;
							} else if (StringUtils.equals(c, "}")) {
								valuecount = valuecount - 1;
								value = value + c;
							} else {
								value = value + c;
							}
							n = n + 1;
						}
						value = value.substring(0, value.length() - 1);
						luaMap.put(key, value);
					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				n = n + 1;
			}
		}
		return luaMap;
	}
}
