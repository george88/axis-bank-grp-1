package de.axis_bank.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.axis_bank.services.BankService;

public class BankServiceTest {

	@Test
	public void testGetLiquidity() {
		BankService service = new BankService();
		service.login("test", "test");
		assertEquals("testGetLiquidity", 2.5, service.getLiquiditaet(), 2.5);
	}

	@Test
	public void testGetRepaymentPlan() {
		// fail("Not yet implemented");
	}

	@Test
	public void login() {
		BankService service = new BankService();
		assertTrue("login", service.login("test", "test"));
	}

}
