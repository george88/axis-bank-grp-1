package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verfügung. Sie ist quasi Eintrag der Datenbanktabelle Kreditantrag. 
 * @author Georg Neufeld
 *
 */
public class Kreditantrag extends DaoObject {

	/************************** Variablen *************************************/

	/**
	 * Das User-Objekt bzw. der Berater zum Kreditantrag
	 */
	private User berater;

	/**
	 * die Identfikationsnummer des Kreditberaters
	 */
	private int idUser = -1;

	/**
	 * Das Datum des Kreditantrages tt.mm.yyyy
	 */
	private String datum;

	/**
	 * Der Status des Kreditantrages
	 */
	private String status;

	/**
	 * Der Name der Filiale, in der der Kreditantrag erstellt wurde
	 */
	private String filiale;

	/**
	 * Die Kredithöhe
	 */
	private double kreditWunsch = -1.0D;

	/**
	 * Die Höhe einer Rate pro Monat
	 */
	private double ratenHoehe = -1.0D;

	/**
	 * Die Anzahl der Raten
	 */
	private int ratenAnzahl = -1;

	/**
	 * Die Identifikationsnummer des zweiten Antragssteller
	 */
	private int idAntragssteller_2 = -1;

	/**
	 * Das Objekt des zweiten Antragssteller
	 */
	private Antragssteller antragssteller_2;

	/**
	 * Die Beschreibung des Verhältnis vom ersten Antragsteller zum zweiten
	 */
	private String verhaeltnisZu_2;

	/************************** Konstruktor *************************************/

	/**
	 * Standardkonstruktor
	 */
	public Kreditantrag() {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller", "idUser" });
	}

	/**
	 * Konstruktor um die Identifikationsnummer des zugehörigen Antragssteller zu setzen
	 * @param idAntragssteller
	 */
	public Kreditantrag(int idAntragssteller) {
		super();
		//Name der Identifikationsattribute der Referenztabeller
		setReferenzIdNames(new String[] { "idAntragssteller", "idUser" });
		//Identifikation setzen zur ersten Referenztabelle
		setReferenzIds(new int[] { idAntragssteller });
	}

	/**
	 * Konstruktor um ein vollständiges Kreditantrag-Objekt zu erstellen 
	 * @param berater
	 * @param datum
	 * @param status
	 * @param filiale
	 * @param kreditWunsch
	 * @param ratenHoehe
	 * @param ratenAnzahl
	 * @param antragssteller_2
	 * @param verhaeltnisZu_2
	 */
	public Kreditantrag(User berater, String datum, String status, String filiale, double kreditWunsch, double ratenHoehe, int ratenAnzahl, Antragssteller antragssteller_2, String verhaeltnisZu_2) {
		super();
		this.berater = berater;
		this.datum = datum;
		this.status = status;
		this.filiale = filiale;
		this.kreditWunsch = kreditWunsch;
		this.ratenHoehe = ratenHoehe;
		this.ratenAnzahl = ratenAnzahl;
		this.setAntragssteller_2(antragssteller_2);
		this.setVerhaeltnisZu_2(verhaeltnisZu_2);
		setReferenzIdNames(new String[] { "idAntragssteller", "idUser" });
	}

	/************************* Getter/Setter ************************************/

	public User getBerater() {
		return berater;
	}

	public void setBerater(User berater) {
		this.berater = berater;
	}

	public String getDatum_dt() {
		return datum;
	}

	public void setDatum_dt(String datum) {
		this.datum = datum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFiliale() {
		return filiale;
	}

	public void setFiliale(String filiale) {
		this.filiale = filiale;
	}

	public double getKreditWunsch() {
		return kreditWunsch;
	}

	public void setKreditWunsch(double kreditWunsch) {
		this.kreditWunsch = kreditWunsch;
	}

	public double getRatenHoehe() {
		return ratenHoehe;
	}

	public void setRatenHoehe(double ratenHoehe) {
		this.ratenHoehe = ratenHoehe;
	}

	public int getRatenAnzahl() {
		return ratenAnzahl;
	}

	public void setRatenAnzahl(int ratenAnzahl) {
		this.ratenAnzahl = ratenAnzahl;
	}

	public void setAntragssteller_2(Antragssteller antragssteller_2) {
		this.antragssteller_2 = antragssteller_2;
	}

	public Antragssteller getAntragssteller_2() {
		return antragssteller_2;
	}

	public void setVerhaeltnisZu_2(String verhaeltnisZu_2) {
		this.verhaeltnisZu_2 = verhaeltnisZu_2;
	}

	public String getVerhaeltnisZu_2() {
		return verhaeltnisZu_2;
	}

	public void setidUser(int idUser) {
		this.idUser = idUser;
	}

	public int getidUser() {
		return idUser;
	}

	public int getIdAntragssteller_2() {
		return idAntragssteller_2;
	}

	public void setIdAntragssteller_2(int idAntragssteller_2) {
		this.idAntragssteller_2 = idAntragssteller_2;
	}
}
