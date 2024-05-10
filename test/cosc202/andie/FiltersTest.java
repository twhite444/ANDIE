package cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;


//all values are calculated with using excel 
public class FiltersTest {

    @Test
    void testMedianFilter() {
        BufferedImage input = new BufferedImage(3, 3, 2);

        input.setRGB(0, 0, -1);
        input.setRGB(1, 0, 2021161080);
        input.setRGB(2, 0, 0);
        input.setRGB(0, 1, -1);
        input.setRGB(1, 1, 2021161080);
        input.setRGB(2, 1, 0);
        input.setRGB(0, 2, -1);
        input.setRGB(1, 2, 2021161080);
        input.setRGB(2, 2, 0);

        BufferedImage expectedOutput = new BufferedImage(3, 3, 2);
        expectedOutput.setRGB(0, 0, -1);
        expectedOutput.setRGB(1, 0, 2021161080);
        expectedOutput.setRGB(2, 0, 0);
        expectedOutput.setRGB(0, 1, -1);
        expectedOutput.setRGB(1, 1, 2021161080);
        expectedOutput.setRGB(2, 1, 0);
        expectedOutput.setRGB(0, 2, -1);
        expectedOutput.setRGB(1, 2, 2021161080);
        expectedOutput.setRGB(2, 2, 0);

        BufferedImage appliedInput = new MedianFilter().apply(input);

        assertImagesEqual(expectedOutput, appliedInput);

    }

    @Test
    void testMeanFilter() {
        BufferedImage input = new BufferedImage(3, 3, 2);

        input.setRGB(0, 0, -1);
        input.setRGB(1, 0, 2021161080);
        input.setRGB(2, 0, 0);
        input.setRGB(0, 1, -1);
        input.setRGB(1, 1, 2021161080);
        input.setRGB(2, 1, 0);
        input.setRGB(0, 2, -1);
        input.setRGB(1, 2, 2021161080);
        input.setRGB(2, 2, 0);

        BufferedImage expectedOutput = new BufferedImage(3, 3, 2);
        expectedOutput.setRGB(0, 0, -757935406);
        expectedOutput.setRGB(1, 0, 2105376125);
        expectedOutput.setRGB(2, 0, 673720360);
        expectedOutput.setRGB(0, 1, -757935406);
        expectedOutput.setRGB(1, 1, 2105376125);
        expectedOutput.setRGB(2, 1, 673720360);
        expectedOutput.setRGB(0, 2, -757935406);
        expectedOutput.setRGB(1, 2, 2105376125);
        expectedOutput.setRGB(2, 2, 673720360);
        BufferedImage appliedInput = new MeanFilter().apply(input);

        assertImagesEqual(expectedOutput, appliedInput);

    }


    @Test
    void testSharpenFilter() {
        BufferedImage input = new BufferedImage(3, 3, 2);

        input.setRGB(0, 0, -1);
        input.setRGB(1, 0, 2021161080);
        input.setRGB(2, 0, 0);
        input.setRGB(0, 1, -1);
        input.setRGB(1, 1, 2021161080);
        input.setRGB(2, 1, 0);
        input.setRGB(0, 2, -1);
        input.setRGB(1, 2, 2021161080);
        input.setRGB(2, 2, 0);

        BufferedImage expectedOutput = new BufferedImage(3, 3, 2);
        expectedOutput.setRGB(0, 0, 1128481602);
        expectedOutput.setRGB(1, 0, 1886417008);
        expectedOutput.setRGB(2, 0, 0);
        expectedOutput.setRGB(0, 1, 1128481602);
        expectedOutput.setRGB(1, 1, 1886417008);
        expectedOutput.setRGB(2, 1, 0);
        expectedOutput.setRGB(0, 2, 1128481602);
        expectedOutput.setRGB(1, 2, 1886417008);
        expectedOutput.setRGB(2, 2, 0);
        BufferedImage appliedInput = new SharpenFilter().apply(input);

        assertImagesEqual(expectedOutput, appliedInput);

    }
    @Test
    void testSoftBlurFilter() {
        BufferedImage input = new BufferedImage(3, 3, 2);

        input.setRGB(0, 0, -1);
        input.setRGB(1, 0, 2021161080);
        input.setRGB(2, 0, 0);
        input.setRGB(0, 1, -1);
        input.setRGB(1, 1, 2021161080);
        input.setRGB(2, 1, 0);
        input.setRGB(0, 2, -1);
        input.setRGB(1, 2, 2021161080);
        input.setRGB(2, 2, 0);

        BufferedImage expectedOutput = new BufferedImage(3, 3, 2);
        expectedOutput.setRGB(0, 0, -286331154);
        expectedOutput.setRGB(1, 0, 2038004089);
        expectedOutput.setRGB(2, 0, 252645135);
        expectedOutput.setRGB(0, 1, -286331154);
        expectedOutput.setRGB(1, 1, 2038004089);
        expectedOutput.setRGB(2, 1, 252645135);
        expectedOutput.setRGB(0, 2, -286331154);
        expectedOutput.setRGB(1, 2, 2038004089);
        expectedOutput.setRGB(2, 2, 252645135);
        BufferedImage appliedInput = new SoftBlur().apply(input);

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
// "Pixel at (" + 1 + ", " + 1 + ") doesn't match expected value.");\
