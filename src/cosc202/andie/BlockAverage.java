package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to BlockAverage.
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

    /** The horizontal distance between block boundaries. */
    private int xDist;
    //vertical distance between block boundaries
    private int yDist;

    /**
     * <p>
     * Constructs a new BlockAverage operation with specified block dimensions.
     * </p>
     * 
     * @param xDist The horizontal distance between block boundaries.
     * @param yDist The vertical distance between block boundaries.
     */
    BlockAverage(int xDist, int yDist) {

        this.xDist = xDist - 1; // minus one as size so xDist and yDist are equal to the side lengths of the blocks
        this.yDist = yDist - 1;

    }

    /**
     * <p>
     * Create a new BlockAverage operation of default type.
     * </p>
     */
    BlockAverage() {

        this(1, 1);

    }

    /**
     * <p>
     * Apply block averaging to an image.
     * </p>
     * 
     * 
     * @param input The image to be BlockAveraged
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {

        //System.out.println(xDist + " " + yDist);

        int width = input.getWidth();
        int height = input.getHeight();

        for (int x = 0; x <= width; x += xDist + 1) { // for each pixel in the input image at multiples of xDist and yDist

            for (int y = 0; y <= height; y += yDist + 1) {

                int argb = 0;

                int r = 0;
                int g = 0;
                int b = 0;
                int a = 0;

                int pixelsInBlock = 0;

                int xLocal = 0;
                int yLocal = 0;

                //int RGB = input.getRGB(Math.min(x + 1, width - 1), Math.min(y + 1, height - 1));

                for (xLocal = 0; xLocal + x < width && xLocal <= xDist; xLocal ++) { // for each pixel within the block, add its color to the total

                    for (yLocal = 0; yLocal + y < height && yLocal <= yDist; yLocal ++) {

                        argb = input.getRGB(Math.min(x + xLocal, width - 1), Math.min(y + yLocal, height - 1));

                        if (argb != 0) {

                            a += (argb & 0xFF000000) >> 24;
                            r += (argb & 0x00FF0000) >> 16;
                            g += (argb & 0x0000FF00) >> 8;
                            b += (argb & 0x000000FF);

                            pixelsInBlock ++; // number of pixels in block may be variable due to being cut off by the edges

                        }

                    }

                }

                a /= Math.max(pixelsInBlock, 1); // divide by the number of pixels in the block
                r /= Math.max(pixelsInBlock, 1);
                g /= Math.max(pixelsInBlock, 1);
                b /= Math.max(pixelsInBlock, 1);

                argb = (a << 24) | (r << 16) | (g << 8) | b;

                for (xLocal = 0; xLocal <= xDist; xLocal ++) { // for each pixel within the block, set its colour to the average colour of the block

                    for (yLocal = 0; yLocal <= yDist; yLocal ++) {

                        input.setRGB(Math.min(x + xLocal, width - 1), Math.min(y + yLocal, height - 1), argb);

                    }

                }

            }

        }
        
        return input;
        
    }
    
}
