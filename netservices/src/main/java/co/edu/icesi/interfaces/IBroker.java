package co.edu.icesi.interfaces;

import java.util.List;

public interface IBroker {

	public void register(String protocol, String ip, String service);

	public List<String> getServicesIP(String protocol, String service);
}
