package de.axisbank.services;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import junit.extensions.RepeatedTest;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class AllServiceTests {
	private static Logger l = Logger.getRootLogger();

	public Test suite() {

		TestSuite suite = new TestSuite(AllServiceTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(de.axisbank.services.filiale.remoteTests.FilialBankServiceTest.class);
		// $JUnit-END$
		return new RepeatedTest(suite, 2);
	}

	public static void main(String[] args) {
		l.addAppender(new ConsoleAppender(new SimpleLayout()));
		l.setLevel(Level.WARN);
		for (int i = 0; i < 25; i++) {
			new Thread() {
				@Override
				public void run() {
					super.run();
					TestResult trs = new TestResult();
					new AllServiceTests().suite().run(trs);
				}
			}.start();
		}

	}

}
