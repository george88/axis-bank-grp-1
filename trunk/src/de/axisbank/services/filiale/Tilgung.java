package de.axisbank.services.filiale;

/**
 * Die Instanz dieser Klasse ist im übertragenen Sinn eine Zeile in dem Tilgungsplan, der über die Methode getTilgungsPlan in der Serviceklasse FilialBankService.
 * @author Georg Neufeld
 *
 */
public class Tilgung {

	/************************** Variablen *************************************/

	/**
	 * Startdatum der Tilgung, meist Anfang des Monats
	 */
	private String startDatum;

	/**
	 * Anfangsschuld oder einfach Kredithöhe am Anfang des Monats
	 */
	private double startSchuld;

	/**
	 * Höhe der monatlichen Rate
	 */
	private double rate;

	/**
	 * Höhe der in der Schuld einthaltenen Zinsen 
	 */
	private double zinsAnteil;

	/**
	 * Höhe der Tilgung
	 */
	private double tilgung;

	/**
	 * Datum des Monatsende
	 */
	private String endDatum;

	/**
	 * Höhe der Schuld am Monatsende
	 */
	private double endSchuld;

	/************************** Konstruktor *************************************/

	/**
	 * Standardkonstruktor
	 */
	public Tilgung() {

	}

	/**
	 * Konstruktor um ein Tilgungs-Obkekt zu erstellen
	 * @param startDatum
	 * @param startSchuld
	 * @param rate
	 * @param zinsAnteil
	 * @param tilgung
	 * @param endDatum
	 * @param endSchuld
	 */
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
