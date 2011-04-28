package de.axis_bank.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.axis_bank.services.filiale.FilialBankService;

public class FilialBankServiceTest {

	@Test
	public void testGetLiquidity() {
		FilialBankService service = new FilialBankService();
		service.login("test", "test");
		assertEquals("testGetLiquidity", 2.5, service.getLiquiditaet(), 2.5);
	}

	@Test
	public void testGetRepaymentPlan() {
		// fail("Not yet implemented");
	}

	@Test
	public void login() {
		FilialBankService service = new FilialBankService();
		assertTrue("login", service.login("test", "test"));
	}

}
