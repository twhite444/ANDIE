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
public class Rotate90Right implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new Rotate90right operation.
     * </p>
     */
    Rotate90Right() {

    }

    /**
     * <p>
     * Rotate image by a given angle
     * </p>
     * 
     * <p>
     * The rotation...
     * newX=y, newY=width-1-x
     * </p>
     * 
     * @param input The image to be rotated
     * @return The resulting rotated image.
     */
    
     public BufferedImage apply(BufferedImage input) {
        //rotate 90 degrees right
        //create height (y), width (x) variables
        int width = input.getWidth();
        int height = input.getHeight();

        //initialize new image array
        BufferedImage rotatedImage = new BufferedImage(height, width, input.getType());
        

        //run through each pixel 
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = input.getRGB(x, y);
                int newX=height-1-y;
                int newY=x;
                rotatedImage.setRGB(newX, newY, pixel);
            }
        }
        
        return rotatedImage;
    }
}
