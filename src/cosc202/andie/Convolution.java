
package cosc202.andie;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * <p>
 * Class for performing image convolution operations.
 * </p>
 * 
 * <p>
 * This class provides methods for applying convolution operations to images.
 * Convolution is a mathematical operation used for various image processing
 * tasks such as blurring, sharpening, edge detection, etc.
 * </p>
 * 
 * @author Tommo White
 * @version 1.0
 */
public class Convolution {
    /**
     * <p>
     * Apply a single pixel convolution operation.
     * </p>
     * 
     * <p>
     * This method applies a single pixel convolution operation using the given kernel
     * to the specified position in the input matrix.
     * </p>
     * 
     * @param input       The input matrix representing the image.
     * @param x           The x-coordinate of the pixel.
     * @param y           The y-coordinate of the pixel.
     * @param kernel      The convolution kernel.
     * @param kernelWidth The width of the kernel.
     * @param kernelHeight The height of the kernel.
     * @return The result of the convolution operation on the specified pixel.
     */
    public static double singlePixelConvolution(double[][] input, int x, int y, float[] kernel, int kernelWidth, int kernelHeight) {

        double output = 0;
        int k=0;
        for (int i = 0; i < kernelHeight; ++i) {
            for (int j = 0; j < kernelWidth; ++j) {
                int pixelX = x + j - kernelWidth / 2;
                int pixelY = y + i - kernelHeight / 2;
                if (pixelX >= 0 && pixelX < input[0].length && pixelY >= 0 && pixelY < input.length) {
                    output += input[pixelY][pixelX] * kernel[k];
                } else {
                    int nearestX = Math.min(Math.max(pixelX, 0), input[0].length - 1);
                    int nearestY = Math.min(Math.max(pixelY, 0), input.length - 1);
                    output += input[nearestY][nearestX] * kernel[k];
                }
                k++;
            }
        }
        return output;
    }

    /**
     * <p>
     * Apply convolution operation to an image.
     * </p>
     * 
     * <p>
     * This method applies the convolution operation to the entire image using the
     * specified kernel.
     * </p>
     * 
     * @param width        The width of the image.
     * @param height       The height of the image.
     * @param image        The image matrix.
     * @param kernel       The convolution kernel.
     * @param kernelWidth  The width of the kernel.
     * @param kernelHeight The height of the kernel.
     * @return The resulting image after convolution.
     */
    private static double[][] applyConvolution(int width, int height, double[][] image, float[] kernel, int kernelWidth, int kernelHeight) {

        double[][] result = new double[height][width];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[y][x] = singlePixelConvolution(image, x, y, kernel, kernelWidth, kernelHeight);
            }
        }
        
        return result;
    }

    /**
     * <p>
     * Apply convolution to an input image.
     * </p>
     * 
     * <p>
     * This method applies convolution to the input image using the specified
     * kernel. It supports offsetting the output values.
     * </p>
     * 
     * @param input        The input image.
     * @param kernel       The convolution kernel.
     * @param kernelWidth  The width of the kernel.
     * @param kernelHeight The height of the kernel.
     * @param offset       Flag indicating whether to offset the output values.
     * @return The convolved image.
     */
    public static BufferedImage convolve(BufferedImage input, float[] kernel, int kernelWidth, int kernelHeight, boolean offset) {
        int height = input.getHeight();
        int width = input.getWidth();

        BufferedImage output = new BufferedImage(width, height, input.getType());

        double[][][] imageArray= new double[4][height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = new Color(input.getRGB(j, i));
                imageArray[0][i][j] = color.getRed();
                imageArray[1][i][j] = color.getGreen();
                imageArray[2][i][j] = color.getBlue();
                imageArray[3][i][j] = color.getAlpha();
            }
        }

        for (int channel = 0; channel < 4; channel++) {
            double[][] convResult = applyConvolution(width, height, imageArray[channel], kernel, kernelWidth, kernelHeight);
            int outputValue=0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (offset) {
                        convResult[y][x]+=127;
                        outputValue = (int) Math.min(255, Math.max(0, convResult[y][x]));
                    } else {
                        outputValue = (int) Math.min(255, Math.max(0, convResult[y][x]));
                    }
                    
                    switch (channel) {
                        case 0: // Red channel
                            output.setRGB(x, y, (output.getRGB(x, y) & 0xFF00FFFF) | (outputValue << 16));
                            break;
                        case 1: // Green channel
                            output.setRGB(x, y, (output.getRGB(x, y) & 0xFFFF00FF) | (outputValue << 8));
                            break;
                        case 2: // Blue channel
                            output.setRGB(x, y, (output.getRGB(x, y) & 0xFFFFFF00) | outputValue);
                            break;
                        case 3: // Alpha channel
                            output.setRGB(x, y, (output.getRGB(x, y) & 0x00FFFFFF) | outputValue << 24);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        return output;
    }
}
