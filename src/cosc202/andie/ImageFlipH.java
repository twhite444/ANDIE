package cosc202.andie;

import java.awt.image.*;

/**
* A class representing an operation to flip an image horizontally.
* This class implements the ImageOperation interface and provides a method to flip an image horizontally.
* 
* <p>
* Flipping an image horizontally involves reversing the order of pixels in each row.
* </p>
 * <p>
 * This class is typically called when the user selects the "Transformations -> Flip -> Horizontal" option.
 * </p>
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
     * Apply the horizontal flip operation to the input image.
     * 
     * <p>
     * This method flips the input image horizontally by reversing the order of pixels in each row.
     * </p>
     * 
     * @param input The image to flip horizontally.
     * @return The resulting horizontally flipped image.
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