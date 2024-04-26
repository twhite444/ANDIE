package cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cosc202.andie.Rotate180;
import cosc202.andie.Rotate90Right;
import cosc202.andie.Rotate90Left;
import cosc202.andie.ImageFlipH;
import cosc202.andie.ImageFlipV;

public class TransformationsTest {
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
    void testRotate90Left() {
        // Expected output image after Rotate90Left
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        expectedOutputImage.setRGB(0, 0, 0x0000FF); // Blue
        expectedOutputImage.setRGB(1, 0, 0x00FFFF); // Cyan
        expectedOutputImage.setRGB(2, 0, 0x808080); // Gray
        expectedOutputImage.setRGB(0, 1, 0x00FF00); // Green
        expectedOutputImage.setRGB(1, 1, 0xFF00FF); // Magenta
        expectedOutputImage.setRGB(2, 1, 0x000000); // Black
        expectedOutputImage.setRGB(0, 2, 0xFF0000); // Red
        expectedOutputImage.setRGB(1, 2, 0xFFFF00); // Yellow
        expectedOutputImage.setRGB(2, 2, 0xFFFFFF); // White


        // Apply Rotate90Left
        BufferedImage outputImage = new Rotate90Left().apply(sampleInputImage);

        // Compare output with expected output
        assertImagesEqual(expectedOutputImage, outputImage);
    }

    @Test
    void testRotate180() {
        // Expected output image after Rotate180
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        expectedOutputImage.setRGB(0, 0, 0x808080); // Gray
        expectedOutputImage.setRGB(1, 0, 0x000000); // Black
        expectedOutputImage.setRGB(2, 0, 0xFFFFFF); // White
        expectedOutputImage.setRGB(0, 1, 0x00FFFF); // Cyan
        expectedOutputImage.setRGB(1, 1, 0xFF00FF); // Magenta
        expectedOutputImage.setRGB(2, 1, 0xFFFF00); // Yellow
        expectedOutputImage.setRGB(0, 2, 0x0000FF); // Blue
        expectedOutputImage.setRGB(1, 2, 0x00FF00); // Green
        expectedOutputImage.setRGB(2, 2, 0xFF0000); // Red


        // Apply Rotate180
        BufferedImage outputImage = new Rotate180().apply(sampleInputImage);

        // Compare output with expected output
        assertImagesEqual(expectedOutputImage, outputImage);
    }

    @Test
    void testRotate90Right() {
        // Expected output image after Rotate90Right
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        expectedOutputImage.setRGB(0, 0, 0xFFFFFF); // White
        expectedOutputImage.setRGB(1, 0, 0xFFFF00); // Yellow
        expectedOutputImage.setRGB(2, 0, 0xFF0000); // Red
        expectedOutputImage.setRGB(0, 1, 0x000000); // Black
        expectedOutputImage.setRGB(1, 1, 0xFF00FF); // Magenta
        expectedOutputImage.setRGB(2, 1, 0x00FF00); // Green
        expectedOutputImage.setRGB(0, 2, 0x808080); // Gray
        expectedOutputImage.setRGB(1, 2, 0x00FFFF); // Cyan
        expectedOutputImage.setRGB(2, 2, 0x0000FF); // Blue

        // Apply Rotate90Right
        BufferedImage outputImage = new Rotate90Right().apply(sampleInputImage);

        // Compare output with expected output
        assertImagesEqual(expectedOutputImage, outputImage);
    }

    @Test
    void testImageFlipV() {
        // Expected output image after ImageFlipV
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        expectedOutputImage.setRGB(0, 0, 0xFFFFFF); // White
        expectedOutputImage.setRGB(1, 0, 0x000000); // Black
        expectedOutputImage.setRGB(2, 0, 0x808080); // Gray
        expectedOutputImage.setRGB(0, 1, 0xFFFF00); // Yellow
        expectedOutputImage.setRGB(1, 1, 0xFF00FF); // Magenta
        expectedOutputImage.setRGB(2, 1, 0x00FFFF); // Cyan
        expectedOutputImage.setRGB(0, 2, 0xFF0000); // Red
        expectedOutputImage.setRGB(1, 2, 0x00FF00); // Green
        expectedOutputImage.setRGB(2, 2, 0x0000FF); // Blue

        // Apply ImageFlipV
        BufferedImage outputImage = new ImageFlipV().apply(sampleInputImage);

        // Compare output with expected output
        assertImagesEqual(expectedOutputImage, outputImage);
    }

    @Test
    void testImageFlipH() {
        // Expected output image after ImageFlipH
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        expectedOutputImage.setRGB(0, 0, 0x0000FF); // Blue
        expectedOutputImage.setRGB(1, 0, 0x00FF00); // Green
        expectedOutputImage.setRGB(2, 0, 0xFF0000); // Red
        expectedOutputImage.setRGB(0, 1, 0x00FFFF); // Cyan
        expectedOutputImage.setRGB(1, 1, 0xFF00FF); // Magenta
        expectedOutputImage.setRGB(2, 1, 0xFFFF00); // Yellow
        expectedOutputImage.setRGB(0, 2, 0x808080); // Gray
        expectedOutputImage.setRGB(1, 2, 0x000000); // Black
        expectedOutputImage.setRGB(2, 2, 0xFFFFFF); // White

        // Apply ImageFlipH
        BufferedImage outputImage = new ImageFlipH().apply(sampleInputImage);

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
