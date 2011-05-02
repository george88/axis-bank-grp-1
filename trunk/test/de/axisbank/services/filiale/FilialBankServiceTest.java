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

import de.axisbank.daos.Antragsteller;
import de.axisbank.services.filiale.FilialBankService;

public class FilialBankServiceTest {

	@Test
	public void testGetLiquidity() {
		FilialBankService service = new FilialBankService();
		service.login("test", "test");
		assertEquals("testGetLiquidity", 2.5, service.getLiquiditaet(), 2.5);
	}

	@Test
	public void testGetRepaymentPlan() {
		// fail("Not yet implemented");
	}

	@Test
	public void login() {
		FilialBankService service = new FilialBankService();
		assertTrue("login", service.login("test", "test"));
	}

	@Test
	public void testWSDL() throws AxisFault {
		ServiceClient sender = new ServiceClient();
		Options options = sender.getOptions();
		EndpointReference targetEPR = new EndpointReference(
				"http://localhost:8080/axis2/services/Filiale_Axis_Bank");
		options.setTo(targetEPR);

		QName opGetAntragsteller = new QName(
				"http://filiale.services.axisbank.de", "getAntragsteller");

		String vorname = "hans";
		String nachname = "meier";
		Object[] opArgs = new Object[] { vorname, nachname };
		OMElement request = BeanUtil.getOMElement(opGetAntragsteller, opArgs,
				null, false, null);

		OMElement response = sender.sendReceive(request);

		Class[] returnTypes = new Class[] { Antragsteller[].class };
		Object[] result = BeanUtil.deserialize(response, returnTypes,
				new DefaultObjectSupplier());

		Antragsteller[] antragsteller = (Antragsteller[]) result[0];
		for (Antragsteller as : antragsteller) {
			System.out.println("Anrede:" + as.getAnrede());
			System.out.println("Vorname:" + as.getVorname());
			System.out.println("Nachname:" + as.getNachname());
		}

	}

}
