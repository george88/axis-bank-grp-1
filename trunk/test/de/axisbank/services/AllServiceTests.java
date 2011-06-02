package de.axisbank.services;

import junit.extensions.RepeatedTest;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class AllServiceTests {

	public Test suite() {
		TestSuite suite = new TestSuite(AllServiceTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(de.axisbank.services.filiale.FilialBankServiceSessionTest.class);
		// $JUnit-END$
		return new RepeatedTest(suite, 1);
	}

	public static void main(String[] args) {

		for (int i = 0; i < 2; i++) {
			new Thread() {
				@Override
				public void run() {
					super.run();
					new AllServiceTests().suite().run(new TestResult());
				}
			}.start();
		}

	}

}
