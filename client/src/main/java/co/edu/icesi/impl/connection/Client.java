package co.edu.icesi.impl.connection;

import org.oasisopen.sca.annotation.Reference;

import co.edu.icesi.interfaces.IServer;

public class Client implements Runnable{
	
	private IServer server;
	
	public Client() {
		// TODO Auto-generated constructor stub
	}

	public IServer getServer() {
		return server;
	}

	@Reference(name = "server")
	public void setServer(IServer server) {
		this.server = server;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	

}
