package cosc202.andie;

import java.awt.image.*;

/**
 * Gaussian Filter
 * @author Charlotte Cook
 */
public class GaussianFilter  implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Gaussian filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed GaussianFilter
     */
    GaussianFilter(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a Gaussian filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Gaussian filter has radius 1.
     * </p>
     * 
     * @see MeanFilter(int)
     */
    GaussianFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Gaussian filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Gaussian filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.  
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Gaussian filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        
        int width = 2 * radius + 1;
        int height = 2 * radius + 1;
        int size = width * height;

        System.out.println("width: " + width + "height: " + height);

        float[] array = new float[size];

        float sigma = (float)radius / 3;

        float denominatior = 2 * (float)Math.PI * sigma * sigma;

        int i = 0;
        float sum = 0;

        for (int y = -radius; y <= radius; y++) {

            for (int x = -radius; x <= radius; x++) {

                array[i] = (1 / denominatior) * (float)Math.exp(-(((x * x) + (y * y)) / (2 * sigma * sigma)));

                sum += array[i];

                i++;

            }

        }

        for(i = 0; i < array.length; i++){
            array[i] = array[i]/sum;
        }        

        Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;

    }

}
