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

		// Die Operation "findHotel" soll aufgerufen werden
		QName opFindHotel = new QName("http://axisbank.de", "getAntragsteller");

		// Die Parameter für die Operation "findHotel"
		// werden definiert...
		String hotelCode = "AX050";
		Object[] opArgs = new Object[] { hotelCode };

		// ...und ein AXIOM-OMElement mit der
		// Request-Nachricht erzeugt
		OMElement request = BeanUtil.getOMElement(opFindHotel, opArgs, null,
				false, null);

		// Der Request wird an den Service abgeschickt.
		// Der Aufruf erfolgt synchron mit dem
		// Kommunikationsmuster IN-OUT
		OMElement response = sender.sendReceive(request);

		// Diese Typen sollte der Web Service zurückliefern...
		Class[] returnTypes = new Class[] { Antragsteller.class };

		// ...und werden mit einer Hilfsroutine in ein
		// Objekt-Array überführt
		Object[] result = BeanUtil.deserialize(response, returnTypes,
				new DefaultObjectSupplier());

		Antragsteller hotel = (Antragsteller) result[0];

	}

}
