package cosc202.andie;

import java.util.*;
import java.awt.Cursor;
import java.awt.event.*;
import javax.swing.*;

// import cosc202.andie.FilterActions.BlockAverageAction;
// import cosc202.andie.FilterActions.GaussianFilterAction;
// import cosc202.andie.FilterActions.MeanFilterAction;
// import cosc202.andie.FilterActions.MedianFilterAction;
// import cosc202.andie.FilterActions.SharpenFilterAction;
// import cosc202.andie.FilterActions.SoftBlurAction;


/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood. 
 * This includes a mean filter (a simple blur) in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FilterActions {
    
    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    //needed for languages:
    private static ResourceBundle bundle;

    /** The menu that will hold the FilterActions */
    private JMenu filterMenu;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        //needed for multilingual support:
        bundle = Andie.LanguageSettings.getMessageBundle();

        actions = new ArrayList<Action>();
        actions.add(new MeanFilterAction(bundle.getString("menu_filter_meanFilter"), null, bundle.getString("menu_filter_meanFilter_desc"), null));
        actions.add(new SoftBlurAction(bundle.getString("menu_filter_softblurFilter"),null,bundle.getString("menu_filter_softblurFilter_desc"), null));
        actions.add(new SharpenFilterAction(bundle.getString("menu_filter_sharpenFilter"),null,bundle.getString("menu_filter_sharpenFilter_desc"), null));
        actions.add(new MedianFilterAction(bundle.getString("menu_filter_medianFilter"), null, bundle.getString("menu_filter_medianFilter_desc"), null));
        actions.add(new GaussianFilterAction(bundle.getString("menu_filter_gaussianFilter"), null, bundle.getString("menu_filter_gaussianFilter_desc"), null));
        actions.add(new BlockAverageAction( bundle.getString("menu_filter_blockAverage"), null, bundle.getString("menu_filter_blockAverage_desc"), null));
        actions.add(new RandomScatteringAction( bundle.getString("menu_filter_randomScattering"), null, bundle.getString("menu_filter_randomScattering_desc"), null));
        actions.add(new EmbossFilterAction(bundle.getString("menu_filter_embossFilter"), null, bundle.getString("menu_filter_embossFilter_desc"), null));
        actions.add(new SobelFilterAction(bundle.getString("menu_filter_sobelFilter"), null, bundle.getString("menu_filter_sobelFilter_desc"), null));
        actions.add(new MotionBlurFilterAction(bundle.getString("menu_filter_motionBlurFilter_vertical"), null, bundle.getString("menu_filter_motionBlurFilter_vertical"), null));

        actions.add(new SobelFilterAction(bundle.getString("menu_filter_randomScattering_selected"), null, bundle.getString("menu_filter_randomScattering_selected_desc"), null));
    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        filterMenu = new JMenu(bundle.getString("menu_filter"));

        for(Action action: actions) {
            filterMenu.add(new JMenuItem(action));
        }

        setShortcuts();

        return filterMenu;
    }

    /** Sets the keyboard shortcuts for filterMenu */
    private void setShortcuts(){
        //Mean filter
        filterMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_M, ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK)); 
        
        //Soft blur
        filterMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_F, ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK));
        
        //Sharpen
        filterMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S, ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK)); 
        
        //Median
        filterMenu.getItem(3).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_A, ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK)); 
        
        //Gaussian filter
        filterMenu.getItem(4).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_G, ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK)); 

        //Block average
        filterMenu.getItem(5).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_B, ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK)); 
        //Random Scattering
        filterMenu.getItem(6).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_R, ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK)); 
        //Emboss filter
        filterMenu.getItem(7).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_E, ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK)); 
        //Sobel filter
        filterMenu.getItem(8).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_O, ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK)); 
        // random scattering selected area
        filterMenu.getItem(9).setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_T, ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK)); 
    

    }

    /**
     * <p>
     * Action to blur an image with a gaussian filter.
     * </p>
     * 
     * @see GaussianFilter
     */
    public class GaussianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Gaussian-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        GaussianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Gaussian filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized {@link GaussianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                // Determine the radius - ask the user.
                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                int option = JOptionPane.showOptionDialog(
                    null,
                    radiusSpinner,
                    bundle.getString("menu_filter_enterFilterRadius1to10px"), 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    new String[]{bundle.getString("optionPane_okButtonText"),bundle.getString("optionPane_cancelButtonText")}, 
                    null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }

                // Create and apply the filter
                target.getImage().apply(new GaussianFilter(radius));
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
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            
            super(name, icon, desc, mnemonic);
            
        }

        /**
         * <p>
         * Callback for when the mean filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                // Determine the radius - ask the user.
                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                int option = JOptionPane.showOptionDialog(
                    null, 
                    radiusSpinner, 
                    bundle.getString("menu_filter_enterFilterRadius1to10px"), 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    new String[]{bundle.getString("optionPane_okButtonText"),bundle.getString("optionPane_cancelButtonText")}, 
                    null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }

                // Create and apply the filter
                target.getImage().apply(new MeanFilter(radius));
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
     * Action to apply a soft blur filter to an image.
     * 
     * <p>
     * This action applies a soft blur filter to an image when triggered. 
     * </p>
     * 
     * @see SoftBlur
     * @see ImageAction
     */
    public class SoftBlurAction extends ImageAction {
        
        /**
         * Creates a new SoftBlurAction.
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */

        SoftBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc, mnemonic);
        }
        /**
         * Callback for when the soft blur action is triggered.
         * 
         * <p>
         * This method is called whenever the SoftBlurAction is triggered.
         * It applies a soft blur filter to the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                target.getImage().apply(new SoftBlur());
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
     * Action to apply a sharpen filter to an image.
     * 
     * <p>
     * This action applies a sharpen filter to an image when triggered. 
     * </p>
     * 
     * @see SharpenFilter
     * @see ImageAction
     */
    public class SharpenFilterAction extends ImageAction {

    	/**
         * Creates a new SharpenFilterAction.
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc, mnemonic);
        }
        
        /**
         * Callback for when the sharpen filter action is triggered.
         * 
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * It applies a sharpen filter to the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                target.getImage().apply(new SharpenFilter());
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
     * Action to blur an image with a median filter.
     * </p>
     * 
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            
            super(name, icon, desc, mnemonic);
            
        }

        /**
         * <p>
         * Callback for when the median action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized {@link MedianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                // Determine the radius - ask the user.
                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                int option = JOptionPane.showOptionDialog(
                    null,
                    radiusSpinner,
                    bundle.getString("menu_filter_enterFilterRadius1to10px"), 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    new String[]{bundle.getString("optionPane_okButtonText"),bundle.getString("optionPane_cancelButtonText")}, 
                    null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }

                // Create and apply the filter
                target.getImage().apply(new MedianFilter(radius));
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
     * Action to apply an emboss filter in a user inputted direction.
     * </p>
     * 
     * @see Emboss
     */
    public class EmbossFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new EmbossFilter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the Emboss action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the EmbossAction is triggered.
         * It embosses in a certain direction depending on user input.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                String direction = bundle.getString("menu_filter_embossFilter_middleLeft");

                // Pop-up dialog box to ask for the cycle type.

                String[] directionOptions = { 
                    bundle.getString("menu_filter_embossFilter_middleLeft"),
                    bundle.getString("menu_filter_embossFilter_topLeft"),
                    bundle.getString("menu_filter_embossFilter_topMiddle"),
                    bundle.getString("menu_filter_embossFilter_topRight"),
                    bundle.getString("menu_filter_embossFilter_middleRight"),
                    bundle.getString("menu_filter_embossFilter_bottomRight"),
                    bundle.getString("menu_filter_embossFilter_bottomMiddle"),
                    bundle.getString("menu_filter_embossFilter_bottomLeft") 
                                            }; // different options for direction

                JComboBox<String> comboBox = new JComboBox<String>(); // drop down menu for options

                for (String i: directionOptions) { // add each option to the menu

                    comboBox.addItem(i);

                }

                int option = JOptionPane.showOptionDialog(null, comboBox, bundle.getString("menu_filter_embossFilter_selectDirection"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,                 new String[]{bundle.getString("optionPane_okButtonText"),bundle.getString("optionPane_cancelButtonText")}, null);

                if (option == JOptionPane.CANCEL_OPTION) { // Check the return value from the dialog box.

                    return;

                } else if (option == JOptionPane.OK_OPTION) {

                    direction = (String) comboBox.getSelectedItem(); // convert to string array
                    if (direction.equals(bundle.getString("menu_filter_embossFilter_middleLeft"))) {
                        direction = "Middle left";
                    } else if (direction.equals(bundle.getString("menu_filter_embossFilter_topLeft"))) {
                        direction = "Top Left";
                    } else if (direction.equals(bundle.getString("menu_filter_embossFilter_topMiddle"))) {
                        direction = "Top Middle";
                    } else if (direction.equals(bundle.getString("menu_filter_embossFilter_topRight"))) {
                        direction = "Top Right";
                    } else if (direction.equals(bundle.getString("menu_filter_embossFilter_middleRight"))) {
                        direction = "Middle Right";
                    } else if (direction.equals(bundle.getString("menu_filter_embossFilter_bottomRight"))) {
                        direction = "Bottom Right";
                    } else if (direction.equals(bundle.getString("menu_filter_embossFilter_bottomMiddle"))) {
                        direction = "Bottom Middle";
                    } else if (direction.equals(bundle.getString("menu_filter_embossFilter_bottomLeft"))) {
                        direction = "Bottom Left";
                    }
                    
                    
                }

                target.getImage().apply(new Emboss(direction));
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
     * Action to apply a sobel filter in a user inputted direction.
     * </p>
     * 
     * @see Emboss
     */
    public class SobelFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new EmbossFilter action for the emboss filters.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        SobelFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the Sobel Filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SobelFilterAction is triggered.
         * It embosses in a certain direction depending on user input.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                String direction = bundle.getString("menu_macro_verticalSobel");

                // Pop-up dialog box to ask for the cycle type.

                String[] directionOptions = {
                    bundle.getString("menu_macro_verticalSobel"),
                    bundle.getString("menu_macro_horizontalSobel") 
                }; // different options for direction

                JComboBox<String> comboBox = new JComboBox<String>(); // drop down menu for options

                for (String i: directionOptions) { // add each option to the menu

                    comboBox.addItem(i);

                }

                int option = JOptionPane.showOptionDialog(null, comboBox, bundle.getString("menu_macro_selectSobelDirection"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,                 new String[]{bundle.getString("optionPane_okButtonText"),bundle.getString("optionPane_cancelButtonText")}, null);

                if (option == JOptionPane.CANCEL_OPTION) { // Check the return value from the dialog box.

                    return;

                } else if (option == JOptionPane.OK_OPTION) {

                    direction = (String) comboBox.getSelectedItem(); // convert to string array
                    if (direction.equals(bundle.getString("menu_macro_verticalSobel"))) {
                        direction = "Vertical Sobel";
                    } else if (direction.equals(bundle.getString("menu_macro_horizontalSobel"))) {
                        direction = "Horizontal Sobel";
                    }
                    
                }

                target.getImage().apply(new Emboss(direction));
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
     * Action to apply a block average filter to an image.
     * 
     * <p>
     * This action applies a block average filter to an image when triggered. 
     * </p>
     * 
     * @see BlockAverage
     * @see ImageAction
     */
    public class BlockAverageAction extends ImageAction {

        /**
         * <p>
         * Create a new Block-average action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        BlockAverageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the Block Average action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the BlockAverageAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized {@link BlockAverage}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                // Determine the x and y distance, ask the user.
                int xDist = 1;
                int yDist = 1;

                // Pop-up dialog box to ask for the x any y values.
                JPanel blockPanel = new JPanel();

                SpinnerNumberModel xRadiusModel = new SpinnerNumberModel(1, 1, 10, 1);
                SpinnerNumberModel yRadiusModel = new SpinnerNumberModel(1, 1, 10, 1);

                JSpinner xSpinner = new JSpinner(xRadiusModel);
                JSpinner ySpinner = new JSpinner(yRadiusModel);

                blockPanel.add(new JLabel(bundle.getString("menu_filter_blockAverage_xSelect")));
                blockPanel.add(xSpinner);
                blockPanel.add(new JLabel(bundle.getString("menu_filter_blockAverage_ySelect")));
                blockPanel.add(ySpinner);

                int option = JOptionPane.showOptionDialog(

                    null,
                    blockPanel,
                    "select block size",
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    new String[]{bundle.getString("optionPane_okButtonText"),bundle.getString("optionPane_cancelButtonText")}, 
                    null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {

                    return;

                } else if (option == JOptionPane.OK_OPTION) {

                    xDist = (int)xSpinner.getValue();
                    yDist = (int)ySpinner.getValue();

                }

                // Create and apply the filter
                target.getImage().apply(new BlockAverage(xDist, yDist));
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
     * Action to blur an image with a random scatter.
     * </p>
     * 
     * @see RandomScattering
     */
    public class RandomScatteringAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RandomScatteringAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            
            super(name, icon, desc, mnemonic);
            
        }

        /**
         * <p>
         * Callback for when the random scatter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RandomScatteringAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized {@link RandomScattering}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                // Determine the radius - ask the user.
                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                int option = JOptionPane.showOptionDialog(
                    null,
                    radiusSpinner,
                    bundle.getString("menu_filter_enterFilterRadius1to10px"), 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    new String[]{bundle.getString("optionPane_okButtonText"),bundle.getString("optionPane_cancelButtonText")}, 
                    null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }

                // Create and apply the filter
                target.getImage().apply(new RandomScattering(radius));
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
     * Action to apply a motion blur filter in a user-inputted direction.
     * 
     * @see MotionBlurFilter
     */
    public class MotionBlurFilterAction extends ImageAction {

        /**
         * Create a new MotionBlurFilterAction.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MotionBlurFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * Callback for when the Motion Blur action is triggered.
         * 
         * This method is called whenever the MotionBlurFilterAction is triggered.
         * It applies motion blur in a certain direction depending on user input.
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            String direction = bundle.getString("menu_filter_motionBlurFilter_horizontal");
            int radius = 1;
            // Pop-up dialog box to ask for the motion blur direction.
            String[] directionOptions = {
                bundle.getString("menu_filter_motionBlurFilter_horizontal"),
                bundle.getString("menu_filter_motionBlurFilter_vertical"), 
                bundle.getString("menu_filter_motionBlurFilter_diagonalTopLeftBottomRight"),
                bundle.getString("menu_filter_motionBlurFilter_diagonalBottomLeftTopRight"),
            };

            JComboBox<String> comboBox = new JComboBox<String>(directionOptions);
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel(bundle.getString("menu_filter_motionBlurFilter_selectDirection")));
            panel.add(comboBox);
            panel.add(new JLabel(bundle.getString("menu_filter_enterFilterRadius1to10px")));
            panel.add(radiusSpinner);

            int option = JOptionPane.showOptionDialog(
                null,
                panel,
                bundle.getString("menu_filter_motionBlurFilter_selectDirection"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{bundle.getString("optionPane_okButtonText"), bundle.getString("optionPane_cancelButtonText")},
                null);

            if (option == JOptionPane.OK_OPTION) {
                direction = (String) comboBox.getSelectedItem();
                if (direction.equals(bundle.getString("menu_filter_motionBlurFilter_vertical"))) {
                    direction = "Vertical";
                } else if (direction.equals(bundle.getString("menu_filter_motionBlurFilter_horizontal"))) {
                    direction = "Horizontal";
                } else if (direction.equals(bundle.getString("menu_filter_motionBlurFilter_diagonalTopLeftBottomRight"))) {
                    direction = "Diagonal Top Left to Bottom Right";
                } else if (direction.equals(bundle.getString("menu_filter_motionBlurFilter_diagonalBottomLeftTopRight"))) {
                    direction = "Diagonal Bottom Left to Top Right";
                } 

                radius = radiusModel.getNumber().intValue();
            } else {
                return; // User canceled, do nothing
            }

            // create and apply the filter
            target.getImage().apply(new MotionBlurFilter(direction, radius));
            target.repaint();
            target.getParent().revalidate();
        }
    }


}
