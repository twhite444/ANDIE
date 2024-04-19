package cosc202.andie;

import java.awt.image.*;
//import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Mean (simple blur) filter.
 * </p>
 * 
 * <p>
 * A Mean filter blurs an image by replacing each pixel by the average of the
 * pixels in a surrounding neighbourhood, and can be implemented by a
 * convolution.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Steven Mills
 * @version 1.0
 */
public class MeanFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
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
     * @param radius The radius of the newly constructed MeanFilter
     */
    MeanFilter(int radius) {

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
    MeanFilter() {

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
     * @param input The image to apply the Mean filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();
        int size = (2 * radius + 1) * (2 * radius + 1);
        BufferedImage outputImage= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int argb =0;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {

                // Kernel
                int i = 0;
                int[] a = new int[size];
                int[] r = new int[size];
                int[] g = new int[size];
                int[] b = new int[size];
                for (int dy = -radius; dy <= radius; ++dy) {
                    int moveY = y + dy;
                    for (int dx = -radius; dx <= radius; ++dx) {
                        int moveX = x + dx;

                        if (moveY >= 0 && moveY < input.getHeight() && moveX >= 0 && moveX < input.getWidth()) {
                            argb = input.getRGB(moveX, moveY);
                            a[i] = (argb & 0xFF000000) >> 24;
                            r[i] = (argb & 0x00FF0000) >> 16;
                            g[i] = (argb & 0x0000FF00) >> 8;
                            b[i] = (argb & 0x000000FF);
                        } else {
                            int tempX=moveX;
                            int tempY=moveY;
                            if(moveX<0){
                                tempX=0;
                            }
                            if(moveY<0){
                                tempY=0;
                            }
                            if(moveX>=input.getWidth()){                               
                                tempX = input.getWidth()-1;
                            }
                            if(moveY>=input.getHeight()){
                                tempY = input.getHeight()-1;
                            }
                            argb = input.getRGB(tempX, tempY);
                            
                            a[i] = (argb & 0xFF000000) >> 24;
                            r[i] = (argb & 0x00FF0000) >> 16;
                            g[i] = (argb & 0x0000FF00) >> 8;
                            b[i] = (argb & 0x000000FF);
                        }
                        i++;
                        
                    }
                }
                
                argb = (meanValue(a, size)<<24)| (meanValue(r, size)<<16)|(meanValue(g, size)<<8)|meanValue(b, size);
               
                outputImage.setRGB(x, y, argb);
            }

            
        }
        
        return outputImage;
    }


    private int meanValue(int[] m, int size){
        int total = 0;
         
        for(int i = 0;i<m.length;i++){
            total+= m[i];
        }

        return total/size;//  mean value 
    }

}


