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
    /** Direction of emboss effect filter*/
    private String direction; 

    /**
     * Create a new Emboss operation with the specified direction.
     * 
     * @param direction The direction of the emboss effect.
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
            case "Middle Left":
                kernel = new float[]{
                    0, 0, 0,
                    1, 0, -1,
                    0, 0, 0
                };
                break;
            case "Top Left":
                kernel = new float[]{
                    1, 0, 0,
                    0, 0, 0,
                    0, 0, -1
                };
                break;
            case "Top Middle":
                kernel = new float[]{
                    0, 1, 0,
                    0, 0, 0,
                    0, -1, 0
                };
                break;
            case "Top Right":
                kernel = new float[]{
                    0, 0, 1,
                    0, 0, 0,
                    -1, 0, 0
                };
                break;
            case "Middle Right":
                kernel = new float[]{
                    0, 0, 0,
                    -1, 0, 1,
                    0, 0, 0
                };
                break;
            case "Bottom Right":
                kernel = new float[]{
                    -1, 0, 0,
                    0, 0, 0,
                    0, 0, 1
                };
                break;
            case "Bottom Middle":
                kernel = new float[]{
                    0, -1, 0,
                    0, 0, 0,
                    0, 1, 0
                };
                break;
            case "Bottom Left":
                kernel = new float[]{
                    0, 0, -1,
                    0, 0, 0,
                    1, 0, 0
                };
                break;
            case "Vertical Sobel":
                kernel = new float[]{
                    (float) -0.5, -1, (float)-.5,
                    0, 0, 0,
                    (float) 0.5, 1, (float) .5
                };
                break;
            case "Horizontal Sobel":
                kernel = new float[]{
                    (float) -0.5, 0, (float).5,
                    -1, 0, 1,
                    (float) -0.5, 0, (float) .5
                };
                break;
            default:
                return input;
        }
        
    
        BufferedImage output = Convolution.convolve(input, kernel, 3, 3, true);
        return output;
    }
    
}
