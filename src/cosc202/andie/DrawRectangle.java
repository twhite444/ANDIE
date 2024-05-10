package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to DrawRectangle on an image.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Liam Williamson
 * @version 1.0
 */
public class DrawRectangle implements ImageOperation, java.io.Serializable {

    int rectStartX;
    int rectStartY;
    int rectWidth;
    int rectHeight;

    /**
     * <p>
     * Create a new DrawRectangle operation of default type.
     * </p>
     */
    DrawRectangle() {

        this.rectStartX = 1;
        this.rectStartY = 1;
        this.rectWidth = 1;
        this.rectHeight = 1;

    }

    /**
     * <p>
     * Create a new DrawRectangle operation of specified type.
     * </p>
     */
    DrawRectangle(int rectStartX, int rectStartY, int rectWidth, int rectHeight) {

        this.rectStartX = rectStartX;
        this.rectStartY = rectStartY;
        this.rectWidth = rectWidth;
        this.rectHeight = rectHeight;

    }

    /**
     * <p>
     * Apply DrawRectangle to an image.
     * </p>
     * 
     * @param input The image to be drawn on
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        
        rectStartX = Math.max(Math.min(input.getWidth() - 1, rectStartX), 0); // clamping for going out of bounds left or up
        rectStartY = Math.max(Math.min(input.getHeight() - 1, rectStartY), 0);

        rectWidth = Math.min(input.getWidth() - rectStartX - 1, rectWidth); // clampng for going out of bounds right or down
        rectHeight = Math.min(input.getHeight() - rectStartY - 1, rectHeight);

        input.getGraphics().drawRect(rectStartX, rectStartY, rectWidth, rectHeight);

        return input;
        
    }

}
