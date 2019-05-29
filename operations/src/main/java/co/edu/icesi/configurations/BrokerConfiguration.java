package co.edu.icesi.configurations;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Service;

@Service
public class BrokerConfiguration {

	
	@Property
	private String brokerHost;
	
	@Property
	private int brokerPort;
	
	@Property
	private String brokerService;

	public String getBrokerHost() {
		return brokerHost;
	}

	public void setBrokerHost(String brokerHost) {
		this.brokerHost = brokerHost;
	}

	public int getBrokerPort() {
		return brokerPort;
	}

	public void setBrokerPort(int brokerPort) {
		this.brokerPort = brokerPort;
	}

	public String getBrokerService() {
		return brokerService;
	}

	public void setBrokerService(String brokerService) {
		this.brokerService = brokerService;
	}
	
	
	
	
	
}
