package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well
 * as zooming
 * in and out.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {

    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;
    private int x, y, x1, y1;
    private BufferedImage original;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is
     * zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally
     * as a percentage.
     * </p>
     */
    private double scale;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {

        image = new EditableImage();

        scale = 1.0;

    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {

        if (!image.hasImage()) { // check if an image exsists

            JOptionPane.showMessageDialog(null,
                    "Error: image does not seem to exist, probably because no image is open", "Error",
                    JOptionPane.ERROR_MESSAGE);

            throw new RuntimeException("no image");

        }

        return image;

    }

    /**
     * <p>
     * Only required for when current image may be null
     * </p>
     *
     * @return the image currently displayed or null
     */
    public EditableImage getImageDangerous() {

        return image;

    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * </p>
     * 
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {

        return 100 * scale;

    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * 
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {

        if (zoomPercent < 50) {

            zoomPercent = 50;

        }

        if (zoomPercent > 200) {

            zoomPercent = 200;

        }

        scale = zoomPercent / 100;

    }

    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a
     * default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {

        if (image.hasImage()) {

            return new Dimension((int) Math.round(image.getCurrentImage().getWidth() * scale),
                    (int) Math.round(image.getCurrentImage().getHeight() * scale));

        } else {

            return new Dimension(450, 450);

        }

    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
       
        super.paintComponent(g);



        if (image.hasImage()&&x ==0) {
           
            Graphics2D g2 = (Graphics2D) g.create();

            g2.scale(scale, scale);

            g2.drawImage(image.getCurrentImage(), null, 0, 0);

            g2.dispose();

        }
        if (x != 0 && x1 != 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            int x = Math.min(this.x, this.x1);
            int y = Math.min(this.y, this.y1);
            int width = Math.abs(this.x - this.x1);
            int height = Math.abs(this.y - this.y1);
            //System.out.println(x+" "+y+" "+width+" "+height);
            g2.drawImage(original, null, 0, 0);

            g2.drawRect(x, y, width, height);
            g2.dispose();
        }
        

    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX1Y1(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
    }

    public void setOriginal(BufferedImage bf){
        original =bf;
    }

   

}
