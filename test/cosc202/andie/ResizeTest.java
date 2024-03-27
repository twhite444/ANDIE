package test.cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cosc202.andie.Resize;

public class ResizeTest {
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
    void testResize50Percent() {
        // Expected output image after resizing by 50%
        BufferedImage expectedOutputImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        expectedOutputImage.setRGB(0, 0, 0xFF0000); // Red (average of input pixels)

        // Apply resizing by 50%
        BufferedImage outputImage = new Resize(0.5).apply(sampleInputImage);

        // Compare output with expected output
        assertImagesEqual(expectedOutputImage, outputImage);
    }

    @Test
    void testResize150Percent() {
        // Expected output image after resizing by 150%
        BufferedImage expectedOutputImage = new BufferedImage(4, 4, BufferedImage.TYPE_INT_RGB);
        // Fill in expected pixels with average of input pixels
        expectedOutputImage.setRGB(0, 0, 0x800000); // Red
        expectedOutputImage.setRGB(1, 0, 0x008000); // Green
        expectedOutputImage.setRGB(2, 0, 0x000080); // Blue
        expectedOutputImage.setRGB(3, 0, 0x808000); // Yellow
        expectedOutputImage.setRGB(0, 1, 0x800080); // Magenta
        expectedOutputImage.setRGB(1, 1, 0x008080); // Cyan
        expectedOutputImage.setRGB(2, 1, 0x808080); // Gray
        expectedOutputImage.setRGB(3, 1, 0xFFFFFF); // White
        expectedOutputImage.setRGB(0, 2, 0x000000); // Black
        expectedOutputImage.setRGB(1, 2, 0x000000); // Black
        expectedOutputImage.setRGB(2, 2, 0x000000); // Black
        expectedOutputImage.setRGB(3, 2, 0x000000); // Black
        expectedOutputImage.setRGB(0, 3, 0x404040); // Dark Gray
        expectedOutputImage.setRGB(1, 3, 0x404040); // Dark Gray
        expectedOutputImage.setRGB(2, 3, 0x404040); // Dark Gray
        expectedOutputImage.setRGB(3, 3, 0x404040); // Dark Gray

        // Apply resizing by 150%
        BufferedImage outputImage = new Resize(1.5).apply(sampleInputImage);

        // Compare output with expected output
        assertImagesEqual(expectedOutputImage, outputImage);
    }

    private void assertImagesEqual(BufferedImage expected, BufferedImage actual) {
        assertEquals(expected.getWidth(), actual.getWidth());
        assertEquals(expected.getHeight(), actual.getHeight());
        for (int y = 0; y < expected.getHeight(); y++) {
            for (int x = 0; x < expected.getWidth(); x++) {
                assertEquals(expected.getRGB(x, y), actual.getRGB(x, y),
                        "Pixel at (" + x + ", " + y + ") doesn't match expected value.");
            }
        }
    }
}
