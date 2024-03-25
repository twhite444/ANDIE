package cosc202.andie;
import java.awt.image.*;

public class ImageFlipV implements ImageOperation, java.io.Serializable{
    ImageFlipV(){
        
    }

    public BufferedImage apply(BufferedImage input){

        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage vFlippedImage = new BufferedImage(width, height, input.getType());

        for(int y = 0; y<height; y++){
            for(int x = 0; x< width; x++){
                int pixel = input.getRGB(x, y);
                int newY=height-1-y;
                vFlippedImage.setRGB(x, newY, pixel);
                
        }
        }

        return vFlippedImage;

    }



}
