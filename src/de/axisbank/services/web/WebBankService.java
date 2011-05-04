package de.axisbank.services.web;

import de.axisbank.daos.Tilgungsplan;

public class WebBankService {

	public WebBankService() {

	}

	public Tilgungsplan getRueckzahlungsPlan() {
		return new Tilgungsplan();
	}

}
