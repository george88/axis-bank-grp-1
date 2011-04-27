package de.axis_bank.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.axis_bank.services.web.WebBankService;

public class FilialBankServiceTest {

	@Test
	public void testGetLiquidity() {
		WebBankService service = new WebBankService();
		service.login("test", "test");
		assertEquals("testGetLiquidity", 2.5, service.getLiquiditaet(), 2.5);
	}

	@Test
	public void testGetRepaymentPlan() {
		// fail("Not yet implemented");
	}

	@Test
	public void login() {
		WebBankService service = new WebBankService();
		assertTrue("login", service.login("test", "test"));
	}

}
