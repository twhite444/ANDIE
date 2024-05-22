package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FileActions {

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;

    // needed for languages:
    private static ResourceBundle bundle;
    
    /** The menu that will hold the FileActions */
    private JMenu fileMenu;

    static {
        bundle = Andie.LanguageSettings.getMessageBundle();
    }

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {

        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction(bundle.getString("menu_file_open"), null,
                bundle.getString("menu_file_open_desc"), null));
        actions.add(new FileSaveAction(bundle.getString("menu_file_save"), null,
                bundle.getString("menu_file_save_desc"), null));
        actions.add(new FileSaveAsAction(bundle.getString("menu_file_saveAs"), null,
                bundle.getString("menu_file_saveAs_desc"), null));
        actions.add(new FileExportAction(bundle.getString("menu_file_export"), null,
                bundle.getString("menu_file_export_desc"), null));
        actions.add(new FileExitAction(bundle.getString("menu_file_exit"), null,
                bundle.getString("menu_file_exit_desc"), null));
    }

    /**
     * <p>
     * Create a menu containing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        fileMenu = new JMenu(bundle.getString("menu_file"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        setShortcuts();

        return fileMenu;
    }

    /** Sets the keyboard shortcuts for fileMenu */
    private void setShortcuts(){
        //Open
        this.fileMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_O, ActionEvent.CTRL_MASK)); 
        
        //Save
        this.fileMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        
        //Save as
        this.fileMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK)); 
        
        //Export
        this.fileMenu.getItem(3).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_E, ActionEvent.CTRL_MASK)); 
        
        //Exit
        this.fileMenu.getItem(4).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            if (target.getImage().hasImage() && target.getImage().imageHasBeenEdited()) { // checks if an image is open and has been edited

                int option = JOptionPane.showOptionDialog(null, bundle.getString("error_message_OPEN_NEW_IMAGE"),
                bundle.getString("message_OPEN"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (option == JOptionPane.CANCEL_OPTION) { // cancel the exiting

                    return;

                }

            }

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {

                try {

                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().open(imageFilepath);

                } catch (NullPointerException ex) {

                    JOptionPane.showMessageDialog(null,
                            (bundle.getString("error_message_NULL_file_not_found") + ex.getMessage()), bundle.getString("error_message_ERROR")                            ,
                            JOptionPane.ERROR_MESSAGE);
                
                        } catch (IllegalArgumentException ex) {

                    JOptionPane.showMessageDialog(null, (bundle.getString("error_message_ILLEGAL_ARGUMENT_file_type_unsupported") + ex.getMessage()),
                            bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);
                
                } catch (FileNotFoundException ex) {

                    JOptionPane.showMessageDialog(null, ("Error: File could not be found\nDetails:" + ex.getMessage()),
                            bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null, (bundle.getString("error_message_UNSPECIFIED_OPEN") + ex.getMessage()),
                            bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

                } 
            }

            target.repaint();
            target.getParent().revalidate();

        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            try {
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_SAVE_no_image_open"));
                }
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }

            try{
                target.getImage().save();

            } catch (NullPointerException ex) {

                JOptionPane.showMessageDialog(null,
                        (bundle.getString("error_message_NULL_file_not_found") + ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);

            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(null,
                        (bundle.getString("error_message_ILLEGAL_ARGUMENT_file_type_unsupported")
                                + ex.getMessage()),
                                bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, (bundle.getString("error_message_UNSPECIFIED") + ex.getMessage() + "\n" + ex),
                bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

            }
        }

    }

    /**
     * <p>
     * Action to export an edited image to a new file location.
     * </p>
     * 
     * @see EditableImage#exportAs(String)
     */
    public class FileExportAction extends ImageAction {

        /**
         * <p>
         * Create a new file-export action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-export action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExportAction is triggered.
         * It prompts the user to select a file and saves the edited image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            try {
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_EXPORT_no_image_open"));
                }
            } catch (NullPointerException ex) {

                JOptionPane.showMessageDialog(null,
                        ex.getMessage(), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }

            try{                
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(target);
            
                if (result == JFileChooser.APPROVE_OPTION) {
                    
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().exportAs(imageFilepath);
                }
            } catch (NullPointerException ex) {

                JOptionPane.showMessageDialog(null,
                        ex.getMessage(), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);

            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(null,
                        (bundle.getString("error_message_ILLEGAL_ARGUMENT_file_type_unsupported")
                        + ex.getMessage()), bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {

                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().saveAs(imageFilepath);

                } catch (NullPointerException ex) {

                    JOptionPane.showMessageDialog(null,
                            (bundle.getString("error_message_NULL_file_not_found") + ex.getMessage()), bundle.getString("error_message_ERROR"),
                            JOptionPane.ERROR_MESSAGE);

                } catch (IllegalArgumentException ex) {

                    JOptionPane.showMessageDialog(null,
                            (bundle.getString("error_message_ILLEGAL_ARGUMENT_file_type_unsupported")
                                    + ex.getMessage()),
                                    bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null, (bundle.getString("error_message_PROBLEM_SAVING") + ex.getMessage() + "\n" + ex),
                    bundle.getString("error_message_ERROR"), JOptionPane.ERROR_MESSAGE);

                }
            }
        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            
            int option = JOptionPane.showOptionDialog(null,
                    bundle.getString("exit_confirmation"), bundle.getString("exit_message"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (option == JOptionPane.CANCEL_OPTION) { // cancel the exiting

                return;

            } else if (option == JOptionPane.OK_OPTION) {

                System.exit(0);

            }

        }

    }
    

}
