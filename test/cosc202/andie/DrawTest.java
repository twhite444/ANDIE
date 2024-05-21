package cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DrawTest {
    private BufferedImage sampleInputImage;

    @BeforeEach
    void setUp() {

        // Create a sample input image (3x3)
        sampleInputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

        // Set some pixels
        sampleInputImage.setRGB(0, 0, 0x00FF00); // Green
        sampleInputImage.setRGB(1, 0, 0x00FF00); // Green
        sampleInputImage.setRGB(2, 0, 0x00FF00); // Green
        sampleInputImage.setRGB(0, 1, 0x00FF00); // Green
        sampleInputImage.setRGB(1, 1, 0x00FF00); // Green
        sampleInputImage.setRGB(2, 1, 0x00FF00); // Green
        sampleInputImage.setRGB(0, 2, 0x00FF00); // Green
        sampleInputImage.setRGB(1, 2, 0x00FF00); // Green
        sampleInputImage.setRGB(2, 2, 0x00FF00); // Green

    }

    @Test
    void testDrawRectangle() {

        // Expected output image after ChangeContrastAndBrightness
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

        // the outside pixels should be red while the centre pixel should be blue
        expectedOutputImage.setRGB(0, 0, 0xFF0000); // Red
        expectedOutputImage.setRGB(1, 0, 0xFF0000); // Red
        expectedOutputImage.setRGB(2, 0, 0xFF0000); // Red
        expectedOutputImage.setRGB(0, 1, 0xFF0000); // Red
        expectedOutputImage.setRGB(1, 1, 0x0000FF); // Blue
        expectedOutputImage.setRGB(2, 1, 0xFF0000); // Red
        expectedOutputImage.setRGB(0, 2, 0xFF0000); // Red
        expectedOutputImage.setRGB(1, 2, 0xFF0000); // Red
        expectedOutputImage.setRGB(2, 2, 0xFF0000); // Red

        // Apply ChangeContrastAndBrightness
        BufferedImage outputImage = new DrawRectangle(0, 0, 3, 3, Color.RED, Color.BLUE).apply(sampleInputImage);

        // Compare output with expected output
        assertImagesEqual(expectedOutputImage, outputImage);
        
    }

    @Test
    void testDrawOval() {

        // Expected output image after ChangeContrastAndBrightness
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

        /**
         * should look like this VV
         *                      GRG
         *                      RBR
         *                      GRG
         */
        expectedOutputImage.setRGB(0, 0, 0x00FF00); // Green
        expectedOutputImage.setRGB(1, 0, 0xFF0000); // Red
        expectedOutputImage.setRGB(2, 0, 0x00FF00); // Green
        expectedOutputImage.setRGB(0, 1, 0xFF0000); // Red
        expectedOutputImage.setRGB(1, 1, 0x0000FF); // Blue
        expectedOutputImage.setRGB(2, 1, 0xFF0000); // Red
        expectedOutputImage.setRGB(0, 2, 0x00FF00); // Green
        expectedOutputImage.setRGB(1, 2, 0xFF0000); // Red
        expectedOutputImage.setRGB(2, 2, 0x00FF00); // Green

        // Apply ChangeContrastAndBrightness
        BufferedImage outputImage = new DrawOval(0, 0, 3, 3, Color.RED, Color.BLUE).apply(sampleInputImage);

        // Compare output with expected output
        assertImagesEqual(expectedOutputImage, outputImage);
        
    }

    @Test
    void testDrawLine() {

        // Expected output image after ChangeContrastAndBrightness
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

        /**
         * should look like this VV
         *                      RGG
         *                      GRG
         *                      GGR
         */
        expectedOutputImage.setRGB(0, 0, 0xFF0000); // Red
        expectedOutputImage.setRGB(1, 0, 0x00FF00); // Green
        expectedOutputImage.setRGB(2, 0, 0x00FF00); // Green
        expectedOutputImage.setRGB(0, 1, 0x00FF00); // Green
        expectedOutputImage.setRGB(1, 1, 0xFF0000); // Red
        expectedOutputImage.setRGB(2, 1, 0x00FF00); // Green
        expectedOutputImage.setRGB(0, 2, 0x00FF00); // Green
        expectedOutputImage.setRGB(1, 2, 0x00FF00); // Green
        expectedOutputImage.setRGB(2, 2, 0xFF0000); // Red

        // Apply ChangeContrastAndBrightness
        BufferedImage outputImage = new DrawLine(0, 0, 2, 2, Color.RED).apply(sampleInputImage);

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
