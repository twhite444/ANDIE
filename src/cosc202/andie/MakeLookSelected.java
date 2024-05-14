package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to make in image look selected.
 * </p>
 * 
 * <p>
 * The only thing this does is make an image a little bluer and a little brighter,
 * as is the traditional way to indicate it is being selected.
 * Only used in @Crop so far
 * </p>
 * 
 * 
 * @author Liam Williamson
 * @version 1.0
 */
public class MakeLookSelected implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new MakeLookSelected operation.
     * </p>
     */
    MakeLookSelected() {

    }

    /**
     * <p>
     * Makes an image look selected.
     * </p>
     * 
     * 
     * @param input The image to be converted to greyscale
     * @return The resulting greyscale image.
     */
    public BufferedImage apply(BufferedImage input) {

        for (int y = 0; y < input.getHeight(); ++y) {

            for (int x = 0; x < input.getWidth(); ++x) {

                int argb = input.getRGB(x, y);

                double a = (argb & 0xFF000000) >> 24;
                double r = (argb & 0x00FF0000) >> 16;
                double g = (argb & 0x0000FF00) >> 8;
                double b = (argb & 0x000000FF);

                r = Math.max(Math.min(255, r * 0.9), 0); // make the image a bit bluer
                g = Math.max(Math.min(255, g * 1.1), 0);
                b = Math.max(Math.min(255, b * 1.3), 0);

                argb = ((int)a << 24) | ((int)r << 16) | ((int)g << 8) | (int)b;

                input.setRGB(x, y, argb);

            }

        }

        input = new ChangeContrastAndBrightness(10, 0).apply(input); // make the image a bit brighter
        
        return input;
        
    }
    
}
