package cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class EmbossFilterTest{
    private BufferedImage sampleInputImage;

    @BeforeEach
    void setUp() {
        int[][] redChannel = {
            {166, 255, 212},
            {35, 144, 81},
            {77, 34, 225}
        };
        
        

        int[][] greenChannel = {
            {18, 233, 199},
            {251, 143, 213},
            {42, 234, 33}
        };
        
        
        int[][] blueChannel = {
            {46, 149, 132},
            {207, 38, 39},
            {220, 160, 97}
        };
        
        

        // Create a BufferedImage with alpha channel
        BufferedImage sampleInputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < redChannel.length; y++) {
            for (int x = 0; x < redChannel[y].length; x++) {
                int alpha = 255; // Max alpha value (opaque)
                int rgb = (alpha << 24) | (redChannel[y][x] << 16) | (greenChannel[y][x] << 8) | blueChannel[y][x];
                sampleInputImage.setRGB(x, y, rgb);
            }
        }
    }

    @Test
    void testEmboss1() {
        int[][] redChannel = {
            {38, 81, 43},
            {18, 81, 63},
            {43, 0, 0}
        };
        
        
        

        int[][] greenChannel = {
            {0, 0, 34},
            {108, 38, 57},
            {0, 9, 201}
        };
        
        
        int[][] blueChannel = {
            {24, 41, 17},
            {169, 168, 126},
            {60, 123, 63}
        };
        
        
        

        // Create a BufferedImage with alpha channel
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < redChannel.length; y++) {
            for (int x = 0; x < redChannel[y].length; x++) {
                int alpha = 255; // Max alpha value (opaque)
                int rgb = (alpha << 24) | (redChannel[y][x] << 16) | (greenChannel[y][x] << 8) | blueChannel[y][x];
                expectedOutputImage.setRGB(x, y, rgb);
            }
        }


        // Apply Rotate90Left
        BufferedImage outputImage = new Emboss().apply(sampleInputImage);

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
