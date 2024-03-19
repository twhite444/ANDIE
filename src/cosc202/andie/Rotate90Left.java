package cosc202.andie;

import java.awt.image.*;


/**
 * <p>
 * ImageOperation to rotate an image 90 degrees to the left.
 * </p>
 * 
 * <p>
 * The images produced by this operation 
 * </p>
 * 
 * @author Tommo White
 * @version 1.0
 */
public class Rotate90Left implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new RotateImage operation.
     * </p>
     */
    Rotate90Left() {

    }

    /**
     * <p>
     * Rotate image by a given angle
     * </p>
     * 
     * <p>
     * The rotation...
     * new x-coordinate is the old y coordinate. new y-coordinate is the old
     * width of image minus x-coordinate minus 1 (Pixel coordinates start at 0, hence minus 1)
     * 
     * </p>
     * 
     * @param input The image to be rotated
     * @return The resulting rotated image.
     */
    public BufferedImage apply(BufferedImage input) {
        //rotate 90 degrees left
        //create height (y), width (x) variables
        int width = input.getWidth();
        int height = input.getHeight();

        //initialize new image array
        BufferedImage rotatedImage = new BufferedImage(height, width, input.getType());
        

        //run through each pixel 
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = input.getRGB(x, y);
                int newX=y;
                int newY=width-1-x;
                rotatedImage.setRGB(newX, newY, pixel);
            }
        }
        
        return rotatedImage;
    }
}
    
