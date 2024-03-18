package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel directly 
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {
    
    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {

        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction("Greyscale", null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G)));

    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {

        JMenu fileMenu = new JMenu("Colour");

        for(Action action: actions) {

            fileMenu.add(new JMenuItem(action));

        }

        JMenu colourCyclemenu = new JMenu("Cycle Colours");

        colourCyclemenu.add(new JMenuItem(new CycleColoursGBRAction("gbr", null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G))));

        fileMenu.add(colourCyclemenu);

        return fileMenu;

    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();

        }

    }


    /**
     * <p>
     * Action to cycle colours from RGB to GBR.
     * </p>
     * 
     * @see CycleColoursGBR
     */
    public class CycleColoursGBRAction extends ImageAction {

        /**
         * <p>
         * Create a new CycleColoursGBR action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        CycleColoursGBRAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the CycleColoursGBR action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the CycleColoursGBRAction is triggered.
         * It cycle colours from RGB to GBR.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.getImage().apply(new CycleColours());
            target.repaint();
            target.getParent().revalidate();

        }

    }


    /**
     * <p>
     * Action to cycle colours from RGB to BRG.
     * </p>
     * 
     * @see CycleColoursBRG
     */
    public class CycleColoursBRGAction extends ImageAction {

        /**
         * <p>
         * Create a new CycleColoursBRG action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        CycleColoursBRGAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the CycleColoursBRG action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the CycleColoursBRGAction is triggered.
         * It cycle colours from RGB to BRG.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.getImage().apply(new CycleColoursBRG());
            target.repaint();
            target.getParent().revalidate();

        }

    }

}

// RGB
// ->
// RBG
// GRB
// BRG
// BGR
// GBR
