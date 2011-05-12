package de.axisbank.services.web;

import java.util.Vector;

public class WebBankService {
	static final int MIN_LAUFZEIT = 12;
	static final int MAX_LAUFZEIT = 84;
	static final double MIN_KREDIT = 5000;
	static final double MAX_KREDIT = 50000;
	static final double ZINSSATZ = 5.59;
	public WebBankService() {

	}

	public KreditWunsch[] getTilgungsPlan(int haushaltsUeberschuss) {

		double startKreditHoehe = MIN_KREDIT;
		int laufzeit = MIN_LAUFZEIT;

		if ((startKreditHoehe / haushaltsUeberschuss) < 12) {
			startKreditHoehe = ((haushaltsUeberschuss * 12)*0.90);
		}

		Vector<KreditWunsch> kw = new Vector<KreditWunsch>();
		
		while (laufzeit <= MAX_LAUFZEIT) {
			
			
			double mz = (Math.pow((1 + (ZINSSATZ/100)),(1d/12d))) - 1;  // monatl. Zins
			double monRate = ((double)Math.round((startKreditHoehe * mz * Math.pow((1 + mz),laufzeit) / (-1 + Math.pow((1 + mz),laufzeit)))*100))/100d; // monatl. Rate
			double gesamtBetrag = monRate *laufzeit;
			
			monRate = (double)Math.round((monRate*100))/100;
			
			if(monRate<=haushaltsUeberschuss)
			{
				double letzteRate=0;
				if((monRate*laufzeit)!= gesamtBetrag)
				{
					letzteRate = gesamtBetrag-(monRate*(laufzeit-1));
				}
				else
				{
					letzteRate = monRate;
				}
				
				
				kw.add(new KreditWunsch(Math.rint(startKreditHoehe), laufzeit, monRate,letzteRate, gesamtBetrag));
				startKreditHoehe += haushaltsUeberschuss;
			}
			
			
				laufzeit++;
			
			if (startKreditHoehe > MAX_KREDIT)
				break;
		}
		KreditWunsch[] k = new KreditWunsch[kw.size()];
		k = kw.toArray(k);
		return k;
	}
}
