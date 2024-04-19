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
public class BlockAverage implements ImageOperation, java.io.Serializable {

    private int xDist;
    private int yDist;

    /**
     * <p>
     * Create a new CycleColours operation of a specified type.
     * </p>
     */
    BlockAverage(int xDist, int yDist) {

        this.xDist = xDist;
        this.yDist = yDist;

    }

        /**
     * <p>
     * Create a new CycleColours operation of default type.
     * </p>
     */
    BlockAverage() {

        this(1, 1);

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

        System.out.println(xDist + " " + yDist);

        int width = input.getWidth();
        int height = input.getHeight();

        for (int x = 0; x <= input.getWidth() + xDist; x += xDist) { // for each pixel in the input image at multiples of xDist and yDist

            for (int y = 0; y <= input.getHeight() + yDist; y += yDist) {

                int r = 0; // inital values for argb
                int g = 0;
                int b = 0;
                int a = 0;

                int RGB = input.getRGB(Math.min(x, width - 1), Math.min(y, height - 1));

                for (int xLocal = 0; xLocal <= xDist; xLocal ++) {

                    for (int yLocal = 0; yLocal <= yDist; yLocal ++) {

                        input.setRGB(Math.min(x + xLocal, width - 1), Math.min(y + yLocal, height - 1), RGB);

                    }

                }

            }

        }
        
        return input;
        
    }
    
}
