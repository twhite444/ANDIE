package cosc202.andie;

import java.awt.image.*;


/**
 * A class representing a sharpening filter operation for images.
 * This class implements the ImageOperation interface and provides a method to
 * apply
 * a sharpening filter to a BufferedImage.
 * <p>
 * This class is typically called when the user selects the "Filter -> Sharpen
 * Filter" option.
 * </p>
 * 
 * @author Yusei Tokito
 * @version 1.0
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {
    /**
     * Constructs a new instance of SharpenFilter.
     */
    private static float[] SHARPEN_MATRIX = {
            0, -1 / 2.0f, 0,
            -1 / 2.0f, 3, -1 / 2.0f,
            0, -1 / 2.0f, 0
    };

    SharpenFilter() {

    }

    /**
     * Applies the sharpening filter to the input BufferedImage.
     *
     * @param input the BufferedImage to which the sharpening filter will be
     *              applied.
     * @return a BufferedImage with the sharpening filter applied.
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

                int argb = (sharpenValue(alpha) << 24) | (sharpenValue(red) << 16) | (sharpenValue(green) << 8)| sharpenValue(blue);
                outputImage.setRGB(x, y, argb);
            }
        }
        return outputImage;
    }


    private static int sharpenValue(int[] s) {
        float result = 0;
        for (int i = 0; i < 9; i++) {
            result += SHARPEN_MATRIX[i] * s[i];
        }
        result = Math.max(0, Math.min(255, result));
        return (int) result;
    }
}