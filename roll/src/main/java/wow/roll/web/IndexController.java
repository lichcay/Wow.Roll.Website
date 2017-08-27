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

import wow.roll.domain.EpgpView;
import wow.roll.domain.ItemLootView;
import wow.roll.service.EpgpService;
import wow.roll.service.ItemLootService;

@Controller
@EnableAutoConfiguration
public class IndexController {

	protected static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private ItemLootService itemLootService;

	@Autowired
	private EpgpService epgpService;

	@RequestMapping("/")
	String index(Model model) {
		List<ItemLootView> loots = itemLootService.getPageItemLootView(0);
		List<EpgpView> epgplist = epgpService.getAllEpgpView();
		model.addAttribute("loots", loots);
		model.addAttribute("epgplist", epgplist);
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
