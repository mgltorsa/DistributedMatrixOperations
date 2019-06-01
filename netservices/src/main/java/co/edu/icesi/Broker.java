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

	private static HashMap<String, Service> ipsByService = new HashMap<String, Service>();
	private static PriorityQueue<Service> priorityQueue = new PriorityQueue<Service>(10, new Comparator<Service>() {

		@Override
		public int compare(Service o1, Service o2) {
			return o1.getWork() == o2.getWork() ? 0:o1.getWork()<o2.getWork() ? -1 : 1;
		}
	});

	private static List<Service> tiffProcessors = new ArrayList<Service>();

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

		if(service.equals("operations")){
			priorityQueue.add(objService);
		}else{
			tiffProcessors.add(objService);
		}

	}

	@Override
	public String getMultiplicationService() throws IllegalArgumentException {


			Service objService=priorityQueue.poll();
			int work = objService.getWork()+1;
			objService.setWork(work);
			priorityQueue.add(objService);
			ipsByService.get(objService.getIp()).setWork(work);
			return objService.getIp()+":"+objService.getPort();

	}

	@Override
	public void notifyByService(String ip) {

		// synchronized (this){
		Service service = ipsByService.get(ip);
		priorityQueue.remove(service);
		int work = service.getWork()-1;
		service.setWork(work);
		priorityQueue.add(service);
		// notify();		
		// }
	}

	@Override
	public String[] getTiffProcessors() throws IllegalArgumentException {
		
		String[] processors = new String[tiffProcessors.size()];
		return tiffProcessors.toArray(processors);
	}

	
}
