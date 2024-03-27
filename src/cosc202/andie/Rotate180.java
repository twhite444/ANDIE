package cosc202.andie;

import java.awt.image.*;


/**
 * <p>
 * ImageOperation to rotate an image 90 degrees to the right.
 * </p>
 * 
 * <p>
 * The images produced by this operation 
 * </p>
 * 
 * @author Tommo White
 * @version 1.0
 */
public class Rotate180 implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new RotateImage operation.
     * </p>
     */
    Rotate180() {

    }

    /**
     * <p>
     * Rotate image by a given angle
     * </p>
     * 
     * <p>
     * The rotation...
     * flips horizontally and vertically
     * </p>
     * 
     * @param input The image to be rotated
     * @return The resulting rotated image.
     */
    
    
    public BufferedImage apply(BufferedImage input) {
        //rotate 180 degrees left
        //create height (y), width (x) variables
        int width = input.getWidth();
        int height = input.getHeight();

        //initialize new image array
        BufferedImage rotatedImage = new BufferedImage(width, height, input.getType());
        

        //run through each pixel 
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = input.getRGB(x, y);
                int newX=width-1-x;
                int newY=height-1-y;
                rotatedImage.setRGB(newX, newY, pixel);
            }
        }
        
        return rotatedImage;
    }
}
    
