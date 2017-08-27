package wow.roll.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import wow.roll.dao.EpgpRepository;
import wow.roll.dao.EpgpViewRepository;
import wow.roll.domain.Epgp;
import wow.roll.domain.EpgpView;

@Service
public class EpgpService {
	protected static Logger logger = LoggerFactory.getLogger(EpgpService.class);

	@Autowired
	private EpgpRepository epgpRp;

	@Autowired
	private EpgpViewRepository epgpViewRp;

	public boolean insertEpgpList(List<Epgp> epgpList) {
		try {
			int length = epgpList.size();
			Epgp epgp, cacheEpgp;
			for (int i = 0; i < length; i++) {
				epgp = epgpList.get(i);
				cacheEpgp = epgpRp.findEpgpByCharacter(epgp.getCharactername());
				if (cacheEpgp == null) {
					epgpRp.save(epgp);
				} else {
					cacheEpgp.setEp(epgp.getEp());
					cacheEpgp.setGp(epgp.getGp());
					cacheEpgp.setPr(epgp.getPr());
					cacheEpgp.setEdittime((new Date()).toString());
					epgpRp.save(cacheEpgp);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public Epgp getEpgpByName(String name) {
		return epgpRp.findEpgpByCharacter(name);
	}

	public List<Epgp> getAllEpgp() {
		List<Epgp> epgplist = new ArrayList<Epgp>();
		epgpRp.findByRemovetagOrderByPrDesc(0).forEach(epgp -> epgplist.add(epgp));
		return epgplist;
	}

	public List<Epgp> getPageEpgp(int page) {
		int size = 20;
		Pageable pageable = new PageRequest(page, size);
		Page<Epgp> epgpPage = epgpRp.returnAllEpgpPage(pageable);
		return epgpPage.getContent();
	}

	public List<EpgpView> getAllEpgpView() {
		List<EpgpView> epgplist = new ArrayList<EpgpView>();
		epgpViewRp.findAllEpgpView().forEach(epgp -> epgplist.add(epgp));
		return epgplist;
	}
}
