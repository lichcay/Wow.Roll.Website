package wow.roll.util;

import java.util.ArrayList;
import java.util.List;

public class EpgpUtils {
	public static List<String[]> EpgpImportHelp(String epgpString) {
		List<String[]> epgpList = new ArrayList<String[]>();
		epgpString = epgpString.substring(2, epgpString.length() - 2);
		String[] epgpArray = epgpString.split("\\],\\[");
		String tempEpgp = "";
		for (int i = 0; i < epgpArray.length; i++) {
			tempEpgp = epgpArray[i];
			String tempEpgpArray[] = tempEpgp.substring(1, tempEpgp.length()).split(",");
			tempEpgpArray[0] = tempEpgpArray[0].substring(0, tempEpgpArray[0].length() - 15);
			epgpList.add(tempEpgpArray);
		}
		return epgpList;
	}

}
