package de.axisbank.tools;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Vector;

import de.axisbank.services.Tilgung;
import de.axisbank.services.Tilgungsplan;

public class TilgungsPlanErsteller {

	public static Tilgung[] erstelleTilgungsPlan(Tilgungsplan tp) {

		Vector<Tilgung> tilgungen = new Vector<Tilgung>();

		if (tp.getLaufzeitMonate() < 0 && tp.getRatenHoehe() > 0) {

			String startDatum = "01" + tp.getKreditBeginn().substring(2);
			String endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
			double startSchuld = tp.getKreditHoehe();
			double rate = tp.getRatenHoehe();
			double zinsAnteil = (startSchuld * ((tp.getZinsatz() / 36000.) * 30.));
			double tilgung = rate - zinsAnteil;
			double endSchuld = startSchuld - tilgung;

			tilgungen.add(new Tilgung(startDatum, startSchuld, rundeAufZweiNachKomma(rate), rundeAufZweiNachKomma(zinsAnteil), rundeAufZweiNachKomma(tilgung), endDatum,
					rundeAufZweiNachKomma(endSchuld)));

			while (endSchuld >= rate) {
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

		} else if (tp.getLaufzeitMonate() > 0 && tp.getRatenHoehe() < 0) {
			String startDatum = "01" + tp.getKreditBeginn().substring(2);
			String endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
			int laufzeitMonate = tp.getLaufzeitMonate();
			double zinsfaktor = Math.pow(((tp.getZinsatz() / 100.) + 1.), (1. / 12.));
			double startSchuld = tp.getKreditHoehe();
			double rate = (startSchuld) * ((Math.pow(zinsfaktor, laufzeitMonate)) / ((Math.pow(zinsfaktor, laufzeitMonate)) - 1.)) * (zinsfaktor - 1.);
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

	public static Double rundeAufZweiNachKomma(double zuRunden) {
		//		return Double.parseDouble(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(zuRunden));
		//				return Math.round((zuRunden) * 100.) / 100.;
		return zuRunden;
	}
}
