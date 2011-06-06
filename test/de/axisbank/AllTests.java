package de.axisbank;

import junit.extensions.RepeatedTest;
import junit.framework.Test;
import junit.framework.TestSuite;
import de.axisbank.services.filiale.direktTests.FilialBankServiceTest;
import de.axisbank.services.web.WebBankServiceTest;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(WebBankServiceTest.class);
		suite.addTestSuite(FilialBankServiceTest.class);

		// $JUnit-END$
		return new RepeatedTest(suite, 5);
	}
}
