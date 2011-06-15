package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verfügung. Sie ist quasi Eintrag der Datenbanktabelle Versicherungen. 
 * @author Georg Neufeld
 *
 */
public class Versicherungen extends DaoObject {

	/************************** Variablen *************************************/

	/**
	 * Art der Versicherung
	 */
	private String versArt;

	/**
	 * Name der Versicherungsgesellschaft
	 */
	private String versGesellschaft;

	/**
	 * Höhe der Versicherungssumme
	 */
	private double versSumme = -1.0D;

	/**
	 * Höhe des monatlichen Beitrags
	 */
	private double mtlBeitrag = -1.0D;

	/************************** Konstruktor *************************************/

	/**
	 * Standardkonstruktor
	 */
	public Versicherungen() {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	/**
	 * Kostruktor  um den Bezug zum Antragssteller zu ermöglichen
	 * @param idAntragssteller
	 */
	public Versicherungen(int idAntragssteller) {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
		setReferenzIds(new int[] { idAntragssteller });
	}

	/**
	 * Konstruktor um ein vollständig gefülltes Versicherungs-Objekt zu erhalten
	 * @param versArt
	 * @param versGesellschaft
	 * @param versSumme
	 * @param mtlBeitrag
	 */
	public Versicherungen(String versArt, String versGesellschaft, double versSumme, double mtlBeitrag) {
		super();
		this.versArt = versArt;
		this.versGesellschaft = versGesellschaft;
		this.versSumme = versSumme;
		this.mtlBeitrag = mtlBeitrag;
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	/************************* Getter/Setter ************************************/

	public String getVersArt() {
		return versArt;
	}

	public void setVersArt(String versArt) {
		this.versArt = versArt;
	}

	public String getVersGesellschaft() {
		return versGesellschaft;
	}

	public void setVersGesellschaft(String versGesellschaft) {
		this.versGesellschaft = versGesellschaft;
	}

	public double getVersSumme() {
		return versSumme;
	}

	public void setVersSumme(double versSumme) {
		this.versSumme = versSumme;
	}

	public double getMtlBeitrag() {
		return mtlBeitrag;
	}

	public void setMtlBeitrag(double mtlBeitrag) {
		this.mtlBeitrag = mtlBeitrag;
	}

}
