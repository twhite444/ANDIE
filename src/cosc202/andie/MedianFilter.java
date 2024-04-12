package cosc202.andie;

import java.awt.Color; 
import java.util.*;
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
    public BufferedImage apply(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //two for-loops that goes throught each pixel in the inputImage
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                //creates an array for each color to store pixel values
                int[] alphaPixelValue = new int[(2 * radius + 1) * (2 * radius + 1)];
                int[] redPixelValue = new int[(2 * radius + 1) * (2 * radius + 1)];
                int[] greenPixelValue = new int[(2 * radius + 1) * (2 * radius + 1)];
                int[] bluePixelValue = new int[(2 * radius + 1) * (2 * radius + 1)];

                int marker = 0;
                //two for-loops that go through each pixel
                for (int x2 = -radius; x2 <= radius; x2++){
                    for (int y2 = -radius; y2 <= radius; y2++){
                        //calculating the new color of the pixels and storing in red green and blue values 

                        int pixelX = Math.min(Math.max(x + x2 ,0), width -1);
                        int pixelY = Math.min(Math.max(y + y2 ,0), height -1);

                        Color pixelColor = new Color(inputImage.getRGB(pixelX, pixelY), true);

                        alphaPixelValue[marker] = pixelColor.getAlpha();
                        redPixelValue[marker] = pixelColor.getRed();
                        greenPixelValue[marker] = pixelColor.getGreen();
                        bluePixelValue[marker] = pixelColor.getBlue();

                        marker++;
                    }
                }
                //set the new values of the red, green and blue pixels into the output image
                int argb = (getMedianColor(alphaPixelValue) << 24) | (getMedianColor(redPixelValue) << 16) | (getMedianColor(greenPixelValue) << 8) | getMedianColor(bluePixelValue);

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
        return finall[length / 2 + 1]; //return new array 
    }
}