package wow.roll.listener;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import wow.roll.config.PropertiesListenerConfig;

public class PropertiesListener implements ApplicationListener<ApplicationStartingEvent> {
	private String propertyFileName;

	public PropertiesListener(String propertyFileName) {
		this.propertyFileName = propertyFileName;
	}

	@Override
	public void onApplicationEvent(ApplicationStartingEvent event) {
		PropertiesListenerConfig.loadAllProperties(propertyFileName);
	}
}
