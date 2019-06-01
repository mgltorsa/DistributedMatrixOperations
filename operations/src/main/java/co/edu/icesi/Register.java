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
public class Register implements Runnable {

	private IBroker broker;

	@Property
	private int portTiff;

	@Property
	private String tiffServiceName;

	@Reference(name = "broker")
	public void setBalancer(IBroker broker) {

		this.broker = broker;

	}	

	@Override
	public void run() {
		try {
			registerServices();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void registerServices() throws Exception {
		
		String ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println("Myip->"+ip);
		broker.register(ip, portTiff, tiffServiceName);
		System.out.println("signed in");
	}
	

}