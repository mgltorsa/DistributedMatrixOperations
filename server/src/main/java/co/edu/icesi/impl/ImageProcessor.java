package co.edu.icesi.impl;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import co.edu.icesi.interfaces.IImageFileProcessor;
import co.edu.icesi.interfaces.IImageLogicProcessor;

public class ImageProcessor implements IImageLogicProcessor{
    
    private ConcurrentHashMap<Point, ImageChunk> imageChunks;
    
    private HashMap<Integer, PointMatrix> typePoints;

    private IImageFileProcessor imageFileProcessor;
    
    public ImageProcessor(IImageFileProcessor ifp) {
        imageFileProcessor = ifp;
        imageChunks = new ConcurrentHashMap<Point, ImageChunk>();
        typePoints = new HashMap<Integer, PointMatrix>();
    }

    public ConcurrentHashMap<Point, ImageChunk> getImageChunks(){
        return imageChunks;
    }

    public void setImageSource(String source){
        imageFileProcessor.setSourcePath(source);
    }

    public void setImageDestination(String dest){
        imageFileProcessor.setDestinationPath(dest);
    }

    @Override
    public synchronized ArrayList<ImageChunk> getImageChunks(int type){
    	Iterator<Point> iter = imageChunks.keySet().iterator();
    	ArrayList<ImageChunk> images = new ArrayList<ImageChunk>();
    	while(iter.hasNext()) {
    		ImageChunk object = imageChunks.remove(iter.next());
    		if(object.getType()==type)
    			images.add(object);
    	}
    	
    	return images;
    }

    @Override
    public void splitImage(int chunkWidth, int chunkHeight) {
        double height = chunkHeight;
        double width = chunkWidth;
        int widthPieces = (int) Math.ceil(imageFileProcessor.getImageWidth() / width);
        int heightPieces = (int) Math.ceil(imageFileProcessor.getImageHeight() / height);
        
        boolean type = false;

        for(int i = 0; i < widthPieces; i++){
            for(int j = 0; j< heightPieces; j++){
                ImageChunk ic = new ImageChunk();
                ic.setPoint(new Point(i, j));
                ic.setHeight(chunkHeight);
                ic.setWidth(chunkWidth);
                
                if(i == widthPieces-1){
                	int mod = imageFileProcessor.getImageWidth()%chunkWidth;
                	ic.setWidth(mod == 0?chunkWidth: mod);
                	ic.setType(ImageChunk.FULL_HEIGHT);
                	type = true;
                }if(j == heightPieces-1){
                	int mod = imageFileProcessor.getImageHeight()%chunkHeight;
                	ic.setWidth(mod == 0?chunkHeight: mod);
                	if(type)
                		ic.setType(ImageChunk.FULL_MOD);
                	else
                		ic.setType(ImageChunk.FULL_WIDTH);
                }
                type = false;
                imageChunks.put(ic.getPoint(), ic);
            }
        }
    }

    @Override
    public void splitImage() {
        if(imageFileProcessor.getImageHeight()<= 10000 && imageFileProcessor.getImageWidth()<=10000){
            ImageChunk ic = new ImageChunk();
            ic.setPoint(new Point(0, 0));
            ic.setHeight(imageFileProcessor.getImageHeight());
            ic.setWidth(imageFileProcessor.getImageWidth());
            ic.setType(ImageChunk.FULL_ABSOLUTE);
            imageChunks.put(ic.getPoint(), ic);
        }else{
            splitImage(ImageChunk.DEFAUL_SIZE, ImageChunk.DEFAUL_SIZE);
        }

    }

    @Override
    public ImageChunk retriveImageChunk() {
        Iterator<Point> iterator = imageChunks.keySet().iterator();
        if(iterator.hasNext()){
             
           return imageChunks.get(iterator.next());
        }
        else
            return null;    
    }

    public ImageChunk retriveImageChunk(int type) {
        Iterator<Point> iterator = imageChunks.keySet().iterator();
        ImageChunk ic = new ImageChunk();
        while(iterator.hasNext()){
            ImageChunk i = imageChunks.get(iterator.next());
            if(i.getType()==type){
                ic = i;
                break;
            }
        }   
        return ic; 
    }

	@Override
	public void setPoints(int type, int[][] pointsChunk) {
		// TODO Auto-generated method stub
		PointMatrix pm = new PointMatrix(pointsChunk);
		typePoints.put(type, pm);
	}
}