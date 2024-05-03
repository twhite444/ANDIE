package cosc202.andie;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class RegionSelection extends ImageAction implements MouseListener, MouseMotionListener {

    private int imageWidth, imageHeight, cropStartX, cropStartY, cropEndX, cropEndY;
    private boolean isInside;
    private Graphics graphics;
    private BufferedImage imageCopy, currentImage, selecetdImage;


    RegionSelection(String name, ImageIcon icon, String desc, Integer mnemonic) {

        super(name, icon, desc, mnemonic);

        currentImage = target.getImage().getCurrentImage();
        imageWidth = currentImage.getWidth();
        imageHeight = currentImage.getHeight();
        target.addMouseListener(this);

        // create a copy of the original image
        imageCopy = EditableImage.deepCopy(currentImage);

    }
    

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        int xtemp = mouseEvent.getX();
        int ytemp = mouseEvent.getY();

        if (xtemp >= 0 && xtemp < imageWidth && ytemp >= 0 && ytemp < imageHeight) {// check if the points are inside the image 

            cropStartX = xtemp;
            cropStartY = ytemp;
            isInside = true;

            target.addMouseMotionListener(this);

        } else {
           
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        if (mouseEvent.getX() != cropStartX || mouseEvent.getY() != cropStartY) {// if the point (where the mouse is released) is not the same as the starting point (mouse entered)

            drawRect(mouseEvent.getX(), mouseEvent.getY());
            setSelecetdImage();
            changeColourOfSelecetdImage();

        } else {

            System.out.println("mouse not moved");

        }

        target.removeMouseMotionListener(this);

        isInside = false;

    }

    private void drawRect(int xtemp, int ytemp) {

        graphics = (Graphics2D) target.getImage().getCurrentImage().getGraphics();

        if (xtemp >= 0 && xtemp < imageWidth && ytemp >= 0 && ytemp < imageHeight && isInside) { // if xtemp and ytemp are insde the image

            cropEndX = xtemp;
            cropEndY = ytemp;

        } else { // otherwise force them inside the image

            if (xtemp > imageWidth) { 

                cropEndX = currentImage.getWidth();

            }
            if (ytemp > imageHeight) {

                cropEndY = currentImage.getHeight();

            }

        }

        ImagePanel imagePanel = new ImagePanel();

        imagePanel.setXY(cropStartX, cropStartY);
        imagePanel.setX1Y1(cropEndX, cropEndY);
        imagePanel.setOriginal(imageCopy);
        imagePanel.paintComponent(graphics);

        target.repaint();

    }

    private void changeColourOfSelecetdImage() { // converts a selected area to greyscale, for testing

        new ConvertToGrey().apply(selecetdImage);
        target.repaint();

    }

    public void setSelecetdImage(){ // sets the selected area to 

        

        selecetdImage = target.getImage().getCurrentImage().getSubimage(Math.min(cropStartX, cropEndX), Math.min(cropStartY, cropEndY), Math.abs(cropStartX - cropEndX), Math.abs(cropStartY - cropEndY));
               
    }

    public BufferedImage getselecetdImage(){
      
        return selecetdImage;

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        drawRect(e.getX(), e.getY());

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
