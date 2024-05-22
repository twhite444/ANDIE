package cosc202.andie;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.*;

//import cosc202.andie.ColourActions.ContrastAndBrightnessAction;
//import cosc202.andie.ColourActions.ConvertToGreyAction;
//import cosc202.andie.ColourActions.ConvertToInverseAction;
//import cosc202.andie.ColourActions.CycleColoursAction;

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

    /** The menu that will hold the ColourActions */
    private JMenu colourMenu;

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
        actions.add(new ContrastAndBrightnessAction(bundle.getString("menu_colour_contrastAndBrightness"), null, bundle.getString("menu_colour_contrastAndBrightness_desc"), Integer.valueOf(KeyEvent.VK_C)));

    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        colourMenu = new JMenu(bundle.getString("menu_colour"));


        for(Action action: actions) {

            colourMenu.add(new JMenuItem(action));

        }

        
        setShortcuts();

        return colourMenu;

    }

    /** Sets the keyboard shortcuts for colourMenu */
    private void setShortcuts(){
        //Convert to grey
        colourMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_G, ActionEvent.META_MASK | ActionEvent.SHIFT_MASK)); 
            
        //Invert
        colourMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_I, ActionEvent.META_MASK));
            
        //Cycle colours
        colourMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_C, ActionEvent.META_MASK | ActionEvent.CTRL_MASK)); 
            
        //Contrast and brightness
        colourMenu.getItem(3).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_B, ActionEvent.META_MASK | ActionEvent.SHIFT_MASK));
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
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
            
            } catch(NullPointerException ex){

            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

            } catch(Exception ex){
            JOptionPane.showMessageDialog(null, bundle.getString("error_message_FILTER") + ex.getMessage(), 
                    bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);
            }
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
            try{

                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                target.getImage().apply(new ConvertToInverse());
                target.repaint();
                target.getParent().revalidate();

            } catch(NullPointerException ex){

                JOptionPane.showMessageDialog(null, ex.getMessage(), 
                        bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

            } catch(Exception ex){
                JOptionPane.showMessageDialog(null, bundle.getString("error_message_FILTER") + ex.getMessage(), 
                        bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to cycle colours from RGB to any.
     * </p>
     * 
     * @see CycleColours
     */
    public class CycleColoursAction extends ImageAction {

        /**
         * <p>
         * Create a new CycleColours action.
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
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }

            String cycleType = bundle.getString("menu_colour_cycleColours_RGB");

            // Pop-up dialog box to ask for the cycle type.

            String[] cycleOptions = {   
                bundle.getString("menu_colour_cycleColours_BGR"),
                bundle.getString("menu_colour_cycleColours_BRG"),
                bundle.getString("menu_colour_cycleColours_GBR"),
                bundle.getString("menu_colour_cycleColours_GRB"),
                bundle.getString("menu_colour_cycleColours_RBG"),
                bundle.getString("menu_colour_cycleColours_RGB") 
            }; // different options for cycle type

            JComboBox<String> comboBox = new JComboBox<String>(); // drop down menu for options

            for (String i: cycleOptions) { // add each option to the menu

                comboBox.addItem(i);

            }

            int option = JOptionPane.showOptionDialog(null, comboBox, bundle.getString("menu_colour_cycleColours_fromRGBTo"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{bundle.getString("optionPane_okButtonText"),bundle.getString("optionPane_cancelButtonText")}, null);

            if (option == JOptionPane.CANCEL_OPTION) { // Check the return value from the dialog box.

                return;

            } else if (option == JOptionPane.OK_OPTION) {

                cycleType = (String) comboBox.getSelectedItem(); // convert to string array
                if (cycleType.equals(bundle.getString("menu_colour_cycleColours_BGR"))) {
                    cycleType = "bgr";
                } else if (cycleType.equals(bundle.getString("menu_colour_cycleColours_BRG"))) {
                    cycleType = "brg";
                } else if (cycleType.equals(bundle.getString("menu_colour_cycleColours_GBR"))) {
                    cycleType = "gbr";
                } else if (cycleType.equals(bundle.getString("menu_colour_cycleColours_GRB"))) {
                    cycleType = "grb";
                } else if (cycleType.equals(bundle.getString("menu_colour_cycleColours_RBG"))) {
                    cycleType = "rbg";
                } else if (cycleType.equals(bundle.getString("menu_colour_cycleColours_RGB"))) {
                    cycleType = "rgb";
                }
            }

            target.getImage().apply(new CycleColours(cycleType));
            target.repaint();
            target.getParent().revalidate();
        } catch(NullPointerException ex){

            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);
        }
        }

    }


    /**
     * <p>
     * Action to change an images contrast and brightness
     * </p>
     * 
     * @see ChangeContrastAndBrightness
     */
    public class ContrastAndBrightnessAction extends ImageAction {

        /**
         * <p>
         * Create a new ContrastAndBrightnessAction.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ContrastAndBrightnessAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the convert to inverse action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ContrastAndBrightnessAction is triggered.
         * It inverts the image
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }

                // Determine the amount - ask the user.
                int brightness = 0;
                int contrast = 0;

                // Pop-up dialog box to ask for the amount value.

                JPanel sliderPanel = new JPanel();

                sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

                sliderPanel.add(new JLabel(bundle.getString("menu_change_brightness")));

                JSlider brightnessSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);

                brightnessSlider.setPreferredSize(new Dimension(500, 50));

                brightnessSlider.setMajorTickSpacing(25);
                brightnessSlider.setMinorTickSpacing(5);
                brightnessSlider.setPaintTicks(true);
                brightnessSlider.setPaintLabels(true);

                sliderPanel.add(brightnessSlider);

                sliderPanel.add(new JLabel(bundle.getString("menu_change_contrast")));

                JSlider contrastSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);

                contrastSlider.setPreferredSize(new Dimension(500, 50));

                contrastSlider.setMajorTickSpacing(25);
                contrastSlider.setMinorTickSpacing(5);
                contrastSlider.setPaintTicks(true);
                contrastSlider.setPaintLabels(true);

                sliderPanel.add(contrastSlider);

                int option = JOptionPane.showOptionDialog(null, sliderPanel, bundle.getString("menu_enter_amount"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Ok", "Cancel"}, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {

                    return;

                } else if (option == JOptionPane.OK_OPTION) {

                    brightness = brightnessSlider.getValue();
                    contrast = contrastSlider.getValue();

                }

                target.getImage().apply(new ChangeContrastAndBrightness(brightness, contrast));
                target.repaint();
                target.getParent().revalidate();

            } catch(NullPointerException ex){

            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

            } catch(Exception ex){
            
                JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);
            }
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
