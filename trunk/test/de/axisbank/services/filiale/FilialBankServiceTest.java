package de.axisbank.services.filiale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.engine.DefaultObjectSupplier;
import org.junit.Test;

import de.axisbank.daos.Antragssteller;
import de.axisbank.services.filiale.FilialBankService;

public class FilialBankServiceTest {

	@Test
	public void testGetLiquidity() {

	}

	@Test
	public void testGetRepaymentPlan() {
		// fail("Not yet implemented");
	}

	@Test
	public void testlogin() throws AxisFault {
		ServiceClient sender = new ServiceClient();
		Options options = sender.getOptions();
		EndpointReference targetEPR = new EndpointReference(
				"http://localhost:8080/axis2/services/FilialBankService");
		options.setTo(targetEPR);

		QName opLogin = new QName("http://filiale.services.axisbank.de",
				"login");

		String benutzername = "Hans";
		String passwort = "Meier";
		Object[] opArgs = new Object[] { benutzername, passwort };
		OMElement request = BeanUtil.getOMElement(opLogin, opArgs, null, false,
				null);

		OMElement response = sender.sendReceive(request);

		Class<?>[] returnTypes = new Class[] { boolean.class };
		Object[] result = BeanUtil.deserialize(response, returnTypes,
				new DefaultObjectSupplier());

		System.out.println("Loginergebnis: " + result[0]);
	}

	@Test
	public void testGetAntragsteller() throws AxisFault {
		ServiceClient sender = new ServiceClient();
		Options options = sender.getOptions();
		EndpointReference targetEPR = new EndpointReference(
				"http://localhost:8080/axis2/services/FilialBankService");
		options.setTo(targetEPR);
		options.setManageSession(true);
		// start Login
		QName opLogin = new QName("http://filiale.services.axisbank.de",
				"login");

		String benutzername = "Hans";
		String passwort = "Meier";
		Object[] opArgs = new Object[] { benutzername, passwort };
		OMElement request0 = BeanUtil.getOMElement(opLogin, opArgs, null,
				false, null);

		OMElement response0 = sender.sendReceive(request0);

		Class<?>[] returnTypes0 = new Class[] { boolean.class };
		Object[] result0 = BeanUtil.deserialize(response0, returnTypes0,
				new DefaultObjectSupplier());

		System.out.println("Login=: " + result0[0]);
		// end Login
		// start getAntragsteller
		QName opGetAntragsteller = new QName(
				"http://filiale.services.axisbank.de", "getAntragsteller");

		String vorname = "Hans";
		String nachname = "Meier";
		opArgs = new Object[] { vorname, nachname };
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
				System.out.println(as.getKreditantraege().length
						+ " Kreditanträge");
				System.out.println("ZweitAntragsteller"
						+ "("
						+ as.getKreditantraege()[0].getAntragssteller_2()
								.getVorname() + ")");
			}
		}

	}
}
