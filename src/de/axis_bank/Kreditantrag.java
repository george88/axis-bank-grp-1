package de.axis_bank;

public class Kreditantrag {

	private int giroDarlehenskonto;

	private String berater;

	private String datum;

	private String status;

	private String filiale;

	private double kreditWunsch;

	private double ratenHoehe;

	private int ratenAnzahl;

	public Kreditantrag() {

	}

	public Kreditantrag(int giroDarlehenskonto, String berater, String datum,
			String status, String filiale, double kreditWunsch,
			double ratenHoehe, int ratenAnzahl) {
		super();
		this.giroDarlehenskonto = giroDarlehenskonto;
		this.berater = berater;
		this.datum = datum;
		this.status = status;
		this.filiale = filiale;
		this.kreditWunsch = kreditWunsch;
		this.ratenHoehe = ratenHoehe;
		this.ratenAnzahl = ratenAnzahl;
	}

	public int getGiroDarlehenskonto() {
		return giroDarlehenskonto;
	}

	public void setGiroDarlehenskonto(int giroDarlehenskonto) {
		this.giroDarlehenskonto = giroDarlehenskonto;
	}

	public String getBerater() {
		return berater;
	}

	public void setBerater(String berater) {
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

}
