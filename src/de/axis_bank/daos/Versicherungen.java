package de.axis_bank.daos;

public class Versicherungen extends DaoObject {

	private String versArt;

	private String versGesellschaft;

	private double versSumme;

	private double mtlBeitrag;

	public Versicherungen() {
	}

	public Versicherungen(String versArt, String versGesellschaft,
			double versSumme, double mtlBeitrag) {
		super();
		this.versArt = versArt;
		this.versGesellschaft = versGesellschaft;
		this.versSumme = versSumme;
		this.mtlBeitrag = mtlBeitrag;
	}

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
