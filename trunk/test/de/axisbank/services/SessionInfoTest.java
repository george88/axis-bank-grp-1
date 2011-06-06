package de.axisbank.services;

import javax.xml.namespace.QName;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.engine.DefaultObjectSupplier;
import org.apache.log4j.*;
import org.junit.Test;
import junit.framework.TestCase;

public class SessionInfoTest extends TestCase {
	private static Logger l = Logger.getRootLogger();

	@Test
	public void testgetSessionInfo() throws AxisFault {
		l.addAppender(new ConsoleAppender(new SimpleLayout()));
		l.setLevel(Level.WARN);
		ServiceClient sender = new ServiceClient();
		Options options = sender.getOptions();
		EndpointReference targetEPR = new EndpointReference("http://localhost:9080/axis2/services/FilialBankService");
		// EndpointReference targetEPR = new
		// EndpointReference("http://japp.george4j.de/axis2/services/FilialBankService");
		options.setTo(targetEPR);

		QName opGetSessionInfos = new QName("http://filiale.services.axisbank.de", "getSessionInfos");
		String passwort = "Kennwort1!";
		Object[] opArgs = new Object[] { passwort };
		OMElement request = BeanUtil.getOMElement(opGetSessionInfos, opArgs, null, false, null);
		OMElement response = sender.sendReceive(request);
		Class<?>[] returnTypes = new Class[] { String.class };
		Object[] result = BeanUtil.deserialize(response, returnTypes, new DefaultObjectSupplier());
		System.out.println(result[0]);

	}
}
