package de.axisbank.services.web;

import java.util.Vector;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class WebBankServiceTest {
	private static Vector<ServiceClient> senders = new Vector<ServiceClient>();

	private ServiceClient getServiceClient(int nr) throws AxisFault {
		ServiceClient sc = null;
		if (senders.size() >= (nr + 1) && senders.get(nr) != null) {
			sc = senders.get(nr);
		} else {
			while (senders.size() < (nr + 1)) {
				ServiceClient s = new ServiceClient();
				Options options = s.getOptions();
				EndpointReference targetEPR = new EndpointReference("http://localhost:9080/axis2/services/FilialBankService");
				options.setTo(targetEPR);
				senders.add(s);
			}
			sc = senders.get(nr);
		}
		return sc;
	}

}
