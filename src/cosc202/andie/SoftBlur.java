package cosc202.andie;

import java.awt.image.*;


/**
 * <p>
 * ImageOperation to apply a Softblur filter.
 * This class implements the ImageOperation interface and provides a method to apply
 * a sharpening filter to a BufferedImage.
 * <p>
 * This class is typically called when the user selects the "Filter -> Softblur Filter" option.
 * </p>
 * @see java.awt.image.ConvolveOp
 * @author Yusei Tokito
 * @version 1.0
 */
public class SoftBlur implements ImageOperation, java.io.Serializable {
   
    private static final float[] SOFTBLUR_MATRIX  = { 0, 1 / 8.0f, 0,
        1 / 8.0f, 1 / 2.0f, 1 / 8.0f,
        0, 1 / 8.0f, 0 };
    /**
     * Constructs a new instance of SoftBlur filter.
     */
   
    SoftBlur() {

    }
/**
     * <p>
     * Apply a Soft Blur filter to an image.
     * </p>
     * 
     * @param input The image to apply the Soft filter to.
     * @return The resulting (soft blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        BufferedImage outputImage = Convolution.convolve(input,SOFTBLUR_MATRIX,3,3,false);
        return outputImage;
    }

}