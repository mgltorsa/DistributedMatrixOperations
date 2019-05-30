package co.edu.icesi;

import java.rmi.Naming;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import co.edu.icesi.configurations.IBrokerConfiguration;
import co.edu.icesi.interfaces.IBroker;

/**
 * MatrixOperations
 */
@Service(interfaces = Runnable.class)
public class MatrixOperationsRegister implements Runnable {

	@Reference
	private IBrokerConfiguration configuration;
	
	private IBroker broker;
	
	@Property
	private int port;
	
	@Property
	private String serviceName;
	
	

	

	private void registerAsService() throws Exception {
		
		setBrokerStub();
		// String ip = InetAddress.getLocalHost().getHostAddress();
		String ip = "localhost";
		System.out.println("Myip->"+ip);
		System.out.println("service name->"+serviceName);
		broker.register("rmi", ip, port, serviceName);
	}

	private void setBrokerStub() throws Exception {
		String brokerHost = configuration.getBrokerHost();
		int brokerPort = configuration.getBrokerPort();
		String brokerService = configuration.getBrokerService();
		
		System.out.println("rmi://"+brokerHost+":"+brokerPort+"/"+brokerService);
		broker = (IBroker) Naming.lookup("rmi://"+brokerHost+":"+brokerPort+"/"+brokerService);
	}
	
	@Override
	public void run() {
		try {
			registerAsService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}