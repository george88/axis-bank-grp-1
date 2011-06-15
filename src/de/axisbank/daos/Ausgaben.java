package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verf�gung. Sie ist quasi Eintrag der Datenbanktabelle Ausgaben 
 * @author Georg Neufeld
 *
 */
public class Ausgaben extends DaoObject {

	/************************** Variablen *************************************/

	/**
	 * Die Art der Ausgabe
	 */
	private String art;

	/**
	 * Die H�he der Ausgabe
	 */
	private double betrag = -1.0D;

	/************************** Konstruktor *************************************/
	/**
	 * Standardkonstruktor
	 */
	public Ausgaben() {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	/**
	 * Konstruktor um die Zugeh�rigkeit der Ausgabe zu einem Antragssteller zuzuordnen
	 * @param idAusgaben
	 */
	public Ausgaben(int idAntragssteller) {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
		setReferenzIds(new int[] { idAntragssteller });
	}

	/**
	 * Konstruktor f�r ein vollst�ndiges Ausgaben-Objekt
	 * @param art
	 * @param betrag
	 */
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
