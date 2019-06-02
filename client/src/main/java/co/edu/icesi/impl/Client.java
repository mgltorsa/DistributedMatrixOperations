package co.edu.icesi.impl;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import co.edu.icesi.interfaces.IServer;

@Service(interfaces = Runnable.class)
public class Client implements Runnable {

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
