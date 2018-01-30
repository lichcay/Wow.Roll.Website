package wow.roll.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wow.roll.domain.EpgpLog;

@Repository
@Table(name = "roll_epgplog")
@Qualifier("epgplogRepository")
public interface EpgpLogRepository extends CrudRepository<EpgpLog, String> {
	@Query("select t from EpgpLog t where t.removetag=0 order by t.timestamp desc")
	public Page<EpgpLog> returnAllEpgpPage(Pageable pageable);

	@Query("select t from EpgpLog t where t.memberid =:id and t.removetag=0 order by t.timestamp desc")
	public Page<EpgpLog> findEpgpPageByMemberId(@Param("id") String id, Pageable pageable);

	@Query("select t from EpgpLog t where t.memberid =:id and t.timestamp = :timestamp and t.removetag=0")
	public EpgpLog findEpgpLogByMemberIdAndTimestamp(@Param("id") String id, @Param("timestamp") String timestamp);
}
