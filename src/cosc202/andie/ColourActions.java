package cosc202.andie;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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

    //needed for languages:
    private static ResourceBundle bundle;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        //needed for multilingual support:
        bundle = Andie.LanguageSettings.getMessageBundle();

        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction(bundle.getString("menu_colour_greyscale"), null, bundle.getString("menu_colour_greyscale_desc"), Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new ConvertToInverseAction(bundle.getString("menu_colour_imageInversion"), null, bundle.getString("menu_colour_imageInversion_desc"), Integer.valueOf(KeyEvent.VK_I)));
        actions.add(new CycleColoursAction(bundle.getString("menu_colour_cycleColours"), null, bundle.getString("menu_colour_cycleColours_desc"), Integer.valueOf(KeyEvent.VK_C)));
    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("menu_colour"));


        for(Action action: actions) {

            fileMenu.add(new JMenuItem(action));

        }

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
     * Action to convert an image to inverse.
     * </p>
     * 
     * @see ConvertToInverse
     */
    public class ConvertToInverseAction extends ImageAction {

        /**
         * <p>
         * Create a new ConvertToInverse action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertToInverseAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the convert to inverse action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToInverseAction is triggered.
         * It inverts the image
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.getImage().apply(new ConvertToInverse());
            target.repaint();
            target.getParent().revalidate();

        }

    }

    /**
     * <p>
     * Action to cycle colours from RGB to BGR.
     * </p>
     * 
     * @see CycleColours
     */
    public class CycleColoursAction extends ImageAction {

        /**
         * <p>
         * Create a new CycleColoursBGR action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        CycleColoursAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the CycleColours action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the CycleColoursAction is triggered.
         * It cycle colours depending on user input.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            String cycleType = "rgb";

            // Pop-up dialog box to ask for the cycle type.

            String[] cycleOptions = {   "bgr",
                                        "brg",
                                        "gbr",
                                        "grb",
                                        "rbg",
                                        "rgb" }; // different options for cycle type

            JComboBox<String> comboBox = new JComboBox<String>(); // drop down menu for options

            for (String i: cycleOptions) { // add each option to the menu

                comboBox.addItem(i);

            }

            int option = JOptionPane.showOptionDialog(null, comboBox, "Cycle colours from rgb to:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,                 new String[]{bundle.getString("optionPane_okButtonText"),bundle.getString("optionPane_cancelButtonText")}, null);

            if (option == JOptionPane.CANCEL_OPTION) { // Check the return value from the dialog box.

                return;

            } else if (option == JOptionPane.OK_OPTION) {

                cycleType = (String) comboBox.getSelectedItem(); // convert to string array

            }

            target.getImage().apply(new CycleColours(cycleType));
            target.repaint();
            target.getParent().revalidate();

        }

    }

}

// RGB
// ->
// BGR x
// BRG x
// GBR x
// GRB x
// RBG x
