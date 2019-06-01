package co.edu.icesi.interfaces;


public interface IBroker{

	public void register(String ip, int port, String service) throws IllegalArgumentException;

	public String[] getTiffProcessors(int quantity) throws IllegalArgumentException;

	public int getTotalProcessors();
}
