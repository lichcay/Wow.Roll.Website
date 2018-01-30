package wow.roll.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wow.roll.domain.EpgpLogView;
import wow.roll.domain.EpgpLogViewShow;

public class ItemUtils {
	private static Map<String, Object> ItemStringHelp(String itemString) {
		String[] itemInfoStr = itemString.split(":");
		Map<String, Object> itemInfo = new HashMap<String, Object>();
		itemInfo.put("itemID", itemInfoStr[0]);
		// itemInfo.put("enchantID", itemInfoStr[1]);
		// itemInfo.put("gemID1", itemInfoStr[2]);
		// itemInfo.put("gemID2", itemInfoStr[3]);
		// itemInfo.put("gemID3", itemInfoStr[4]);
		// itemInfo.put("gemID4", itemInfoStr[5]);
		// itemInfo.put("suffixID", itemInfoStr[6]);
		// itemInfo.put("uniqueID", itemInfoStr[7]);
		// itemInfo.put("linkLevel", itemInfoStr[8]);
		// itemInfo.put("specializationID", itemInfoStr[9]);
		// itemInfo.put("upgradeTypeID", itemInfoStr[10]);
		// itemInfo.put("instanceDifficultyID", itemInfoStr[11]);
		// itemInfo.put("numBonusIDs", itemInfoStr[12]);
		String numBonusIDstr = itemInfoStr[12];
		if (StringUtils.isNotEmpty(numBonusIDstr)) {
			int numBonusIDs = Integer.parseInt(numBonusIDstr);
			String[] bonusIDs = new String[numBonusIDs];
			for (int i = 13; i < 13 + numBonusIDs; i++) {
				bonusIDs[i - 13] = itemInfoStr[i];
			}
			itemInfo.put("bonusIDs", bonusIDs);
		}
		return itemInfo;
	}

	public static List<EpgpLogViewShow> transEpgpLogViewToShow(List<EpgpLogView> epgpLogView) {
		List<EpgpLogViewShow> epgpLogViewToShow = new ArrayList<EpgpLogViewShow>();
		for (int i = 0; i < epgpLogView.size(); i++) {
			String type = epgpLogView.get(i).getType();
			if (StringUtils.equals(type, "GP")) {
				String itemStringRaw = epgpLogView.get(i).getGear().split("\\|H")[1].split("\\|h")[0];
				itemStringRaw = itemStringRaw.substring(5, itemStringRaw.length());
				Map<String, Object> itemInfo = ItemStringHelp(itemStringRaw);
				String itemId = (String) itemInfo.get("itemID");
				String[] bonusIDs = (String[]) itemInfo.get("bonusIDs");
				String bonusID = "";
				for (int j = 0; j < bonusIDs.length; j++) {
					bonusID = bonusID + bonusIDs[j] + ",";
				}
				bonusID = "[" + bonusID;
				bonusID = bonusID.substring(0, bonusID.length() - 1) + "]";
				EpgpLogViewShow epgpLogViewShow = new EpgpLogViewShow();
				epgpLogViewShow.setAmount(epgpLogView.get(i).getAmount());
				epgpLogViewShow.setBonusids(bonusID);
				epgpLogViewShow.setCclass(epgpLogView.get(i).getCclass());
				epgpLogViewShow.setCharactername(epgpLogView.get(i).getCharactername());
				epgpLogViewShow.setItemid(itemId);
				epgpLogViewShow.setReason(epgpLogView.get(i).getReason());
				epgpLogViewShow.setTimestamp(epgpLogView.get(i).getTimestamp());
				epgpLogViewShow.setType(type);
				epgpLogViewToShow.add(epgpLogViewShow);
			} else {
				EpgpLogViewShow epgpLogViewShow = new EpgpLogViewShow();
				epgpLogViewShow.setAmount(epgpLogView.get(i).getAmount());
				epgpLogViewShow.setBonusids("");
				epgpLogViewShow.setCclass(epgpLogView.get(i).getCclass());
				epgpLogViewShow.setCharactername(epgpLogView.get(i).getCharactername());
				epgpLogViewShow.setItemid("");
				epgpLogViewShow.setReason(epgpLogView.get(i).getReason());
				epgpLogViewShow.setTimestamp(epgpLogView.get(i).getTimestamp());
				epgpLogViewShow.setType(type);
				epgpLogViewToShow.add(epgpLogViewShow);
			}

		}
		return epgpLogViewToShow;
	}

}
