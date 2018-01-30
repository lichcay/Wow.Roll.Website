package wow.roll.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import wow.roll.domain.BnAccount;
import wow.roll.domain.EpgpLogView;
import wow.roll.domain.EpgpLogViewShow;
import wow.roll.domain.EpgpView;
import wow.roll.domain.ItemLootView;
import wow.roll.domain.Member;
import wow.roll.service.BnAccountService;
import wow.roll.service.EpgpLogService;
import wow.roll.service.EpgpService;
import wow.roll.service.ItemLootService;
import wow.roll.service.MemberService;
import wow.roll.service.RedisService;
import wow.roll.util.BattleNetApi;
import wow.roll.util.ItemUtils;
import wow.roll.util.Utils;

@Controller
@EnableAutoConfiguration
public class IndexController {

	protected static Logger logger = LoggerFactory.getLogger(IndexController.class);

	private @Value("${epgp.rank}") int rank;

	@Autowired
	private ItemLootService itemLootService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private EpgpService epgpService;

	@Autowired
	private EpgpLogService epgpLogService;

	@Autowired
	private BnAccountService bnAccountService;

	@Autowired
	private RedisService redisService;

	@RequestMapping("/")
	String index(Model model) {
		List<ItemLootView> loots = itemLootService.getPageItemLootView(0);
		model.addAttribute("loots", loots);
		HttpSession session = getSession();
		String isGuest = (String) session.getAttribute("isGuest");
		if (StringUtils.equals(isGuest, "false")) {
			List<EpgpView> epgplist = epgpService.getAllEpgpView(rank);
			List<EpgpLogView> epgploglist = epgpLogService.getAllEpgpView(rank, 0);
			List<EpgpLogViewShow> epgpLogViewShow = ItemUtils.transEpgpLogViewToShow(epgploglist);
			model.addAttribute("epgploglist", epgpLogViewShow);
			model.addAttribute("epgplist", epgplist);
		} else {
			List<EpgpView> epgplist = new ArrayList<EpgpView>();
			List<EpgpLogView> epgploglist = new ArrayList<EpgpLogView>();
			model.addAttribute("epgploglist", epgploglist);
			model.addAttribute("epgplist", epgplist);
		}
		return "index";
	}

	@RequestMapping("/pageloot")
	@ResponseBody
	String pageloot(Model model, @RequestBody JSONObject jsonObj) {
		List<ItemLootView> loots = itemLootService.getPageItemLootView(jsonObj.getIntValue("pageno"));
		String temp = JSON.toJSONString(loots);
		return temp;
	}

	@RequestMapping("/pageepgp")
	@ResponseBody
	String pageepgp(Model model, @RequestBody JSONObject jsonObj) {
		List<EpgpLogView> epgp = epgpLogService.getAllEpgpView(rank, jsonObj.getIntValue("pageno"));
		List<EpgpLogViewShow> epgpLogViewShow = ItemUtils.transEpgpLogViewToShow(epgp);
		String temp = JSON.toJSONString(epgpLogViewShow);
		return temp;
	}

	@RequestMapping("/oauth2callback")
	String oauth2callback(Model model, String code) {
		HttpSession session = getSession();
		BnAccount bnAccount_s = (BnAccount) session.getAttribute("bnaccount");
		if (StringUtils.isNotEmpty(bnAccount_s.getId())) {
			return "redirect:/";
		}
		session.setAttribute("isGuest", "false");
		String token = "";
		if (StringUtils.isNotEmpty(code)) {
			token = BattleNetApi.getOauth2AccessToken(code);
			session.setAttribute("token", token);
		}
		// get BattleNet Account. If it doesn't exist in table insert.
		Map<String, String> account = BattleNetApi.getAccount(token);
		BnAccount bnAccount = bnAccountService.getBnAccountById(account.get("btid"));
		if (bnAccount == null) {
			bnAccount = new BnAccount();
			bnAccount.setId(Utils.getUUID());
			bnAccount.setBattletag(account.get("battletag"));
			bnAccount.setBtid(account.get("btid"));
			bnAccount.setCreatetime((new Date()).toString());
			bnAccount.setEdittime(bnAccount.getCreatetime());
			bnAccount.setRemovetag(0);
			bnAccountService.insertBnAccount(bnAccount);
		}
		// set the flag for if its a guest
		int flag_guest = 0;
		// try to get characters in this BattleNet Account form local table
		List<Member> mlist = memberService.getMemberMyBnTag(bnAccount.getBtid());
		// If there is no record in local table ,get characters in this
		// BattleNet Account using BattleNet API
		if (mlist.size() == 0) {
			List<String> characters = BattleNetApi.getAccountCharacters(token);
			// if the BattleNet Account has no guild character, which means its
			// a guest. set a parameter for recognize it.
			if (characters.size() == 0) {
				flag_guest = 1;
				session.setAttribute("isGuest", "true");
			} else {
				for (int i = 0; i < characters.size(); i++) {
					String characterName = characters.get(i);
					Member m = memberService.getMemberByName(characterName);
					m.setBnaccountid(bnAccount.getBtid());
					mlist.add(m);
					memberService.updateMember(m);
				}
				// Set default character to the BattleNet Account
				bnAccount.setDefaultcharacterid(mlist.get(0).getId());
				bnAccountService.updateBnAccount(bnAccount);
			}
		}
		// if the BattleNet Account is not a guest ,get default character name
		// and avatar.
		if (flag_guest == 0) {
			List<EpgpView> epgplist = epgpService.getAllEpgpView(rank);
			model.addAttribute("epgplist", epgplist);
			List<EpgpLogView> epgploglist = epgpLogService.getAllEpgpView(rank, 0);
			List<EpgpLogViewShow> epgpLogViewShow = ItemUtils.transEpgpLogViewToShow(epgploglist);
			model.addAttribute("epgploglist", epgpLogViewShow);
			for (int i = 0; i < mlist.size(); i++) {
				if (StringUtils.equals(mlist.get(i).getId(), bnAccount.getDefaultcharacterid())) {
					session.setAttribute("default_character_name", mlist.get(i).getName());
					session.setAttribute("default_character_avatar", mlist.get(i).getThumbnail());
				}
			}
		} else {
			List<EpgpView> epgplist = new ArrayList<EpgpView>();
			model.addAttribute("epgplist", epgplist);
			List<EpgpLogView> epgploglist = new ArrayList<EpgpLogView>();
			model.addAttribute("epgploglist", epgploglist);
			session.setAttribute("default_character_name", "");
			session.setAttribute("default_character_avatar", "");
		}
		// seesion operations
		String account_session = bnAccount.getId();
		String session_id = session.getId();
		String redis_name = session_id + "name";
		String redis_avatar = session_id + "avatar";
		String redis_acc = session_id + "bnAccount";
		if (redisService.exists(account_session)) {
			String tmpSessionId = (String) redisService.get(account_session);
			String tmp_redis_name = tmpSessionId + "name";
			String tmp_redis_avatar = tmpSessionId + "avatar";
			String tmp_redis_acc = tmpSessionId + "bnAccount";
			redisService.remove(account_session, tmpSessionId, tmp_redis_name, tmp_redis_avatar, tmp_redis_acc);
		}
		Long expireTime = (long) (3600 * 24 * 3);
		redisService.set(account_session, session_id, expireTime);
		redisService.set(session_id, token, expireTime);
		redisService.set(redis_name, session.getAttribute("default_character_name"), expireTime);
		redisService.set(redis_avatar, session.getAttribute("default_character_avatar"), expireTime);
		redisService.set(redis_acc, bnAccount, expireTime);
		session.setAttribute("bnaccount", bnAccount);
		model.addAttribute("characters", mlist);
		List<ItemLootView> loots = itemLootService.getPageItemLootView(0);
		model.addAttribute("loots", loots);
		return "index";
	}

	public static HttpSession getSession() {
		HttpSession session = null;
		try {
			session = getRequest().getSession();
		} catch (Exception e) {
		}
		return session;
	}

	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}
}
