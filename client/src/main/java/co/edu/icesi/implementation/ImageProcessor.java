package co.edu.icesi.implementation;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

import co.edu.icesi.interfaces.IImageFileProcessor;
import co.edu.icesi.interfaces.IImageProcessor;

public class ImageProcessor implements IImageProcessor{
	
	

    private HashMap<Point, ImageChunk> imageChunks;

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
    public ImageChunk getRemainingImageChunk() {
        Iterator<Point> iterator = imageChunks.keySet().iterator();
        if(iterator.hasNext())
            return imageChunks.get(iterator.next());
        else
            return null;    
    }

    @Override
    public void splitImage(int chunkWidth, int chunkHeight) {
        imageFileProcessor.setImageProperties();
        double height = chunkHeight;
        double width = chunkWidth;
        int widthPieces = (int) Math.ceil(imageFileProcessor.getImageWidth() / width)-1;
        int heightPieces = (int) Math.ceil(imageFileProcessor.getImageHeight() / height)-1;

        for(int i = 0; i <= widthPieces; i++){
            for(int j = 0; j<= heightPieces; j++){
                ImageChunk ic = new ImageChunk();
                ic.setPoint(new Point(i, j));
                ic.setHeight(chunkHeight);
                ic.setWidth(chunkWidth);
                
                if(i == widthPieces){
                	
                	int mod = imageFileProcessor.getImageWidth()%chunkWidth;
                	ic.setWidth(mod == 0?chunkWidth: mod);
                }if(j == heightPieces){
                	int mod = imageFileProcessor.getImageHeight()%chunkHeight;
                	ic.setWidth(mod == 0?chunkHeight: mod);
                }
                
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
        return null;
    }

    @Override
    public void setImageChunkProcessed(int[][] points, ImageChunk chunk) {
        imageChunks.remove(chunk.getPoint());
        imageFileProcessor.saveImageChunk(points, chunk);
    }
}