package cosc202.andie;


import java.awt.image.BufferedImage;





public class EmbossFilterTest{
    static BufferedImage sampleInputImage;

    
    public static void main() {
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
        sampleInputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < redChannel.length; y++) {
            for (int x = 0; x < redChannel[y].length; x++) {
                int alpha = 255; // Max alpha value (opaque)
                int rgb = (alpha << 24) | (redChannel[y][x] << 16) | (greenChannel[y][x] << 8) | blueChannel[y][x];
                sampleInputImage.setRGB(x, y, rgb);
            }
        }
        
        int[][] redChannelE = {
            {38, 81, 43},
            {18, 81, 63},
            {43, 0, 0}
        };
        
        
        

        int[][] greenChannelE = {
            {0, 0, 34},
            {108, 38, 57},
            {0, 9, 201}
        };
        
        
        int[][] blueChannelE = {
            {24, 41, 17},
            {169, 168, 126},
            {60, 123, 63}
        };
        
        
        

        // Create a BufferedImage with alpha channel
        BufferedImage expectedOutputImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < redChannel.length; y++) {
            for (int x = 0; x < redChannel[y].length; x++) {
                int alpha = 255; // Max alpha value (opaque)
                int rgb = (alpha << 24) | (redChannelE[y][x] << 16) | (greenChannelE[y][x] << 8) | blueChannelE[y][x];
                expectedOutputImage.setRGB(x, y, rgb);
            }
        }


        // Apply Rotate90Left
        BufferedImage outputImage = new Emboss("1").apply(sampleInputImage);

        for (int y = 0; y < outputImage.getHeight(); y++) {
            for (int x = 0; x < outputImage.getWidth(); x++) {
                // Get the RGB value of the pixel
                int rgb = outputImage.getRGB(x, y);
                // Extract individual color components
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                // Print the RGB values
                System.out.println("Pixel at (" + x + "," + y + "): Red = " + red + ", Green = " + green + ", Blue = " + blue);
            }
        }

        // Compare output with expected output
        System.out.println(expectedOutputImage);
        System.out.println(outputImage);
        
    }
}

    