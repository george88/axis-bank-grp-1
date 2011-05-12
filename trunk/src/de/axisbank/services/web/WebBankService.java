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
		double zinssatz = 0.0595;
		
		
		double startKreditHoehe = minkredit;
		int startLaufzeit = laufzeitmin;

		if ((startKreditHoehe / haushaltsUeberschuss) < 12) {
			startKreditHoehe = ((haushaltsUeberschuss * 12)*0.90);
		}

		Vector<KreditWunsch> kw = new Vector<KreditWunsch>();

		while (startLaufzeit <= laufzeitmax) {
			double annuitaet = (double)Math.round((startKreditHoehe *(((zinssatz*Math.pow((laufzeitmax/12), (1+zinssatz))))/(Math.pow((laufzeitmax/12), (1+zinssatz))-1)))*100)/100;
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
			
			if (startKreditHoehe > maxKredit)
				break;
		}
		KreditWunsch[] k = new KreditWunsch[kw.size()];
		k = kw.toArray(k);
		return k;
	}
}
