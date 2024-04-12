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
public class ChangeContrast implements ImageOperation, java.io.Serializable {

    private int amount;

    /**
     * <p>
     * Create a new CycleColours operation of a specified type.
     * </p>
     */
    ChangeContrast(int amount) {

        this.amount = amount;

    }

        /**
     * <p>
     * Create a new CycleColours operation of default type.
     * </p>
     */
    ChangeContrast() {

        this(0);

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
        
        System.out.println(amount);

        for (int y = 0; y < input.getHeight(); ++y) {

            for (int x = 0; x < input.getWidth(); ++x) {

                int argb = input.getRGB(x, y);

                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                input.setRGB(x, y, argb);

            }

        }
        
        return input;
        
    }
    
}
