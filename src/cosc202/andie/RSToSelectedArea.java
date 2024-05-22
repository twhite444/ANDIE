package cosc202.andie;

import java.awt.Graphics2D;
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
 * @author Yusei Tokito
 * @version 1.0
 */
public class RSToSelectedArea implements ImageOperation, java.io.Serializable {
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
    RSToSelectedArea() {

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
    RSToSelectedArea(int cropStartX, int cropStartY, int cropWidth, int cropHeight) {

        this.cropStartX = cropStartX;
        this.cropStartY = cropStartY;
        this.cropWidth = cropWidth;
        this.cropHeight = cropHeight;

    }

    /**
     * <p>
     * Apply RandomScattering to an selected area.
     * </p>
     * 
     * @param input The image to be cropped
     * @return The resulting random scattering applied image
     */
    public BufferedImage apply(BufferedImage input) {

        if (cropStartX > input.getWidth() || cropStartY > input.getHeight()) {

            throw new RuntimeException("crop attempted entierly outside image");

        }
        
        cropStartX = Math.max(Math.min(input.getWidth() - 1, cropStartX), 0); // clamping for going out of bounds left or up
        cropStartY = Math.max(Math.min(input.getHeight() - 1, cropStartY), 0);

        cropWidth = Math.max(Math.min(Math.abs(input.getWidth()) - cropStartX, cropWidth), 1); // clampng for going out of bounds right or down
        cropHeight = Math.max(Math.min(Math.abs(input.getHeight()) - cropStartY, cropHeight), 1);

        BufferedImage bf = input.getSubimage(cropStartX, cropStartY, cropWidth, cropHeight);
        RandomScattering ba = new RandomScattering(5);
        BufferedImage selectedApplied= ba.apply(bf);
        Graphics2D g2d = input.createGraphics();
        g2d.drawImage(selectedApplied,cropStartX,cropStartY,null);
        g2d.dispose();
 

        return input;
        
    }

}
