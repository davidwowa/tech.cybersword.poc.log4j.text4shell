package tech.cybersword.poc.log4jtext4shell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LogManager.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		logger.info("started....");
	}
	
	public static byte[] test(int start, int end) {
		return getCriticalUserData(end);
	}
	
	public static byte[] getCriticalUserData(int end) {
		// load critical data .... 
		return new byte[end];
	}
}