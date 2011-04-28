package de.axis_bank.services.web;

import de.axis_bank.daos.Rueckzahlungsplan;

public class WebBankService {

	public WebBankService() {

	}

	public Rueckzahlungsplan getRueckzahlungsPlan() {
		return new Rueckzahlungsplan();
	}

}
