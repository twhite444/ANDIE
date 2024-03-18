package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to invert colours.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Liam Williamson
 * @version 1.0
 */
public class ConvertToInverse implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new ConvertToInverse operation.
     * </p>
     */
    ConvertToInverse() {

    }

    /**
     * <p>
     * Apply colour inverting to an image.
     * </p>
     * 
     * 
     * @param input The image to be inverted
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
  
        for (int y = 0; y < input.getHeight(); ++y) {

            for (int x = 0; x < input.getWidth(); ++x) {

                int argb = input.getRGB(x, y);

                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                argb = (a << 24) | (255 - r << 16) | (255 - g << 8) | 255 - b;

                input.setRGB(x, y, argb);
            }

        }
        
        return input;
        
    }
    
}
