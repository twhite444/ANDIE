package cosc202.andie;

import java.awt.image.*;

/**
 * Motion Blur Filter
 * This class implements a motion blur filter to apply a blur effect in a specific direction to images.
 * The filter operates by convolving the image with a specified kernel.
 * 
 * @author Tommo White
 */
public class MotionBlurFilter implements ImageOperation, java.io.Serializable {
    
    /** Radius of the motion blur */
    private int radius;

    /** Direction of motion blur */
    private String direction;

    /**
     * Create a new MotionBlurFilter operation with the specified direction.
     * 
     * @param direction The direction of the motion blur effect.
     * @param radius The radius of the motion blur effect (determines kernel size).
     */
    MotionBlurFilter(String direction, int radius) {
        this.direction = direction;
        this.radius = radius;
    }

    /**
     * Apply the motion blur effect to an image using a convolution operation based on the specified direction.
     * 
     * @param input The input image to apply the motion blur effect to.
     * @return The resulting image with motion blur effect applied.
     */
    public BufferedImage apply(BufferedImage input) {
        //create kernel of size based on radius
        int width = 2 * radius + 1;
        int height = 2 * radius + 1;
        int size = width * height;
        float[] kernel = new float[size];

        // Define kernels based on direction
        switch (direction) {
            case "Horizontal":
                for (int i = 0; i < size; i += width) {
                    kernel[i] = 1.0f / width;
                }
                break;
            case "Vertical":
                for (int i = 0; i < height; i++) {
                    kernel[i] = 1.0f / height;
                }
                break;
            case "Diagonal Top Left to Bottom Right":
                for (int i = 0; i < size; i += (width + 1)) {
                    kernel[i] = 1.0f / (width + 1);
                }
                break;
            case "Diagonal Bottom Left to Top Right":
                for (int i = width - 1; i < size - 1; i += (width - 1)) {
                    kernel[i] = 1.0f / (width - 1);
                }
                break;
            default:
                return input; // If direction is not recognized, it will return original image
        }

        BufferedImage output = Convolution.convolve(input, kernel, width, height, false);
        return output;
    }
}
