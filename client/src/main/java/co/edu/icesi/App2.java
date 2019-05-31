package co.edu.icesi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

public class App2 {

	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = writers.next();
		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//		param.setCompressionQuality(scale);
		ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
		writer.setOutput(ios);

		File f = new File("./data/dest/test.jpg");
		FileImageOutputStream output = new FileImageOutputStream(f);

		BufferedImage bf = new BufferedImage(30000, 30000, BufferedImage.TYPE_INT_RGB);
		IIOImage image = new IIOImage(bf, null, null);

//		writer.write(null, new IIOImage(bf, null, null), param);
//		byte[] data = baos.toByteArray();
		writer.write(null, image, param);
		writer.dispose();
		ios.close();
		baos.close();
//		return data;
	}

}
