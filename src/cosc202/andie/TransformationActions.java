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
        actions.add(new Rotate90LeftAction("Rotate 90 (left)", null, "Rotate image 90 degrees to the left", null));
        actions.add(new Rotate90RightAction("Rotate 90 (right)", null, "Rotate image 90 degrees to the right", null));
        actions.add(new Rotate180Action("Rotate 180", null, "Rotate image 180 degrees", null));
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

}
