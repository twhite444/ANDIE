package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to DrawLine on an image.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Liam Williamson
 * @version 1.0
 */
public class DrawLine implements ImageOperation, java.io.Serializable {

    int lineStartX;
    int lineStartY;
    int lineEndX;
    int lineEndY;

    /**
     * <p>
     * Create a new DrawLine operation of default type.
     * </p>
     */
    DrawLine() {

        this.lineStartX = 1;
        this.lineStartY = 1;
        this.lineEndX = 1;
        this.lineEndY = 1;

    }

    /**
     * <p>
     * Create a new DrawLine operation of specified type.
     * </p>
     */
    DrawLine(int lineStartX, int lineStartY, int lineEndX, int lineEndY) {

        this.lineStartX = lineStartX;
        this.lineStartY = lineStartY;
        this.lineEndX = lineEndX;
        this.lineEndY = lineEndY;

    }

    /**
     * <p>
     * Apply DrawLine to an image.
     * </p>
     * 
     * @param input The image to be drawn on
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        
        lineStartX = Math.max(Math.min(input.getWidth(), lineStartX), 0); // clamping for going out of bounds for the start
        lineStartY = Math.max(Math.min(input.getHeight(), lineStartY), 0);

        lineEndX = Math.max(Math.min(input.getWidth(), lineEndX), 0); // clamping for going out of bounds for the end
        lineEndY = Math.max(Math.min(input.getHeight(), lineEndY), 0);

        input.getGraphics().drawLine(lineStartX, lineStartY, lineEndX, lineEndY);

        return input;
        
    }

}
