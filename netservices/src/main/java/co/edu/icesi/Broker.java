package co.edu.icesi;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import co.edu.icesi.interfaces.IBroker;

/**
 * Hello world!
 *
 */
public class Broker extends UnicastRemoteObject implements IBroker {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Broker() throws RemoteException {
		super();
		System.out.println("Broker initialized");
	}

	private static HashMap<String, List<String>> ipsByService = new HashMap<String, List<String>>();

	@Override
	public void register(String protocol, String ip, int port, String service) throws IllegalArgumentException {

		if (service == null || ip == null || protocol == null) {
			throw new IllegalArgumentException("values cannot be null");
		}
		if (service.isEmpty() || ip.isEmpty() || protocol.isEmpty()) {
			throw new IllegalArgumentException("values cannot be empty");
		}

		if (!ipsByService.containsKey(service)) {
			ipsByService.put(service, new ArrayList<String>());
		}

		
		ipsByService.get(service).add(ip+":"+port);
		
		System.out.println("Printing services");
		for(List<String> services : ipsByService.values()) {
			for(String s : services) {
				System.out.println("s->"+s);
			}
		}

	}

	/**
	 * list, a list of string with a format as ip:port
	 */
	@Override
	public List<String> getServicesIP(String service) throws IllegalArgumentException {
		if (service == null ) {
			throw new IllegalArgumentException("values cannot be null");
		}
		if (service.isEmpty()) {
			throw new IllegalArgumentException("values cannot be empty");
		}
		List<String> ips = new ArrayList<String>();
		List<String> byService = ipsByService.get(service);

		if (byService.isEmpty()) {
			throw new IllegalArgumentException("Doesn't exists with these protocols or services");
		}

		ips.addAll(filterReachableIps(byService, service));		
		

		return ips;
	}

	private Collection<? extends String> filterReachableIps(List<String> by, String service) {
				
		List<String> ips = new ArrayList<String>();		
		for (String host : by) {
			try {
				InetAddress inet = InetAddress.getByName(host);
				if(inet.isReachable(200)) {
					ips.add(host);
				}else {
					ipsByService.get(service).remove(host);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return ips;
	}
}
