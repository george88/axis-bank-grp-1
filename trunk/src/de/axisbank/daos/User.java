package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verfügung. Sie ist quasi Eintrag der Datenbanktabelle User 
 * @author Georg Neufeld
 *
 */
public class User extends DaoObject {

	/************************** Variablen *************************************/

	/**
	 * Benutzername des Filialmitarbeiters
	 */
	private String benutzername;

	/**
	 * Passwort des Filialmitarbeiters
	 */
	private String passwort;

	/**
	 * Anmeldestatus des Filialmitarbeiters
	 */
	private int status = -1;

	/**
	 * Zeitpunkt der letzten Anmeldung am System des Filialmitarbeiters
	 */
	private long letzterLogin;

	/**
	 * Vorname des Filialmitarbeiters
	 */
	private String vorname;

	/**
	 * Nachname des Filialmitarbeiters
	 */
	private String nachname;

	/**
	 * Geburtsdatum des Filialmitarbeiters tt.mm.yyyy
	 */
	private String gebDatum;

	/************************** Konstruktor *************************************/

	/**
	 * Standardkonstruktor
	 */
	public User() {
		super();
	}

	/**
	 * Konstruktor um ein vollständig gefülltes User-Objekt zu erhalten
	 * @param benutzername
	 * @param passwort
	 * @param status
	 * @param letzterLogin
	 * @param vorname
	 * @param nachname
	 * @param gebDatum
	 */
	public User(String benutzername, String passwort, int status, long letzterLogin, String vorname, String nachname, String gebDatum) {
		super();
		this.benutzername = benutzername;
		this.passwort = passwort;
		this.status = status;
		this.letzterLogin = letzterLogin;
		this.vorname = vorname;
		this.nachname = nachname;
		this.gebDatum = gebDatum;
	}

	/************************* Getter/Setter ************************************/

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getLetzterLogin() {
		return letzterLogin;
	}

	public void setLetzterLogin(long letzterLogin) {
		this.letzterLogin = letzterLogin;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getGebDatum_dt() {
		return gebDatum;
	}

	public void setGebDatum_dt(String gebDatum) {
		this.gebDatum = gebDatum;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getPasswort() {
		return passwort;
	}

}
