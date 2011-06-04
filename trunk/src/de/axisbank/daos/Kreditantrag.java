package de.axisbank.daos;

public class Kreditantrag extends DaoObject {

	private User berater;

	private int idUser = -1;

	private String datum;

	private String status;

	private String filiale;

	private double kreditWunsch = -1.0D;

	private double ratenHoehe = -1.0D;

	private int ratenAnzahl = -1;

	private int idAntragssteller_2 = -1;

	private Antragssteller antragssteller_2;

	private String verhaeltnisZu_2;

	public Kreditantrag() {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller", "idUser" });
	}

	public Kreditantrag(int idAntragssteller) {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller", "idUser" });
		setReferenzIds(new int[] { idAntragssteller });
	}

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
