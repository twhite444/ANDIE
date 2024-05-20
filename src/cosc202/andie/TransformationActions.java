package cosc202.andie;

import java.util.*;
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
        KeyEvent.VK_MINUS, ActionEvent.META_MASK | ActionEvent.SHIFT_MASK)); 
        
        //Resize 150
        transformMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_PLUS, ActionEvent.META_MASK | ActionEvent.SHIFT_MASK));

        //Flip vertical
        flipsMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_OPEN_BRACKET, ActionEvent.META_MASK)); 
        
        //Flip horizontal
        flipsMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_CLOSE_BRACKET, ActionEvent.META_MASK));

        //rotate 90 left
        rotationsMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_COMMA, ActionEvent.META_MASK)); 
        
        //rotate 90 right
        rotationsMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_PERIOD, ActionEvent.META_MASK));

        //rotate 180
        rotationsMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_SLASH, ActionEvent.META_MASK));
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
            target.getImage().apply(new Rotate90Left());
            target.repaint();
            target.getParent().revalidate();
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
            target.getImage().apply(new Rotate90Right());
            target.repaint();
            target.getParent().revalidate();
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
            target.getImage().apply(new Rotate180());
            target.repaint();
            target.getParent().revalidate();
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
            target.getImage().apply(new ImageFlipV());
            target.repaint();
            target.getParent().revalidate();
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
            target.getImage().apply(new ImageFlipH());
            target.repaint();
            target.getParent().revalidate();
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

            target.getImage().apply(new Resize(1.5));
            target.repaint();
            target.getParent().revalidate();
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

            target.getImage().apply(new Resize(0.5));
            target.repaint();
            target.getParent().revalidate();
        }

    }

}
