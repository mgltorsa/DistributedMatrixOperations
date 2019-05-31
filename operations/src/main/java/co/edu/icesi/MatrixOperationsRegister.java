package co.edu.icesi;

import java.net.InetAddress;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import co.edu.icesi.interfaces.IBroker;

/**
 * MatrixOperations
 */
@Service
public class MatrixOperationsRegister {

	private IBroker broker;

	@Property
	private int port;

	@Property
	private String serviceName;

	@Reference(name = "broker")
	public void setBroker(IBroker broker) {

		this.broker = broker;
		try {
			registerAsService();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	


	private void registerAsService() throws Exception {
		
		String ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println("Myip->"+ip);
		System.out.println("service name->"+serviceName);
		broker.register("rmi", ip, port, serviceName);
	}
	

}