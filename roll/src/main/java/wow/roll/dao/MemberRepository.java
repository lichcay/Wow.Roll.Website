package wow.roll.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wow.roll.domain.Member;

@Repository
@Table(name = "roll_member")
@Qualifier("memberRepository")
public interface MemberRepository extends CrudRepository<Member, String> {
	@Query("select t from Member t where t.name=:name")
	public Member findMemberByName(@Param("name") String name);

}
