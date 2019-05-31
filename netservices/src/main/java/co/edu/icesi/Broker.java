package co.edu.icesi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
	

	private static HashMap<String, List<Service>> ipsByService = new HashMap<String, List<Service>>();
	private static PriorityQueue<Service> priorityQueue = new PriorityQueue<Service>(new Comparator<Service>() {

		@Override
		public int compare(Service o1, Service o2) {
			return Integer.compare(o1.getWork(), o2.getWork());
		}
	});

	@Override
	public void register(String protocol, String ip, int port, String service) throws IllegalArgumentException {

		if (service == null || ip == null || protocol == null) {
			throw new IllegalArgumentException("values cannot be null");
		}
		if (service.isEmpty() || ip.isEmpty() || protocol.isEmpty()) {
			throw new IllegalArgumentException("values cannot be empty");
		}

		if (!ipsByService.containsKey(service)) {
			ipsByService.put(service, new ArrayList<Service>());
		}

		
		Service objService = new Service(ip, port);
		ipsByService.get(service).add(objService);
		priorityQueue.add(objService);

	}

	@Override
	public String getMultiplicationService(String service) throws IllegalArgumentException {

		Service objService=priorityQueue.poll();
		objService.setWork(objService.getWork()+1);
		priorityQueue.add(objService);

		return objService.getIp()+":"+objService.getPort();
	}

	
}
