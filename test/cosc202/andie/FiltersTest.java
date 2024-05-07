package cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public class FiltersTest {

    @Test
    void testMedianFilter() {
        BufferedImage input = new BufferedImage(3, 3, 2);

        input.setRGB(0, 0, 16776960);
        input.setRGB(1, 0, 13172735);
        input.setRGB(2, 0, -1766537216);
        input.setRGB(0, 1, 16711935);
        input.setRGB(1, 1, 1689543880);
        input.setRGB(2, 1, -925748586);
        input.setRGB(0, 2, -6946816);
        input.setRGB(1, 2, -925067726);
        input.setRGB(2, 2, -2130739200);

        BufferedImage expectedOutput = new BufferedImage(3, 3, 2);
        expectedOutput.setRGB(0, 0, 16776960);
        expectedOutput.setRGB(1, 0, 13172735);
        expectedOutput.setRGB(2, 0, -925748586);
        expectedOutput.setRGB(0, 1, 16711935);
        expectedOutput.setRGB(1, 1, -6946816);
        expectedOutput.setRGB(2, 1, -925748586);
        expectedOutput.setRGB(0, 2, -6946816);
        expectedOutput.setRGB(1, 2, -925067726);
        expectedOutput.setRGB(2, 2, -925748586);
        BufferedImage appliedInput = new MedianFilter().apply(input);

        assertImagesEqual(expectedOutput, appliedInput);

    }

    @Test
    void testMeanFilter() {
        BufferedImage input = new BufferedImage(3, 3, 2);

        input.setRGB(0, 0, 16776960);
        input.setRGB(1, 0, 13172735);
        input.setRGB(2, 0, -1766537216);
        input.setRGB(0, 1, 16711935);
        input.setRGB(1, 1, 1689543880);
        input.setRGB(2, 1, -925748586);
        input.setRGB(0, 2, -6946816);
        input.setRGB(1, 2, -925067726);
        input.setRGB(2, 2, -2130739200);

        BufferedImage expectedOutput = new BufferedImage(3, 3, 2);
        expectedOutput.setRGB(0, 0, 201824562);
        expectedOutput.setRGB(1, 0, -299185312);
        expectedOutput.setRGB(2, 0, -800195187);
        expectedOutput.setRGB(0, 1, 92303671);
        expectedOutput.setRGB(1, 1, -446537114);
        expectedOutput.setRGB(2, 1, -985377901);
        expectedOutput.setRGB(0, 2, -17217218);
        expectedOutput.setRGB(1, 2, -593888917);
        expectedOutput.setRGB(2, 2, -1170560616);
        BufferedImage appliedInput = new MeanFilter().apply(input);

        assertImagesEqual(expectedOutput, appliedInput);

    }

    private void assertImagesEqual(BufferedImage expectedOutput, BufferedImage appliedInput) {
        assertEquals(expectedOutput.getWidth(), appliedInput.getWidth());
        assertEquals(expectedOutput.getHeight(), appliedInput.getHeight());
        for (int y = 0; y < appliedInput.getHeight(); y++) {
            for (int x = 1; x < appliedInput.getWidth(); x++) {
                assertEquals(expectedOutput.getRGB(x, y), appliedInput.getRGB(x, y),
                        "Pixel at (" + x + ", " + y + ") doesn't match expected value.");
            }
        }
        

    }

}

// assertEquals(expectedOutput.getRGB(0, 0), appliedInput.getRGB(0, 0),
// "Pixel at (" + 0 + ", " + 0 + ") doesn't match expected value.");
// assertEquals(expectedOutput.getRGB(1, 1), appliedInput.getRGB(1, 1),
// "Pixel at (" + 1 + ", " + 1 + ") doesn't match expected value.");