package co.edu.icesi;

import java.awt.Point;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import co.edu.icesi.configurations.BrokerConfiguration;
import co.edu.icesi.interfaces.IBroker;
import co.edu.icesi.interfaces.IMatrixOperations;
import co.edu.icesi.interfaces.IServer;

/**
 * Hello world!
 *
 */
@Service(interfaces=Runnable.class)
public class ServerWrapper implements Runnable {
	
	@Reference
	private BrokerConfiguration configuration;

	@Property
	private String service;


	private IMatrixOperations operations;

	private IBroker broker;

	@Override
	public void run() {
		try {
			setBrokerStub();
			runTestBroker();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void runTestBroker() throws Exception{
		System.out.println("Service->"+service);
		List<String> ips = broker.getServicesIP(service);
		System.out.println("printing ips");
		for(String ip: ips) {
			System.out.println("ip->"+ip);
			String[] info = ip.split(":");
			String ipr = info[0];
			String port = info[1];
			System.out.println("ipr-> "+ipr+" port-> "+port);
			operations = (IMatrixOperations)Naming.lookup("rmi://"+ipr+":"+port+"/"+service);
			operations.rotateImage(0, 0, 0, 0, 0);
		}
	}

	private void setBrokerStub() throws Exception {
		String brokerHost = configuration.getBrokerHost();
		int brokerPort = configuration.getBrokerPort();
		String brokerService = configuration.getBrokerService();
		broker = (IBroker) Naming.lookup("rmi://"+brokerHost+":"+brokerPort+"/"+brokerService);
	}
	
}
