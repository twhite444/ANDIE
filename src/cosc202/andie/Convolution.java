
package cosc202.andie;
import java.awt.Color;
import java.awt.image.BufferedImage;
//import java.awt.image.Kernel;
import java.awt.image.BufferedImage;

public class Convolution {
    public static double singlePixelConvolution(double[][] input,
                                                int x, int y,
                                                float[] kernel,
                                                int kernelWidth,
                                                int kernelHeight) {
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

    private static double[][] applyConvolution(int width, int height, double[][] image, float[] kernel, int kernelWidth, int kernelHeight) {
        double[][] result = new double[height][width];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[y][x] = singlePixelConvolution(image, x, y, kernel, kernelWidth, kernelHeight);
            }
        }
        
        return result;
    }

    public static BufferedImage convolve(BufferedImage input, float[] kernel, int kernelWidth, int kernelHeight, boolean offset) {
        int height = input.getHeight();
        int width = input.getWidth();


        BufferedImage output = new BufferedImage(width, height, input.getType());

        int numChannels = input.getType() == BufferedImage.TYPE_BYTE_GRAY ? 1 : 3;
        double[][][] imageArray= new double[numChannels][height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = new Color(input.getRGB(j, i));
                imageArray[0][i][j] = color.getRed();
                imageArray[1][i][j] = color.getGreen();
                imageArray[2][i][j] = color.getBlue();
            }
        }

        for (int channel = 0; channel < numChannels; channel++) {
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
                        default:
                            break;
                    }
                }
            }
        }


        // for (int y = 0; y < output.getHeight(); y++) {
        //     for (int x = 0; x < output.getWidth(); x++) {
        //         // Get the RGB value of the pixel
        //         int rgb = output.getRGB(x, y);
        //         // Extract individual color components
        //         int red = (rgb >> 16) & 0xFF;
        //         int green = (rgb >> 8) & 0xFF;
        //         int blue = rgb & 0xFF;
        //         // Print the RGB values
        //         System.out.println("Pixel at (" + x + "," + y + "): Red = " + red + ", Green = " + green + ", Blue = " + blue);
        //     }
        // }

        return output;
    }
}
