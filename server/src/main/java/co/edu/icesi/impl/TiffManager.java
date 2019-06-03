package co.edu.icesi.impl;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import co.edu.icesi.interfaces.ITiffManager;

/**
 * TiffManager
 */
public class TiffManager implements ITiffManager {

    public static final int MAX_PIXELS_IN_MEMORY = 1024*1024;

    private ImageReader getImageReader(String sourcePath) throws IOException {
        File file = new File(sourcePath);
        ImageInputStream input =  ImageIO.createImageInputStream(file);
        ImageReader reader =  ImageIO.getImageReaders(input).next();
        reader.setInput(input);
        return reader;
    }

    @Override
    public List<Rectangle> calculateRegions(String sourcePath, int cores) {

        try {
            ImageReader reader =  getImageReader(sourcePath);
            int width = reader.getWidth(reader.getMinIndex());
            int height = reader.getHeight(reader.getMinIndex());

            if(width*height<MAX_PIXELS_IN_MEMORY){
                return Arrays.asList(new Rectangle(0, 0, width, height));
            }else{

                //MAX_PIXELS_IN_MEMORY/cores = pixels per core
                //pixels per core /width = block size
                int block = Math.min(MAX_PIXELS_IN_MEMORY/cores/width, (int) (Math.ceil(height/(double) cores )));
                
                return calculateRectangles(block,width, height);
            }

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    private List<Rectangle> calculateRectangles(int block, int width, int height) {

        List<Rectangle> rectangles = new ArrayList<Rectangle>();
        for (int y = 0; y < height; y+=block) {

            int realHeight = Math.min(block, height-y);
            Rectangle rectangle = new Rectangle(0,y,width,realHeight);
            rectangles.add(rectangle);
        }
        return rectangles;
    }

    @Override
    public boolean isImage(File file) {

        String mimetype = new MimetypesFileTypeMap().getContentType(file);
        
        if(mimetype.equals("image")){
            return true;
        }else{
            return ImageIO.getImageReadersByMIMEType(mimetype).hasNext();
        }
        
    }

    
}