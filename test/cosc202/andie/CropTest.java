package cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cosc202.andie.Crop;

public class CropTest {
    private BufferedImage sampleInputImage;

    @BeforeEach
    void setUp() {

        // Create a sample input image (3x3)
        sampleInputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

    }

    @Test
    void testChangeContrastAndBrightness() {

        // Expected output image after ChangeContrastAndBrightness
        BufferedImage expectedOutputImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

        // Apply ChangeContrastAndBrightness
        BufferedImage outputImage = new Crop(0, 0, 2, 2).apply(sampleInputImage);

        // Compare output with expected output
        assertImagesEqual(expectedOutputImage, outputImage);
        
    }

    private void assertImagesEqual(BufferedImage expected, BufferedImage actual) {

        assertEquals(expected.getWidth(), actual.getWidth());
        assertEquals(expected.getHeight(), actual.getHeight());

        for (int y = 0; y < expected.getHeight(); y++) {

            for (int x = 0; x < expected.getWidth(); x++) {

                assertEquals(expected.getRGB(x, y), actual.getRGB(x, y), "Pixel at (" + x + ", " + y + ") doesn't match expected value.");

            }

        }

    }

}
