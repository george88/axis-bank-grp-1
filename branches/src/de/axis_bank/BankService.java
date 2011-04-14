package de.axis_bank;

public class BankService {

	public final static int Liqui_1 = 1;
	public final static int Liqui_2 = 2;
	public final static int Liqui_3 = 3;
	public final static int Liqui_4 = 4;
	public final static int Liqui_5 = 5;

	public int getLiquidity() {

		return Liqui_1;
	}

	public RepaymentPlan getRepaymentPlan() {

		return new RepaymentPlan();
	}

}
