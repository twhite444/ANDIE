package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics;
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
    Color lineColor;

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
        this.lineColor = Color.black;

    }

    /**
     * <p>
     * Create a new DrawLine operation of specified type.
     * </p>
     */
    DrawLine(int lineStartX, int lineStartY, int lineEndX, int lineEndY, Color lineColor) {

        this.lineStartX = lineStartX;
        this.lineStartY = lineStartY;
        this.lineEndX = lineEndX;
        this.lineEndY = lineEndY;
        this.lineColor = lineColor;

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
        
        lineStartX = Math.max(Math.min(input.getWidth() - 1, lineStartX), 0); // clamping for going out of bounds for the start
        lineStartY = Math.max(Math.min(input.getHeight() - 1, lineStartY), 0);

        lineEndX = Math.max(Math.min(input.getWidth() - 1, lineEndX), 0); // clamping for going out of bounds for the end
        lineEndY = Math.max(Math.min(input.getHeight() - 1, lineEndY), 0);

       // input.getGraphics().drawLine(lineStartX, lineStartY, lineEndX, lineEndY);
       paint(input.getGraphics());

        return input;
        
    }

    /**
     * Paints the line onto the given Graphics context.
     * 
     * @param g The Graphics context to paint on.
     */
    public void paint(Graphics g){
        g.setColor(lineColor);
        g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);

    }


}
