package co.edu.icesi.configurations;

import org.osoa.sca.annotations.Property;

public class BrokerConfiguration implements IBrokerConfiguration {


	private String brokerHost;

	private int brokerPort;

	private String brokerService;


	@Override
	public String getBrokerHost() {
		return brokerHost;
	}

	@Override
	public void setBrokerHost(String brokerHost) {
		this.brokerHost = brokerHost;
	}

	@Override
	public int getBrokerPort() {
		return brokerPort;
	}
	
	@Override
	public void setBrokerPort(int brokerPort) {
		this.brokerPort = brokerPort;
	}

	@Override
	public String getBrokerService() {
		return brokerService;
	}

	@Override
	public void setBrokerService(String brokerService) {
		this.brokerService = brokerService;
	}



}
