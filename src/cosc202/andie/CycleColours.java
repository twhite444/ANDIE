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
public class CycleColours implements ImageOperation, java.io.Serializable {
    /** The type of color cycling to be done */
    private String type;

    /**
     * <p>
     * Create a new CycleColours operation of a specified type.
     * </p>
     */
    CycleColours(String type) {

        this.type = type;

    }

        /**
     * <p>
     * Create a new CycleColours operation of default type.
     * </p>
     */
    CycleColours() {

        this("rgb");

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

        //System.out.println(type);
        //System.out.println(type.charAt(0) + " " + type.charAt(1) + " " + type.charAt(2));
  
        for (int y = 0; y < input.getHeight(); ++y) {

            for (int x = 0; x < input.getWidth(); ++x) {

                int argb = input.getRGB(x, y);

                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                switch (type) {

                    case "bgr":

                        argb = (a << 24) | (b << 16) | (g << 8) | r;
                        break;

                    case "gbr":

                        argb = (a << 24) | (b << 16) | (r << 8) | g;
                        break;

                    case "grb":

                        argb = (a << 24) | (g << 16) | (r << 8) | b;
                        break;

                    case "brg":

                        argb = (a << 24) | (g << 16) | (b << 8) | r;
                        break;

                    case "rbg":

                        argb = (a << 24) | (r << 16) | (b << 8) | g;
                        break;
                    
                    case "rgb":

                        argb = (a << 24) | (r << 16) | (g << 8) | b;
                        break;

                    default:

                        return input;

                }

                input.setRGB(x, y, argb);
            }

        }
        
        return input;
        
    }
    
}
