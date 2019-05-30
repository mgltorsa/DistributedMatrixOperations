package co.edu.icesi;

import java.rmi.Naming;
import java.util.List;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import co.edu.icesi.interfaces.IBroker;
import co.edu.icesi.interfaces.IMatrixOperations;

/**
 * Hello world!
 *
 */
@Service(interfaces=Runnable.class)
public class ServerWrapper implements Runnable {
	
	@Property
	private String service;


	private IMatrixOperations operations;

	private IBroker broker;
	
	@Reference(name="broker")
	public void setBroker(IBroker broker) {
		this.broker=broker;
	}

	@Override
	public void run() {
		try {
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
	
	
}
