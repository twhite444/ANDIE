package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to crop an image.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Liam Williamson
 * @version 1.0
 */
public class Crop implements ImageOperation, java.io.Serializable {
    /** The starting X coordinate of the crop area */
    int cropStartX;
    /** The starting Y coordinate of the crop area */
    int cropStartY;
    /** The width of the cropped area */
    int cropWidth;
    /** The height of the cropped area */
    int cropHeight;

    /**
     * <p>
     * Create a new Crop operation of default type.
     * </p>
     */
    Crop() {

        this.cropStartX = 1;
        this.cropStartY = 1;
        this.cropWidth = 1;
        this.cropHeight = 1;

    }

    /**
     * <p>
     * Create a new Crop operation of specified type.
     * </p>
     */
    Crop(int cropStartX, int cropStartY, int cropWidth, int cropHeight) {

        this.cropStartX = cropStartX;
        this.cropStartY = cropStartY;
        this.cropWidth = cropWidth;
        this.cropHeight = cropHeight;

    }

    /**
     * <p>
     * Apply Crop to an image.
     * </p>
     * 
     * @param input The image to be cropped
     * @return The resulting cropped image.
     */
    public BufferedImage apply(BufferedImage input) {
        
        cropStartX = Math.max(Math.min(input.getWidth() - 1, cropStartX), 0); // clamping for going out of bounds left or up
        cropStartY = Math.max(Math.min(input.getHeight() - 1, cropStartY), 0);

        cropWidth = Math.max(Math.min(Math.abs(input.getWidth()) - cropStartX, cropWidth), 1); // clampng for going out of bounds right or down
        cropHeight = Math.max(Math.min(Math.abs(input.getHeight()) - cropStartY, cropHeight), 1);

        return input.getSubimage(cropStartX, cropStartY, cropWidth, cropHeight);
        
    }

}
