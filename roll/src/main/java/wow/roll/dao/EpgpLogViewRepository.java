package wow.roll.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wow.roll.domain.EpgpLogView;

@Repository
@Qualifier("epgpViewRepository")
public interface EpgpLogViewRepository extends CrudRepository<EpgpLogView, String> {
	@Query("select new wow.roll.domain.EpgpLogView(m.cclass, m.name, t.type, t.reason, t.gear, t.amount, t.timestamp) from EpgpLog t, Member m where t.memberid=m.id and m.rank<=:rank and t.removetag=0 and m.removetag=0 group by t.id order by t.timestamp desc")
	public Page<EpgpLogView> findAllEpgpLogView(@Param("rank") int rank, Pageable pageable);

	@Query("select new wow.roll.domain.EpgpLogView(m.cclass, m.name, t.type, t.reason, t.gear, t.amount, t.timestamp) from EpgpLog t, Member m where t.memberid=m.id and m.rank<=:rank and m.name=:name and t.removetag=0 and m.removetag=0 group by t.id order by t.timestamp desc")
	public Page<EpgpLogView> findEpgpLogViewByName(@Param("rank") int rank, @Param("name") String name, Pageable pageable);

}
