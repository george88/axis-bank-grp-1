package de.axis_bank;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BankServiceTest {

	@Test
	public void testGetLiquidity() {
		BankService service = new BankService();
		assertEquals("", 2.5, service.getLiquidity(), 2.5);

	}

	@Test
	public void testGetRepaymentPlan() {
		// fail("Not yet implemented");
	}

}
