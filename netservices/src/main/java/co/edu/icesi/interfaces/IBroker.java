package co.edu.icesi.interfaces;

import java.util.List;

import org.osoa.sca.annotations.Service;

@Service
public interface IBroker{

	public void register(String protocol, String ip, int port, String service) throws IllegalArgumentException;

	public List<String> getServicesIP(String service) throws IllegalArgumentException;

}
