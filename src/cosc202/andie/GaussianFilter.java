package cosc202.andie;

import java.awt.image.*;

/**
 * Gaussian Filter
 * 
 * @author Charlotte Cook
 */
public class GaussianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Gaussian filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed GaussianFilter
     */
    GaussianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Gaussian filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Gaussian filter has radius 1.
     * </p>
     * 
     * @see MeanFilter(int)
     */
    GaussianFilter() {
        this(1);
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
        BufferedImage outputImage = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int width = 2 * radius + 1;
        int height = 2 * radius + 1;
        int size = width * height;

        float[] array = new float[size];

        float sigma = (float) radius / 3;

        float denominatior = 2 * (float) Math.PI * sigma * sigma;

        int i = 0;
        float sum = 0;

        for (int y = -radius; y <= radius; y++) {

            for (int x = -radius; x <= radius; x++) {

                array[i] = (1 / denominatior) * (float) Math.exp(-(((x * x) + (y * y)) / (2 * sigma * sigma)));

                sum += array[i];

                i++;

            }

        }

        for (i = 0; i < array.length; i++) {
            array[i] = array[i] / sum;
        }
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                // int size = (2*radius+1)*(2*radius+1);
                int[] alpha = new int[size];
                int[] red = new int[size];
                int[] green = new int[size];
                int[] blue = new int[size];
                int n = 0;

                for (int dy = -radius; dy <= radius; ++dy) {
                    int moveY = y + dy;
                    for (int dx = -radius; dx <= radius; ++dx) {
                        int moveX = x + dx;
                        if (moveY >= 0 && moveY < input.getHeight() && moveX >= 0 && moveX < input.getWidth()) {
                            int pixel = input.getRGB(moveX, moveY);
                            alpha[n] = (pixel & 0xFF000000) >>> 24;
                            red[n] = (pixel & 0x00FF0000) >> 16;
                            green[n] = (pixel & 0x0000FF00) >> 8;
                            blue[n] = (pixel & 0x000000FF);
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
                            // alpha[i] = (pixel >> 24) & 0xFF;
                            // red[i] = (pixel >> 16) & 0xFF;
                            // green[i] = (pixel >> 8) & 0xFF;
                            // blue[i] = pixel & 0xFF;

                            alpha[n] = (pixel & 0xFF000000) >>> 24;
                            red[n] = (pixel & 0x00FF0000) >> 16;
                            green[n] = (pixel & 0x0000FF00) >> 8;
                            blue[n] = (pixel & 0x000000FF);
                        }
                        n++;
                    }
                }

                int argb = (gaussianCalc(alpha, array) << 24) | (gaussianCalc(red, array) << 16)
                        | (gaussianCalc(green, array) << 8) | gaussianCalc(blue, array);
                outputImage.setRGB(x, y, argb);
            }
        }
        return outputImage;

        // Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, array);
        // ConvolveOp convOp = new ConvolveOp(kernel);
        // BufferedImage output = new BufferedImage(input.getColorModel(),
        // input.copyData(null), input.isAlphaPremultiplied(), null);
        // convOp.filter(input, output);

        // return output;

    }

    private static int gaussianCalc(int[] s, float[] array) {
        float result = 0;
        for (int i = 0; i < array.length; i++) {
            result += array[i] * s[i];
        }
        result = Math.max(0, Math.min(255, result));
        return (int) result;
    }

}
