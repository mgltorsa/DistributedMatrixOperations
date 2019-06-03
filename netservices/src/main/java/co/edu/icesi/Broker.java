package co.edu.icesi;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.HashMap;

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