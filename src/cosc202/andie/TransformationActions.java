package cosc202.andie;

import java.util.*;
import java.awt.Cursor;
import java.awt.event.*;
import javax.swing.*;


/**
 * <p>
 * Actions provided by the Transformations menu.
 * </p>
 * 
 * <p>
 * The transformations menu contains the actions...
 * resize
 * flip horizontally and vertically
 * rotations, 90 degrees left right and 180 degrees
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Tommo White
 * @version 1.0
 */
public class TransformationActions {
    
    /** A list of actions for the Transformations menu. */
    protected ArrayList<Action> actions;

    //needed for languages:
    private static ResourceBundle bundle;

     /** The menu that will hold the TransformationActions */
    private JMenu transformMenu;
    /** The submenu that will hold the flip actions */
    private JMenu flipsMenu;
    /** The submenu that will hold the rotate actions */
    private JMenu rotationsMenu;

    /**
     * <p>
     * Create a set of transformation menu actions.
     * </p>
     */
    public TransformationActions() {
        //needed for multilingual support:
        bundle = Andie.LanguageSettings.getMessageBundle();

        actions = new ArrayList<Action>();

        actions.add(new Resize50Action(bundle.getString("menu_transform_resize50"), null, bundle.getString("menu_transform_resize_desc"), null));
        actions.add(new Resize150Action(bundle.getString("menu_transform_resize150"), null, bundle.getString("menu_transform_resize_desc"), null));
        actions.add(new CropAction(bundle.getString("menu_transform_crop_image"), null, bundle.getString("menu_transform_crop_image_desc"), null));

    }

    /**
     * <p>
     * Create a menu containing the list of Transformation actions.
     * </p>
     * 
     * @return The transformation menu UI element.
     */
    public JMenu createMenu() {
       this.transformMenu = new JMenu(bundle.getString("menu_transform"));

        for(Action action: actions) {
            transformMenu.add(new JMenuItem(action));
        }

        this.flipsMenu = new JMenu(bundle.getString("menu_transform_flipMenu")); //add flipsMenu actions for horizontal and vertical flip
        flipsMenu.add(new ImageFlipVAction(bundle.getString("menu_transform_flipVertical"),null, bundle.getString("menu_transform_flipVertical_desc"),null));
        flipsMenu.add(new ImageFlipHAction(bundle.getString("menu_transform_flipHorizontal"),null,bundle.getString("menu_transform_flipHorizontal_desc"),null));
        transformMenu.add(flipsMenu);

        this.rotationsMenu = new JMenu(bundle.getString("menu_transform_rotate")); // add rotaions menu for rotaions
        rotationsMenu.add(new Rotate90LeftAction(bundle.getString("menu_transform_rotateLeft"),null, bundle.getString("menu_transform_rotateLeft_desc"),null));
        rotationsMenu.add(new Rotate90RightAction(bundle.getString("menu_transform_rotateRight"),null, bundle.getString("menu_transform_rotateRight_desc"),null));
        rotationsMenu.add(new Rotate180Action(bundle.getString("menu_transform_rotate180"),null, bundle.getString("menu_transform_rotate180_desc"),null));
        transformMenu.add(rotationsMenu);

        setShortcuts();

        return transformMenu;
    }
    
    /** Sets the keyboard shortcuts for transformMenu */
    private void setShortcuts(){
        //Resize 50
        transformMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK)); 
        
        //Resize 150
        transformMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_EQUALS, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));

        //Flip vertical
        flipsMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_OPEN_BRACKET, ActionEvent.CTRL_MASK)); 
        
        //Flip horizontal
        flipsMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_CLOSE_BRACKET, ActionEvent.CTRL_MASK));

        //rotate 90 left
        rotationsMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_COMMA, ActionEvent.CTRL_MASK)); 
        
        //rotate 90 right
        rotationsMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_PERIOD, ActionEvent.CTRL_MASK));

        //rotate 180
        rotationsMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_SLASH, ActionEvent.CTRL_MASK));
        //Crop
        transformMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_C, ActionEvent.CTRL_MASK)); 
        

       
            
        
    }

    /**
     * <p>
     * Action to rotate an image 90 degrees to the left.
     * </p>
     * 
     * @see Rotate90Left
     */
    public class Rotate90LeftAction extends ImageAction {

        /**
         * <p>
         * Create a new Rotate90Left action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Rotate90LeftAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the rotate-90-left action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Rotate90Left is triggered.
         * It rotates the image 90 degrees to the left.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                target.getImage().apply(new Rotate90Left());
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /** Action to rotate an image 90 degrees to the right. */
    public class Rotate90RightAction extends ImageAction {

        /**
         * <p>
         * Create a new Rotate90Left action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Rotate90RightAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the rotate-90-left action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Rotate90Left is triggered.
         * It rotates the image 90 degrees to the left.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                target.getImage().apply(new Rotate90Right());
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /** Action to rotate an image 180 degrees. */
    public class Rotate180Action extends ImageAction {

        /**
         * <p>
         * Create a new Rotate90Left action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Rotate180Action(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the rotate-90-left action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Rotate90Left is triggered.
         * It rotates the image 90 degrees to the left.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                target.getImage().apply(new Rotate180());
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    
    }

    /** Action to flip an image vertically. */
    public class ImageFlipVAction extends ImageAction {

        /**
         * <p>
         * Create a new Rotate90Left action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ImageFlipVAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the image flip vertically  is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Rotate90Left is triggered.
         * It rotates the image 90 degrees to the left.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                target.getImage().apply(new ImageFlipV());
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /** Action to flip an image horizontally. */
    public class ImageFlipHAction extends ImageAction {

        /**
         * <p>
         * Create a new Rotate90Left action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ImageFlipHAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the image flip vertically  is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Rotate90Left is triggered.
         * It rotates the image 90 degrees to the left.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                target.getImage().apply(new ImageFlipH());
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /** Action to resize an image to 150% of its original size. */
    public class Resize150Action extends ImageAction {

        /**
         * <p>
         * Create a new Resize action with a scale of 150%.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Resize150Action(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when resize 150 is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Resize150Action is triggered.
         * It resizes the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                target.getImage().apply(new Resize(1.5));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /** Action to resize an image to 50% of its original size. */
    public class Resize50Action extends ImageAction {

        /**
         * <p>
         * Create a new Resize action with a scale of 50%.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Resize50Action(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when resize 50 is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Resize50Action is triggered.
         * It resizes the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                target.getImage().apply(new Resize(0.5));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }    
        }

    }


    /**
     * <p>
     * Action to crop an image.
     * </p>
     * 
     * @see Crop
     * @see MakeLookSelected
     */
    public class CropAction extends ImageAction implements MouseListener, MouseMotionListener {

        /** The starting X coordinate of the crop area */
        int cropStartX = 0;
        /** The starting Y coordinate of the crop area */
        int cropStartY = 0;
        /** The width of the cropped area */
        int cropWidth = 1;
        /** The height of the cropped area */
        int cropHeight = 1;

        /**
         * <p>
         * Create a new crop action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        CropAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the crop action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the CropAction is triggered.
         * It crops the image
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.addMouseListener(this);
            target.addMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // changes the cursor to a cross

        }

        @Override
        public void mousePressed(MouseEvent click) { // gets the position of the mouse when it is clicked
            try{
                cropStartX = (int)(click.getX() * 1 / (target.getZoom() / 100)); // accounts for the zoom of the image to find the clicked position 
                cropStartY = (int)(click.getY() * 1 / (target.getZoom() / 100));

                // draw a rectangle, this acts as the selection box
                target.getImage().apply(new DrawRectangle(cropStartX, cropStartY, Math.abs(cropStartX - Math.max(cropStartX, 0)), Math.abs(cropStartY - Math.max(cropStartY, 0)),true));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void mouseReleased(MouseEvent unclick) {
            try{
                int cropEndX = (int)(unclick.getX() * 1 / (target.getZoom() / 100));
                int cropEndY = (int)(unclick.getY() * 1 / (target.getZoom() / 100));

                // remove the selection box
                target.getImage().undoNoRedo();

                target.removeMouseListener(this); // removes the mouse listner so crops dont keep happeneing
                target.removeMouseMotionListener(this);

                target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // returs the cursor to default

                cropWidth = Math.abs(cropStartX - Math.max(cropEndX, 0)); // gets the distance x & y between the click and the unclick
                cropHeight = Math.abs(cropStartY - Math.max(cropEndY, 0));

                cropStartX = Math.min(cropStartX, cropEndX); // gets the most top left x, y corner of the selected rectangle
                cropStartY = Math.min(cropStartY, cropEndY);

                target.getImage().apply(new Crop(cropStartX, cropStartY, cropWidth, cropHeight));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }

        }

        /** 
        * Draws part of the image over top of the current image, made blueish to appear selected.
        * As this is not an image operation and as such is not put on the stack it
        * will go away as soon as the image is updated (the mouse is moved again or is unclicked)
        * @see MakeLookSelected
        */
        @Override
        public void mouseDragged(MouseEvent drag) { // whenever the mouse is dragged
            try{
                int currentX = Math.min(Math.max((int)(drag.getX() * 1 / (target.getZoom() / 100)), 0), target.getImage().getCurrentImage().getWidth()); // the position of the mouse, clamped to inside the image
                int currentY = Math.min(Math.max((int)(drag.getY() * 1 / (target.getZoom() / 100)), 0), target.getImage().getCurrentImage().getHeight());

                int initialX = Math.min(Math.max(cropStartX, 0), (target.getImage().getCurrentImage().getWidth()));  // start point of the selection, clamped inside the image
                int initialY = Math.min(Math.max(cropStartY, 0), (target.getImage().getCurrentImage().getHeight()));

                int topLeftX  = Math.min(initialX, currentX); // the top left corner of the selection x & y
                int topLeftY = Math.min(initialY, currentY);

                int width = Math.max(Math.abs(initialX - currentX), 1); // size of the selection, clamped to at least one
                int height = Math.max(Math.abs(initialY - currentY), 1);

                target.getImage().undoNoRedo(); // remove the preivious selection box and draw a new one
                target.getImage().apply(new DrawRectangle(topLeftX, topLeftY, width, height,true));

                target.repaint();
                target.getParent().revalidate();

                // draws the blueish selection box
                if (width > 1 && height > 1) { // makes the selection box only show up if there is some area to highlight, conviently this also stops it from trying to highlight things when the entire elected area is outside the image

                    target.getGraphics().drawImage(new MakeLookSelected().apply(target.getImage().getCurrentImage().getSubimage(topLeftX, topLeftY, width, height)), topLeftX, topLeftY, null);

                }
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }

        }

        @Override
        public void mouseMoved(MouseEvent arg0) {}

        @Override
        public void mouseClicked(MouseEvent arg0) {}

        @Override
        public void mouseEntered(MouseEvent arg0) {}

        @Override
        public void mouseExited(MouseEvent arg0) {}

    }

}
