package de.axisbank.services.filiale;

/**
 * Der Tilgungsplan wird von einem  Tool Namens TilgungsPLanersteller aus dem package tool erstellt.
 * @author Georg Neufeld
 *
 */
public class Tilgungsplan {

	/************************** Variablen *************************************/

	/**
	 * Kredithöhe
	 */
	private double kreditHoehe;

	/**
	 * Kreditbeginn tt.mm.yyyy
	 */
	private String kreditBeginn;

	/**
	 * Zinssatz
	 */
	private double zinsatz;

	/**
	 * Höhe der monatlichen Rate
	 */
	private double ratenHoehe;

	/**
	 * Laufzeit des Kredites, quasi Anzahl der Monate
	 */
	private int laufzeitMonate;

	/**
	 * Array von Typ Tilgung
	 * Eine Tilgung entspricht einem Monat
	 */
	private Tilgung[] tilgungen;

	/************************** Konstruktor *************************************/

	/**
	 * Standardkontruktor
	 */
	public Tilgungsplan() {

	}

	/**
	 * Konstruktor für die Erstellung eines Tilgungsplan-Objektes
	 * @param kreditHoehe
	 * @param kreditBeginn
	 * @param zinsatz
	 * @param ratenHoehe
	 * @param laufzeitMonate
	 * @param tilgungen
	 */
	public Tilgungsplan(double kreditHoehe, String kreditBeginn, double zinsatz, double ratenHoehe, int laufzeitMonate, Tilgung[] tilgungen) {
		super();
		this.kreditHoehe = kreditHoehe;
		this.kreditBeginn = kreditBeginn;
		this.zinsatz = zinsatz;
		this.ratenHoehe = ratenHoehe;
		this.laufzeitMonate = laufzeitMonate;
		this.tilgungen = tilgungen;
	}

	/************************* Getter/Setter ************************************/

	public double getKreditHoehe() {
		return kreditHoehe;
	}

	public void setKreditHoehe(double kreditHoehe) {
		this.kreditHoehe = kreditHoehe;
	}

	public String getKreditBeginn() {
		return kreditBeginn;
	}

	public void setKreditBeginn(String kreditBeginn) {
		this.kreditBeginn = kreditBeginn;
	}

	public double getZinsatz() {
		return zinsatz;
	}

	public void setZinsatz(double zinsatz) {
		this.zinsatz = zinsatz;
	}

	public double getRatenHoehe() {
		return ratenHoehe;
	}

	public void setRatenHoehe(double ratenHoehe) {
		this.ratenHoehe = ratenHoehe;
	}

	public int getLaufzeitMonate() {
		return laufzeitMonate;
	}

	public void setLaufzeitMonate(int laufzeitMonate) {
		this.laufzeitMonate = laufzeitMonate;
	}

	public Tilgung[] getTilgungen() {
		return tilgungen;
	}

	public void setTilgungen(Tilgung[] tilgungen) {
		this.tilgungen = tilgungen;
	}
}
