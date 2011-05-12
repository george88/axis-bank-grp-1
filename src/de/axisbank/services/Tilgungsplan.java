package de.axisbank.services;

public class Tilgungsplan {

	private int laufzeitMonate;

	private double ratenHoehe;

	private double zinsSatz;

	private double kredithoehe;

	public int getLaufzeitMonate() {
		return laufzeitMonate;
	}

	public void setLaufzeitMonate(int laufzeitMonate) {
		this.laufzeitMonate = laufzeitMonate;
	}

	public double getRatenHoehe() {
		return ratenHoehe;
	}

	public void setRatenHoehe(double ratenHoehe) {
		this.ratenHoehe = ratenHoehe;
	}


	public double getZinsSatz() {
		return zinsSatz;
	}

	public void setZinsSatz(double zinsSatz) {
		this.zinsSatz = zinsSatz;
	}

	public double getKredithoehe() {
		return kredithoehe;
	}

	public void setKredithoehe(double kredithoehe) {
		this.kredithoehe = kredithoehe;
	}

	public Tilgungsplan() {
	}
}
