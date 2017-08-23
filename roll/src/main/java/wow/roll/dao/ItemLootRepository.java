package wow.roll.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wow.roll.domain.ItemLoot;

@Repository
@Table(name = "roll_itemloots")
@Qualifier("itemlootRepository")
public interface ItemLootRepository extends CrudRepository<ItemLoot, String> {
	@Query("select t from ItemLoot t where t.charactername=:name and removetag=0")
	public ItemLoot findItemLootByName(@Param("name") String name);

	public ItemLoot findItemLootByCharacternameAndLoottimestampAndItemidAndRemovetag(String name, String stamp, String id, int tag);

	public Iterable<ItemLoot> findByRemovetag(int tag);
}
