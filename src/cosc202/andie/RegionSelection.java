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
    private int width, height, x, x1, y, y1;
    private boolean isInside;
    private Graphics g;
    private BufferedImage copied_target, currentImage, selectedArea;
  

    RegionSelection(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
        currentImage = target.getImage().getCurrentImage();
        width = currentImage.getWidth();
        height = currentImage.getHeight();
        target.addMouseListener(this);

        // create a copy of the original image
        copied_target = EditableImage.deepCopy(currentImage);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int xtemp = e.getX();
        int ytemp = e.getY();

        if (xtemp >= 0 && xtemp < width && ytemp >= 0 && ytemp < height) {// check if the points are inside the image 
            x = xtemp;
            y = ytemp;
            isInside = true;
            target.addMouseMotionListener(this);
        } else {
           
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(Math.abs(x-e.getX())!=0&&  Math.abs(y-e.getY())!=0){// check if points are different from when it's clicked
            drawRect(e.getX(),  e.getY());
            changeColourOfSelectedArea();
        }
        target.removeMouseMotionListener(this);
            isInside = false;
    }

    private void drawRect(int xtemp, int ytemp) {
        g = (Graphics2D) target.getImage().getCurrentImage().getGraphics();
        if (xtemp >= 0 && xtemp < width && ytemp >= 0 && ytemp < height && isInside) {
            x1 = xtemp;
            y1 = ytemp;
        } else {
            if (xtemp > width)
                x1 = currentImage.getWidth();
            if (ytemp > height)
                y1 = currentImage.getHeight();

            // does select inside the image
        }
        ImagePanel ip = new ImagePanel();
        ip.setXY(x, y);
        ip.setX1Y1(x1, y1);
        ip.setOriginal(copied_target);
        ip.paintComponent(g);
        target.repaint();
    }

    private void changeColourOfSelectedArea() {
        selectedArea = target.getImage().getCurrentImage().getSubimage(Math.min(x, x1), Math.min(y, y1),
                Math.abs(x - x1), Math.abs(y - y1));
        ConvertToGrey ctg = new ConvertToGrey();
        ctg.apply(selectedArea);
        target.repaint();
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
