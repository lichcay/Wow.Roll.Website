package wow.roll.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import wow.roll.domain.BnAccount;
import wow.roll.service.RedisService;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private RedisService redisService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object obj) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		String session_id = session.getId();
		if (redisService.exists(session_id)) {
			session.setAttribute("isGuest", "false");
			String redis_name = session_id + "name";
			String redis_avatar = session_id + "avatar";
			String redis_acc = session_id + "bnAccount";
			String name = (String) redisService.get(redis_name);
			String avatar = (String) redisService.get(redis_avatar);
			BnAccount bnAccount = (BnAccount) redisService.get(redis_acc);
			session.setAttribute("bnaccount", bnAccount);
			session.setAttribute("default_character_name", name);
			session.setAttribute("default_character_avatar", avatar);
		} else {
			session.setAttribute("isGuest", "true");
			BnAccount tmp = new BnAccount();
			tmp.setBattletag("");
			session.setAttribute("bnaccount", tmp);
			session.setAttribute("default_character_name", "");
			session.setAttribute("default_character_avatar", "");
		}
		return true;
	}

}
