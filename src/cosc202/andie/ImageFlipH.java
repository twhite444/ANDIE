package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * This is the class for image flipping horizontally.
 * </p>
 * 
 * <p>
 * Once the user click the Flip -> Horizontally on the tab , this class gets
 * called
 * </p>
 * 
 * 
 * @author Yusei Tokito
 * @version 1.0
 */
public class ImageFlipH implements ImageOperation, java.io.Serializable {
    /**
     * Default constractor
     */
    ImageFlipH() {

    }

    /**
     * 
     * <p>
     * Flipping the image horizontally
     * </p>
     * 
     * 
     * @param input The image to flip the image horizontally.
     * @return The resulting flipped horizontally image
     */
    public BufferedImage apply(BufferedImage input) {

        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage hFlippedImage = new BufferedImage(width, height, input.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = input.getRGB(x, y);
                int newX = width - 1 - x;
                hFlippedImage.setRGB(newX, y, pixel);
            }
        }

        return hFlippedImage;

    }

}