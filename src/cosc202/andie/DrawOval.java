package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to DrawOval on an image.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Liam Williamson
 * @version 1.0
 */
public class DrawOval implements ImageOperation, java.io.Serializable {

    int ovalStartX;
    int ovalStartY;
    int ovalWidth;
    int ovalHeight;

    /**
     * <p>
     * Create a new DrawOval operation of default type.
     * </p>
     */
    DrawOval() {

        this.ovalStartX = 1;
        this.ovalStartY = 1;
        this.ovalWidth = 1;
        this.ovalHeight = 1;

    }

    /**
     * <p>
     * Create a new DrawOval operation of specified type.
     * </p>
     */
    DrawOval(int ovalStartX, int ovalStartY, int ovalWidth, int ovalHeight) {

        this.ovalStartX = ovalStartX;
        this.ovalStartY = ovalStartY;
        this.ovalWidth = ovalWidth;
        this.ovalHeight = ovalHeight;

    }

    /**
     * <p>
     * Apply DrawOval to an image.
     * </p>
     * 
     * @param input The image to be drawn on
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        
        ovalStartX = Math.max(Math.min(input.getWidth(), ovalStartX), 0); // clamping for going out of bounds left or up
        ovalStartY = Math.max(Math.min(input.getHeight(), ovalStartY), 0);

        ovalWidth = Math.min(Math.abs(input.getWidth()) - ovalStartX - 1, ovalWidth); // clampng for going out of bounds right or down
        ovalHeight = Math.min(Math.abs(input.getHeight()) - ovalStartY - 1, ovalHeight);

        input.getGraphics().drawOval(ovalStartX, ovalStartY, ovalWidth, ovalHeight);

        return input;
        
    }

}
