package de.axisbank.services.web;

import java.util.Vector;

public class WebBankService {
	static final int MIN_LAUFZEIT = 12;
	static final int MAX_LAUFZEIT = 84;
	static final double MIN_KREDIT = 5000;
	static final double MAX_KREDIT = 50000;
	static final double ZINSSATZ = 0.0595;
	
	public WebBankService() {

	}

	public KreditWunsch[] getTilgungsPlan(int haushaltsUeberschuss) {

		double startKreditHoehe = MIN_KREDIT;
		int startLaufzeit = MIN_LAUFZEIT;

		if ((startKreditHoehe / haushaltsUeberschuss) < 12) {
			startKreditHoehe = ((haushaltsUeberschuss * 12)*0.90);
		}

		Vector<KreditWunsch> kw = new Vector<KreditWunsch>();

		while (startLaufzeit <= MAX_LAUFZEIT) {
			double annuitaet = (double)Math.round((startKreditHoehe *(((ZINSSATZ*Math.pow((startLaufzeit/12), (1+ZINSSATZ))))/(Math.pow((startLaufzeit/12), (1+ZINSSATZ))-1)))*100)/100;
		    double gesamtBetrag = (double)Math.round((startKreditHoehe+annuitaet)*100)/100;
			double monRate = gesamtBetrag/startLaufzeit;
			monRate = (double)Math.round((monRate*100))/100;
			System.out.println("MonRate: "+monRate);
			if(monRate<=haushaltsUeberschuss)
			{
				double letzteRate=0;
				if((monRate*startLaufzeit)!= gesamtBetrag)
				{
					letzteRate = gesamtBetrag-(monRate*(startLaufzeit-1));
				}
				else
				{
					letzteRate = monRate;
				}
				
				
				kw.add(new KreditWunsch(Math.rint(startKreditHoehe), startLaufzeit, monRate,letzteRate, gesamtBetrag));
				startKreditHoehe += haushaltsUeberschuss;
			}
			
			
				startLaufzeit++;
			
			if (startKreditHoehe > MAX_KREDIT)
				break;
		}
		KreditWunsch[] k = new KreditWunsch[kw.size()];
		k = kw.toArray(k);
		return k;
	}
}
