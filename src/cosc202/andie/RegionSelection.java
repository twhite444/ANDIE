package cosc202.andie;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.*;

public class RegionSelection extends ImageAction implements MouseListener, MouseMotionListener {
    private int width;
    private int height;
    private int x;
    private int x1;
    private int y;
    private int y1;
    private boolean isInside;
    private BufferedImage currentImage;
    private BufferedImage image;
    private Graphics g;

    RegionSelection(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
        currentImage = target.getImage().getCurrentImage();
        width = currentImage.getWidth();
        height = currentImage.getHeight();
        target.addMouseListener(this);
        // target.addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int xtemp = e.getX();
        int ytemp = e.getY();

        if (xtemp >= 0 && xtemp < width && ytemp >= 0 && ytemp < height) {
            x = xtemp;
            y = ytemp;
            isInside = true;
            target.addMouseMotionListener(this);

        } else {

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int xtemp = e.getX();
        int ytemp = e.getY();
        if (xtemp >= 0 && xtemp < width && ytemp >= 0 && ytemp < height && isInside) {
            x1 = xtemp;
            y1 = ytemp;
            //target.repaint();
        } else {
            // does nothing because it's out of the image

        }

    }
    @Override
    public void mouseDragged(MouseEvent e) {

        x1 = e.getX();
        y1 = e.getY();
        g = (Graphics2D) target.getImage().getCurrentImage().getGraphics();  
        ImagePanel ip = new ImagePanel();
        ip.setXY(x, y);
        ip.setX1Y1(x1, y1);
        ip.paintComponent(g);
        target.repaint();
        target.getParent().revalidate();
        
       

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
