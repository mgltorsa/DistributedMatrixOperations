package co.edu.icesi.interfaces;

import java.awt.Point;

import org.osoa.sca.annotations.Service;

@Service
public interface IServer {

    public int[][] rotate(Point init, Point last) ;
}
