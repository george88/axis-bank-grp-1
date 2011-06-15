package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verfügung. Sie ist quasi Eintrag der Datenbanktabelle Arbeitgeber 
 * @author Georg Neufeld
 *
 */
public class Arbeitgeber extends DaoObject {

	/************************** Variablen *************************************/

	/**
	 * Name des Arbeitgebers
	 */
	private String nameArbeitgeber;

	/**
	 * beschäftigt seit tt.mm.yyyy
	 */
	private String beschSeit;

	/**
	 * Strasse des Arbeitgebers
	 */
	private String strArbeitgeber;

	/**
	 * Hausnummer des Arbeitgebers
	 */
	private String hnrArbeitgeber;

	/**
	 * Postleitzahl des Arbeitgebers
	 */
	private String plzArbeitgeber;

	/**
	 * Ort des Arbeitgebers
	 */
	private String ortArbeitgeber;

	/************************** Konstruktor *************************************/
	/**
	 * Standardkonstruktor
	 */
	public Arbeitgeber() {
		super();
		//Name des Attributs zur Referenztabelle Antragsteller --> Ein Arbeitgeber gehört zu genau einem Antragssteller
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	/**
	 * Konstruktor um die Zuordnung zum Antragssteller zu erreichen
	 * @param idArbeitgeber
	 */
	public Arbeitgeber(int idAntragssteller) {
		super();
		//Name des Attributs zur Referenztabelle Antragsteller --> Ein Arbeitgeber gehört zu genau einem Antragssteller
		setReferenzIdNames(new String[] { "idAntragssteller" });
		//Identifikationsnummer des Attributs zur Referenztabelle
		setReferenzIds(new int[] { idAntragssteller });
	}

	/**
	 * Konstruktor für einen vollständigen Arbeitgeber
	 * @param nameArbeitgeber
	 * @param beschSeit
	 * @param strArbeitgeber
	 * @param hnrArbeitgeber
	 * @param plzArbeitgeber
	 * @param ortArbeitgeber
	 */
	public Arbeitgeber(String nameArbeitgeber, String beschSeit, String strArbeitgeber, String hnrArbeitgeber, String plzArbeitgeber, String ortArbeitgeber) {
		super();
		this.nameArbeitgeber = nameArbeitgeber;
		this.beschSeit = beschSeit;
		this.strArbeitgeber = strArbeitgeber;
		this.hnrArbeitgeber = hnrArbeitgeber;
		this.plzArbeitgeber = plzArbeitgeber;
		this.ortArbeitgeber = ortArbeitgeber;
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	/************************* Getter/Setter ************************************/

	public String getNameArbeitgeber() {
		return nameArbeitgeber;
	}

	public void setNameArbeitgeber(String nameArbeitgeber) {
		this.nameArbeitgeber = nameArbeitgeber;
	}

	public String getBeschSeit_dt() {
		return beschSeit;
	}

	public void setBeschSeit_dt(String beschSeit) {
		this.beschSeit = beschSeit;
	}

	public String getStrArbeitgeber() {
		return strArbeitgeber;
	}

	public void setStrArbeitgeber(String strArbeitgeber) {
		this.strArbeitgeber = strArbeitgeber;
	}

	public String getHnrArbeitgeber() {
		return hnrArbeitgeber;
	}

	public void setHnrArbeitgeber(String hnrArbeitgeber) {
		this.hnrArbeitgeber = hnrArbeitgeber;
	}

	public String getPlzArbeitgeber() {
		return plzArbeitgeber;
	}

	public void setPlzArbeitgeber(String plzArbeitgeber) {
		this.plzArbeitgeber = plzArbeitgeber;
	}

	public String getOrtArbeitgeber() {
		return ortArbeitgeber;
	}

	public void setOrtArbeitgeber(String ortArbeitgeber) {
		this.ortArbeitgeber = ortArbeitgeber;
	}
}
