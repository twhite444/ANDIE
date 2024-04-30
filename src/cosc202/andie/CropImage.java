package cosc202.andie;

import java.awt.image.BufferedImage;


public class CropImage implements ImageOperation, java.io.Serializable {
    private BufferedImage output;
    private int width;
    private int height;
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    @Override
    public BufferedImage apply(BufferedImage input) {
    
        
        return input.getSubimage(getTopX(), getTopY(), width, height);
    }

    public CropImage(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        setWidthHeight();
    }
    // assume that x1, x2, y1 and y2 are all positive
    public void setWidthHeight() {
        this.width = Math.max(x1,x2)-Math.min(x1,x2);
        this.height = Math.max(y1,y2)-Math.min(y1,y2);
    }

    public int getTopX(){
        return Math.min(x1,x2);
    }
    public int getTopY(){
        return Math.min(y1,y2);
    }

}
