package co.edu.icesi;

import java.awt.Point;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;

import co.edu.icesi.interfaces.IBroker;
import co.edu.icesi.interfaces.IMatrixOperations;
import co.edu.icesi.interfaces.IServer;

/**
 * Server
 */
public class Server implements IServer, Runnable {

	
	private IBroker broker;
    
    @Property
	private String service;
	
	private IMatrixOperations operations;


    @Reference(name="broker")
    public void setBalancer(IBroker broker) {
        this.broker=broker;
    }

    @Override
    public void run() {
        runTest();
    }

    private void runTest() {

        String[] info = broker.getMultiplicationService().split(":");
        String ip = info[0];
        String port = info[1];

        System.out.println(ip+":"+port);
        try {
            operations= (IMatrixOperations) Naming.lookup("rmi://"+ip+":"+port+"/"+service);
            double[][] res = operations.matrixMultiplication(new double[][]{{1},{2}}, new double[][]{{1},{2}});
            System.out.println(res);

        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    @Override
    public int[][] rotate(int[] initPoint, int[] lastPoint) {
        
        return null;
    }

    @Override
    public int[][] rotate(int[] initPoint, int[] lastPoint, int[] middlePoint) {
        return null;
    }
    
    

}