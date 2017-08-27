package wow.roll.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import wow.roll.domain.Epgp;
import wow.roll.service.EpgpService;
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
}
