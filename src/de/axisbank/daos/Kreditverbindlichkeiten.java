package de.axisbank.daos;

public class Kreditverbindlichkeiten extends DaoObject {

	private String kreditinstitut;

	private double gesamtschuld;

	private double restSchuld;

	private double mtlRate;

	public Kreditverbindlichkeiten(String kreditinstitut, double gesamtschuld,
			double restSchuld, double mtlRate) {
		super();
		this.kreditinstitut = kreditinstitut;
		this.gesamtschuld = gesamtschuld;
		this.restSchuld = restSchuld;
		this.mtlRate = mtlRate;
	}

	public Kreditverbindlichkeiten() {

	}

	public String getKreditinstitut() {
		return kreditinstitut;
	}

	public void setKreditinstitut(String kreditinstitut) {
		this.kreditinstitut = kreditinstitut;
	}

	public double getGesamtschuld() {
		return gesamtschuld;
	}

	public void setGesamtschuld(double gesamtschuld) {
		this.gesamtschuld = gesamtschuld;
	}

	public double getRestSchuld() {
		return restSchuld;
	}

	public void setRestSchuld(double restSchuld) {
		this.restSchuld = restSchuld;
	}

	public double getMtlRate() {
		return mtlRate;
	}

	public void setMtlRate(double mtlRate) {
		this.mtlRate = mtlRate;
	}
}