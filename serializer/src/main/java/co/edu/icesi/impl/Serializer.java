package co.edu.icesi.impl;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.sun.prism.Image;

import org.w3c.dom.ls.LSOutput;

import main.java.co.edu.icesi.interfaces.ISerializer;

/**
 * Serializer
 */
public class Serializer implements ISerializer{

    private static int workers;
	
	private static int lock;
	
    private static String destPath;
    
    private static String sourcePath;
    
    public Serializer(){

    }

	public boolean isLocked() {
		if(lock == 0)
			return true;
		return false;
	}

	public int getWorkers() {
		return workers;
	}

	@Override
	public void setWorkers(int workers) {
		Serializer.workers = workers;
	}

	public static int getLock() {
		return lock;
	}

	public static void setLock(int lock) {
		Serializer.lock = lock;
	}

	public static String getDestPath() {
		return destPath;
	}

	@Override
	public void setDestPath(String destPath) {
		this.destPath = destPath;
    }
    
    @Override
	public void setSourcePath(String sourcePath) {
		this.sourcePath= sourcePath;
	}

	@Override
	public void drawImage(int x, int y, int width, int height, int[][] points) {
		// TODO Auto-generated method stub
		BufferedImage originalImageChunk = getImageChunk(x, y, width, height, sourcePath);
        
        int lastPoint = width*height;

        int xLeft = points[0][lastPoint];

        int xRight = points[0][lastPoint+1];

        int yBottom = points[1][lastPoint];

        int yTop = points[1][lastPoint+1];

        BufferedImage newImageChunk = new BufferedImage(xRight-xLeft, yTop-yBottom, BufferedImage.TYPE_INT_RGB);

        double w = width;

        for(int i = 0; i < lastPoint; i++){

            int c = lastPoint%width;
            int r = (lastPoint/w);

            int newX = points[0][i]-xLeft;
            int newY = points[1][i]-yBottom;

            newImageChunk.setRGB(newX, newY, originalImageChunk.getRGB(c, r));
        }

		saveImageChunk(newImageChunk, destPath);
		
    }
    
    public BufferedImage getImageChunk(int x, int y, int width, int height, String source){
        File image = new File(source);
        ImageInputStream iis = ImageIO.createImageInputStream(image);
        ImageReader ir = ImageIO.getImageReaders(iis).next();

        ir.setInput(iis);

        ImageReadParam irp = ir.getDefaultReadParam();
        irp.setSourceRegion(new Rectangle(x, y, width, height));
        BufferedImage bi = ir.read(0, irp);

        ir.dispose();
        iis.close();

        return bi;
    }

    public BufferedImage saveImageChunk(BufferedImage chunk, String dest){
        File image = new File(dest);
        ImageOutputStream ios = ImageIO.createImageOutputStream(image);
        ImageReader ir = ImageIO.getImageReaders(ios).next();
        ImageWriter iw = ImageIO.getImageWriter(ir);

        iw.setOutput(ios);
        iw.write(null, new IIOImage(chunk, null, null), null);
        iw.dispose();
        ios.close();

        return bi;
    }
}