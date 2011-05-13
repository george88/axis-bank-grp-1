package de.axisbank.services.web;
/**
 * Diese Klasse definiert den Kreditwunsch des Kunden
 * 
 * @version 13.05.2011
 * @author Robert Beese
 */

public class KreditWunsch {

	/**************************Variablen*************************************/
	private double kreditHoehe;

	private int laufzeit;

	private double monRate;

	private double letzteRate;

	private double gesamtBetrag;

	/**************************Konstruktor*************************************/
	public KreditWunsch(double kreditHoehe, int laufzeit, double monRate,
			double letzteRate, double gesamtBetrag) {
		super();
		this.kreditHoehe = kreditHoehe;
		this.laufzeit = laufzeit;
		this.monRate = monRate;
		this.letzteRate = letzteRate;
		this.gesamtBetrag = gesamtBetrag;
	}

	public KreditWunsch() {

	}
	
	/*************************Getter/Setter************************************/
	public double getKreditHoehe() {
		return kreditHoehe;
	}

	public void setKreditHoehe(double kreditHoehe) {
		this.kreditHoehe = kreditHoehe;
	}

	public int getLaufzeit() {
		return laufzeit;
	}

	public void setLaufzeit(int laufzeit) {
		this.laufzeit = laufzeit;
	}

	public double getMonRate() {
		return monRate;
	}

	public void setMonRate(double monRate) {
		this.monRate = monRate;
	}

	public double getLetzteRate() {
		return letzteRate;
	}

	public void setLetzteRate(double letzteRate) {
		this.letzteRate = letzteRate;
	}

	public double getGesamtBetrag() {
		return gesamtBetrag;
	}

	public void setGesamtBetrag(double gesamtBetrag) {
		this.gesamtBetrag = gesamtBetrag;
	}
}
