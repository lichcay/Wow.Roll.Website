package wow.roll.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import wow.roll.dao.EpgpLogRepository;
import wow.roll.dao.EpgpLogViewRepository;
import wow.roll.domain.EpgpLog;
import wow.roll.domain.EpgpLogView;

@Service
public class EpgpLogService {
	protected static Logger logger = LoggerFactory.getLogger(EpgpLogService.class);

	@Autowired
	private EpgpLogRepository epgpLogRp;

	@Autowired
	private EpgpLogViewRepository epgpLogViewRp;

	public boolean insertEpgpLogList(List<EpgpLog> epgpList) {
		try {
			int length = epgpList.size();
			EpgpLog epgp, cacheEpgp;
			for (int i = 0; i < length; i++) {
				epgp = epgpList.get(i);
				cacheEpgp = epgpLogRp.findEpgpLogByMemberIdAndTimestamp(epgp.getMemberid(), epgp.getTimestamp());
				if (cacheEpgp == null) {
					epgpLogRp.save(epgp);
				} else if (!cacheEpgp.equals(epgp)) {
					epgpLogRp.save(epgp);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public List<EpgpLog> getAllEpgp() {
		List<EpgpLog> epgplist = new ArrayList<EpgpLog>();
		epgpLogRp.findAll().forEach(epgp -> epgplist.add(epgp));
		return epgplist;
	}

	public List<EpgpLog> getPageEpgp(int page) {
		int size = 20;
		Pageable pageable = new PageRequest(page, size);
		Page<EpgpLog> epgpPage = epgpLogRp.returnAllEpgpPage(pageable);
		return epgpPage.getContent();
	}

	public List<EpgpLogView> getAllEpgpView(int rank, int page) {
		int size = 20;
		Pageable pageable = new PageRequest(page, size);
		Page<EpgpLogView> epgpPage = epgpLogViewRp.findAllEpgpLogView(rank, pageable);
		return epgpPage.getContent();
	}

	public List<EpgpLogView> getEpgpViewPageByName(int rank, int page, String name) {
		int size = 20;
		Pageable pageable = new PageRequest(page, size);
		Page<EpgpLogView> epgpPage = epgpLogViewRp.findEpgpLogViewByName(rank, name, pageable);
		return epgpPage.getContent();
	}
}
