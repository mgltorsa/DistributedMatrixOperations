package co.edu.icesi;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import co.edu.icesi.interfaces.IBroker;

/**
 * Hello world!
 *
 */
public class Broker implements IBroker {
	
	/**
	 * 
	 */
	

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

	@Override
	public String getMultiplicationService(String service) throws IllegalArgumentException {
		return null;
	}

	
}
