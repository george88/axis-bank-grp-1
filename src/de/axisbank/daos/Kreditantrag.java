package de.axisbank.daos;

public class Kreditantrag extends DaoObject {

	private int giroDarlehenskonto = -1;

	private User berater;

	private String datum;

	private String status;

	private String filiale;

	private double kreditWunsch = -1.0D;

	private double ratenHoehe = -1.0D;

	private int ratenAnzahl = -1;

	private Antragssteller antragssteller_2;

	private String verhaeltnisZu_2;

	public Kreditantrag() {
		super();
		setReferenzIdName("idAntragssteller");
	}

	public Kreditantrag(int giroDarlehenskonto, User berater, String datum,
			String status, String filiale, double kreditWunsch,
			double ratenHoehe, int ratenAnzahl,
			Antragssteller antragssteller_2, String verhaeltnisZu_2) {
		super();
		this.giroDarlehenskonto = giroDarlehenskonto;
		this.berater = berater;
		this.datum = datum;
		this.status = status;
		this.filiale = filiale;
		this.kreditWunsch = kreditWunsch;
		this.ratenHoehe = ratenHoehe;
		this.ratenAnzahl = ratenAnzahl;
		this.setAntragssteller_2(antragssteller_2);
		this.setVerhaeltnisZu_2(verhaeltnisZu_2);
	}

	public int getGiroDarlehenskonto() {
		return giroDarlehenskonto;
	}

	public void setGiroDarlehenskonto(int giroDarlehenskonto) {
		this.giroDarlehenskonto = giroDarlehenskonto;
	}

	public User getBerater() {
		return berater;
	}

	public void setBerater(User berater) {
		this.berater = berater;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
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

}
