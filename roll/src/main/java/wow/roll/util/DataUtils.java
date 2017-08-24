package wow.roll.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wow.roll.domain.ItemLoot;
import wow.roll.domain.Member;
import wow.roll.service.ItemLootService;
import wow.roll.service.MemberService;

@Component
public class DataUtils {
	@Autowired
	private MemberService memberService;

	@Autowired
	private ItemLootService itemLootService;

	protected Logger logger = LoggerFactory.getLogger(MemberService.class);

	public void refreshdata() {
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
	}
}
