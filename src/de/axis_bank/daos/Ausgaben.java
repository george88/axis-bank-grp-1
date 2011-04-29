package de.axis_bank.daos;

public class Ausgaben extends DaoObject {

	private String art;

	private double betrag;

	public Ausgaben() {

	}

	public Ausgaben(String art, double betrag) {
		super();
		this.art = art;
		this.betrag = betrag;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public double getBetrag() {
		return betrag;
	}

	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}

}
