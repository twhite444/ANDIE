package cosc202.andie;

//import java.awt.image.*;


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
public class Rotate180 implements java.io.Serializable {

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
    
    public int[][] apply(int[][] input) {
        //rotate 180 degrees
        //create height (y), width (x) variables
        int width = input[0].length;
        int height = input.length;

        //initialize new image array
        int[][] rotatedImage = new int[height][width];

        //run through each pixel 
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int newX=width-1-x;
                int newY=height-1-y;
                rotatedImage[newY][newX]=input[y][x];
            }
        }
        
        return rotatedImage;
    }
    }
    

