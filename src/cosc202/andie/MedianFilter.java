package cosc202.andie;

import java.util.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * A class representing a median filter operation for images.
 * This class implements the ImageOperation interface and provides a method to apply
 * a median filter to a BufferedImage.
 *
 *
 * @author Cushla Bridges
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;
    

  
    /**
     * <p>
     * Construct a median filter with the given size.
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
    MedianFilter(int radius) {

        this.radius = radius;    

    }

    /**
     * <p>
     * Construct a median filter with the default size.
     * </p
     * >
     * <p>
     * By default, a median filter has radius 1.
     * </p>
     * 
     * @see MedianFilter(int)
     */
    MedianFilter() {

        this(1);

    }


/*Method that takes in a input image between specific radius bounds and applys
 * the median filter to it. 
 * @param BufferedImage inputImage, int radius 
 * @return BufferedImage
 */
public BufferedImage apply(BufferedImage input) {
    BufferedImage outputImage = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);

    for (int y = 0; y < input.getHeight(); ++y) {
        for (int x = 0; x < input.getWidth(); ++x) {
            int size = (2*radius+1)*(2*radius+1);
            int[] alpha = new int[size];
            int[] red = new int[size];
            int[] green = new int[size];
            int[] blue = new int[size];
            int i = 0;

            for (int dy = -radius; dy <= radius; ++dy) {
                int moveY = y + dy;
                for (int dx = -radius; dx <= radius; ++dx) {
                    int moveX = x + dx;
                    if (moveY >= 0 && moveY < input.getHeight() && moveX >= 0 && moveX < input.getWidth()) {
                        int pixel = input.getRGB(moveX, moveY);
                        alpha[i] = (pixel & 0xFF000000) >>> 24;
                        red[i] = (pixel & 0x00FF0000) >> 16;
                        green[i] = (pixel & 0x0000FF00) >> 8;
                        blue[i]  = (pixel & 0x000000FF);
                    } else {
                        int tempX = moveX;
                        int tempY = moveY;
                        if (moveX < 0) {
                            tempX = 0;
                        }
                        if (moveY < 0) {
                            tempY = 0;
                        }
                        if (moveX >= input.getWidth()) {
                            tempX = input.getWidth() - 1;
                        }
                        if (moveY >= input.getHeight()) {
                            tempY = input.getHeight() - 1;
                        }
                        int pixel = input.getRGB(tempX, tempY);
                        // alpha[i] = (pixel >> 24) & 0xFF;
                        // red[i] = (pixel >> 16) & 0xFF;
                        // green[i] = (pixel >> 8) & 0xFF;
                        // blue[i] = pixel & 0xFF;

                        alpha[i] = (pixel & 0xFF000000) >>> 24;
                        red[i] = (pixel & 0x00FF0000) >> 16;
                        green[i] = (pixel & 0x0000FF00) >> 8;
                        blue[i]  = (pixel & 0x000000FF);
                   }
                    i++;
                }
            }

            int argb = (getMedianValue(alpha) << 24) | (getMedianValue(red) << 16) | (getMedianValue(green) << 8)| getMedianValue(blue);
            outputImage.setRGB(x, y, argb);
        }
    }
    return outputImage;
}

    /*Method that calculates the median values for the color arrays of integers
     * @param int[] values
     * @return int[] 
     */
    private static int getMedianColor(int[] values) {

        int length = values.length;
        int[] finall = values.clone();

        //sort array of values 
        Arrays.sort(finall);
        System.out.println(Arrays.toString(values));
        int result = finall[length / 2];
        result = Math.max(0, Math.min(255, result));
        return result; //return new array 
    }

}