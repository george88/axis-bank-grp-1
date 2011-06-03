package de.axisbank.services.filiale.remoteTests;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.engine.DefaultObjectSupplier;
import org.junit.Test;

import de.axisbank.daos.Antragssteller;
import de.axisbank.daos.Arbeitgeber;
import de.axisbank.daos.Einnahmen;
import de.axisbank.daos.Kreditantrag;
import de.axisbank.daos.User;
import de.axisbank.daos.Versicherungen;
import de.axisbank.services.Tilgung;
import de.axisbank.services.Tilgungsplan;
import de.axisbank.services.filiale.FilialBankService;

public class FilialBankServiceTest extends TestCase {

	private static ServiceClient sender;

	static long sessionID;
	static Antragssteller antragssteller;

	private ServiceClient getServiceClient() throws AxisFault {
		if (sender == null) {
			sender = new ServiceClient();
			Options options = sender.getOptions();
			EndpointReference targetEPR = new EndpointReference("http://localhost:9080/axis2/services/FilialBankService");
			// EndpointReference targetEPR = new
			// EndpointReference("http://japp.george4j.de/axis2/services/FilialBankService");
			options.setTo(targetEPR);
		}
		return sender;
	}

	/**
	 * Test der Methode " testLogin()".<br />
	 * <br /><br />
	 * Beschreibung der Methode : siehe {@link FilialBankService}
	 * <br /><br />
	 * Test - Cases : <br />
	 * 1. Verbindung vorhanden<br/>
	 * 2. Remotefunktion erreichbar<br />
	 * 3. Login erfolgreich --> bei Erfolg SessionID({@link Long}) zur�ck, die nicht -1 ist <br />
	 */
	@Test
	public void testLogin() throws AxisFault {
		ServiceClient sender = getServiceClient();
		// start Login
		QName opLogin = new QName("http://filiale.services.axisbank.de", "login");

		String benutzername = "1";
		String passwort = "1";
		Object[] opArgs = new Object[] { benutzername, passwort };
		OMElement request = BeanUtil.getOMElement(opLogin, opArgs, null, false, null);

		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { Long.class };

		Object[] result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());

		System.out.println("SessionID =" + result[0]);
		sessionID = (Long) result[0];
		assertEquals("Es wurde ein Wert ungleich 0 erwartet", true, (Long) result[0] != -1);
		// end Login
	}

	@Test
	public void testgetUser() throws AxisFault {
		ServiceClient sender = getServiceClient();
		// start Login
		QName opLogin = new QName("http://filiale.services.axisbank.de", "getUser");

		String benutzername = "1";
		Object[] opArgs = new Object[] { benutzername, sessionID };
		OMElement request = BeanUtil.getOMElement(opLogin, opArgs, null, false, null);

		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { User.class };

		Object[] result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());

		if (result != null && result.length > 0 && result[0] instanceof User) {
			User user = (User) result[0];
			Method[] methods = user.getClass().getMethods();
			for (Method m : methods) {
				if (m.getName().startsWith("get")) {
					try {
						System.out.println(m.getName().substring(3) + "\t\t=" + m.invoke(user, new Object[] {}));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Test
	public void testGetAntragsteller() throws AxisFault {

		ServiceClient sender = getServiceClient();

		// start getAntragsteller
		QName opGetAntragsteller = new QName("http://filiale.services.axisbank.de", "getAntragssteller");

		String vorname = "Daniel";
		String nachname = "Schmitz";
		String gebDatum = null;
		int hauptGirokonto = -1;
		System.out.println(sessionID);
		Object[] opArgs = new Object[] { vorname, nachname, gebDatum, hauptGirokonto, sessionID };
		OMElement request = BeanUtil.getOMElement(opGetAntragsteller, opArgs, null, false, null);

		OMElement response = sender.sendReceive(request);

		Class<?>[] returnTypes = new Class[] { Antragssteller[].class };
		Object[] result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());

		if (result != null && result.length > 0) {
			Antragssteller[] antragsteller = (Antragssteller[]) result[0];
			if (((Antragssteller[]) result[0]).length > 0)
				antragssteller = ((Antragssteller[]) result[0])[0];

			for (Antragssteller as : antragsteller) {
				System.out.println("Anrede:" + as.getAnrede());
				System.out.println("Vorname:" + as.getVorname());
				System.out.println("Nachname:" + as.getNachname());

				if (as.getKreditantraege() != null) {

					System.out.println("\n\n" + as.getKreditantraege().length + "Kreditantr�ge:\n");
					for (Kreditantrag ka : as.getKreditantraege()) {
						System.out.println("");
						try {
							System.out.println("Berater:" + ka.getBerater().getBenutzername());
						} catch (Exception e) {
						}

						System.out.println("Kredithoehe:" + ka.getKreditWunsch());
						try {
							System.out.println("Zweitantragssteller:" + ka.getAntragssteller_2().getVorname() + "\n\n");
							System.out.println("VerhaeltnisZu2:" + ka.getVerhaeltnisZu_2() + "\n\n");
						} catch (Exception e) {
						}
						System.out.println("");
					}
				}
				if (as.getEinnahmen() != null) {

					System.out.println("\n\n" + as.getEinnahmen().length + "Einnahmen:\n");
					for (Einnahmen e : as.getEinnahmen()) {
						System.out.println("");
						System.out.println("Art der Einnahme:" + e.getArt());

						System.out.println("Betrag:" + e.getBetrag());

						System.out.println("");
					}
				}
				if (as.getVersicherungen() != null) {

					System.out.println("\n\n" + as.getVersicherungen().length + "Versicherungen:\n");
					for (Versicherungen e : as.getVersicherungen()) {
						System.out.println("");
						System.out.println("Art der Versicherunge:" + e.getVersArt());
						System.out.println("Name der Versicherunge:" + e.getVersGesellschaft());

						System.out.println("Monatlicher Betrag:" + e.getMtlBeitrag());

						System.out.println("");
					}
				}
				System.out.println("Arbeitgeber:");
				if (as.getArbeitgeber() != null)
					for (Arbeitgeber ag : as.getArbeitgeber())
						System.out.println(ag.getNameArbeitgeber());
			}
		}
	}

	@Test
	public void testGetLiquiditaet() throws AxisFault {

		ServiceClient sender = getServiceClient();

		double einnahmen = 5000, ueberschuss = 200, rateKredit = 500;
		QName opLogin = new QName("http://filiale.services.axisbank.de", "getLiquiditaet");

		Object[] opArgs = new Object[] { einnahmen, ueberschuss, rateKredit, sessionID };
		OMElement request = BeanUtil.getOMElement(opLogin, opArgs, null, false, null);

		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { Boolean.class };

		Object[] result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());

		System.out.println("Liqidit�t: " + result[0]);
		// end Login
	}

	@Test
	public void testGetServerInfos() throws AxisFault {

		ServiceClient sender = getServiceClient();

		// start Login
		QName opLogin = new QName("http://filiale.services.axisbank.de", "getServerInfos");

		Object[] opArgs = new Object[] { "Kennwort1!", 0, "" };
		OMElement request = BeanUtil.getOMElement(opLogin, opArgs, null, false, null);

		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { String.class };

		Object[] result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());

		System.out.println("ServerInfo= " + result[0]);
		// end Login
	}

	@Test
	public void testUpdateAntragssteller() throws AxisFault {
		ServiceClient sender = getServiceClient();

		// start getAntragsteller
		QName opGetAntragsteller = new QName("http://filiale.services.axisbank.de", "updateAntragssteller");
		if (antragssteller != null) {
			Antragssteller as = antragssteller;
			as.setGebDatum_dt("12.12.2012");
			System.out.println(sessionID);

			Object[] opArgs = new Object[] { as, sessionID };
			OMElement request = BeanUtil.getOMElement(opGetAntragsteller, opArgs, null, false, null);

			OMElement response = sender.sendReceive(request);

			Class<?>[] returnTypes = new Class[] { boolean.class };
			Object[] result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());
			boolean results = (Boolean) result[0];
			System.out.println(" Datensatz geupdated:" + results);
		}
	}

	@Test
	public void testGetTilgungsPlan() throws AxisFault {
		ServiceClient sender = getServiceClient();

		// start Login
		QName opgetTilgungsPlan = new QName("http://filiale.services.axisbank.de", "getTilgungsPlan");

		double kreditHoehe = 12550;
		String kreditBeginn = "01.02.2012";
		double zinsatzDifferenz = +0.;
		double ratenHoehe = 555;
		int laufzeitMonate = -1;

		Object[] opArgs = new Object[] { kreditHoehe, kreditBeginn, zinsatzDifferenz, ratenHoehe, laufzeitMonate, sessionID };
		OMElement request = BeanUtil.getOMElement(opgetTilgungsPlan, opArgs, null, false, null);

		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { Tilgungsplan.class };
		Object[] result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());
		DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
		if (result != null && result.length > 0) {
			Tilgungsplan tp = (Tilgungsplan) result[0];
			if (tp != null) {
				System.out.println("Zinsatz liegt bei " + tp.getZinsatz() + "%");
				System.out.println("Restschuld Anfang der Laufzeit\t\tRate\tZinsanteil\tTilgung\t\tRestschuld Ende der Laufzeit");
				System.out.println("_____________________________________________________________________________________________________________");
				double summeZinsAnteil = 0.;
				double summeTilgung = 0.;
				double summeRaten = 0.;
				for (Tilgung t : tp.getTilgungen()) {
					System.out.println(t.getStartDatum() + "\t|" + (t.getStartSchuld() > 1000 ? df.format(t.getStartSchuld()) : df.format(t.getStartSchuld()) + "\t") + "\t\t|"
							+ df.format(t.getRate()) + "\t\t|" + df.format(t.getZinsAnteil()) + "\t|" + df.format(t.getTilgung()) + "\t\t|" + t.getEndDatum() + "\t|" + df.format(t.getEndSchuld()));
					summeZinsAnteil += t.getZinsAnteil();
					summeTilgung += t.getTilgung();
					summeRaten += t.getRate();
				}
				System.out.println("_____________________________________________________________________________________________________________");
				System.out.println("Summen:\t\t\t\t\t|" + df.format(summeRaten) + "\t\t|" + df.format(summeZinsAnteil) + "\t|" + df.format(summeTilgung));
			}
		}

		kreditHoehe = 12550;
		kreditBeginn = "01.02.2012";
		zinsatzDifferenz = 0.;
		ratenHoehe = -1;
		laufzeitMonate = 23;

		opArgs = new Object[] { kreditHoehe, kreditBeginn, zinsatzDifferenz, ratenHoehe, laufzeitMonate, sessionID };
		request = BeanUtil.getOMElement(opgetTilgungsPlan, opArgs, null, false, null);

		response = sender.sendReceive(request);
		result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());
		if (result != null && result.length > 0) {
			Tilgungsplan tp = (Tilgungsplan) result[0];
			if (tp != null) {
				System.out.println("Zinsatz liegt bei " + tp.getZinsatz() + "%");
				System.out.println("Restschuld Anfang der Laufzeit\t\tRate\tZinsanteil\tTilgung\t\tRestschuld Ende der Laufzeit");
				System.out.println("_____________________________________________________________________________________________________________");
				double summeZinsAnteil = 0.;
				double summeTilgung = 0.;
				double summeRaten = 0.;
				for (Tilgung t : tp.getTilgungen()) {
					System.out.println(t.getStartDatum() + "\t|" + (t.getStartSchuld() > 1000 ? df.format(t.getStartSchuld()) : df.format(t.getStartSchuld()) + "\t") + "\t\t|"
							+ df.format(t.getRate()) + "\t\t|" + df.format(t.getZinsAnteil()) + "\t|" + df.format(t.getTilgung()) + "\t\t|" + t.getEndDatum() + "\t|" + df.format(t.getEndSchuld()));
					summeZinsAnteil += t.getZinsAnteil();
					summeTilgung += t.getTilgung();
					summeRaten += t.getRate();
				}
				System.out.println("_____________________________________________________________________________________________________________");
				System.out.println("Summen:\t\t\t\t\t|" + df.format(summeRaten) + "\t\t|" + df.format(summeZinsAnteil) + "\t|" + df.format(summeTilgung));
			}
		}
	}

	@Test
	public void testInsertAntragsteller() throws AxisFault {
		ServiceClient sender = getServiceClient();

		// start getAntragsteller
		QName opInsertAntragsteller = new QName("http://filiale.services.axisbank.de", "insertAntragssteller");

		Antragssteller as = antragssteller;
		if (as != null) {
			as.setGebDatum_dt("12.12.2012");
			System.out.println(sessionID);
			as.setEinnahmen(new Einnahmen[] { new Einnahmen("Strichen", 5.99) });

			Object[] opArgs = new Object[] { as, sessionID };
			OMElement request = BeanUtil.getOMElement(opInsertAntragsteller, opArgs, null, false, null);

			OMElement response = sender.sendReceive(request);

			Class<?>[] returnTypes = new Class[] { boolean.class };
			Object[] result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());
			boolean results = (Boolean) result[0];
			System.out.println(" Datensatz inserted:" + results);
		}
	}

	@Test
	public void testlogoff() throws AxisFault {
		ServiceClient sender = getServiceClient();

		// start Login
		QName opLogin = new QName("http://filiale.services.axisbank.de", "logoff");

		Object[] opArgs = new Object[] { sessionID };
		OMElement request = BeanUtil.getOMElement(opLogin, opArgs, null, false, null);

		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { boolean.class };
		Object[] result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());
		System.out.println("Logoff( " + sessionID + " ):" + result[0]);
		assertEquals("LogoffReturnValue", true, result[0]);
		testGetServerInfos();
	}
}
