package co.edu.icesi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.HashMap;

import co.edu.icesi.interfaces.IBroker;

/**
 * Hello world!
 *
 */
public class Broker extends UnicastRemoteObject implements IBroker, Runnable {

	private static final long serialVersionUID = 1L;

	public Broker() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */

	private static HashMap<String, Service> ipsByService = new HashMap<String, Service>();
	private static LinkedList<Service> tiffProcessors = new LinkedList<Service>();
	private static LinkedList<Service> serializers = new LinkedList<Service>();
	private static int currentService=0;


	@Override
	public void run() {
		try {
			System.out.println("binding...");
			LocateRegistry.createRegistry(5555);
			Naming.rebind("rmi://localhost:5555/redirecting", this);
			System.out.println("binded");
			
		} catch (Exception e) {
			//TODO: handle exceptionmatrixOperations
		}

	}

	@Override
	public void register(String ip, int port, String service) throws IllegalArgumentException {

		if (service == null || ip == null ) {
			throw new IllegalArgumentException("values cannot be null");
		}
		if (service.isEmpty() || ip.isEmpty()) {
			throw new IllegalArgumentException("values cannot be empty");
		}

		Service objService = new Service(service, ip, port);
		ipsByService.put(ip, objService);

		if(service.equals("tiff")){
			tiffProcessors.add(objService);
		}else if(service.equals("serializer")){
			serializers.add(objService);
		}
		
	

	}



	@Override
	public String[] getTiffProcessors(int quantity) throws IllegalArgumentException {
	
		String[] services = new String[quantity];
		for (int i = 0; i < quantity; i++) {
			services[i]=getNextTiffProcessor();
		}
		
		return services;
	}

	private String getNextTiffProcessor() {
		
		if(currentService>tiffProcessors.size()){
			currentService=0;
		}
		String processor = tiffProcessors.get(currentService++).toString();
		return processor;
	}

	@Override
	public int getTotalProcessors() {
		return tiffProcessors.size();
	}

	@Override
	public String[] getImageSerializers() throws IllegalArgumentException {
		
		
		String[] services = new String[serializers.size()];
		for (int i = 0; i < services.length; i++) {
			services[i]=serializers.get(i).toString();
		}
		
		return services;
	}
	
}