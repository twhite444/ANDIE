package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;


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
        actions.add(new BlockAverageAction(bundle.getString("menu_filter_blockAverage"), null, bundle.getString("menu_filter_blockAverage_desc"), null));

    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("menu_filter"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

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
        }

    }

    public class SoftBlurAction extends ImageAction {

        SoftBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc, mnemonic);
        }
        public void actionPerformed(ActionEvent e){
            target.getImage().apply(new SoftBlur());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class SharpenFilterAction extends ImageAction {

        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc, mnemonic);
        }
        public void actionPerformed(ActionEvent e){
            target.getImage().apply(new SharpenFilter());
            target.repaint();
            target.getParent().revalidate();
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
        }

    }


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

            // Determine the x and y distance, ask the user.
            int xDist = 1;
            int yDist = 1;

            // Pop-up dialog box to ask for the x any y values.
            JPanel blockPanel = new JPanel();

            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);

            JSpinner xSpinner = new JSpinner(radiusModel);
            JSpinner ySpinner = new JSpinner(radiusModel);

            blockPanel.add(new JLabel(bundle.getString("menu_filter_blockAverage_xSelect")));
            blockPanel.add(xSpinner);
            blockPanel.add(new JLabel(bundle.getString("menu_filter_blockAverage_ySelect")));
            blockPanel.add(ySpinner);

            int option = JOptionPane.showOptionDialog(

                null,
                blockPanel,
                bundle.getString("menu_filter_blockAverage_window_title"),
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
            target.getImage().apply(new BlockAverage(xDIst, yDIst));
            target.repaint();
            target.getParent().revalidate();
        }

    }


}
