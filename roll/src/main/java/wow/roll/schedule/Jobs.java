package wow.roll.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import wow.roll.util.DataUtils;

@Component
public class Jobs {
	public final static long FVE_Minute = 5 * 60 * 1000;

	@Autowired
	private DataUtils dataUtils;

	@Scheduled(fixedDelay = FVE_Minute)
	public void fixedDelayJob() {
		dataUtils.refreshdata();
	}

}
