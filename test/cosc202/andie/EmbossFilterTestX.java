package cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class EmbossFilterTestX{
    private BufferedImage sampleInputImage;

    @BeforeEach
    void setUp() {
        int[][] redChannel = {
            {166, 255, 212, 245},
            {35, 144, 81, 132},
            {77, 34, 225, 195},
            {3, 66, 113, 189}
        };
        

        int[][] greenChannel = {
            {18, 233, 199, 36},
            {251, 143, 213, 111},
            {42, 234, 33, 85},
            {29, 108, 89, 25}
        };
        
        int[][] blueChannel = {
            {46, 149, 132, 158},
            {207, 38, 39, 129},
            {220, 160, 97, 121},
            {138, 20, 161, 228}
        };
        

        // Create a BufferedImage with alpha channel
        BufferedImage sampleInputImage = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);
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
            {-89, -46, 10, -33},
            {-109, -46, 12, -51},
            {43, -148, -161, 30},
            {-63, -110, -123, -76}
        };
        
        

        int[][] greenChannel = {
            {-215, 108, -192, -79},
            {-181, 38, 9, -60},
            {197, 32, 149, 83},
            {163, 102, -52, 64}
        };
        
        
        int[][] blueChannel = {
            {-103, -86, -9, -26},
            {169, 168, -91, -90},
            {60, 123, 39, -24},
            {118, -23, -208, -67}
        };
        
        

        // Create a BufferedImage with alpha channel
        BufferedImage expectedOutputImage = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);
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
