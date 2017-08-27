package wow.roll.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wow.roll.domain.Epgp;

@Repository
@Table(name = "roll_epgp")
@Qualifier("epgpRepository")
public interface EpgpRepository extends CrudRepository<Epgp, String> {
	@Query("select t from Epgp t where t.charactername=:name and t.removetag=0")
	public Epgp findEpgpByCharacter(@Param("name") String name);

	public Iterable<Epgp> findByRemovetagOrderByPrDesc(int tag);

	@Query("select t from Epgp t where t.removetag=0 order by t.pr desc")
	public Page<Epgp> returnAllEpgpPage(Pageable pageable);
}
