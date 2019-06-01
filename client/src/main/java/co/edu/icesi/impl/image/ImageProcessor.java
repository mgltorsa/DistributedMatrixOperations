package co.edu.icesi.impl.image;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import co.edu.icesi.interfaces.image.IImageFileProcessor;
import co.edu.icesi.interfaces.image.IImageLogicProcessor;

public class ImageProcessor implements IImageLogicProcessor{
	
    private HashMap<Point, ImageChunk> imageChunks;
    
    private HashMap<Integer, PointMatrix> typePoints;

    private IImageFileProcessor imageFileProcessor;

    public ImageProcessor() {
        imageFileProcessor = new TiffProcessor();
        imageChunks = new HashMap<Point, ImageChunk>();
    }

    public HashMap<Point, ImageChunk> getImageChunks(){
        return imageChunks;
    }

    public void setImageSource(String source){
        imageFileProcessor.setSourcePath(source);
    }

    public void setImageDestination(String dest){
        imageFileProcessor.setDestinationPath(dest);
    }

    @Override
    public ArrayList<ImageChunk> getImageChunks(int type){
    	Iterator<Point> iter = imageChunks.keySet().iterator();
    	ArrayList<ImageChunk> images = new ArrayList<>();
    	while(iter.hasNext()) {
    		ImageChunk object = imageChunks.remove(iter.next());
    		if(object.getType()==type)
    			images.add(object);
    	}
    	
    	return images;
    }

    @Override
    public void splitImage(int chunkWidth, int chunkHeight) {
        imageFileProcessor.setImageProperties();
        double height = chunkHeight;
        double width = chunkWidth;
        int widthPieces = (int) Math.ceil(imageFileProcessor.getImageWidth() / width)-1;
        int heightPieces = (int) Math.ceil(imageFileProcessor.getImageHeight() / height)-1;
        
        boolean type = false;

        for(int i = 0; i <= widthPieces; i++){
            for(int j = 0; j<= heightPieces; j++){
                ImageChunk ic = new ImageChunk();
                ic.setPoint(new Point(i, j));
                ic.setHeight(chunkHeight);
                ic.setWidth(chunkWidth);
                
                if(i == widthPieces){
                	int mod = imageFileProcessor.getImageWidth()%chunkWidth;
                	ic.setWidth(mod == 0?chunkWidth: mod);
                	ic.setType(ImageChunk.FULL_HEIGHT);
                	type = true;
                }if(j == heightPieces){
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
        splitImage(ImageChunk.DEFAUL_SIZE, ImageChunk.DEFAUL_SIZE);
    }

    @Override
    public ImageChunk retriveImageChunk() {
    	Iterator<Point> iterator = imageChunks.keySet().iterator();
        if(iterator.hasNext())
            return imageChunks.get(iterator.next());
        else
            return null;    
    }

    @Override
    public void setImageChunkProcessed(int[][] points, ImageChunk chunk) {
        imageChunks.remove(chunk.getPoint());
        imageFileProcessor.saveImageChunk(points, chunk);
    }

	@Override
	public void setPoints(int type, int[][] pointsChunk) {
		// TODO Auto-generated method stub
		PointMatrix pm = new PointMatrix(pointsChunk);
		typePoints.put(type, pm);
	}
}