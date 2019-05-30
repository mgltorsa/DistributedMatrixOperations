package co.edu.icesi;

import java.awt.Point;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.osoa.sca.annotations.Service;

import co.edu.icesi.interfaces.IServer;

/**
 * Hello world!
 *
 */
@Service(interfaces = Runnable.class)
public class AppClient implements Runnable {
    private IServer server;

    @Override
    public void run() {
        try {
            server = (IServer) Naming.lookup("rmi://192.168.131.20:5000/server");
            int[][] matrix = server.rotate(new Point(0, 1), new Point(5, 5));
            System.out.println("printing matrix");
            System.out.println("{");
            for(int[] row : matrix){
                for(int data : row){
                    System.out.print(data+",");
                }
                System.out.println();
            }
            System.out.println("}");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
