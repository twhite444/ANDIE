package cosc202.andie;

import java.awt.image.*;


/**
 * Emboss Filter
 * @author Tommo White
 */
public class Emboss  implements ImageOperation, java.io.Serializable {

    public static BufferedImage applyConvolution(BufferedImage input, Kernel kernel) {
        ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_CLAMP);


        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);

        convOp.filter(input, output);

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
        String type = "1";
        Kernel kernel = null;
        switch (type) {
            case "1":
                kernel = new Kernel(3, 3, new float[]{
                    0, 0, 0,
                    1, 0, -1,
                    0, 0, 0
                });
                break;
            case "2":
                kernel = new Kernel(3, 3, new float[]{
                    1, 0, 0,
                    0, 0, 0,
                    0, 0, -1
                });
                break;
            case "3":
                kernel = new Kernel(3, 3, new float[]{
                    0, 1, 0,
                    0, 0, 0,
                    0, -1, 0
                });
                break;
            case "4":
                kernel = new Kernel(3, 3, new float[]{
                    0, 0, 1,
                    0, 0, 0,
                    -1, 0, 0
                });
                break;
            case "5":
                kernel = new Kernel(3, 3, new float[]{
                    0, 0, 0,
                    -1, 0, 1,
                    0, 0, 0
                });
                break;
            case "6":
                kernel = new Kernel(3, 3, new float[]{
                    -1, 0, 0,
                    0, 0, 0,
                    0, 0, 1
                });
                break;
            case "7":
                kernel = new Kernel(3, 3, new float[]{
                    0, -1, 0,
                    0, 0, 0,
                    0, 1, 0
                });
                break;
            case "8":
                kernel = new Kernel(3, 3, new float[]{
                    0, 0, -1,
                    0, 0, 0,
                    1, 0, 0
                });
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
