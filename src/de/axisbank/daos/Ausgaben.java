package de.axisbank.daos;

public class Ausgaben extends DaoObject {

	private String art;

	private double betrag = -1.0D;

	public Ausgaben() {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	public Ausgaben(int idAusgaben) {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
		setReferenzIds(new int[] { idAusgaben });
	}

	public Ausgaben(String art, double betrag) {
		super();
		this.art = art;
		this.betrag = betrag;
		setReferenzIdNames(new String[] { "idAntragssteller" });
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
