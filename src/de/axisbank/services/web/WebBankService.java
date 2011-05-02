package de.axisbank.services.web;

import de.axisbank.daos.Rueckzahlungsplan;

public class WebBankService {

	public WebBankService() {

	}

	public Rueckzahlungsplan getRueckzahlungsPlan() {
		return new Rueckzahlungsplan();
	}

}
