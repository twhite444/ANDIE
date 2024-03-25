package cosc202.andie;
import java.awt.image.*;

public class ImageFlipH implements ImageOperation, java.io.Serializable{
    ImageFlipH(){
        
    }

    public BufferedImage apply(BufferedImage input){

        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage hFlippedImage = new BufferedImage(width, height, input.getType());

        for(int y = 0; y<height; y++){
            for(int x = 0; x< width; x++){
                int pixel = input.getRGB(x, y);
                int newX=width-1-x;
                hFlippedImage.setRGB(newX, y, pixel);
                
        }
        }

        return hFlippedImage;

    }



}