package de.axisbank.services.filiale.direktTests;

import java.text.DecimalFormat;
import junit.framework.TestCase;
import org.junit.Test;
import de.axisbank.daos.Antragssteller;
import de.axisbank.daos.User;
import de.axisbank.daos.Versicherungen;
import de.axisbank.services.filiale.FilialBankService;
import de.axisbank.services.filiale.Tilgung;
import de.axisbank.services.filiale.Tilgungsplan;
import de.axisbank.tools.TilgungsPlanErsteller;

public class FilialBankServiceTest extends TestCase {

	private static Antragssteller antragssteller;

	private long getSessionID() {
		return new FilialBankService().login("Guido", "1411");
	}

	/**
	 * Test der Methode " testLogin()".<br />
	 * <br /><br />
	 * Beschreibung der Methode : siehe {@link FilialBankService}
	 * <br /><br />
	 * Test - Cases : <br />
	 * 1. Verbindung vorhanden<br/>
	 * 2. Login erfolgreich --> bei Erfolg SessionID({@link Long}) zurück, die nicht -1 ist <br />
	 */
	@Test
	public void testLogin() {
		FilialBankService wbs = new FilialBankService();
		long sessID = wbs.login("Guido", "1411");
		assertTrue("SessionId ist -1 --> Benutzer nicht vorhanden oder Datenbank nicht erreichbar", sessID != -1);
		System.out.println("Benutzer angemeldet: SessionID:" + sessID);
	}

	@Test
	public void testgetUser() {
		FilialBankService wbs = new FilialBankService();
		User user = wbs.getUser("Guido", getSessionID());
		if (user != null) {
			assertEquals("Benutzername stimmt nicht überein", "Guido", user.getBenutzername());
		}
	}

	@Test
	public void testGetAntragsteller() {

		String vorname = "Daniel";
		String nachname = "Schmitz";
		String gebDatum = null;
		int hauptGirokonto = -1;
		FilialBankService wbs = new FilialBankService();
		Antragssteller[] ass = wbs.getAntragssteller(vorname, nachname, gebDatum, hauptGirokonto, getSessionID());

		if (ass != null && ass.length > 0) {
			antragssteller = ass[0];
			for (Antragssteller as : ass) {
				assertTrue("Vorname enthält nicht gesuchten Vornamen", as.getVorname().contains(vorname));
				assertTrue("Vorname enthält nicht gesuchten Nachnamen", as.getNachname().contains(nachname));
			}
		}
	}

	@Test
	public void testGetLiquiditaet() {
		System.out.println("testGetLiquiditaet");
		double einnahmen = 5000, ueberschuss = 600, rateKredit = 500;
		FilialBankService wbs = new FilialBankService();
		boolean r = wbs.getLiquiditaet(einnahmen, ueberschuss, rateKredit, getSessionID());
		assertTrue("Liquidität nicht vorhanden", r);
		einnahmen = 5000;
		ueberschuss = 0;
		rateKredit = 500;
		r = wbs.getLiquiditaet(einnahmen, ueberschuss, rateKredit, getSessionID());
		assertFalse("Liquidität vorhanden", r);
	}

	@Test
	public void testGetServerInfos() {
		System.out.println("testGetServerInfos");
		FilialBankService wbs = new FilialBankService();
		String[] s = wbs.getSessionInfos("Kennwort1!");
		for (String ss : s)
			System.out.println(ss);
	}

	@Test
	public void testUpdateAntragssteller() {
		System.out.println("testUpdateAntragssteller");
		Antragssteller as = null;
		if (antragssteller != null) {
			as = antragssteller;
			as.setGebDatum_dt("12.12.2012");
		} else {
			as = new Antragssteller();
			as.setId(1);
			as.setBeruf("Hausmeister");
		}
		FilialBankService wbs = new FilialBankService();
		boolean r = wbs.updateAntragssteller(as, getSessionID());
		assertTrue("Antragssteller nicht erfolgreich aktualisiert", r);
	}

	@Test
	public void testGetTilgungsPlan() {
		System.out.println("testGetTilgungsPlan");
		double kreditHoehe = 12550;
		String kreditBeginn = "01.02.2012";
		double zinsatzDifferenz = +0.;
		double ratenHoehe = 555;
		int laufzeitMonate = -1;
		FilialBankService wbs = new FilialBankService();
		Tilgungsplan tp = wbs.getTilgungsPlan(kreditHoehe, kreditBeginn, zinsatzDifferenz, ratenHoehe, laufzeitMonate, getSessionID());
		DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
		if (tp != null) {
			System.out.println("Zinsatz liegt bei " + tp.getZinsatz() + "%");
			System.out.println("Restschuld Anfang der Laufzeit\t\tRate\tZinsanteil\tTilgung\t\tRestschuld Ende der Laufzeit");
			System.out.println("_____________________________________________________________________________________________________________");
			double summeZinsAnteil = 0.;
			double summeTilgung = 0.;
			double summeRaten = 0.;
			for (Tilgung t : tp.getTilgungen()) {
				System.out.println(t.getStartDatum() + "\t|" + (t.getStartSchuld() > 1000 ? df.format(t.getStartSchuld()) : df.format(t.getStartSchuld()) + "\t") + "\t\t|" + df.format(t.getRate())
						+ "\t\t|" + df.format(t.getZinsAnteil()) + "\t|" + df.format(t.getTilgung()) + "\t\t|" + t.getEndDatum() + "\t|" + df.format(t.getEndSchuld()));
				summeZinsAnteil += t.getZinsAnteil();
				summeTilgung += t.getTilgung();
				summeRaten += t.getRate();
			}
			System.out.println("_____________________________________________________________________________________________________________");
			System.out.println("Summen:\t\t\t\t\t|" + df.format(summeRaten) + "\t\t|" + df.format(summeZinsAnteil) + "\t|" + df.format(summeTilgung));
			assertEquals("Tilgungssumme stimmt NICHT mit Kreditwunsch nicht überein", summeTilgung, kreditHoehe, 0.005D);
		}

		kreditHoehe = 12550;
		kreditBeginn = "01.02.2012";
		zinsatzDifferenz = 0.;
		ratenHoehe = -1;
		laufzeitMonate = 23;

		wbs = new FilialBankService();
		tp = wbs.getTilgungsPlan(kreditHoehe, kreditBeginn, zinsatzDifferenz, ratenHoehe, laufzeitMonate, getSessionID());
		if (tp != null) {
			System.out.println("Zinsatz liegt bei " + tp.getZinsatz() + "%");
			System.out.println("Restschuld Anfang der Laufzeit\t\tRate\tZinsanteil\tTilgung\t\tRestschuld Ende der Laufzeit");
			System.out.println("_____________________________________________________________________________________________________________");
			double summeZinsAnteil = 0.;
			double summeTilgung = 0.;
			double summeRaten = 0.;
			for (Tilgung t : tp.getTilgungen()) {
				System.out.println(t.getStartDatum() + "\t|" + (t.getStartSchuld() > 1000 ? df.format(t.getStartSchuld()) : df.format(t.getStartSchuld()) + "\t") + "\t\t|" + df.format(t.getRate())
						+ "\t\t|" + df.format(t.getZinsAnteil()) + "\t|" + df.format(t.getTilgung()) + "\t\t|" + t.getEndDatum() + "\t|" + df.format(t.getEndSchuld()));
				summeZinsAnteil += t.getZinsAnteil();
				summeTilgung += t.getTilgung();
				summeRaten += t.getRate();
			}
			System.out.println("_____________________________________________________________________________________________________________");
			System.out.println("Summen:\t\t\t\t\t|" + df.format(summeRaten) + "\t\t|" + df.format(summeZinsAnteil) + "\t|" + df.format(summeTilgung));
			assertEquals("Tilgungssumme stimmt NICHT mit Kreditwunsch nicht überein", summeTilgung, kreditHoehe, 0.005D);
		}

	}

	@Test
	public void testInsertAntragsteller() {
		System.out.println("testInsertAntragsteller");
		System.out.println("Test Versicherung hinzufügen zum vorhandenen Antragssteller ");
		Antragssteller as = new Antragssteller();
		as.setId(1);
		Versicherungen v = new Versicherungen();
		v.setMtlBeitrag(55);
		v.setVersArt("TestVersicherung");
		v.setVersGesellschaft("TestGesellschaft");
		v.setVersSumme(50000000);
		as.setVersicherungen(new Versicherungen[] { v });
		FilialBankService wbs = new FilialBankService();
		boolean r = wbs.insertAntragssteller(as, getSessionID());
		assertTrue("Fehler beim Eintrag einer neuen Versicherung", r);
		wbs = new FilialBankService();
		Antragssteller[] ass = wbs.getAntragssteller(antragssteller.getVorname(), antragssteller.getNachname(), antragssteller.getGebDatum_dt(), antragssteller.getHauptGirokonto(), getSessionID());
		if (ass != null) {
			if (ass.length == 1) {
				Antragssteller a = ass[0];
				Versicherungen found = null;
				for (Versicherungen vs : a.getVersicherungen()) {
					if (vs.getVersArt().equals("TestVersicherung") && vs.getVersGesellschaft().equals("TestGesellschaft") && vs.getVersSumme() == 50000000) {
						found = vs;
						break;
					}
				}
				assertNotNull("vorher hinzugefügte Versicherung NICHT vorhanden", found);
				System.out.println("Hinzugefügte versicher vorhanden");
				wbs = new FilialBankService();
				int id = a.getId();
				a = new Antragssteller();
				a.setId(id);
				a.setVersicherungen(new Versicherungen[] { found });
				boolean deleted = wbs.deleteAntragssteller(a, true, getSessionID());
				assertTrue("Versicherung konnte NICHT gewlöscht werden", deleted);
			}
		}

		System.out.println("Test Versicherung hinzufügen zum neu erstellten Antragssteller ");
		as = new Antragssteller();
		as.setId(-1);
		as.setVorname("Testantragsteller");
		as.setNachname("Testantragsteller");
		as.setGebDatum_dt("05.05.2005");
		as.setHauptGirokonto(1111111);
		v = new Versicherungen();
		v.setMtlBeitrag(55);
		v.setVersArt("TestVersicherung");
		v.setVersGesellschaft("TestGesellschaft");
		v.setVersSumme(50000000);
		as.setVersicherungen(new Versicherungen[] { v });
		wbs = new FilialBankService();
		r = wbs.insertAntragssteller(as, getSessionID());
		assertTrue("Fehler beim Eintrag einer neuen Versicherung", r);
		wbs = new FilialBankService();
		ass = wbs.getAntragssteller("Testantragsteller", null, null, -1, getSessionID());
		assertTrue("neuer Antragssteller nicht vorhanden", ass != null && ass.length > 0);
		Antragssteller a = ass[0];
		as = a;
		boolean found = false;
		for (Versicherungen vs : a.getVersicherungen()) {
			if (vs.getVersArt().equals("TestVersicherung") && vs.getVersGesellschaft().equals("TestGesellschaft") && vs.getVersSumme() == 50000000) {
				found = true;
				break;
			}
		}
		assertTrue("vorher hinzugefügte Versicherung NICHT vorhanden", found);
		System.out.println("Hinzugefügte versicher vorhanden");
		wbs = new FilialBankService();
		r = wbs.deleteAntragssteller(as, false, getSessionID());
		assertTrue("testantragssteller konnte nicht gelöscht werden", r);
		System.out.println("Testantragssteller erfolgreich gelöscht.");
	}

	@Test
	public void testInsertKreditantrag() {
		System.out.println("testInsertKreditantrag");

		String status = "testFiliale";

		String verhaeltnisZu_2 = "Mutter";

		Tilgungsplan tp = new Tilgungsplan();
		tp.setKreditHoehe(5000.);
		tp.setLaufzeitMonate(12);
		tp.setZinsatz(5.59);
		tp.setKreditBeginn("12.12.2012");
		tp.setTilgungen(TilgungsPlanErsteller.erstelleTilgungsPlan(tp));

		String filiale = "PB NORD";

		Antragssteller as = new Antragssteller();
		as.setId(1);

		Antragssteller as2 = new Antragssteller();
		as2.setId(2);

		User berater = new User();
		berater.setId(1);

		FilialBankService wbs = new FilialBankService();
		boolean erfolg = wbs.insertKreditantrag(berater, as, as2, verhaeltnisZu_2, filiale, status, tp, getSessionID());
		assertTrue("Kreditantrag wurde NICHT erfolgreich eingefügt ", erfolg);
		System.out.println("Kreditantrag erfolgreich eingefügt");
	}

	@Test
	public void testLogoff() {
		FilialBankService wbs = new FilialBankService();
		boolean r = wbs.logoff(getSessionID());

		assertTrue("Abmeldung ohne Erfolg", r);
		System.out.println("Abmeldung erfolgreich");
	}
}
