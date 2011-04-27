package de.axis_bank;

public class Einnahmen {

	private double einkommen;

	private double rente;

	private double mietEinnahmen;

	private double kinderGeld;

	private double sonstEinnahmen;

	public Einnahmen(double einkommen, double rente, double mietEinnahmen,
			double kinderGeld, double sonstEinnahmen) {
		super();
		this.einkommen = einkommen;
		this.rente = rente;
		this.mietEinnahmen = mietEinnahmen;
		this.kinderGeld = kinderGeld;
		this.sonstEinnahmen = sonstEinnahmen;
	}

	public Einnahmen() {

	}

	public double getEinkommen() {
		return einkommen;
	}

	public void setEinkommen(double einkommen) {
		this.einkommen = einkommen;
	}

	public double getRente() {
		return rente;
	}

	public void setRente(double rente) {
		this.rente = rente;
	}

	public double getMietEinnahmen() {
		return mietEinnahmen;
	}

	public void setMietEinnahmen(double mietEinnahmen) {
		this.mietEinnahmen = mietEinnahmen;
	}

	public double getKinderGeld() {
		return kinderGeld;
	}

	public void setKinderGeld(double kinderGeld) {
		this.kinderGeld = kinderGeld;
	}

	public double getSonstEinnahmen() {
		return sonstEinnahmen;
	}

	public void setSonstEinnahmen(double sonstEinnahmen) {
		this.sonstEinnahmen = sonstEinnahmen;
	}

}
