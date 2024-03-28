package cosc202.andie;

import java.awt.image.*;

/**
 * A class representing a sharpening filter operation for images.
 * This class implements the ImageOperation interface and provides a method to apply
 * a sharpening filter to a BufferedImage.
 * <p>
 * This class is typically called when the user selects the "Filter -> Sharpen Filter" option.
 * </p>
 * @author Yusei Tokito
 * @version 1.0
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {
    /**
     * Constructs a new instance of SharpenFilter.
     */
    SharpenFilter() {

    }
    /**
     * Applies the sharpening filter to the input BufferedImage.
     *
     * @param input the BufferedImage to which the sharpening filter will be applied.
     * @return a BufferedImage with the sharpening filter applied.
     */
    public BufferedImage apply(BufferedImage input) {
        float[] array = { 0, -1 / 2.0f, 0,
                -1 / 2.0f, 3, -1 / 2.0f,
                0, -1 / 2.0f, 0 };

        Kernel kernel = new Kernel(3, 3, array);

        ConvolveOp convOp = new ConvolveOp(kernel);

        BufferedImage output = new BufferedImage(input.getColorModel(),
                input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }
}
