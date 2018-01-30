package wow.roll.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wow.roll.dao.BnAccountRepository;
import wow.roll.domain.BnAccount;

@Service
public class BnAccountService {
	protected static Logger logger = LoggerFactory.getLogger(BnAccountService.class);

	@Autowired
	private BnAccountRepository bnAccountRp;

	public boolean insertBnAccount(BnAccount account) {
		try {
			BnAccount cacheAccount;
			cacheAccount = bnAccountRp.findBnAccountById(account.getBtid());
			if (cacheAccount == null) {
				bnAccountRp.save(account);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public BnAccount getBnAccountByName(String name) {
		return bnAccountRp.findBnAccountByName(name);
	}

	public BnAccount getBnAccountById(String id) {
		return bnAccountRp.findBnAccountById(id);
	}

	public boolean delBnAccount(BnAccount account) {
		try {
			account.setRemovetag(1);
			bnAccountRp.save(account);
		} catch (Exception e) {

			logger.error(e.toString());
			return false;
		}
		return true;
	}

	public boolean updateBnAccount(BnAccount account) {
		try {
			bnAccountRp.save(account);
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
		return true;
	}
}
