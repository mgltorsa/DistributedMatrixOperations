package co.edu.icesi;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.rmi.Naming;

import org.osoa.sca.annotations.Reference;

import co.edu.icesi.configurations.BrokerConfiguration;
import co.edu.icesi.interfaces.IBroker;
import co.edu.icesi.interfaces.IMatrixOperations;

/**
 * MatrixOperations
 */
public class MatrixOperations implements IMatrixOperations {

	private BrokerConfiguration configuration;
	
	private IBroker broker;
	
	private String serviceName;

	
	@Reference
	public void setConfiguration(BrokerConfiguration configuration) {
		this.configuration=configuration;
		try {
			registerAsService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void registerAsService() throws Exception {
		
		setBrokerStub();
		String ip = InetAddress.getLocalHost().getHostAddress();
		broker.register("rmi", ip, serviceName);
	}

	private void setBrokerStub() throws Exception {
		String brokerHost = configuration.getBrokerHost();
		int brokerPort = configuration.getBrokerPort();
		String brokerService = configuration.getBrokerService();
		broker = (IBroker) Naming.lookup("rmi://"+brokerHost+":"+brokerPort+"/"+brokerService);
	}

	@Override
	public BufferedImage rotateImage(int x, int y, int width, int height, double phi) {
		return null;
	}

	@Override
	public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2) {
		return null;
	}

}