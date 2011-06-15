package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verfügung. Sie ist quasi Eintrag der Datenbanktabelle Einnahmen. 
 * @author Georg Neufeld
 *
 */
public class Einnahmen extends DaoObject {

	/************************** Variablen *************************************/

	/**
	 * Der Art der Einnahme
	 */
	private String art;

	/**
	 * Die Höhe der Einnahmes
	 */
	private double betrag = -1.0D;

	/************************** Konstruktor *************************************/
	/**
	 * Konstruktor um ein vollständiges Einnahmen-Objekt zu erstellen
	 */
	public Einnahmen(String art, double betrag) {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
		this.art = art;
		this.betrag = betrag;
	}

	/**
	 * Standardkonstruktor
	 */
	public Einnahmen() {
		super();
		//Name des Attributs zur Referenztabelle Antragsteller --> Ein Arbeitgeber gehört zu genau einem Antragssteller
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	public Einnahmen(int idAntragssteller) {
		super();
		//Name des Attributs zur Referenztabelle Antragsteller --> Ein Arbeitgeber gehört zu genau einem Antragssteller
		setReferenzIdNames(new String[] { "idAntragssteller" });
		//Identifikationsnummer des Attributs zur Referenztabelle
		setReferenzIds(new int[] { idAntragssteller });
	}

	/************************* Getter/Setter ************************************/
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
