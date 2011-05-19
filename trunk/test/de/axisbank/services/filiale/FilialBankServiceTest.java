package de.axisbank.services.filiale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.engine.DefaultObjectSupplier;
import org.apache.axis2.util.Loader;
import org.apache.log4j.Logger;
import org.junit.Test;

import de.axisbank.daos.Antragssteller;
import de.axisbank.daos.Arbeitgeber;
import de.axisbank.daos.DaoObject;
import de.axisbank.daos.Einnahmen;
import de.axisbank.daos.Kreditantrag;
import de.axisbank.daos.Versicherungen;
import de.axisbank.services.filiale.FilialBankService;

public class FilialBankServiceTest extends TestCase {

	private static Vector<ServiceClient> senders = new Vector<ServiceClient>();

	private static long sessionID;
	private static Antragssteller antragssteller;

	private static ServiceClient getServiceClient(int nr) throws AxisFault {
		ServiceClient sc = null;
		if (senders.size() >= (nr + 1) && senders.get(nr) != null) {
			sc = senders.get(nr);
		} else {
			while (senders.size() < (nr + 1)) {
				ServiceClient s = new ServiceClient();
				Options options = s.getOptions();
				EndpointReference targetEPR = new EndpointReference(
						"http://localhost:9080/axis2/services/FilialBankService");
				options.setTo(targetEPR);
				// options.setManageSession(true);
				senders.add(s);
			}
			sc = senders.get(nr);
		}
		return sc;
	}

	@Test
	public void testGetLiquidity() {

	}

	@Test
	public void testGetRepaymentPlan() {
		// fail("Not yet implemented");
	}

	@Test
	public void testlogin() throws AxisFault {
		ServiceClient sender = getServiceClient(0);

		// start Login
		QName opLogin = new QName("http://filiale.services.axisbank.de",
				"login");

		String benutzername = "Test";
		String passwort = "test";
		Object[] opArgs = new Object[] { benutzername, passwort };
		OMElement request = BeanUtil.getOMElement(opLogin, opArgs, null, false,
				null);

		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { Long.class };

		Object[] result = BeanUtil.deserialize(response, returnTypes,
				new DefaultObjectSupplier());

		System.out.println("SessionID=: " + result[0]);
		sessionID = (Long) result[0];
		// end Login
	}

	@Test
	public void testGetAntragsteller() throws AxisFault {

		ServiceClient sender = getServiceClient(0);

		// start getAntragsteller
		QName opGetAntragsteller = new QName(
				"http://filiale.services.axisbank.de", "getAntragssteller");

		String vorname = "Da";
		String nachname = "Schmi";
		String gebDatum = null;
		int hauptGirokonto = -1;
		System.out.println(sessionID);
		Object[] opArgs = new Object[] { vorname, nachname, gebDatum,
				hauptGirokonto, sessionID };
		OMElement request = BeanUtil.getOMElement(opGetAntragsteller, opArgs,
				null, false, null);

		OMElement response = sender.sendReceive(request);

		Class<?>[] returnTypes = new Class[] { Antragssteller[].class };
		Object[] result = BeanUtil.deserialize(response, returnTypes,
				new DefaultObjectSupplier());

		if (result != null && result.length > 0) {
			Antragssteller[] antragsteller = (Antragssteller[]) result[0];
			antragssteller = ((Antragssteller[]) result[0])[0];

			for (Antragssteller as : antragsteller) {
				System.out.println("Anrede:" + as.getAnrede());
				System.out.println("Vorname:" + as.getVorname());
				System.out.println("Nachname:" + as.getNachname());

				if (as.getKreditantraege() != null) {

					System.out.println("\n\n" + as.getKreditantraege().length
							+ "Kreditanträge:\n");
					for (Kreditantrag ka : as.getKreditantraege()) {
						System.out.println("");
						try {
							System.out.println("Berater:"
									+ ka.getBerater().getBenutzername());
						} catch (Exception e) {
						}

						System.out.println("Kredithoehe:"
								+ ka.getKreditWunsch());
						try {
							System.out.println("Zweitantragssteller:"
									+ ka.getAntragssteller_2().getVorname()
									+ "\n\n");
						} catch (Exception e) {
						}
						System.out.println("");
					}
				}
				if (as.getEinnahmen() != null) {

					System.out.println("\n\n" + as.getEinnahmen().length
							+ "Einnahmen:\n");
					for (Einnahmen e : as.getEinnahmen()) {
						System.out.println("");
						System.out.println("Art der Einnahme:" + e.getArt());

						System.out.println("Betrag:" + e.getBetrag());

						System.out.println("");
					}
				}
				if (as.getVersicherungen() != null) {

					System.out.println("\n\n" + as.getVersicherungen().length
							+ "Versicherungen:\n");
					for (Versicherungen e : as.getVersicherungen()) {
						System.out.println("");
						System.out.println("Art der Versicherunge:"
								+ e.getVersArt());
						System.out.println("Name der Versicherunge:"
								+ e.getVersGesellschaft());

						System.out.println("Monatlicher Betrag:"
								+ e.getMtlBeitrag());

						System.out.println("");
					}
				}
				System.out.println("Arbeitgeber:");
				for (Arbeitgeber ag : as.getArbeitgeber())
					System.out.println(ag.getNameArbeitgeber());
			}
		}
	}

	@Test
	public void testUpdateAntragssteller() throws AxisFault {
		ServiceClient sender = getServiceClient(0);

		// start getAntragsteller
		QName opGetAntragsteller = new QName(
				"http://filiale.services.axisbank.de", "updateAntragssteller");

		Antragssteller as = antragssteller;
		as.setGebDatum_dt("12.12.2012");
		System.out.println(sessionID);

		Object[] opArgs = new Object[] { as, sessionID };
		OMElement request = BeanUtil.getOMElement(opGetAntragsteller, opArgs,
				null, false, null);

		OMElement response = sender.sendReceive(request);

		Class<?>[] returnTypes = new Class[] { int.class };
		Object[] result = BeanUtil.deserialize(response, returnTypes,
				new DefaultObjectSupplier());
		int results = (Integer) result[0];
		System.out.println(results + " Datensatz/Datensätze geupdated");
	}

	@Test
	public void testlogout() throws AxisFault {
		ServiceClient sender = getServiceClient(0);

		// start Login
		QName opLogin = new QName("http://filiale.services.axisbank.de",
				"logoff");

		Object[] opArgs = new Object[] { sessionID };
		OMElement request = BeanUtil.getOMElement(opLogin, opArgs, null, false,
				null);

		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { boolean.class };
		Object[] result = BeanUtil.deserialize(response, returnTypes,
				new DefaultObjectSupplier());
		System.out.println("Logoff( " + sessionID + " ):" + result[0]);
		assertEquals("LogoffReturnValue", true, result[0]);
	}
}
