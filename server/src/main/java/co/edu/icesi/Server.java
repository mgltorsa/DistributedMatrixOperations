package co.edu.icesi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
public class Server extends UnicastRemoteObject implements IServer, Runnable {

	private static final long serialVersionUID = 1L;

	private String service;

	private BrokerConfiguration configuration;

	private IMatrixOperations operations;

	private IBroker broker;

	public Server() throws RemoteException {
		super();
	}

	@Override
	public void run() {
		BrokerConfiguration configuration = new BrokerConfiguration();
		configuration.setBrokerHost("192.168.131.20");
		configuration.setBrokerPort(7777);
		configuration.setBrokerService("redirecting");
		setBrokerConfiguration(configuration);
		try {
			setupServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void setupServer() throws Exception {
		LocateRegistry.createRegistry(5000);
		Naming.rebind("rmi://localhost:5000/server", this);
		
	}

	// @Reference
	public void setBrokerConfiguration(BrokerConfiguration configuration) {
		this.configuration=configuration;
		try {
			setBrokerStub();
			runTestBroker();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void rotate(){
		System.out.println("Rotating");
	}

	
	private void runTestBroker() throws Exception{
		service="operations";
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
