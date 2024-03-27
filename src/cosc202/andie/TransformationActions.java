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

    /**
     * <p>
     * Create a set of transformation menu actions.
     * </p>
     */
    public TransformationActions() {
        actions = new ArrayList<Action>();

        actions.add(new ResizeAction("Resize", null, "resize the image from 1% - 1000%", null));
        actions.add(new Resize50Action("Resize to 50%", null, "resize the image", null));
        actions.add(new Resize150Action("Resize to 150%", null, "resize the image", null));
        
    }

    /**
     * <p>
     * Create a menu containing the list of Transformation actions.
     * </p>
     * 
     * @return The transformation menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Transformations");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        JMenu flipsMenu= new JMenu("Flip"); //add flipsMenu actions for horizontal and vertical flip
        flipsMenu.add(new ImageFlipVAction("Vertical",null,"Flip image vertically",null));
        flipsMenu.add(new ImageFlipHAction("Horizontal",null,"Flip image horizontally",null));
        fileMenu.add(flipsMenu);

        JMenu rotationsMenu= new JMenu("Rotations"); // add rotaions menu for rotaions
        rotationsMenu.add(new Rotate90LeftAction("90 (left)", null, "Rotate image 90 degrees to the left", null));
        rotationsMenu.add(new Rotate90RightAction("90 (right)", null, "Rotate image 90 degrees to the right", null));
        rotationsMenu.add(new Rotate180Action("180", null, "Rotate image 180 degrees", null));
        fileMenu.add(rotationsMenu);

        return fileMenu;
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

    public class ResizeAction extends ImageAction {

        /**
         * <p>
         * Create a new Resize action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when resize is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ResizeAction is triggered.
         * It resizes the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the scale - ask the user.
            int scale = 100;

            // Pop-up dialog box to ask for the scale value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(100d, 1d, 1000d, 1d);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, "Enter scale (%)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                scale = radiusModel.getNumber().intValue();
            }

            target.getImage().apply(new Resize(scale / 100));
            target.repaint();
            target.getParent().revalidate();
        }

    }

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
