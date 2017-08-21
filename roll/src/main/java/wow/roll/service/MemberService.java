package wow.roll.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wow.roll.dao.MemberRepository;
import wow.roll.domain.Member;

@Service
public class MemberService {
	protected static Logger logger = LoggerFactory.getLogger(MemberService.class);

	@Autowired
	private MemberRepository memberRp;

	public boolean insertMemberList(List<Member> member) {
		try {
			int length = member.size();
			Member mb, cacheMb;
			for (int i = 0; i < length; i++) {
				mb = member.get(i);
				cacheMb = memberRp.findMemberByName(mb.getName());
				if (cacheMb == null) {
					memberRp.save(mb);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public Member getMemberByName(String name) {
		return memberRp.findMemberByName(name);
	}

	public List<Member> getAllMember() {
		List<Member> mlist = new ArrayList<Member>();
		memberRp.findAll().forEach(member -> mlist.add(member));
		return mlist;
	}

	public boolean delMembers(List<Member> memberList) {
		try {
			memberRp.delete(memberList);
		} catch (Exception e) {

			logger.error(e.toString());
			return false;
		}
		return true;
	}
}
