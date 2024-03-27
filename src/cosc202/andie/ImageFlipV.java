package cosc202.andie;

import java.awt.image.*;


/**
 * A class representing an operation to flip an image vertically.
 * This class implements the ImageOperation interface and provides a method to flip an image vertically.
 * 
 * <p>
 * Flipping an image vertically involves reversing the order of pixels in each column.
 * </p>
 * 
 * <p>
 * This class is typically called when the user selects the "Transformations -> Flip -> Vertical" option.
 * </p>
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
     * Apply the vertical flip operation to the input image.
     * 
     * <p>
     * This method flips the input image vertically by reversing the order of pixels in each column.
     * </p>
     * 
     * @param input The image to flip vertically.
     * @return The resulting vertically flipped image.
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
