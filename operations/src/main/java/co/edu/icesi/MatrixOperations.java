package co.edu.icesi;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import co.edu.icesi.configurations.BrokerConfiguration;
import co.edu.icesi.interfaces.IBroker;
import co.edu.icesi.interfaces.IMatrixOperations;

/**
 * MatrixOperations
 */
@Service(interfaces = Runnable.class)
public class MatrixOperations extends UnicastRemoteObject implements IMatrixOperations, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BrokerConfiguration configuration;
	
	private IBroker broker;
	
	private int port;
	
	private String serviceName;
	
	public MatrixOperations() throws RemoteException {
		super();
	}
	
	public void setConfiguration(BrokerConfiguration configuration) {
		this.configuration=configuration;
		try {
			registerAsService();
			setupServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setupServer() throws Exception {
		LocateRegistry.createRegistry(8888);
		Naming.rebind("rmi://localhost:8888/operations", this);
		
	}

	private void registerAsService() throws Exception {
		
		setBrokerStub();
		String ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println("Myip->"+ip);
		System.out.println("service name->"+serviceName);
		broker.register("rmi", ip, 8888, serviceName);
		serviceName=Math.random()*20+"ss";
		System.out.println("service name->"+serviceName);
		broker.register("rmi", ip, 9999, serviceName);
	}

	private void setBrokerStub() throws Exception {
		String brokerHost = configuration.getBrokerHost();
		int brokerPort = configuration.getBrokerPort();
		String brokerService = configuration.getBrokerService();
		
		System.out.println("rmi://"+brokerHost+":"+brokerPort+"/"+brokerService);
		broker = (IBroker) Naming.lookup("rmi://"+brokerHost+":"+brokerPort+"/"+brokerService);
	}

	@Override
	public int[][] rotateImage(int x, int y, int width, int height, double phi) {
		return null;
	}

	@Override
	public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2) {
		return null;
	}
	
	@Override
	public void run() {
		port=8888;
		serviceName="operations";
		BrokerConfiguration configuration = new BrokerConfiguration();
		configuration.setBrokerHost("172.30.176.134");
		configuration.setBrokerPort(7777);
		configuration.setBrokerService("redirecting");
		setConfiguration(configuration);
	}

}