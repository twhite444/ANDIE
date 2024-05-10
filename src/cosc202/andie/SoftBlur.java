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
        BufferedImage outputImage = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {

                int[] alpha = new int[9];
                int[] red = new int[9];
                int[] green = new int[9];
                int[] blue = new int[9];
                int i = 0;

                for (int dy = -1; dy <= 1; ++dy) {
                    int moveY = y + dy;
                    for (int dx = -1; dx <= 1; ++dx) {
                        int moveX = x + dx;
                        if (moveY >= 0 && moveY < input.getHeight() && moveX >= 0 && moveX < input.getWidth()) {
                            int pixel = input.getRGB(moveX, moveY);
                            alpha[i] = (pixel >>> 24) & 0xFF;
                            red[i] = (pixel >> 16) & 0xFF;
                            green[i] = (pixel >> 8) & 0xFF;
                            blue[i] = pixel & 0xFF;
                        } else {
                            int tempX = moveX;
                            int tempY = moveY;
                            if (moveX < 0) {
                                tempX = 0;
                            }
                            if (moveY < 0) {
                                tempY = 0;
                            }
                            if (moveX >= input.getWidth()) {
                                tempX = input.getWidth() - 1;
                            }
                            if (moveY >= input.getHeight()) {
                                tempY = input.getHeight() - 1;
                            }
                            int pixel = input.getRGB(tempX, tempY);
                            alpha[i] = (pixel >>> 24) & 0xFF;
                            red[i] = (pixel >> 16) & 0xFF;
                            green[i] = (pixel >> 8) & 0xFF;
                            blue[i] = pixel & 0xFF;
                       }
                        i++;
                    }
                }

                int argb = (getSoftBlurValue(alpha) << 24) | (getSoftBlurValue(red) << 16) | (getSoftBlurValue(green) << 8)| getSoftBlurValue(blue);
                outputImage.setRGB(x, y, argb);
            }
        }
        return outputImage;
    }


    private static int getSoftBlurValue(int[] s) {
        float result = 0;
        for (int i = 0; i < 9; i++) {
            result += SOFTBLUR_MATRIX[i] * s[i];
        }
        result = Math.max(0, Math.min(255, result));
        return (int) result;
    }
}