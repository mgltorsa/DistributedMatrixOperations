package co.edu.icesi.interfaces;

import org.osoa.sca.annotations.Service;

@Service(interfaces=Runnable.class)
public interface IServer {

    public void rotate();
}
