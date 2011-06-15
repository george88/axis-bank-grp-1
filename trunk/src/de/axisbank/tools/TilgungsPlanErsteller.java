package de.axisbank.tools;

import java.util.Vector;

import de.axisbank.services.filiale.Tilgung;
import de.axisbank.services.filiale.Tilgungsplan;

/**
 * Der TilgungsPlanErsteller enthält nur eine Methode um einen Tilgungsplan zu erstellen.
 * @author Georg Neufeld
 *
 */
public class TilgungsPlanErsteller {

	/**
	 * Erstellt anhand eines vorgefüllten Tilgungsplan-Objekt eines vollständige Tilgung, ausgehend von der Laufzeit eines Kredits oder der Ratenhöhe des Kredites 
	 * @param tp
	 * @return Tilgung[]
	 */
	public static Tilgung[] erstelleTilgungsPlan(Tilgungsplan tp) {

		Vector<Tilgung> tilgungen = new Vector<Tilgung>();

		//Abfrage, ob die Berechung ausgehend der Laufzeit oder der Ratenhöhe stattfinden soll
		if (tp.getLaufzeitMonate() < 0 && tp.getRatenHoehe() > 0) {
			//Berechung der Tilgungen anhand eienr festen Ratenhöhe			

			//Festlegung des Startdatums immer auf den Ersten des monats
			String startDatum = "01" + tp.getKreditBeginn().substring(2);
			//Aus dem Startdatum das zugehörige Endatum ds Monats
			String endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
			double startSchuld = tp.getKreditHoehe();
			double rate = tp.getRatenHoehe();
			//Berechnung des Zinsanteil aus dem Tageszins multipliziert eines 30-Tage-Monats mit dem angegebenen Zinssatz
			double zinsAnteil = (startSchuld * ((tp.getZinsatz() / 36000.) * 30.));
			//Berechnung der Tilgungshöhe aus der Rate
			double tilgung = rate - zinsAnteil;
			//Berechung der Schuld am Ende des Monats
			double endSchuld = startSchuld - tilgung;

			//Eintragung der ersten Tilgungszeile, quasi der Tilgung des ersten Monats
			tilgungen.add(new Tilgung(startDatum, startSchuld, rundeAufZweiNachKomma(rate), rundeAufZweiNachKomma(zinsAnteil), rundeAufZweiNachKomma(tilgung), endDatum,
					rundeAufZweiNachKomma(endSchuld)));

			//Schleife, in der alle Tilgungen berechnet werden bis auf die letzte und erste Tilgung 
			while (endSchuld >= rate) {

				//Erhöhung des Startdatums
				startDatum = DatumsKonvertierung.getNaechstenMonatsAnfang(endDatum);

				//Festlegung der Schuld am anfang des neuen Monats
				startSchuld = endSchuld;

				//Berechung des Zinsanteils
				zinsAnteil = (startSchuld * (tp.getZinsatz() / 1200.));

				//Berechung der Höhe der Tilgung
				tilgung = (rate - zinsAnteil);

				//Berechung der Schuld am Ende des Monats
				endSchuld = (startSchuld - tilgung);

				//Setzen des letzten Datum des Monats
				endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);

				//neuer Eintrag einer Tilgung
				tilgungen.add(new Tilgung(startDatum, startSchuld, rundeAufZweiNachKomma(rate), rundeAufZweiNachKomma(zinsAnteil), rundeAufZweiNachKomma(tilgung), endDatum,
						rundeAufZweiNachKomma(endSchuld)));
			}

			//Berechung der letzten Tilgung
			if (endSchuld > 0) {

				startDatum = DatumsKonvertierung.getNaechstenMonatsAnfang(endDatum);
				startSchuld = (endSchuld);
				endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);

				//Unterschied zum vorherigen Vorgehen zur Berechung einer Tilgung --> Tilgung gleich Startschuld
				tilgung = (startSchuld);
				zinsAnteil = (startSchuld * (tp.getZinsatz() / 1200.));

				//Unterschied zum vorherigen Vorgehen --> die letzte Rate ist die Restschuld
				rate = (tilgung + zinsAnteil);

				//Die Endschuld ergibt in diesem Fall immer 0
				endSchuld = (startSchuld - tilgung);

				tilgungen.add(new Tilgung(startDatum, startSchuld, rundeAufZweiNachKomma(rate), rundeAufZweiNachKomma(zinsAnteil), rundeAufZweiNachKomma(tilgung), endDatum,
						rundeAufZweiNachKomma(endSchuld)));
			}

		} else if (tp.getLaufzeitMonate() > 0 && tp.getRatenHoehe() < 0) {
			//Berechung der Tilgungen anhand eienr festen Laufzeit			

			String startDatum = "01" + tp.getKreditBeginn().substring(2);
			String endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
			int laufzeitMonate = tp.getLaufzeitMonate();
			double startSchuld = tp.getKreditHoehe();

			//Die Berechnung folgender zwei Zeilen stellen den einzigen Unterschied zur Berechung anhand einer festen Rate dar, ergo: es wird erst die Rate anhand der Laufzeit berechnet
			double zinsfaktor = Math.pow(((tp.getZinsatz() / 100.) + 1.), (1. / 12.));
			double rate = (startSchuld) * ((Math.pow(zinsfaktor, laufzeitMonate)) / ((Math.pow(zinsfaktor, laufzeitMonate)) - 1.)) * (zinsfaktor - 1.);
			//Ende der Berechung der Rate

			double zinsAnteil = (startSchuld * ((tp.getZinsatz() / 36000.) * 30.));
			double tilgung = rate - zinsAnteil, endSchuld = startSchuld - tilgung;
			tilgungen.add(new Tilgung(startDatum, startSchuld, rate, zinsAnteil, tilgung, endDatum, endSchuld));
			for (int i = 1; i < laufzeitMonate; i++) {
				startDatum = DatumsKonvertierung.getNaechstenMonatsAnfang(endDatum);
				startSchuld = endSchuld;
				zinsAnteil = (startSchuld * (tp.getZinsatz() / 1200.));
				tilgung = (rate - zinsAnteil);
				endSchuld = (startSchuld - tilgung);
				endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
				tilgungen.add(new Tilgung(startDatum, startSchuld, rundeAufZweiNachKomma(rate), rundeAufZweiNachKomma(zinsAnteil), rundeAufZweiNachKomma(tilgung), endDatum,
						rundeAufZweiNachKomma(endSchuld)));
			}
			if (endSchuld > 0) {
				startDatum = DatumsKonvertierung.getNaechstenMonatsAnfang(endDatum);
				startSchuld = (endSchuld);
				endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
				tilgung = (startSchuld);
				zinsAnteil = (startSchuld * (tp.getZinsatz() / 1200.));
				rate = (tilgung + zinsAnteil);
				endSchuld = (startSchuld - tilgung);
				tilgungen.add(new Tilgung(startDatum, startSchuld, rundeAufZweiNachKomma(rate), rundeAufZweiNachKomma(zinsAnteil), rundeAufZweiNachKomma(tilgung), endDatum,
						rundeAufZweiNachKomma(endSchuld)));
			}
		}
		Tilgung[] tilgs = new Tilgung[tilgungen.size()];
		tilgs = tilgungen.toArray(tilgs);
		return tilgs;
	}

	@Deprecated
	public static Double rundeAufZweiNachKomma(double zuRunden) {
		//		return Double.parseDouble(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(zuRunden));
		//				return Math.round((zuRunden) * 100.) / 100.;
		return zuRunden;
	}
}
