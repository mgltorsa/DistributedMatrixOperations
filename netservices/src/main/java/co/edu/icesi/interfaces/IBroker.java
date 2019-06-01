package co.edu.icesi.interfaces;


import org.osoa.sca.annotations.Service;

@Service
public interface IBroker{

	public void register(String ip, int port, String service) throws IllegalArgumentException;

	public String getMultiplicationService() throws IllegalArgumentException;

	public void notifyByService(String ip);


}
