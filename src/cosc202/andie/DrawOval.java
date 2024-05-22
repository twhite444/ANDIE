package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics;
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
    /** The starting X coordinate of the oval */
    int ovalStartX;
    /** The starting Y coordinate of the oval */
    int ovalStartY;
    /** The width of the oval */
    int ovalWidth;
    /** The height of the oval */
    int ovalHeight;
    /** The fill color of the oval */
    Color fillColor;
    /** The color of the line of the oval */
    Color lineColor;

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
        this.fillColor = Color.black;
        this.lineColor = Color.black;

    }

    /**
     * <p>
     * Create a new DrawOval operation of specified type.
     * </p>
     */
    DrawOval(int ovalStartX, int ovalStartY, int ovalWidth, int ovalHeight, Color lineColor, Color fillColor) {

        this.ovalStartX = ovalStartX;
        this.ovalStartY = ovalStartY;
        this.ovalWidth = ovalWidth;
        this.ovalHeight = ovalHeight;
        this.lineColor = lineColor;
        this.fillColor = fillColor;

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
        
        ovalStartX = Math.max(Math.min(input.getWidth() - 1, ovalStartX), 0); // clamping for going out of bounds left or up
        ovalStartY = Math.max(Math.min(input.getHeight() - 1, ovalStartY), 0);

        ovalWidth = Math.min(input.getWidth() - ovalStartX - 1, ovalWidth); // clamping for going out of bounds right or down
        ovalHeight = Math.min(input.getHeight() - ovalStartY - 1, ovalHeight);
        
        //input.getGraphics().setColor(Color.blue);
        //input.getGraphics().fillOval(ovalStartX, ovalStartY, ovalWidth, ovalHeight);
        paint(input.getGraphics());
        return input;
        
    }
    
    /**
     * Paints the oval onto the given Graphics context.
     * 
     * @param g The Graphics context to paint on.
     */
    public void paint(Graphics g){

        g.setColor(fillColor);
        g.fillOval(ovalStartX, ovalStartY, ovalWidth, ovalHeight);
        g.setColor(lineColor);
        g.drawOval(ovalStartX, ovalStartY, ovalWidth, ovalHeight);

    }

}
