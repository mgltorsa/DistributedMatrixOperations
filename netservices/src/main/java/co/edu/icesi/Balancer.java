package co.edu.icesi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import co.edu.icesi.interfaces.IBalancer;

/**
 * Hello world!
 *
 */
public class Balancer implements IBalancer {

	/**
	 * 
	 */

	private static HashMap<String, Service> ipsByService = new HashMap<String, Service>();
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

		Service objService = new Service(ip, port);
		ipsByService.put(ip, objService);
		priorityQueue.add(objService);

		notify();

	}

	@Override
	public String getMultiplicationService(String service) throws IllegalArgumentException {

		while (priorityQueue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		synchronized (this){
			Service objService=priorityQueue.poll();
			int work = objService.getWork()+1;
			objService.setWork(work);
			priorityQueue.add(objService);
			ipsByService.get(objService.getIp()).setWork(work);
			return objService.getIp()+":"+objService.getPort();
		}

	}

	@Override
	public void notifyByService(String ip) {

		synchronized (this){
		Service service = ipsByService.get(ip);
		priorityQueue.remove(service);
		int work = service.getWork()-1;
		service.setWork(work);
		priorityQueue.add(service);
		notify();		
		}
	}

	
}
