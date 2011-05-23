package de.axisbank.daos;

public class User extends DaoObject {

	private String benutzername;

	private String passwort;

	private int status = -1;

	private long letzterLogin;

	private String vorname;

	private String nachname;

	private String gebDatum;

	public User() {
		super();
	}

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
