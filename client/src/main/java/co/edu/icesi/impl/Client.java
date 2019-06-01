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
		server.recieve("./data/source/img.jpg", "./data/dest/img.jpg", 45d);
	}

	@Reference(name="server")
	public void setServer(IServer server) {
		this.server = server;
	}
}
