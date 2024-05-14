package cosc202.andie;

import java.awt.image.*;


/**
 * Emboss Filter
 * This class implements an emboss filter to apply an emboss effect to images.
 * The filter operates by convolving the image with a specified kernel.
 * 
 * @author Tommo White
 */
public class Emboss  implements ImageOperation, java.io.Serializable {

    private String direction; // Direction of emboss effect: "1" to "8"

    /**
     * Create a new Emboss operation with the specified direction.
     * 
     * @param direction The direction of the emboss effect ("1" to "8").
     */
    Emboss(String direction) {
        this.direction = direction;
    }

    /**
     * Apply the emboss effect to an image using a convolution class based on the specified direction.
     * 
     * @param input The input image to apply the emboss effect to.
     * @return The resulting embossed image.
     */
    public BufferedImage apply(BufferedImage input) {
        float[] kernel = null;
        switch (direction) {
            case "1":
                kernel = new float[]{
                    0, 0, 0,
                    1, 0, -1,
                    0, 0, 0
                };
                break;
            case "2":
                kernel = new float[]{
                    1, 0, 0,
                    0, 0, 0,
                    0, 0, -1
                };
                break;
            case "3":
                kernel = new float[]{
                    0, 1, 0,
                    0, 0, 0,
                    0, -1, 0
                };
                break;
            case "4":
                kernel = new float[]{
                    0, 0, 1,
                    0, 0, 0,
                    -1, 0, 0
                };
                break;
            case "5":
                kernel = new float[]{
                    0, 0, 0,
                    -1, 0, 1,
                    0, 0, 0
                };
                break;
            case "6":
                kernel = new float[]{
                    -1, 0, 0,
                    0, 0, 0,
                    0, 0, 1
                };
                break;
            case "7":
                kernel = new float[]{
                    0, -1, 0,
                    0, 0, 0,
                    0, 1, 0
                };
                break;
            case "8":
                kernel = new float[]{
                    0, 0, -1,
                    0, 0, 0,
                    1, 0, 0
                };
                break;
            default:
                return input;
        }
        
    
        BufferedImage output = Convolution.convolve(input, kernel, 3, 3, true);
        return output;
    }
    
}
