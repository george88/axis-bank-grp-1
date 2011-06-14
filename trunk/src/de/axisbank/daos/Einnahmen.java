package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verf�gung. Sie ist quasi Eintrag der Datenbanktabelle Einnahmen. 
 * @author Georg Neufeld
 *
 */
public class Einnahmen extends DaoObject {

	private String art;

	private double betrag = -1.0D;

	public Einnahmen(String art, double betrag) {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
		this.art = art;
		this.betrag = betrag;
	}

	public Einnahmen() {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	public Einnahmen(int idAntragssteller) {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
		setReferenzIds(new int[] { idAntragssteller });
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
