package co.edu.icesi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

public class App3 {
	public static void main(String[] args) throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tif");
		ImageWriter writer = writers.next();
		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//		param.setCompressionQuality(scale);
		ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
		writer.setOutput(ios);

		File f = new File("./data/dest/image.jpg");
		FileImageOutputStream output = new FileImageOutputStream(f);

		BufferedImage bf = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
		IIOImage image = new IIOImage(bf, null, null);
		writer.prepareInsertEmpty(0, ImageTypeSpecifier., width, height, imageMetadata, thumbnails, param);
		System.out.println(writer.canInsertImage(0));
//		writer.write(null, new IIOImage(bf, null, null), param);
//		byte[] data = baos.toByteArray();
//		writer.write(null, image, param);
		writer.dispose();
		ios.close();
		baos.close();
//		return data;
		
	}
}
