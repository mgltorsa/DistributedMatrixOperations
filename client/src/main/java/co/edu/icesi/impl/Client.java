package co.edu.icesi.impl;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import co.edu.icesi.interfaces.IServer;

@Service(interfaces=Runnable.class)
public class Client implements Runnable{
	
	private IServer server;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		server.recieve("./data/source/image.jpg", "Hola", Double.parseDouble("30"));
	}

	@Reference(name="server")
	public void setServer(IServer server) {
		this.server = server;
	}
}
