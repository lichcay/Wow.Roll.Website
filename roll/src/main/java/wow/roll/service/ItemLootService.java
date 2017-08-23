package wow.roll.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import wow.roll.dao.ItemLootRepository;
import wow.roll.dao.ItemLootViewRepository;
import wow.roll.domain.ItemLoot;
import wow.roll.domain.ItemLootView;

@Service
public class ItemLootService {
	protected static Logger logger = LoggerFactory.getLogger(ItemLootService.class);

	@Autowired
	private ItemLootRepository lootRp;

	@Autowired
	private ItemLootViewRepository lootVRp;

	public boolean insertItemLootList(List<ItemLoot> loots) {
		try {
			int length = loots.size();
			ItemLoot loot, cacheLoot;
			for (int i = 0; i < length; i++) {
				loot = loots.get(i);
				cacheLoot = lootRp.findItemLootByCharacternameAndLoottimestampAndItemidAndRemovetag(loot.getCharactername(), loot.getLoottimestamp(),
						loot.getItemid(), 0);
				if (cacheLoot == null) {
					lootRp.save(loot);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public ItemLoot getItemLootByName(String name) {
		return lootRp.findItemLootByName(name);
	}

	public List<ItemLoot> getAllItemLoot() {
		List<ItemLoot> lootlist = new ArrayList<ItemLoot>();
		lootRp.findByRemovetag(0).forEach(loot -> lootlist.add(loot));
		return lootlist;
	}

	public List<ItemLootView> getPageItemLootView(int page) {
		int size = 20;
		Sort sort = new Sort(Direction.DESC, "loottimestamp");
		Pageable pageable = new PageRequest(page, size, sort);
		Page<ItemLootView> lootlist = lootVRp.returnAllItemView(pageable);
		return lootlist.getContent();
	}

}
