package co.edu.icesi.interfaces;


import org.osoa.sca.annotations.Service;

@Service
public interface IServer {

    public int[][] rotate(int[] initPoint, int[] lastPoint);

    public int[][] rotate(int[] initPoint, int[] lastPoint, int[] middlePoint);
}
