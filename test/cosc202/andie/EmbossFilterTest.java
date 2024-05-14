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

        // int[][] redChannel = {
        //     {255, 255, 255},
        //     {255, 255, 255},
        //     {255, 255, 255}
        // };
        
        

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
        sampleInputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
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
            {38, 81, 170},
            {18, 81, 190},
            {170, 0, 0}
        };
        
        // int[][] redChannel = {
        //     {255, 255, 255},
        //     {255, 255, 255},
        //     {255, 255, 255}
        // };
        
        
        

        int[][] greenChannel = {
            {0, 0, 161},
            {235, 165, 57},
            {0, 136, 255}
        };
        
        
        int[][] blueChannel = {
            {24, 41, 144},
            {255, 255, 126},
            {187, 250, 190}
        };



        
        
        

        // Create a BufferedImage with alpha channel
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < redChannel.length; y++) {
            for (int x = 0; x < redChannel[y].length; x++) {
                int alpha = 127; // Max alpha value (opaque)
                int rgb = (alpha << 24) | (redChannel[y][x] << 16) | (greenChannel[y][x] << 8) | blueChannel[y][x];
                expectedOutputImage.setRGB(x, y, rgb);
            }
        }


        // Apply Rotate90Left
        BufferedImage outputImage = new Emboss("1").apply(sampleInputImage);

        // Compare output with output image
        for (int y = 0; y < outputImage.getHeight(); y++) {
            for (int x = 0; x < outputImage.getWidth(); x++) {
                // Get the RGB value of the pixel
                int rgb = outputImage.getRGB(x, y);
                // Extract individual color components
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int alpha = (rgb >> 24) & 0xFF;
                // Print the RGB values
                System.out.println("Pixel at (" + x + "," + y + "): Red = " + red + ", Green = " + green + ", Blue = " + blue + ", Alpha = " + alpha);
            }
        }
        // Compare output with expected output
        for (int y = 0; y < expectedOutputImage.getHeight(); y++) {
            for (int x = 0; x < expectedOutputImage.getWidth(); x++) {
                // Get the RGB value of the pixel
                int rgb = expectedOutputImage.getRGB(x, y);
                // Extract individual color components
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int alpha = (rgb >> 24) & 0xFF;
                // Print the RGB values
                System.out.println("Pixel at (" + x + "," + y + "): Red = " + red + ", Green = " + green + ", Blue = " + blue + ", Alpha = " + alpha);
            }
        }
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
