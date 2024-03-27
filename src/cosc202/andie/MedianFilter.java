package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Median filter.
 * </p>
 * 
 * <p>
 * Median filter description goes here
 * 
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Yusei Tokito
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {
    
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Mean filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter
     */
    MedianFilter(int radius) {

        this.radius = radius;    

    }

    /**
     * <p>
     * Construct a Mean filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Mean filter has radius 1.
     * </p>
     * 
     * @see MeanFilter(int)
     */
    MedianFilter() {

        this(1);

    }

    /**
     * <p>
     * Apply a Mean filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Mean filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.  
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {

        int size = (2*radius+1) * (2*radius+1);
        int argb =0; // default
        
        for (int y = 0; y < input.getHeight(); ++y) {

            for (int x = 0; x < input.getWidth(); ++x) {
                float [] array = new float[size];
                int[] a = new int[size];
                int[] r = new int[size];
                int[] g = new int[size];
                int[] b = new int[size];
                int index = 0;
                for(int y1 = -radius; y1<=radius; ++y1){
                    
                    for(int x1 = -radius; x1<=radius ;++x1 ){
                        int moveX = x + x1;
                        int moveY = y + y1;
                        //condition where there is no such a pixel(out of bounds) fill with 0
                        if(moveX<0&&moveY<0&&input.getHeight()<moveY&&input.getWidth()<moveX){
                            a[index] = 0;
                            r[index] = 0;
                            g[index] = 0;
                            b[index] = 0;

                        }else{
                            argb = input.getRGB(moveX, moveY);
                
                            a[index]= (argb & 0xFF000000) >> 24;
                            r[index] = (argb & 0x00FF0000) >> 16;
                            g[index]= (argb & 0x0000FF00) >> 8;
                            b[index]= (argb & 0x000000FF);
                        }
                        index++;
                    }
                    
                }
                Arrays.sort(a);
                Arrays.sort(r);
                Arrays.sort(g);
                Arrays.sort(b);
                int medianIndex = size/2 +1;
                argb = (a[medianIndex]<< 24) | (r[medianIndex] << 16) | (g[medianIndex] << 8) | b[medianIndex];
                input.setRGB(x,y,argb);
                

                
                

                    
            }

            }
    

    //    Arrays.fill(array, 1.0f/size);


        //Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);

        //ConvolveOp convOp = new ConvolveOp(kernel);

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);

      //  convOp.filter(input, output);
        

        return output;
    }


}
