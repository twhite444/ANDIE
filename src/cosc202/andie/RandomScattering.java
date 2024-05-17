package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * A class representing a sharpening filter operation for images.
 * This class implements the ImageOperation interface and provides a method to
 * apply
 * a sharpening filter to a BufferedImage.
 * <p>
 * This class is typically called when the user selects the "Filter -> Sharpen
 * Filter" option.
 * </p>
 * 
 * @author Yusei Tokito
 * @version 1.0
 */
public class RandomScattering implements ImageOperation, java.io.Serializable {
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a RandomScattering filter with the default size.
     * </p
     * >
     * <p>
     * By default, a RandomScattering filter has radius 1.
     * </p>
     * 
     * @see RandomScattering(int)
     */
    RandomScattering() {
        this(1);
    }

    /**
     * <p>
     * Construct a RandomScattering filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed medianFilter
     */
    RandomScattering(int radius) {

        this.radius = radius;

    }

    /**
     * Applies the sharpening filter to the input BufferedImage.
     *
     * @param input the BufferedImage to which the sharpening filter will be
     *              applied.
     * @return a BufferedImage with the sharpening filter applied.
     */
    public BufferedImage apply(BufferedImage input) {
        Random r = new Random();
       // int size = (2 * radius + 1) * (2 * radius + 1);
        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                ArrayList<Integer> possibleXs = new ArrayList<>();
                ArrayList<Integer> possibleYs = new ArrayList<>();
                for (int dy = -radius; dy <= radius; ++dy) {
                    int moveY = y + dy;
                    for (int dx = -radius; dx <= radius; ++dx) {
                        int moveX = x + dx;
                        if (moveX > 0 && moveX < width && moveY > 0 && moveY < height) {
                            possibleXs.add(moveX);
                            possibleYs.add(moveY);
                        }

                    }
                }
                int randX = possibleXs.get(r.nextInt(0,possibleXs.size()));
                int randY =  possibleYs.get(r.nextInt(0,possibleYs.size()));
                int argb = input.getRGB(randX,randY);
                outputImage.setRGB(x, y, argb);
            }
        }
        return outputImage;

    }
}
