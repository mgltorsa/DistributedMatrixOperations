package co.edu.icesi;

import java.rmi.Naming;
import java.util.List;

import org.osoa.sca.annotations.Reference;

import co.edu.icesi.configurations.BrokerConfiguration;
import co.edu.icesi.interfaces.IBroker;
import co.edu.icesi.interfaces.IServer;

/**
 * Hello world!
 *
 */
public class Server implements IServer
{
    
	private String service;
	
	private BrokerConfiguration configuration;
	
	private IBroker broker;
	
	
	@Reference
	public void setBrokerConfiguration(BrokerConfiguration configuration) {
		this.configuration=configuration;
		try {
			setBrokerStub();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		runTestBroker();
	}
	
	private void runTestBroker() {
		System.out.println("Service->"+service);
		List<String> ips = broker.getServicesIP(service);
		System.out.println("printing ips");
		for(String ip: ips) {
			System.out.println("ip->"+ip);
		}
	}

	private void setBrokerStub() throws Exception {
		String brokerHost = configuration.getBrokerHost();
		int brokerPort = configuration.getBrokerPort();
		String brokerService = configuration.getBrokerService();
		broker = (IBroker) Naming.lookup("rmi://"+brokerHost+":"+brokerPort+"/"+brokerService);
	}
	
}
