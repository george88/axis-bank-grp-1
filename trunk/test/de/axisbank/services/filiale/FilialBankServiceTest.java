package de.axisbank.services.filiale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.xml.namespace.QName;

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
import de.axisbank.services.filiale.FilialBankService;

public class FilialBankServiceTest {

	private static Vector<ServiceClient> senders = new Vector<ServiceClient>();

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

		String benutzername = "Kreditberater";
		String passwort = "Superman";
		Object[] opArgs = new Object[] { benutzername, passwort };
		OMElement request = BeanUtil.getOMElement(opLogin, opArgs, null, false,
				null);

		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { boolean.class };

		Object[] result = BeanUtil.deserialize(response, returnTypes,
				new DefaultObjectSupplier());

		System.out.println("Login=: " + result[0]);
		// end Login
	}

	@Test
	public void testGetAntragsteller() throws AxisFault {
		ServiceClient sender = getServiceClient(0);

		// start getAntragsteller
		QName opGetAntragsteller = new QName(
				"http://filiale.services.axisbank.de", "getAntragsteller");

		String vorname = "Hans";
		String nachname = "Meier";
		Object[] opArgs = new Object[] { vorname, nachname };
		OMElement request = BeanUtil.getOMElement(opGetAntragsteller, opArgs,
				null, false, null);

		OMElement response = sender.sendReceive(request);

		Class<?>[] returnTypes = new Class[] { Antragssteller[].class };
		Object[] result = BeanUtil.deserialize(response, returnTypes,
				new DefaultObjectSupplier());

		if (result != null && result.length > 0) {
			Antragssteller[] antragsteller = (Antragssteller[]) result[0];
			for (Antragssteller as : antragsteller) {
				System.out.println("Anrede:" + as.getAnrede());
				System.out.println("Vorname:" + as.getVorname());
				System.out.println("Nachname:" + as.getNachname());

				if (as.getKreditantraege() != null
						&& as.getKreditantraege().length > 0) {
					System.out.println(as.getKreditantraege().length
							+ " Kreditanträge");

					System.out.println("LetzterLogin:"
							+ new Date(as.getKreditantraege()[0].getBerater()
									.getLetzterLogin()));

					System.out.println("ZweitAntragsteller"
							+ "("
							+ as.getKreditantraege()[0].getAntragssteller_2()
									.getVorname() + ")");
				}

			}
		}

	}

	@Test
	public void testlogout() throws AxisFault {
		ServiceClient sender = getServiceClient(0);

		// start Login
		QName opLogin = new QName("http://filiale.services.axisbank.de",
				"logoff");

		Object[] opArgs = new Object[] {};
		OMElement request = BeanUtil.getOMElement(opLogin, opArgs, null, false,
				null);

		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { boolean.class };
		Object[] result = BeanUtil.deserialize(response, returnTypes,
				new DefaultObjectSupplier());
		assertEquals("LogoffReturnValue", true, result[0]);
	}
}
