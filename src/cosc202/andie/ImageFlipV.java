package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * This is the class for image flipping vertically .
 * </p>
 * 
 * <p>
 * Once the user click the Flip -> Vertically on the tab , this class gets
 * called
 * </p>
 * 
 * 
 * @author Yusei Tokito
 * @version 1.0
 */
public class ImageFlipV implements ImageOperation, java.io.Serializable {

    /**
     * Default constractor
     */
    ImageFlipV() {

    }

    /**
     * 
     * <p>
     * Flipping the image vertically
     * </p>
     * 
     * 
     * @param input The image to flip the image vertically.
     * @return The resulting flipped vertically image
     */
    public BufferedImage apply(BufferedImage input) {

        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage vFlippedImage = new BufferedImage(width, height, input.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = input.getRGB(x, y);
                int newY = height - 1 - y;
                vFlippedImage.setRGB(x, newY, pixel);

            }
        }

        return vFlippedImage;

    }

}
