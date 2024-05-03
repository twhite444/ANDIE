
package cosc202.andie;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;
import java.awt.image.BufferedImage;

public class Convolution {
    public static BufferedImage convolve(BufferedImage input, float[] kernel, int kernelWidth, int kernelHeight, boolean offset) {
        int inputHeight = input.getHeight();
        int inputWidth = input.getWidth();
        int outputHeight = inputHeight - kernelHeight + 1;
        int outputWidth = inputWidth - kernelWidth + 1;

        BufferedImage output = new BufferedImage(outputWidth, outputHeight, input.getType());

        // Separate each color channel for color images
        int numChannels = input.getType() == BufferedImage.TYPE_BYTE_GRAY ? 1 : 3;
        int[] inputRGB = new int[inputWidth * inputHeight * numChannels];
        input.getRGB(0, 0, inputWidth, inputHeight, inputRGB, 0, inputWidth);

        int[] outputRGB = new int[outputWidth * outputHeight * numChannels];

        for (int channel = 0; channel < numChannels; channel++) {
            for (int i = 0; i < outputHeight; i++) {
                for (int j = 0; j < outputWidth; j++) {
                    float sum = 0;
                    for (int k = 0; k < kernelHeight; k++) {
                        for (int l = 0; l < kernelWidth; l++) {
                            int rowIndex = Math.min(Math.max(i + k, 0), inputHeight - 1);
                            int colIndex = Math.min(Math.max(j + l, 0), inputWidth - 1);
                            int pixelValue = (inputRGB[(rowIndex * inputWidth + colIndex) * numChannels + channel]) & 0xFF;
                            sum += pixelValue * kernel[k * kernelWidth + l];
                        }
                    }
                    if (offset) {
                        sum = Math.max(0, Math.min(127, sum));
                    }
                    int outputValue = (int) sum;
                    outputValue = Math.min(255, Math.max(0, outputValue));
                    outputRGB[(i * outputWidth + j) * numChannels + channel] = outputValue;
                }
            }
        }

        // Set output image RGB values
        output.setRGB(0, 0, outputWidth, outputHeight, outputRGB, 0, outputWidth);

        return output;
    }
}
