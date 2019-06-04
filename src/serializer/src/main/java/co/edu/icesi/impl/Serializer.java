package co.edu.icesi.impl;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.*;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.w3c.dom.ls.LSOutput;

import java.net.InetAddress;
import java.io.Serializable;

import co.edu.icesi.interfaces.ISerializer;
import co.edu.icesi.interfaces.IBroker;



import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;

import java.io.File;

/**
 * Serializer
 */
public class Serializer extends UnicastRemoteObject implements ISerializer, Runnable, Serializable{

    private static int workers;
	
    private static int lock = 1;
    
    private static int lock2 = 1;
	
    private static String destPath;
    
    private static String sourcePath;

    private static int currentSerialization;

    private static int currentBuffered;


    @Property
    private String serviceName;

    @Property
    private int port;

    private IBroker broker;

    public Serializer() throws RemoteException {
        super();
    }

    @Reference(name = "broker")
	public void setBalancer(IBroker broker) {
		this.broker = broker;
	}

    @Override
    public void run(){
        try {
			registerServices();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void registerServices() throws Exception {
		
		String ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println("Myip->"+ip);
		broker.register(ip, port, serviceName);
		System.out.println("signed in");
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
    
    @Override
    public boolean isPathLocked() throws RemoteException {
        return lock2==0;
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
        System.out.println("destPath-> "+destPath);
        this.destPath = destPath;
        this.lock2=0;
    }
    
    @Override
	public void setSourcePath(String sourcePath) {
        System.out.println("sourcepath-> "+sourcePath);
        this.sourcePath= sourcePath;
        this.lock2=0;
	}

	@Override
	public void drawImage(int x, int y, int width, int height, int[][] points) {
        //0 is locked
        lock=0;
        System.out.println("locked");

        System.out.println("x-> "+x);
        System.out.println("y-> "+y);
        System.out.println("width-> "+width);
        System.out.println("height-> "+height);

		// TODO Auto-generated method stub
		BufferedImage originalImageChunk = getImageChunk(x, y, width, height, sourcePath);
        
        int lastPoint = width*height;

        int xLeft = points[0][lastPoint];

        int xRight = points[0][lastPoint+1];

        int yBottom = points[1][lastPoint];

        int yTop = points[1][lastPoint+1];

        BufferedImage newImageChunk = new BufferedImage(xRight-xLeft, yTop-yBottom, originalImageChunk.getType());

        double w = width;

        for(int i = 0; i < lastPoint; i++){
            
            //TODO: handle exception
            int c = i%width;
            int r = (int) (i/w);
            
            int newX = points[0][i]-xLeft;
            int newY = points[1][i]-yBottom;
            newImageChunk.setRGB(newX, newY, originalImageChunk.getRGB(width-c-1, r));
            
        }


        if(!joinImage(newImageChunk)){
            saveImageChunk(newImageChunk, destPath);
        }
        
        lock=1;

        workers-=1;
        System.out.println("current workers = "+workers);
        if(workers<=0){
            this.lock2=1;
            System.out.println("unlocked by path");
        }
        System.out.println("unlocked");

		
    }
    
    private boolean joinImage(BufferedImage imageChunk) {
        return false;
    }

    public BufferedImage getImageChunk(int x, int y, int width, int height, String source) {
        try{
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

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }

        return null;
    }

    public void saveImageChunk(BufferedImage chunk, String dest){
        try {
            String[] infoDest =  split(dest,".");
            String splitedname = infoDest[0];
            String mimetype = infoDest[1];
            String realDest = splitedname+"_"+currentSerialization+"."+mimetype;
            File image = new File(realDest);


            System.out.println("image file in-> " +  realDest);
            // ImageOutputStream ios = ImageIO.createImageOutputStream(image);
            // ImageReader ir = ImageIO.getImageReaders(ios).next();
            // ImageWriter iw = ImageIO.getImageWriter(ir);
            // ImageWriteParam irp = iw.getDefaultWriteParam();
            // irp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            // iw.setOutput(ios);
            ImageIO.write(chunk,"jpg",image);
            // iw.dispose();
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }

    }

    private String[] split(String dest, String string) {
        return dest.replace('.', ',').split(",");
    }
}