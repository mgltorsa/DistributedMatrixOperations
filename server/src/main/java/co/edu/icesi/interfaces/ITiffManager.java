package co.edu.icesi.interfaces;

import java.awt.Rectangle;
import java.util.List;

import org.osoa.sca.annotations.Service;

/**
 * ITiffManager
 */
@Service
public interface ITiffManager {

    public List<Rectangle> calculateRegions(String sourcePath, int cores);
}