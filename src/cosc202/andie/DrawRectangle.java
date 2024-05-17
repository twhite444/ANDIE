package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics;
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
    Color fillColor;
    Color lineColor;
    boolean  isCrop = false;

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
        this.fillColor = Color.black;
        this.lineColor = Color.black;

        

    }

    /**
     * <p>
     * Create a new DrawRectangle operation of specified type.
     * </p>
     */
    DrawRectangle(int rectStartX, int rectStartY, int rectWidth, int rectHeight, Color lineColor, Color fillColor) {

        this.rectStartX = rectStartX;
        this.rectStartY = rectStartY;
        this.rectWidth = rectWidth;
        this.rectHeight = rectHeight;
        this.lineColor = lineColor;
        this.fillColor = fillColor;

    }
    DrawRectangle(int rectStartX, int rectStartY, int rectWidth, int rectHeight, boolean isCrop) {

        this.rectStartX = rectStartX;
        this.rectStartY = rectStartY;
        this.rectWidth = rectWidth;
        this.rectHeight = rectHeight;
        this.isCrop = isCrop;

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

        if(isCrop) {
            input.getGraphics().drawRect(rectStartX, rectStartY, rectWidth, rectHeight);
        } else {
            paint(input.getGraphics());
        }
        

        return input;
        
    }

    /**
     * Paints the rectangle onto the given Graphics context.
     * 
     * @param g The Graphics context to paint on.
     */
    public void paint(Graphics g){
        
        g.setColor(fillColor);
        g.fillRect(rectStartX, rectStartY, rectWidth, rectHeight);
        g.setColor(lineColor);
        g.drawRect(rectStartX, rectStartY, rectWidth, rectHeight);

    }

}
