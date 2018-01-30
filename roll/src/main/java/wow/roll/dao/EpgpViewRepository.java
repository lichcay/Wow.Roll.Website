package wow.roll.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wow.roll.domain.EpgpView;

@Repository
@Qualifier("epgpViewRepository")
public interface EpgpViewRepository extends CrudRepository<EpgpView, String> {
	@Query("select new wow.roll.domain.EpgpView(m.cclass, t.charactername, t.ep, t.gp, t.pr) from Epgp t, Member m where t.charactername=m.name and m.rank<=:rank and t.removetag=0 and m.removetag=0 group by t.id order by t.pr desc")
	public Iterable<EpgpView> findAllEpgpView(@Param("rank") int rank);

}
