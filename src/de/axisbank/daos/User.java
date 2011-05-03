package de.axisbank.daos;

public class User extends DaoObject {

	private String username;

	private int status = -1;

	private long letzerLogin;

	private String vorname;

	private String nachname;

	private String gebDatum;

	public User() {

	}

	public User(String username, int status, long letzerLogin, String vorname,
			String nachname, String gebDatum) {
		super();
		this.username = username;
		this.status = status;
		this.letzerLogin = letzerLogin;
		this.vorname = vorname;
		this.nachname = nachname;
		this.gebDatum = gebDatum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getLetzerLogin() {
		return letzerLogin;
	}

	public void setLetzerLogin(long letzerLogin) {
		this.letzerLogin = letzerLogin;
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

	public String getGebDatum() {
		return gebDatum;
	}

	public void setGebDatum(String gebDatum) {
		this.gebDatum = gebDatum;
	}

}
