package cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cosc202.andie.ChangeContrastAndBrightness;

public class BlockTest {
    private BufferedImage sampleInputImage;

    @BeforeEach
    void setUp() {

        // Create a sample input image (3x3)
        sampleInputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

        // Set some pixels
        sampleInputImage.setRGB(0, 0, 0xFF0000); // Red
        sampleInputImage.setRGB(1, 0, 0x00FF00); // Green
        sampleInputImage.setRGB(2, 0, 0x0000FF); // Blue
        sampleInputImage.setRGB(0, 1, 0xFFFF00); // Yellow
        sampleInputImage.setRGB(1, 1, 0xFF00FF); // Magenta
        sampleInputImage.setRGB(2, 1, 0x00FFFF); // Cyan
        sampleInputImage.setRGB(0, 2, 0xFFFFFF); // White
        sampleInputImage.setRGB(1, 2, 0x000000); // Black
        sampleInputImage.setRGB(2, 2, 0x808080); // Gray

    }

    @Test
    void testBlockAverage() {

        // Expected output image after BlockAverage
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

        // all the colours should be grey
        expectedOutputImage.setRGB(0, 0, 0x000000); // Grey
        expectedOutputImage.setRGB(1, 0, 0x7f7f7f); // Grey
        expectedOutputImage.setRGB(2, 0, 0x7f7f7f); // Grey
        expectedOutputImage.setRGB(0, 1, 0x7f7f7f); // Grey
        expectedOutputImage.setRGB(1, 1, 0x7f7f7f); // Grey
        expectedOutputImage.setRGB(2, 1, 0x7f7f7f); // Grey
        expectedOutputImage.setRGB(0, 2, 0x7f7f7f); // Grey
        expectedOutputImage.setRGB(1, 2, 0x7f7f7f); // Grey
        expectedOutputImage.setRGB(2, 2, 0x7f7f7f); // Grey

        // Apply BlockAverage
        BufferedImage outputImage = new BlockAverage(5, 5).apply(sampleInputImage);

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
