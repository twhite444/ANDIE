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
     * @param input The image to be converted to greyscale
     * @return The resulting greyscale image.
     */
    public BufferedImage apply(BufferedImage input) {
        
        input.getGraphics().drawRect(rectStartX, rectStartY, rectWidth, rectHeight);

        return input;
        
    }

}
