
package cosc202.andie;
import java.awt.image.BufferedImage;
//import java.awt.image.Kernel;
import java.awt.image.BufferedImage;

public class Convolution {
    public static BufferedImage convolve(BufferedImage input, float[] kernel, int kernelWidth, int kernelHeight, boolean offset) {
        int inputHeight = input.getHeight();
        int inputWidth = input.getWidth();


        BufferedImage output = new BufferedImage(inputWidth, inputHeight, input.getType());

        // Separate each color channel for color images
        int numChannels = input.getType() == BufferedImage.TYPE_BYTE_GRAY ? 1 : 3;
        int[] inputRGB = new int[inputWidth * inputHeight * numChannels];
        input.getRGB(0, 0, inputWidth, inputHeight, inputRGB, 0, inputWidth);

        int[] outputRGB = new int[inputWidth * inputHeight * numChannels];

        for (int channel = 0; channel < numChannels; channel++) {
            for (int i = 0; i < inputHeight; i++) {
                for (int j = 0; j < inputWidth; j++) {
                    float sum = 0;
                    for (int k = 0; k < kernelHeight; k++) {
                        for (int l = 0; l < kernelWidth; l++) {
                            int rowIndex = Math.min(Math.max(i + k - kernelHeight / 2, 0), inputHeight - 1);
                            int colIndex = Math.min(Math.max(j + l - kernelWidth / 2, 0), inputWidth - 1);
                            int pixelValue = (inputRGB[(rowIndex * inputWidth + colIndex) * numChannels + channel]) & 0xFF;
                            sum += pixelValue * kernel[k * kernelWidth + l];
                        }
                    }
                    if (offset) {
                        sum = Math.max(0, Math.min(127, sum));
                    }
                    int outputValue = (int) sum;
                    outputValue = Math.min(255, Math.max(0, outputValue));
                    outputRGB[(i * inputWidth + j) * numChannels + channel] = outputValue;
                }
            }
        }

        // Set output image RGB values
        output.setRGB(0, 0, inputWidth, inputHeight, outputRGB, 0, inputWidth);
        
        for (int y = 0; y < output.getHeight(); y++) {
            for (int x = 0; x < output.getWidth(); x++) {
                // Get the RGB value of the pixel
                int rgb = output.getRGB(x, y);
                // Extract individual color components
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                // Print the RGB values
                System.out.println("Pixel at (" + x + "," + y + "): Red = " + red + ", Green = " + green + ", Blue = " + blue);
            }
        }

        return output;
    }
}
