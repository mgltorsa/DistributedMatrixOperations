<<<<<<< HEAD
package co.edu.icesi.interfaces;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.osoa.sca.annotations.Service;

@Service
public interface IServer extends Remote {

    public int[][] rotate(Point init, Point last) throws RemoteException;
}
=======
package co.edu.icesi.interfaces;

import org.osoa.sca.annotations.Service;

@Service
public interface IServer {

	public void recieve(String sourcePath, String destPath, double phi);

}
>>>>>>> d4755263fc315e725c44e61eecea6eef0e37914e
