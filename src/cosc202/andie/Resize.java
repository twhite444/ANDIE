package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to resize an image to either 50% or 150% of the original size.
 * </p>
 * 
 * <p>
 * The images produced by this operation can be either half or 1.5 times the original size.
 * </p>
 * 
 * @author Tommo White
 * @version 1.0
 */
public class Resize implements ImageOperation, java.io.Serializable {

    private double scale; // Scaling factor: 0.5 for 50%, 1.5 for 150%

    /**
     * <p>
     * Create a new Resize operation with the specified scaling factor.
     * </p>
     * 
     * @param scale The scaling factor. 0.5 for 50%, 1.5 for 150%.
     */
    Resize(double scale) {
        this.scale = scale;
    }

    /**
     * <p>
     * Resize the image by the specified scaling factor.
     * </p>
     * 
     * @param input The image to be resized.
     * @return The resulting resized image.
     */
    public BufferedImage apply(BufferedImage input) {
        int newWidth = (int) (input.getWidth() * scale);
        int newHeight = (int) (input.getHeight() * scale);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, input.getType());

        Graphics2D g2d = resizedImage.createGraphics();

        g2d.drawImage(input.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

        g2d.dispose();

        return resizedImage;
    }
}
