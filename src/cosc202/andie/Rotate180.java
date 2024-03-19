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
public class RotateImage implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new RotateImage operation.
     * </p>
     */
    RotateImage() {

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
        //rotate 180 degrees
        //create height (y), width (x) variables
        int width = input.getWidth();
        int height = input.getHeight();

        //initialize new image array
        int[][] rotatedImage = new int[height][width];

        //run through each pixel 
        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                int newX=width-1-x;
                int newY=height-1-y;
                rotatedImage[height-1-y][width-1-x]=image[y][x];
            }
        }
        
        return rotatedImage;
    }
    }
    
}
