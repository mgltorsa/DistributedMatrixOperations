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

    public static final int MAX_PIXELS_PER_BLOCK = 4096;
    public static final int MAX_PIXELS_IN_MEMORY = 4096*4096;

    private ImageReader getImageReader(String sourcePath) throws IOException {
        File file = new File(sourcePath);
        ImageInputStream input =  ImageIO.createImageInputStream(file);
        ImageReader reader =  ImageIO.getImageReaders(input).next();
        reader.setInput(input);
        return reader;
    }

    @Override
    public List<Rectangle> calculateRegions(String sourcePath) {

        try {
            ImageReader reader =  getImageReader(sourcePath);
            int width = reader.getWidth(reader.getMinIndex());
            int height = reader.getHeight(reader.getMinIndex());

            if(width*height< MAX_PIXELS_IN_MEMORY ){
                return Arrays.asList(new Rectangle(0, 0, width, height));
            }else{

                //MAX_PIXELS_IN_MEMORY/cores = pixels per core
                //pixels per core /width = block size
                int blocksByWidth = width/MAX_PIXELS_PER_BLOCK;
                int blocksByHeight = height/MAX_PIXELS_PER_BLOCK;

                int blocks = blocksByWidth*blocksByHeight;

                return calculateRectangles(blocks, width, height);
            }

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    private List<Rectangle> calculateRectangles(int blocks, int width, int height) {

        List<Rectangle> rectangles = new ArrayList<Rectangle>();
        for (int i = 0; i < width; i+=MAX_PIXELS_PER_BLOCK) {
            int rwidth = width%MAX_PIXELS_PER_BLOCK != 0 ?  width%MAX_PIXELS_PER_BLOCK : MAX_PIXELS_PER_BLOCK;
            for (int j = 0; j < height; j+=MAX_PIXELS_PER_BLOCK) {
                int rheight = height%MAX_PIXELS_PER_BLOCK !=0 ? height%MAX_PIXELS_PER_BLOCK : MAX_PIXELS_PER_BLOCK;
                rectangles.add(new Rectangle(i,j,rwidth,rheight ));
            }
            
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