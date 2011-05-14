package de.axisbank.services.web;

import java.util.Vector;

/**
 * Diese Klasse liefert alle Methoden fuer die Internetanwendung der Axis Bank
 * 
 * @version 13.05.2011
 * @author Robert Beese
 */

public class WebBankService {
	/**************************Konstanten*************************************/
	static final int MIN_LAUFZEIT = 12;
	static final int MAX_LAUFZEIT = 84;
	static final double MIN_KREDIT = 5000;
	static final double MAX_KREDIT = 50000;
	static final double ZINSSATZ = 5.59;
	
	/**************************Konstruktor*************************************/
	public WebBankService() {

	}
	/**************************public Methoden*************************************/
	
	/**
	*Diese Methode bekommt eine Wunschrate und berechnet daraus die m�glichen Kreditkonstellation.
	*
	*@param haushaltsUeberschuss - h�he der monatlichen Rate
	*@return KreditWunsch[] - ein Array mit Kreditw�nschen
	*/
	public KreditWunsch[] getTilgungsPlanDurchRate(int haushaltsUeberschuss) {

		/**************************Variablen*************************************/
		double kreditHoehe = MIN_KREDIT; //Kredithoehe mit der die Berechnung begonnen wird
											  //initial = MIN_KREDIT
		int laufZeit = MIN_LAUFZEIT; //Laufzeit bei der die Berechnung begonnen wird
									 //initial = MIN_LAUFZEIT
		double monZins; //monatlicher Zins
		double monRate; //monatliche Rate
		double gesamtBetrag; //Gesamtkosten des Kredites
		double letzteRate=0; //letze Rate des Kredites
		Vector<KreditWunsch> bufferVector = new Vector<KreditWunsch>(); //Vector in der die Berechnungen zwischengespeichert werden
		KreditWunsch[] rueckgabeArray; //R�ckgabearray
		
		/**************************Code*************************************/
		//Wenn der haushaltsUeberschuss den MIN_KREDIT in weniger als MIN_LAUFZEIT
		//zurueckzahlen kann, wird die startKreditHoehe neu gesetzt
		if ((MIN_KREDIT / haushaltsUeberschuss) < MIN_LAUFZEIT) {
			kreditHoehe = ((haushaltsUeberschuss * 12)*0.90);
		}
		
		//Wenn die Starthoehe groesser als der Maximalkredit ist, dann wird nichts berechnet
		if(kreditHoehe<=MAX_KREDIT)
		{
			//solange die Maximallaufzeit nicht erreicht ist
			while (laufZeit <= MAX_LAUFZEIT) {
				
				
				monZins = (Math.pow((1 + (ZINSSATZ/100)),(1d/12d))) - 1; //berechne monatlichen Zins 
				monRate = (kreditHoehe * monZins * Math.pow((1 + monZins),laufZeit) / (-1 + Math.pow((1 + monZins),laufZeit))); //berechne monatl. Rate
				gesamtBetrag = monRate *laufZeit; //berechne Gesamtbetrag
				
				monRate = (double)Math.round((monRate*100))/100; //mon. Rate zur Darstellung runden
				
				//erzeugt nur einen Eintrag wenn die mon. Rate den Haushaltsueberschuss nicht
				//ueberschreitet
				if(monRate<=haushaltsUeberschuss)
				{
					//Berechnung der letzten Rate
					if((monRate*laufZeit)!= gesamtBetrag)
					{
						letzteRate = gesamtBetrag-(monRate*(laufZeit-1));
					}
					else
					{
						letzteRate = monRate;
					}
					bufferVector.add(new KreditWunsch(Math.rint(kreditHoehe), laufZeit, monRate,letzteRate, gesamtBetrag)); //Objekt dem Vector hinzufuegen
					kreditHoehe += haushaltsUeberschuss; // Naechste Kredithoehe
				}	
				laufZeit++;//Laufzeit um einen Monat erhoehen
				
				//Abbruch wenn Maximalkredit erreicht ist
				if (kreditHoehe > MAX_KREDIT)
					break;
			}			
		}
		rueckgabeArray = new KreditWunsch[bufferVector.size()]; //Groesse des Rueckgabearrays festlegen
		rueckgabeArray = bufferVector.toArray(rueckgabeArray); //Zwischenspeicher im Rueckgabearray ablegen
		return rueckgabeArray;//Rueckgabearray zurueckgeben
	}
	
	/**
	*Diese Methode bekommt eine Wunschrate und berechnet daraus die m�glichen Kreditkonstellation.
	*
	*@param haushaltsUeberschuss - h�he der monatlichen Rate
	*@return KreditWunsch[] - ein Array mit Kreditw�nschen
	*/
	public KreditWunsch getTilgungsPlanDurchBetragLaufzeit(double kreditHoehe,int laufZeit)
	{
		KreditWunsch kreditWunsch = new KreditWunsch();
		double monZins; //monatlicher Zins
		double monRate = 0; //monatliche Rate
		double gesamtBetrag = 0; //Gesamtkosten des Kredites
		double letzteRate=0; //letze Rate des Kredites
		
		//wird nur ausgefuehrt, wenn die uebergebenen Werte in Rahmen liegen
		if(kreditHoehe>=MIN_KREDIT && kreditHoehe<=MAX_KREDIT && laufZeit >=MIN_LAUFZEIT && laufZeit <=MAX_LAUFZEIT)
		{
			monZins = (Math.pow((1 + (ZINSSATZ/100)),(1d/12d))) - 1; //berechne monatlichen Zins 
			monRate = (kreditHoehe * monZins * Math.pow((1 + monZins),laufZeit) / (-1 + Math.pow((1 + monZins),laufZeit))); //berechne monatl. Rate
			gesamtBetrag = monRate *laufZeit; //berechne Gesamtbetrag
			
			monRate = (double)Math.round((monRate*100))/100; //mon. Rate zur Darstellung runden
			
			//Berechnung der letzten Rate
			if((monRate*laufZeit)!= gesamtBetrag)
			{
				letzteRate = gesamtBetrag-(monRate*(laufZeit-1));
			}
			else
			{
				letzteRate = monRate;
			}
			
			//Objekt setzen
			kreditWunsch.setKreditHoehe(kreditHoehe);
			kreditWunsch.setLaufzeit(laufZeit);
			kreditWunsch.setMonRate(monRate);
			kreditWunsch.setLetzteRate(letzteRate);
			kreditWunsch.setGesamtBetrag(gesamtBetrag);
		}
		else
		{
			kreditWunsch = null; 
		}
		
		return kreditWunsch;
	}
}
