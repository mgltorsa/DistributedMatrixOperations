package co.edu.icesi.impl;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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
		System.out.println("instruction: {simple-instruction},{directory with images},{dest directory}");
		System.out.println("simple-instruction: {r,param} -> to rotate with param degrees");
		System.out.println("type exit to close app");
		runApp();

	}

	private void runApp(){
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("starting client, type instruction");		
		
		while(true){

			server.recieve("./data/source/img.jpg", "./data/dest/img.jpg", 45d);
			String line = input.readLine();
			if(line.toLowerCase().contains("exit")){
				break;
			}else{
				String[] instruction = parseInstruction(line);
				String simpleInstruction = instruction[0];
				String param = instruction[1];
				try{
					double phi = Double.parseDouble(param);
					String sourcePath = instruction[2];
					String directoryPath = instruction[3];
					server.recieve(sourcePath,diretoryPath,phi);
				}catch(Exception e){
					System.out.println("error parseando param");
				}

			}
			
		}

		System.out.println("closing...");
	}

	private String[] parseInstruction(String instruction){
		String[] info = instruction.split(",");
		return info;
	}

	@Reference(name="server")
	public void setServer(IServer server) {
		this.server = server;
	}
}
