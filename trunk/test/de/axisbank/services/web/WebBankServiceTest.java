package de.axisbank.services.web;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Testet die Klasse WebBankService <br>
 * <br>
 * <p>
 * Test-Cases: <br>
 * 1. Testen der Methode getTilgungsPlanDurchRate - uebergibt die Gewuenscht
 * Rate und prueft die Rueckgabe<br>
 * 2. Testen der Methode getTilgungsPlanDurchBetragLaufzeit - uebergibt
 * Kredithoehe und Laufzeit und erwartet eine Kreditauskunft zu diesen Werten
 * </p>
 * 
 * @author PrivateRyan
 * 
 */
public class WebBankServiceTest extends TestCase {

	WebBankService wbs = new WebBankService();

	@Test
	public void testGetTilgungsPlanDurchRate() {
		int ueberschuss = 200;
		KreditWunsch[] kw = wbs.getTilgungsPlanDurchRate(ueberschuss);
		assertTrue(kw[0].getKreditHoehe() == 5000D
				&& kw[0].getMonRate() <= (double) ueberschuss);
	}

	@Test
	public void testGetTilgungsPlanDurchBetragLaufzeit() {
		int laufZeit = 12;
		double kreditHoehe = 10000;
		KreditWunsch kw = wbs.getTilgungsPlanDurchBetragLaufzeit(kreditHoehe,
				laufZeit);
		assertTrue(kw.getKreditHoehe() == kreditHoehe
				&& kw.getLaufzeit() == laufZeit
				&& kw.getGesamtBetrag() > kreditHoehe);
	}
}
