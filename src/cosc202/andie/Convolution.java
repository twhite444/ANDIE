
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
                // Calculate the coordinates of the corresponding pixel in the input image
                int pixelX = x + j - kernelWidth / 2;
                int pixelY = y + i - kernelHeight / 2;
                // Check if the pixel coordinates are within the bounds of the input image
                if (pixelX >= 0 && pixelX < input[0].length && pixelY >= 0 && pixelY < input.length) {
                    // Apply the convolution operation
                    output += input[pixelY][pixelX] * kernel[k];
                } else {
                    // If the pixel coordinates are outside the image boundaries, use the nearest pixel value
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
        
        // Iterate over each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Apply the convolution operation to the current pixel
                result[y][x] = singlePixelConvolution(image, x, y, kernel, kernelWidth, kernelHeight);
            }
        }
        
        return result;
    }

    public static BufferedImage convolve(BufferedImage input, float[] kernel, int kernelWidth, int kernelHeight, boolean offset) {
        int height = input.getHeight();
        int width = input.getWidth();


        BufferedImage output = new BufferedImage(width, height, input.getType());

        // Separate each color channel for color images
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
            // Apply convolution to the current color channel
            double[][] convResult = applyConvolution(width, height, imageArray[channel], kernel, kernelWidth, kernelHeight);
            
            // Set the RGB values of the output image using the convolution result
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    convResult[y][x]+=127;
                    int outputValue = (int) Math.min(255, Math.max(0, convResult[y][x]));
                    // Set the RGB value of the output image
                    output.setRGB(x, y, new Color(outputValue, outputValue, outputValue).getRGB());
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
