<<<<<<< HEAD
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
		// Descomentar y arreglar, no retorna nada
		// List<String> byService = ipsByService.get(service);

		// if (byService.isEmpty()) {
		// 	throw new IllegalArgumentException("Doesn't exists with these protocols or services");
		// }

		// ips.addAll(filterReachableIps(byService, service));		

		//para test, comentar luego
		for(List<String> ss : ipsByService.values()){
			for(String str : ss){
				ips.add(str);
			}
		}

		

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
=======
package co.edu.icesi;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import co.edu.icesi.interfaces.IBroker;

/**
 * Hello world!
 *
 */
public class Broker implements IBroker {

	/**
	 * 
	 */

	private static HashMap<String, Service> ipsByService = new HashMap<String, Service>();
	private static LinkedList<Service> queue = new LinkedList<Service>();


	@Override
	public void register(String ip, int port, String service) throws IllegalArgumentException {

		if (service == null || ip == null ) {
			throw new IllegalArgumentException("values cannot be null");
		}
		if (service.isEmpty() || ip.isEmpty()) {
			throw new IllegalArgumentException("values cannot be empty");
		}

		Service objService = new Service(ip, port);
		ipsByService.put(ip, objService);

		
		queue.add(objService);
		
	


	}



	@Override
	public String[] getTiffProcessors(int quantity) throws IllegalArgumentException {
	
		String[] services = new String[quantity];
		int limit = quantity<queue.size() ? quantity : queue.size();
		for (int i = 0; i < limit; i++) {
			services[i]=queue.get(i).toString();
		}
		return services;
	}

	@Override
	public int getTotalProcessors() {
		return queue.size();
	}
	
}
>>>>>>> d4755263fc315e725c44e61eecea6eef0e37914e
