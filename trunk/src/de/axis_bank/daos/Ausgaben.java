package de.axis_bank.daos;

public class Ausgaben {

	private double mieteDarlehen;

	private double wohnnebenkosten;

	private double lebenserhaltung;

	private double telefonMobilfunk;

	private String kfzKosten;

	private String privKrankenvers;

	private String sparVertraege;

	public Ausgaben(double mieteDarlehen, double wohnnebenkosten,
			double lebenserhaltung, double telefonMobilfunk, String kfzKosten,
			String privKrankenvers, String sparVertraege) {
		super();
		this.mieteDarlehen = mieteDarlehen;
		this.wohnnebenkosten = wohnnebenkosten;
		this.lebenserhaltung = lebenserhaltung;
		this.telefonMobilfunk = telefonMobilfunk;
		this.kfzKosten = kfzKosten;
		this.privKrankenvers = privKrankenvers;
		this.sparVertraege = sparVertraege;
	}

	public Ausgaben() {

	}

	public double getMieteDarlehen() {
		return mieteDarlehen;
	}

	public void setMieteDarlehen(double mieteDarlehen) {
		this.mieteDarlehen = mieteDarlehen;
	}

	public double getWohnnebenkosten() {
		return wohnnebenkosten;
	}

	public void setWohnnebenkosten(double wohnnebenkosten) {
		this.wohnnebenkosten = wohnnebenkosten;
	}

	public double getLebenserhaltung() {
		return lebenserhaltung;
	}

	public void setLebenserhaltung(double lebenserhaltung) {
		this.lebenserhaltung = lebenserhaltung;
	}

	public double getTelefonMobilfunk() {
		return telefonMobilfunk;
	}

	public void setTelefonMobilfunk(double telefonMobilfunk) {
		this.telefonMobilfunk = telefonMobilfunk;
	}

	public String getKfzKosten() {
		return kfzKosten;
	}

	public void setKfzKosten(String kfzKosten) {
		this.kfzKosten = kfzKosten;
	}

	public String getPrivKrankenvers() {
		return privKrankenvers;
	}

	public void setPrivKrankenvers(String privKrankenvers) {
		this.privKrankenvers = privKrankenvers;
	}

	public String getSparVertraege() {
		return sparVertraege;
	}

	public void setSparVertraege(String sparVertraege) {
		this.sparVertraege = sparVertraege;
	}
}
