package co.edu.icesi;

import java.rmi.Naming;


import org.osoa.sca.annotations.Service;

import co.edu.icesi.configurations.BrokerConfiguration;
import co.edu.icesi.interfaces.IBroker;

/**
 * MatrixOperations
 */
@Service(interfaces = Runnable.class)
public class MatrixOperationsRegister implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BrokerConfiguration configuration;
	
	private IBroker broker;
	
	private int port;
	
	private String serviceName;
	
	
	public void setConfiguration(BrokerConfiguration configuration) {
		this.configuration=configuration;
		try {
			registerAsService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private void registerAsService() throws Exception {
		
		setBrokerStub();
		// String ip = InetAddress.getLocalHost().getHostAddress();
		String ip = "192.168.131.20";
		System.out.println("Myip->"+ip);
		System.out.println("service name->"+serviceName);
		broker.register("rmi", ip, 8888, serviceName);
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
		port=8888;
		serviceName="operations";
		BrokerConfiguration configuration = new BrokerConfiguration();
		configuration.setBrokerHost("192.168.131.20");
		configuration.setBrokerPort(7777);
		configuration.setBrokerService("redirecting");
		setConfiguration(configuration);
	}

}