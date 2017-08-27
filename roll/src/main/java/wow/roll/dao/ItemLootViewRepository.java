package wow.roll.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wow.roll.domain.ItemLootView;

@Repository
@Qualifier("itemlootviewRepository")
public interface ItemLootViewRepository extends CrudRepository<ItemLootView, String> {
	@Query("select new wow.roll.domain.ItemLootView(t.id,t.charactername,m.cclass,t.loottimestamp,t.itemid,t.context,t.bonuslists) from ItemLoot t,Member m where t.charactername=m.name and t.removetag=0 and m.removetag=0 group by t.id order by t.loottimestamp desc")
	public Page<ItemLootView> returnAllItemView(Pageable pageable);
}
