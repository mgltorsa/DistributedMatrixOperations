package co.edu.icesi.interfaces;


public interface IBalancer{

	public void register(String protocol, String ip, int port, String service) throws IllegalArgumentException;

	public String getMultiplicationService(String service) throws IllegalArgumentException;

	public void notifyByService(String ip);

}
