package de.axisbank.services.web;

import java.util.Vector;

import de.axisbank.tools.KonfigFiles;

/**
 * Diese Klasse liefert alle Methoden fuer die Internetanwendung der Axis Bank
 * 
 * @version 13.05.2011
 * @author Robert Beese
 */

public class WebBankService {
	/************************** Konstanten *************************************/

	private static final int MIN_LAUFZEIT = KonfigFiles.getInt(KonfigFiles.Kalkulation_MIN_LAUFZEIT);
	private static final int MAX_LAUFZEIT = KonfigFiles.getInt(KonfigFiles.Kalkulation_MAX_LAUFZEIT);
	private static final double MIN_KREDIT = KonfigFiles.getDouble(KonfigFiles.Kalkulation_MIN_KREDIT);;
	private static final double MAX_KREDIT = KonfigFiles.getDouble(KonfigFiles.Kalkulation_MAX_KREDIT);;
	private static final double ZINSSATZ = KonfigFiles.getDouble(KonfigFiles.Kalkulation_ZINSSATZ);;

	/************************** Konstruktor *************************************/
	public WebBankService() {

	}

	/************************** public Methoden *************************************/

	/**
	 * Diese Methode bekommt eine Wunschrate und berechnet daraus die möglichen
	 * Kreditkonstellation.
	 * 
	 * @param haushaltsUeberschuss
	 *            - höhe der monatlichen Rate
	 * @return KreditWunsch[] - ein Array mit Kreditwünschen
	 */
	public KreditWunsch[] getTilgungsPlanDurchRate(int haushaltsUeberschuss) {

		/************************** Variablen *************************************/
		double kreditHoehe = MIN_KREDIT; // Kredithoehe mit der die Berechnung
											// begonnen wird
											// initial = MIN_KREDIT
		int laufZeit = MIN_LAUFZEIT; // Laufzeit bei der die Berechnung begonnen
										// wird
										// initial = MIN_LAUFZEIT
		double monZins = 0; // monatlicher Zins
		double monRate = 0; // monatliche Rate
		double gesamtBetrag = 0; // Gesamtkosten des Kredites
		double letzteRate = 0; // letze Rate des Kredites
		Vector<KreditWunsch> bufferVector = new Vector<KreditWunsch>(); // Vector
																		// in
																		// der
																		// die
																		// Berechnungen
																		// zwischengespeichert
																		// werden
		KreditWunsch[] rueckgabeArray; // Rückgabearray

		/************************** Code *************************************/
		// Wenn der haushaltsUeberschuss den MIN_KREDIT in weniger als
		// MIN_LAUFZEIT
		// zurueckzahlen kann, wird die startKreditHoehe neu gesetzt
		if ((MIN_KREDIT / haushaltsUeberschuss) < MIN_LAUFZEIT) {
			kreditHoehe = ((haushaltsUeberschuss * 12) * 0.90);
		}

		// Wenn die Starthoehe groesser als der Maximalkredit ist, dann wird
		// nichts berechnet
		if (kreditHoehe <= MAX_KREDIT) {
			// solange die Maximallaufzeit nicht erreicht ist
			while (laufZeit <= MAX_LAUFZEIT) {
				monZins = MonZins(); // berechne monatlichen Zins
				monRate = MonRate(kreditHoehe, monZins, laufZeit); // berechne
																	// monatl.
																	// Rate
				gesamtBetrag = monRate * laufZeit; // berechne Gesamtbetrag

				monRate = (double) Math.round((monRate * 100)) / 100; // mon.
																		// Rate
																		// zur
																		// Darstellung
																		// runden

				// erzeugt nur einen Eintrag wenn die mon. Rate den
				// Haushaltsueberschuss nicht
				// ueberschreitet
				if (monRate <= haushaltsUeberschuss) {
					// Berechnung der letzten Rate
					if ((monRate * laufZeit) != gesamtBetrag) {
						letzteRate = gesamtBetrag - (monRate * (laufZeit - 1));
					} else {
						letzteRate = monRate;
					}
					bufferVector.add(new KreditWunsch(Math.rint(kreditHoehe), laufZeit, monRate, letzteRate, gesamtBetrag, ZINSSATZ)); // Objekt
					// dem
					// Vector
					// hinzufuegen
					kreditHoehe += haushaltsUeberschuss; // Naechste Kredithoehe
				}
				laufZeit++;// Laufzeit um einen Monat erhoehen

				// Abbruch wenn Maximalkredit erreicht ist
				if (kreditHoehe > MAX_KREDIT)
					break;
			}
		}
		rueckgabeArray = new KreditWunsch[bufferVector.size()]; // Groesse des
																// Rueckgabearrays
																// festlegen
		rueckgabeArray = bufferVector.toArray(rueckgabeArray); // Zwischenspeicher
																// im
																// Rueckgabearray
																// ablegen
		return rueckgabeArray;// Rueckgabearray zurueckgeben
	}

	/**
	 * Diese Methode bekommt Laufzeit und Kredithoehe und berechnet daraus die
	 * möglichen Kreditkonstellation.
	 * 
	 * @param kreditHoehe
	 *            - höhe des Kredites
	 * @param laufZeit
	 *            - gewuenschte Laufzeit
	 * @return KreditWunsch - ein Objekt mit dem Kreditwunsch
	 */
	public KreditWunsch getTilgungsPlanDurchBetragLaufzeit(double kreditHoehe, int laufZeit) {
		KreditWunsch kreditWunsch = new KreditWunsch();
		double monZins = 0; // monatlicher Zins
		double monRate = 0; // monatliche Rate
		double gesamtBetrag = 0; // Gesamtkosten des Kredites
		double letzteRate = 0; // letze Rate des Kredites

		// wird nur ausgefuehrt, wenn die uebergebenen Werte in Rahmen liegen
		if (kreditHoehe >= MIN_KREDIT && kreditHoehe <= MAX_KREDIT && laufZeit >= MIN_LAUFZEIT && laufZeit <= MAX_LAUFZEIT) {
			monZins = MonZins();// berechne monatlichen Zins
			monRate = MonRate(kreditHoehe, monZins, laufZeit); // berechne
																// monatl. Rate
			gesamtBetrag = monRate * laufZeit; // berechne Gesamtbetrag

			monRate = (double) Math.round((monRate * 100)) / 100; // mon. Rate
																	// zur
																	// Darstellung
																	// runden

			// Berechnung der letzten Rate
			if ((monRate * laufZeit) != gesamtBetrag) {
				letzteRate = gesamtBetrag - (monRate * (laufZeit - 1));
			} else {
				letzteRate = monRate;
			}

			// Objekt setzen
			kreditWunsch.setKreditHoehe(kreditHoehe);
			kreditWunsch.setLaufzeit(laufZeit);
			kreditWunsch.setMonRate(monRate);
			kreditWunsch.setLetzteRate(letzteRate);
			kreditWunsch.setGesamtBetrag(gesamtBetrag);
			kreditWunsch.setZinssatz(ZINSSATZ);
		} else {
			kreditWunsch = null; // leeres Objekt
		}

		return kreditWunsch;
	}

	/************************** private Methoden *************************************/
	/**
	 * Diese Methode berechnet den monatlichen Zins
	 * 
	 * @return monZins - monatlicher Zins
	 */
	private double MonZins() {
		double monZins = (Math.pow((1 + (ZINSSATZ / 100)), (1d / 12d))) - 1; // berechne
																				// monatlichen
																				// Zins
		return monZins;
	}

	/**
	 * Diese Methode berechnet die monatliche Rate
	 * 
	 * @param kreditHoehe
	 *            - Kredithoehe
	 * @param monZins
	 *            - monatlicher Zins
	 * @param laufZeit
	 *            - gewuenschte Gesamtlaufzeit
	 * @return monRate - monatlicher Rate
	 */
	private double MonRate(double kreditHoehe, double monZins, int laufZeit) {
		double monRate = (kreditHoehe * monZins * Math.pow((1 + monZins), laufZeit) / (-1 + Math.pow((1 + monZins), laufZeit))); // berechne
																																	// monatl.
																																	// Rate
		return monRate;
	}
}
