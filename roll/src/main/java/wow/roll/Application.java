package wow.roll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		// application.addListeners(new PropertiesListener("bnapi.properties"));
		application.setAdditionalProfiles("general.properties");
		application.run(args);
	}

}
