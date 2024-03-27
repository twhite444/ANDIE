import java.awt.Color; 
import java.awt.imageBufferedImage;


public class MedianFilter {

/*Method that takes in a input image between specific radius bounds and applys
 * the median filter to it. 
 * @param BufferedImage inputImage, int radius 
 * @return BufferedImage
 */
    public static BufferedImage applyMedianFilter(BufferedImage inputImage, int radius) {
        int w = inputImage.getWidth();
        int h = inputImage.getHeight();
        BufferedImage outputImage= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //two for-loops that goes throught each pixel in the inputImage
        for(int x = 0; x < h; x++) {
            for(int y = 0; y < w; y++) {
                //creates an array for each color to store pixel values
                int[] redPixelValue = new int[(2 * radius + 1) * (2 * radius + 1)];
                int[] greenPixelValue = new int[(2 * radius + 1) * (2 * radius + 1)];
                int[] bluePixelValue = new int[(2 * radius + 1) * (2 * radius + 1)];
                int marker = 0;
                //two for-loops that go through each pixel
                for (int z = -radius; z <= radius; z++){
                    for (int p = -radius; p <= radius; p++){
                        //calculating the new color of the pixels and storing in red green and blue values 
                        int pixelOne= Math.min(Math.max(x + dx,0), width -1);
                        int pixelTwo= Math.min(Math.max(x + dx,0), width -1);
                        Color pixelColor = new Color(inputImage.getRGB(pizelOne, pixelTwo));
                        redPixelValye[marker] = pixelColor.getRed();
                        greenPixelValye[marker] = pixelColor.getGreen();
                        bluePixelValye[marker] = pixelColor.getBlue();
                        index++;
                    }
                }
                //set the new values of the red, green and blue pixels into the output image
                outputImage.setRGB(x, y, getMedianColor(redValues), getMedianColor(greenValues), getMedianColor(blueValues));
            }
        }
        return outputImage;
    }

    /*Method that calculates the median values for the color arrays of integers
     * @param int[] values
     * @return int[] 
     */
    private static int getMedianColor(int[] values) {
        int length = values.length;
        int[] finall = values.clone();
        //sort array of values 
        Arrays.sort(finall);
        return finall[length / 2]; //return new array 
    }
}