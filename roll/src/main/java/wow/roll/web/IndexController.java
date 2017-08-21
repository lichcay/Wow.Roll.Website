package wow.roll.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import wow.roll.domain.Member;
import wow.roll.service.MemberService;
import wow.roll.util.BattleNetApi;

@Controller
@EnableAutoConfiguration
public class IndexController {

	protected static Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private MemberService memberService;

	@RequestMapping("/")
	String home() {
		logger.debug("Start connect battlenet API...");
		List<Member> members = BattleNetApi.getGuildMember();
		logger.debug("Got members, analysing...");
		if (memberService.insertMemberList(members)) {
			logger.debug("Successfully inserted guild members.");
		}
		List<Member> memberToDel = memberService.getAllMember();
		memberToDel.removeAll(members);
		if (memberToDel.size() != 0) {
			memberService.delMembers(memberToDel);
		}
		return "Hello World!!!";
	}

}
