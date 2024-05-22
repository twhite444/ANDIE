package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * <p>
 * Actions provided by the Macros menu.
 * </p>
 * 
 */
public class MacroActions{

    private static ResourceBundle bundle;
    private JMenu macrosMenu;

    /** A list of actions for the Macros menu. */
    protected ArrayList<Action> macroActions;

    static {
        bundle = Andie.LanguageSettings.getMessageBundle();
    }

    public MacroActions(){

        macroActions = new ArrayList<Action>();
        macroActions.add(new ApplyMacroAction(bundle.getString("menu_macro_applyMacro"), null, 
        bundle.getString("menu_macro_applyMacroAction_desc"), Integer.valueOf(KeyEvent.VK_M)));
        macroActions.add(new RecordMacroAction(bundle.getString("menu_macro_recordMacro"), null, 
        bundle.getString("menu_macro_recordMacro_desc"), Integer.valueOf(KeyEvent.VK_M)));
        macroActions.add(new EndMacroAction(bundle.getString("menu_macro_endMacro"), null, 
        bundle.getString("menu_macro_endMacro_desc"), Integer.valueOf(KeyEvent.VK_M)));
    }
    /**
     * <p>
     * Create a menu containing the list of Macro actions.
     * </p>
     * 
     * @return The Macros menu UI element.
     */
    public JMenu createMacrosMenu() {
        
        this.macrosMenu = new JMenu(bundle.getString("menu_macro_macros"));
        
        macrosMenu.add(new JMenuItem(macroActions.get(0))); // Apply macro
        macrosMenu.add(new JMenuItem(macroActions.get(1))); // Record macro
        macrosMenu.add(new JMenuItem(macroActions.get(2))); // End macro
        macrosMenu.getItem(2).setVisible(false); //button to end recording is hidden
        setShortcuts();
        

        return macrosMenu;
    }

    /** Sets the keyboard shortcuts for macrosMenu */
    private void setShortcuts(){
        //Apply macro
        macrosMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_1, ActionEvent.CTRL_MASK)); 
        
        //Record macro - shortcut does not apply when button is hidden
        macrosMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_2, ActionEvent.CTRL_MASK)); 
        
        //End macro - shortcut does not apply when button is hidden
        macrosMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_3, ActionEvent.CTRL_MASK)); 

    }

    /**
     * <p>
     * Action to apply a macro to an image.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class ApplyMacroAction extends ImageAction {

        /**
         * <p>
         * Applies an existing macro to the image.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ApplyMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the apply macro action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ApplyMacroAction is triggered.
         * It prompts the user to select a macro file and applies it to an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            try {
                if (!target.getImageDangerous().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_menu_macro_errorApplyNoImage"));
                }
                
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    bundle.getString("menu_macro_macroFiles"), bundle.getString("menu_macro_ops"));
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {

                    String macroFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    // int[] macroSize = target.getImage().getMacroSize(macroFilepath);
                    // int macroWidth = macroSize[0];
                    // int macroHeight = macroSize[1];
                    // int imageWidth = target.getImage().getCurrentWidth();
                    // int imageHeight= target.getImage().getCurrentHeight();
                    // if(imageWidth == macroWidth && imageHeight == macroHeight){
                        target.getImage().applyMacro(macroFilepath);
                    // } else{
                    //     Resize resize = new Resize();
                        // target.getImage() = new Resize().apply(current, originalMacroWidth, originalMacroHeight);  
                    // }
                    
                }
                target.repaint();
                target.getParent().revalidate();

            } catch (NullPointerException ex) {

                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()),bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, (bundle.getString("error_message_menu_macro_errorApplyMacro") + ex.getMessage()) + "\n" + ex,
                        bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    /**
     * <p>
     * Action to record actions to a macro.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class RecordMacroAction extends ImageAction {

        /**
         * <p>
         * Records a new macros action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RecordMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the record-macro action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RecordMacroAction is triggered.
         * It starts the recording of operations to a new macro file. 
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                if (!target.getImageDangerous().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_menu_macro_errorApplyNoImage"));
                }
                macrosMenu.getItem(1).setVisible(false); //Once recording, option to record disappears
                macrosMenu.getItem(2).setVisible(true); // Option to end recording then appears
               
                target.getImage().recordMacro();
                

            } catch (NullPointerException ex) {

                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, (bundle.getString("error_message_menu_macro_errorRecordMacro") + ex.getMessage()) + "\n" + ex,
                bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

            }
        }
    }
    /**
     * <p>
     * Action to stop recording a macro and save it to a specified file.
     * </p>
     * 
     */
    public class EndMacroAction extends ImageAction {

        /**
         * <p>
         * Create a new end-macro action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EndMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the end-macro action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the EndMacroAction is triggered.
         * It ends the macro recording and prompts the user to select a file, then saves the macro to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
                JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Macro files", "ops");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showSaveDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                

                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImageDangerous().saveMacro(imageFilepath);
                    macrosMenu.getItem(2).setVisible(false); //Once saved, option to end recording disappears
                    macrosMenu.getItem(1).setVisible(true); // Option to start recording then reappears
                }
                } catch (NullPointerException ex) {

                    JOptionPane.showMessageDialog(null,
                            (bundle.getString("error_message_NULL_FILE_TYPE_WRONG") + ex.getMessage()), bundle.getString("error_message_ERROR"),
                            JOptionPane.ERROR_MESSAGE);

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null, (bundle.getString("error_message_PROBLEM_SAVING") + ex.getMessage() + "\n" + ex),
                    bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

                }
            
            
        }
    }
}