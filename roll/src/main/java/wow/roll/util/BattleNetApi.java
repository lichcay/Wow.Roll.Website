package wow.roll.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import wow.roll.domain.ItemLoot;
import wow.roll.domain.Member;

@Configuration
@PropertySource(value = { "classpath:bnapi.properties", "classpath:general.properties" })
public class BattleNetApi {

	@Value("${api.key}")
	private static String apiKey = "czbvpfc8pcpms7m22k53r2h8vxht2a3y";

	@Value("${url.guildprofile}")
	private static String guildProfileUrl = "https://eu.api.battle.net/wow/guild/";

	@Value("${guild.name}")
	private static String guildName = "R O L L";

	@Value("${realm.name}")
	private static String realmName = "Shattered Hand";

	protected static Logger logger = LoggerFactory.getLogger(BattleNetApi.class);

	public static List<Member> getGuildMember() {
		List<Member> memberList = new ArrayList<Member>();
		String fields = "members";
		String apiUrl = "";
		try {
			apiUrl = guildProfileUrl + URLEncoder.encode(realmName, "utf-8") + "/" + URLEncoder.encode(guildName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.debug(e.toString());
		}
		apiUrl = StringUtils.replace(apiUrl, "+", "%20");
		logger.debug(apiUrl);
		Map<String, Object> params = paramsGen(fields);
		String rsp = HttpClientUtil.doGetSSL(apiUrl, params);
		JSONObject guild = JSON.parseObject(rsp);
		JSONArray members = guild.getJSONArray("members");
		JSONObject memberWithRank;
		JSONObject memberJ;
		int rank;
		for (int i = 0; i < members.size(); i++) {
			memberWithRank = members.getJSONObject(i);
			memberJ = memberWithRank.getJSONObject("character");
			rank = memberWithRank.getIntValue("rank");
			Member m = new Member();
			m.setId(Utils.getUUID());
			m.setName(memberJ.getString("name"));
			m.setCclass(memberJ.getIntValue("class"));
			m.setRace(memberJ.getIntValue("race"));
			m.setGender(memberJ.getIntValue("gender"));
			m.setLevel(memberJ.getIntValue("level"));
			m.setThumbnail(memberJ.getString("thumbnail"));
			m.setRank(rank);
			m.setEpgp(0);
			m.setCreatetime((new Date()).toString());
			m.setEdittime(m.getCreatetime());
			m.setRemovetag(0);
			memberList.add(m);
		}
		return memberList;

	}

	public static List<ItemLoot> getGuildItemLootNews() {
		List<ItemLoot> itemlootList = new ArrayList<ItemLoot>();
		String fields = "news";
		String apiUrl = "";
		try {
			apiUrl = guildProfileUrl + URLEncoder.encode(realmName, "utf-8") + "/" + URLEncoder.encode(guildName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.debug(e.toString());
		}
		apiUrl = StringUtils.replace(apiUrl, "+", "%20");
		logger.debug(apiUrl);
		Map<String, Object> params = paramsGen(fields);
		String rsp = HttpClientUtil.doGetSSL(apiUrl, params);
		JSONObject guild = JSON.parseObject(rsp);
		JSONArray news = guild.getJSONArray("news");
		JSONObject tmpdata;
		for (int i = 0; i < news.size(); i++) {
			tmpdata = news.getJSONObject(i);
			if (StringUtils.equals(tmpdata.getString("type"), "itemLoot")) {
				ItemLoot loot = new ItemLoot();
				loot.setId(Utils.getUUID());
				loot.setCharactername(tmpdata.getString("character"));
				loot.setLoottimestamp(tmpdata.getString("timestamp"));
				loot.setItemid(tmpdata.getString("itemId"));
				loot.setContext(tmpdata.getString("context"));
				loot.setBonuslists(tmpdata.getString("bonusLists"));
				loot.setCreatetime((new Date()).toString());
				loot.setEdittime(loot.getCreatetime());
				loot.setRemovetag(0);
				itemlootList.add(loot);
			}
		}
		return itemlootList;
	}

	private static Map<String, Object> paramsGen(String fields) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fields", fields);
		params.put("locale", "en_GB");
		params.put("apikey", apiKey);
		return params;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
