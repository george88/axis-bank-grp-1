package de.axisbank.services.web;

import de.axisbank.services.Tilgungsplan;

public class WebBankService {

	public WebBankService() {

	}

	public Tilgungsplan getTilgungsPlan(int haushaltsUeberschuss) {
		System.out.println("Hü:" + haushaltsUeberschuss);
		return new Tilgungsplan();
	}

}
