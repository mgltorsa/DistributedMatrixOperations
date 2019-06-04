package co.edu.icesi.impl;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;

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
		
		try {
			setServer((IServer) Naming.lookup("rmi://localhost:5001/server"));
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		runApp();

	}

	private void runApp(){
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("starting client, type instruction");		
		
		while(true){
			System.out.println("instruction: {simple-instruction},{directory with images},{dest directory}");
		System.out.println("simple-instruction: {r,param} -> to rotate with param degrees");
		System.out.println("type exit to close app");

			// server.recieve("./data/source/img.jpg", "./data/dest/img.jpg", 45d);
			try{
			String line = input.readLine();
			if(line.toLowerCase().equals("exit")){
				break;
			}else{
				String[] instruction = parseInstruction(line);
				String simpleInstruction = instruction[0];
				String param = instruction[1];
				
				double phi = Double.parseDouble(param);
				String sourcePath = instruction[2];
				String directoryPath = instruction[3];
				server.recieve(sourcePath,directoryPath,phi);
			}

			}catch(Exception e){
				e.printStackTrace();
			}
			
		}

		System.out.println("closing...");
	}

	private String[] parseInstruction(String instruction){
		String[] info = instruction.split(",");
		return info;
	}

	// @Reference(name="server")
	public void setServer(IServer server) {
		this.server = server;
	}
}
