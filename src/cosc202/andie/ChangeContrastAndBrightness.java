package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to cycle colours.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ChangeContrastAndBrightness implements ImageOperation, java.io.Serializable {

    private int brightness;
    private int contrast;

    /**
     * <p>
     * Create a new CycleColours operation of a specified type.
     * </p>
     */
    ChangeContrastAndBrightness(int brightness, int contrast) {

        this.brightness = brightness;
        this.contrast = contrast;

    }

        /**
     * <p>
     * Create a new CycleColours operation of default type.
     * </p>
     */
    ChangeContrastAndBrightness() {

        this(0, 0);

    }

    /**
     * <p>
     * Apply colour cycing to an image.
     * </p>
     * 
     * 
     * @param input The image to be cycled
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {

        System.out.println(brightness + " " + contrast);

        for (int y = 0; y < input.getHeight(); ++y) {

            for (int x = 0; x < input.getWidth(); ++x) {

                int argb = input.getRGB(x, y);

                double a = (argb & 0xFF000000) >> 24;
                double r = (argb & 0x00FF0000) >> 16;
                double g = (argb & 0x0000FF00) >> 8;
                double b = (argb & 0x000000FF);

                r = ((1 + ((double)contrast / 100)) * (r - 127.5)) + (127.5 * (1 + ((double)brightness / 100))); // fromula for recolouring pixels based on a percent change of contrast and brightness
                g = ((1 + ((double)contrast / 100)) * (g - 127.5)) + (127.5 * (1 + ((double)brightness / 100)));
                b = ((1 + ((double)contrast / 100)) * (b - 127.5)) + (127.5 * (1 + ((double)brightness / 100)));

                if (r > 255) { r = 255;} // clamping for values ovr 255
                if (g > 255) { g = 255;}
                if (b > 255) { b = 255;}

                if (r < 0) { r = 0;} // clamping for values below 0
                if (g < 0) { g = 0;}
                if (b < 0) { b = 0;}

                argb = ((int)a << 24) | ((int)r << 16) | ((int)g << 8) | (int)b;

                input.setRGB(x, y, argb);

            }

        }
        
        return input;
        
    }
    
}
