package de.axisbank.services.filiale;

/**
 * Die Instanz dieser Klasse ist im übertragenen Sinn eine Zeile in dem Tilgungsplan, der über die Methode getTilgungsPlan in der Serviceklasse FilialBankService.
 * @author Georg Neufeld
 *
 */
public class Tilgung {

	/************************** Variablen *************************************/
	private String startDatum;

	private double startSchuld;

	private double rate;

	private double zinsAnteil;

	private double tilgung;

	private String endDatum;

	private double endSchuld;

	public Tilgung() {

	}

	/************************** Konstruktor *************************************/
	public Tilgung(String startDatum, double startSchuld, double rate, double zinsAnteil, double tilgung, String endDatum, double endSchuld) {
		super();
		this.startDatum = startDatum;
		this.startSchuld = startSchuld;
		this.rate = rate;
		this.zinsAnteil = zinsAnteil;
		this.tilgung = tilgung;
		this.endDatum = endDatum;
		this.endSchuld = endSchuld;
	}

	/************************* Getter/Setter ************************************/
	public String getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(String startDatum) {
		this.startDatum = startDatum;
	}

	public double getStartSchuld() {
		return startSchuld;
	}

	public void setStartSchuld(double startSchuld) {
		this.startSchuld = startSchuld;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getZinsAnteil() {
		return zinsAnteil;
	}

	public void setZinsAnteil(double zinsAnteil) {
		this.zinsAnteil = zinsAnteil;
	}

	public double getTilgung() {
		return tilgung;
	}

	public void setTilgung(double tilgung) {
		this.tilgung = tilgung;
	}

	public String getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	public double getEndSchuld() {
		return endSchuld;
	}

	public void setEndSchuld(double endSchuld) {
		this.endSchuld = endSchuld;
	}
}
