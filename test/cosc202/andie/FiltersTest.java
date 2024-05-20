package cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;


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

        // expectedOutput.setRGB(0, 0, -1);
        // expectedOutput.setRGB(1, 0, -269488145);
        // expectedOutput.setRGB(2, 0, 1128481603);
        // expectedOutput.setRGB(0, 1, -1);
        // expectedOutput.setRGB(1, 1, -269488145);
        // expectedOutput.setRGB(2, 1, 1128481603);
        // expectedOutput.setRGB(0, 2, -1);
        // expectedOutput.setRGB(1, 2, -269488145);
        // expectedOutput.setRGB(2, 2, 1128481603);
        BufferedImage appliedInput = new SharpenFilter().apply(input);
        // Compare output with output image
        System.out.println("Input image: ");
        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                // Get the RGB value of the pixel
                int rgb = input.getRGB(x, y);
                // Extract individual color components
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int alpha = (rgb >> 24) & 0xFF;
                // Print the RGB values
                System.out.println("Pixel at (" + x + "," + y + "): Red = " + red + ", Green = " + green + ", Blue = " + blue + ", Alpha = " + alpha);
            }
        }
        // Compare output with output image
        System.out.println("Output image: ");
        for (int y = 0; y < appliedInput.getHeight(); y++) {
            for (int x = 0; x < appliedInput.getWidth(); x++) {
                // Get the RGB value of the pixel
                int rgb = appliedInput.getRGB(x, y);
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
        System.out.println("Expected Output image: ");
        for (int y = 0; y < expectedOutput.getHeight(); y++) {
            for (int x = 0; x < expectedOutput.getWidth(); x++) {
                // Get the RGB value of the pixel
                int rgb = expectedOutput.getRGB(x, y);
                // Extract individual color components
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int alpha = (rgb >> 24) & 0xFF;
                // Print the RGB values
                System.out.println("Pixel at (" + x + "," + y + "): Red = " + red + ", Green = " + green + ", Blue = " + blue + ", Alpha = " + alpha);
            }
        }
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
    @Test
    void testGaussianFilter() {
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
        expectedOutput.setRGB(0, 0, -50529028);
        expectedOutput.setRGB(1, 0, 2021161080);
        expectedOutput.setRGB(2, 0, 16843009);
        expectedOutput.setRGB(0, 1, -50529028);
        expectedOutput.setRGB(1, 1, 2021161080);
        expectedOutput.setRGB(2, 1, 16843009);
        expectedOutput.setRGB(0, 2, -50529028);
        expectedOutput.setRGB(1, 2, 2021161080);
        expectedOutput.setRGB(2, 2, 16843009);

        BufferedImage appliedInput = new GaussianFilter().apply(input);
        // Compare output with output image
        for (int y = 0; y < appliedInput.getHeight(); y++) {
            for (int x = 0; x < appliedInput.getWidth(); x++) {
                // Get the RGB value of the pixel
                int rgb = appliedInput.getRGB(x, y);
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
        for (int y = 0; y < expectedOutput.getHeight(); y++) {
            for (int x = 0; x < expectedOutput.getWidth(); x++) {
                // Get the RGB value of the pixel
                int rgb = expectedOutput.getRGB(x, y);
                // Extract individual color components
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int alpha = (rgb >> 24) & 0xFF;
                // Print the RGB values
                System.out.println("Pixel at (" + x + "," + y + "): Red = " + red + ", Green = " + green + ", Blue = " + blue + ", Alpha = " + alpha);
            }
        }
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
