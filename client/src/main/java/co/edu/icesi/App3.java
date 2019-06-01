package co.edu.icesi;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;

public class App3 {
	
	public static void main(String[] args) {
		try {
			File image = new File("./data/source/image.jpg");
			ImageInputStream iis = ImageIO.createImageInputStream(image);

			ImageReader r = ImageIO.getImageReaders(iis).next();
			ImageWriter w = ImageIO.getImageWriter(r);

			r.setInput(iis);

			ImageReadParam param = r.getDefaultReadParam();
			param.setSourceRegion(new Rectangle(0, 0, ic.getHeight(), ic.getWidth()));
			BufferedImage bi = r.read(0, param);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
