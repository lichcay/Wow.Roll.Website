package wow.roll.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class TestController {

	protected static Logger logger = LoggerFactory.getLogger(TestController.class);

	/*
	 * @RequestMapping("/") String home() {
	 * 
	 * logger.debug("Connecting with battlenet API。。。"); return "Hello World!";
	 * }
	 */

	@RequestMapping("/hello/{myName}")
	String index(@PathVariable String myName, Model model) {
		logger.debug("access helloName,Name={}", myName);
		model.addAttribute("myName", myName);
		return "index";
	}
}
