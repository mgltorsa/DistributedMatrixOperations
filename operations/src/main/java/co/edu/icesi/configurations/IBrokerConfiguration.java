package co.edu.icesi.configurations;


/**
 * IBrokerConfiguration
 */
public interface IBrokerConfiguration {

    
    public String getBrokerHost();

	public void setBrokerHost(String brokerHost);

	public int getBrokerPort();
 
	public void setBrokerPort(int brokerPort);

	public String getBrokerService();

	public void setBrokerService(String brokerService);
}