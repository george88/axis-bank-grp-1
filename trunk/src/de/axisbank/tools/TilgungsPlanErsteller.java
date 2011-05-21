package de.axisbank.tools;

import java.text.NumberFormat;
import java.util.Vector;

import de.axisbank.services.Tilgung;
import de.axisbank.services.Tilgungsplan;

public class TilgungsPlanErsteller {

	public static Tilgung[] erstelleTilgungsPlan(Tilgungsplan tp) {

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		Vector<Tilgung> tilgungen = new Vector<Tilgung>();

		if (tp.getLaufzeitMonate() < 0 && tp.getRatenHoehe() > 0) {

			String startDatum = "01" + tp.getKreditBeginn().substring(2), endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
			double startSchuld = tp.getKreditHoehe(), rate = tp.getRatenHoehe(), zinsAnteil = rundeAufZweiNachKomma(startSchuld * ((tp.getZinsatz() / 36000.) * 30.)), tilgung = rate - zinsAnteil, endSchuld = startSchuld
					- tilgung;

			tilgungen.add(new Tilgung(startDatum, startSchuld, rate, zinsAnteil, tilgung, endDatum, endSchuld));

			while (endSchuld >= rate) {
				startDatum = DatumsKonvertierung.getNaechstenMonatsAnfang(endDatum);
				startSchuld = endSchuld;
				zinsAnteil = rundeAufZweiNachKomma(startSchuld * (tp.getZinsatz() / 1200.));
				tilgung = rundeAufZweiNachKomma(rate - zinsAnteil);
				endSchuld = rundeAufZweiNachKomma(startSchuld - tilgung);
				endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
				tilgungen.add(new Tilgung(startDatum, startSchuld, rate, zinsAnteil, tilgung, endDatum, endSchuld));
			}
			if (endSchuld > 0) {
				startDatum = DatumsKonvertierung.getNaechstenMonatsAnfang(endDatum);
				startSchuld = rundeAufZweiNachKomma(endSchuld);
				endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
				tilgung = rundeAufZweiNachKomma(startSchuld);
				zinsAnteil = 0;
				rate = rundeAufZweiNachKomma(tilgung);
				endSchuld = rundeAufZweiNachKomma(startSchuld - tilgung);
				tilgungen.add(new Tilgung(startDatum, startSchuld, rate, zinsAnteil, tilgung, endDatum, endSchuld));
			}

		} else if (tp.getLaufzeitMonate() > 0 && tp.getRatenHoehe() < 0) {
			String startDatum = "01" + tp.getKreditBeginn().substring(2), endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
			int laufzeitMonate = tp.getLaufzeitMonate();
			double zinsfaktor = Math.pow(((tp.getZinsatz() / 100.) + 1.), (1. / 12.));
			double startSchuld = tp.getKreditHoehe(),

			rate =

			(startSchuld)

			*

			((Math.pow(zinsfaktor, laufzeitMonate))

			/ ((Math.pow(zinsfaktor, laufzeitMonate)) - 1.))

			*

			(zinsfaktor - 1.),

			zinsAnteil = (startSchuld * ((tp.getZinsatz() / 36000.) * 30.)), tilgung = rate - zinsAnteil, endSchuld = startSchuld - tilgung;
			tilgungen.add(new Tilgung(startDatum, startSchuld, rate, zinsAnteil, tilgung, endDatum, endSchuld));
			for (int i = 1; i < laufzeitMonate; i++) {
				startDatum = DatumsKonvertierung.getNaechstenMonatsAnfang(endDatum);
				startSchuld = endSchuld;
				zinsAnteil = (startSchuld * (tp.getZinsatz() / 1200.));
				tilgung = (rate - zinsAnteil);
				endSchuld = (startSchuld - tilgung);
				endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
				tilgungen.add(new Tilgung(startDatum, startSchuld, rate, zinsAnteil, tilgung, endDatum, endSchuld));
			}
			if (endSchuld > 0) {
				startDatum = DatumsKonvertierung.getNaechstenMonatsAnfang(endDatum);
				startSchuld = (endSchuld);
				endDatum = DatumsKonvertierung.getMonatsEnde(startDatum);
				tilgung = (startSchuld);
				zinsAnteil = 0;
				rate = (tilgung);
				endSchuld = (startSchuld - tilgung);
				tilgungen.add(new Tilgung(startDatum, startSchuld, rate, zinsAnteil, tilgung, endDatum, endSchuld));
			}
		}
		Tilgung[] tilgs = new Tilgung[tilgungen.size()];
		tilgs = tilgungen.toArray(tilgs);
		return tilgs;
	}

	public static Double rundeAufZweiNachKomma(double zuRunden) {
		return Math.round((zuRunden) * 100.) / 100.;
	}
}
