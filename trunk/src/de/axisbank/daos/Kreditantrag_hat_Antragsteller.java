package de.axisbank.daos;

public class Kreditantrag_hat_Antragsteller extends DaoObject {

	private String verhaeltnis;

	public Kreditantrag_hat_Antragsteller() {

	}

	public Kreditantrag_hat_Antragsteller(String verhaeltnis) {
		super();
		this.verhaeltnis = verhaeltnis;
	}

	public void setVerhaeltnis(String verhaeltnis) {
		this.verhaeltnis = verhaeltnis;
	}

	public String getVerhaeltnis() {
		return verhaeltnis;
	}
}
