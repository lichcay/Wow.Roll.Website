package wow.roll.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wow.roll.domain.BnAccount;

@Repository
@Table(name = "roll_bnaccount")
@Qualifier("bnAccountRepository")
public interface BnAccountRepository extends CrudRepository<BnAccount, String> {
	@Query("select t from BnAccount t where t.btid=:id and t.removetag=0")
	public BnAccount findBnAccountById(@Param("id") String id);

	@Query("select t from BnAccount t where t.battletag=:name and t.removetag=0")
	public BnAccount findBnAccountByName(@Param("name") String name);

}
