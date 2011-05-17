package de.axisbank.daos;

public class Einnahmen extends DaoObject {

	private String art;

	private double betrag = -1.0D;

	public Einnahmen(String art, double betrag) {
		super();
		this.art = art;
		this.betrag = betrag;
	}

	public Einnahmen() {
		super();
		setReferenzIdName("idAntragssteller");
	}

	public Einnahmen(int idAntragssteller) {
		super();
		setReferenzIdName("idAntragssteller");
		setReferenzId(idAntragssteller);
	}

	public void setArt(String art) {
		this.art = art;
	}

	public String getArt() {
		return art;
	}

	public double getBetrag() {
		return betrag;
	}

	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}

}