package wow.roll.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import wow.roll.domain.ItemLoot;
import wow.roll.domain.ItemLootView;
import wow.roll.domain.Member;
import wow.roll.service.ItemLootService;
import wow.roll.service.MemberService;
import wow.roll.util.BattleNetApi;

@Controller
@EnableAutoConfiguration
public class IndexController {

	protected static Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private ItemLootService itemLootService;

	@RequestMapping("/refreshdata")
	String refreshdata() {
		logger.debug("Start scan guild members using BNApi...");
		logger.debug("Connecting  BNApi...");
		List<Member> members = BattleNetApi.getGuildMember();
		logger.debug("Got members, analysing...");
		if (memberService.insertMemberList(members)) {
			logger.debug("Successfully inserted guild members.");
		}
		List<Member> memberToDel = memberService.getAllMember();
		logger.debug("Checking quited members...");
		memberToDel.removeAll(members);
		if (memberToDel.size() != 0) {
			logger.debug("Deleting quited members...");
			memberService.delMembers(memberToDel);
		}
		logger.debug("Successfully deleted quited members...");

		logger.debug("Start scan guild news using BNApi...");
		logger.debug("Connecting  BNApi...");
		List<ItemLoot> loots = BattleNetApi.getGuildItemLootNews();
		logger.debug("Got loots, analysing...");
		if (itemLootService.insertItemLootList(loots)) {
			logger.debug("Successfully inserted guild news.");
		}
		return "123";
	}

	@RequestMapping("/")
	String index(Model model) {
		List<ItemLootView> loots = itemLootService.getPageItemLootView(1);
		model.addAttribute("loots", loots);
		return "index";
	}

	@RequestMapping("/pageloot")
	@ResponseBody
	String pageloot(Model model, @RequestBody JSONObject jsonObj) {
		List<ItemLootView> loots = itemLootService.getPageItemLootView(jsonObj.getIntValue("pageno"));
		String temp = JSON.toJSONString(loots);
		return temp;
	}

}
