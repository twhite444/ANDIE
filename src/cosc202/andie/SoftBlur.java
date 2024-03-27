package cosc202.andie;

import java.awt.image.*;


/**
 * <p>
 * ImageOperation to apply a Softblur filter.
 * </p>
 * 
 * <p>
 * </p>
 * 
 * 
 * @see java.awt.image.ConvolveOp
 * @author Yusei Tokito
 * @version 1.0
 */
public class SoftBlur implements ImageOperation, java.io.Serializable {
   /**Default constractor */
   
    SoftBlur() {

    }
/**
     * <p>
     * Apply a Soft Blur filter to an image.
     * </p>
     * 
     * <p>
     * 
     * </p>
     * 
     * @param input The image to apply the Soft filter to.
     * @return The resulting (soft blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        float[] array = { 0, 1 / 8.0f, 0,
                1 / 8.0f, 1 / 2.0f, 1 / 8.0f,
                0, 1 / 8.0f, 0 };

        Kernel kernel = new Kernel(3, 3, array);

        ConvolveOp convOp = new ConvolveOp(kernel);

        BufferedImage output = new BufferedImage(input.getColorModel(),
                input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }
}
