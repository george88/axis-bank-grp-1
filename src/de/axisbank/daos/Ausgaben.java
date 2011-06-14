package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verfügung. Sie ist quasi Eintrag der Datenbanktabelle Ausgaben 
 * @author Georg Neufeld
 *
 */
public class Ausgaben extends DaoObject {

	/************************** Variablen *************************************/
	private String art;

	private double betrag = -1.0D;

	/************************** Konstruktor *************************************/
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

	/************************* Getter/Setter ************************************/
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
