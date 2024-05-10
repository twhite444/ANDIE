package cosc202.andie;

import java.awt.image.*;


/**
 * Emboss Filter
 * @author Tommo White
 */
public class Emboss  implements ImageOperation, java.io.Serializable {

    private String direction; // Scaling factor: 0.5 for 50%, 1.5 for 150%

    /**
     * <p>
     * Create a new Resize operation with the specified scaling factor.
     * </p>
     * 
     * @param direction The scaling factor. 0.5 for 50%, 1.5 for 150%.
     * @return 
     */
    Emboss(String direction) {
        this.direction = direction;
    }

    public static BufferedImage applyConvolution(BufferedImage input, float[] kernel) {
        BufferedImage output = Convolution.convolve(input, kernel, 3, 3, true);

        return output;
    }
    /**
     * <p>
     * Apply a Gaussian filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Gaussian filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.  
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Gaussian filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        //String type = "1";
        //Kernel kernel = null;
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
        
        if (kernel != null) {
            BufferedImage output = applyConvolution(input, kernel);
            return output;
        
        }
    return input;
    }
    
}
