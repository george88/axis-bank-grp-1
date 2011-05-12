package de.axisbank.services.web;

import java.util.Vector;

import de.axisbank.services.Tilgungsplan;

public class WebBankService {

	public WebBankService() {

	}

	public KreditWunsch[] getTilgungsPlan(int haushaltsUeberschuss) {
		System.out.println("Hü:" + haushaltsUeberschuss);
		int laufzeitmin = 12;
		int laufzeitmax = 84;
		double minkredit = 5000;
		double maxKredit = 50000;
		double zinssatz = 0.069;

		double startKreditHoehe = minkredit;
		int startLaufzeit = laufzeitmin;

		if ((startKreditHoehe / haushaltsUeberschuss) < 12) {
			startKreditHoehe = (haushaltsUeberschuss * 12) * (1 - zinssatz);
		}

		Vector<KreditWunsch> kw = new Vector<KreditWunsch>();

		while (startLaufzeit <= laufzeitmax) {
			double monRate = startKreditHoehe * (1 + zinssatz);
			kw.add(new KreditWunsch(Math.rint(startKreditHoehe), startLaufzeit,
					monRate, 200, 200));
			startKreditHoehe += haushaltsUeberschuss * (1 - zinssatz);
			startLaufzeit++;
			if (startKreditHoehe > maxKredit)
				break;
		}
		KreditWunsch[] k = new KreditWunsch[kw.size()];
		k = kw.toArray(k);
		return k;
	}
}
