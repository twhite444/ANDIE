package cosc202.andie;

import java.util.*;
import java.awt.event.*;

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

    //needed for languages:
    private static ResourceBundle bundle;

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {
        //needed for multilingual support:
        bundle = Andie.LanguageSettings.getMessageBundle();

        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction(bundle.getString("menu_file_open"), null, bundle.getString("menu_file_open_desc"), Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction(bundle.getString("menu_file_save"), null, bundle.getString("menu_file_save_desc"), Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction(bundle.getString("menu_file_saveAs"), null, bundle.getString("menu_file_saveAs_desc"), Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExportAction(bundle.getString("menu_file_export"), null, bundle.getString("menu_file_export_desc"), Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new FileExitAction(bundle.getString("menu_file_exit"), null, bundle.getString("menu_file_exit_desc"), Integer.valueOf(0)));
    }

    /**
     * <p>
     * Create a menu containing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("menu_file"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
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

            if (target.getImageDangerous().hasImage()) { // checks is an image is open

            int option = JOptionPane.showOptionDialog(null, "Open new image?\nUnsaved progress will be lost.", "Open?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (option == JOptionPane.CANCEL_OPTION) { // cancel the exiting

                return;

            }

            }

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {

                try {

                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImageDangerous().open(imageFilepath);

                } catch (NullPointerException ex) {

                    JOptionPane.showMessageDialog(null, ("Error: Null error, the file type is probably wrong\n" + ex.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);

                } catch (Exception ex) {
                    
                    JOptionPane.showMessageDialog(null, ("Error: Unspecified error\n" + ex.getMessage()) + "\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

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

                target.getImageDangerous().save();

            } catch (NullPointerException ex) {
                
                JOptionPane.showMessageDialog(null, ("Error: Null error, the file type is probably wrong\n" + ex.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
                
            } catch (IllegalArgumentException ex) {
                
                JOptionPane.showMessageDialog(null, ("Error: Illegal argument error, probably tried to save an image that doesnt exist\n" + ex.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, ("Error: Unspecified error\n" + ex.getMessage() + "\n" + ex), "Error", JOptionPane.ERROR_MESSAGE);

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
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
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
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImageDangerous().exportAs(imageFilepath);

                } catch (NullPointerException ex) {
                
                    JOptionPane.showMessageDialog(null, ("Error: Null error, the file type is probably wrong\n" + ex.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
                    
                } catch (IllegalArgumentException ex) {
                    
                    JOptionPane.showMessageDialog(null, ("Error: Illegal argument error, probably tried to save an image that doesnt exist\n" + ex.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
    
                } catch (Exception ex) {
    
                    JOptionPane.showMessageDialog(null, ("Error: Unspecified error\n" + ex.getMessage() + "\n" + ex), "Error", JOptionPane.ERROR_MESSAGE);
    
                }
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
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {

                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImageDangerous().saveAs(imageFilepath);

                } catch (NullPointerException ex) {
                
                    JOptionPane.showMessageDialog(null, ("Error: Null error, the file type is probably wrong\n" + ex.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);    

                } catch (IllegalArgumentException ex) {
                
                    JOptionPane.showMessageDialog(null, ("Error: Illegal argument error, probably tried to save an image that doesnt exist\n" + ex.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null, ("Error: problem saving as\n" + ex.getMessage() + "\n" + ex), "Error", JOptionPane.ERROR_MESSAGE);

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

            int option = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?\nUnsaved progress will be lost.", "Exit?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (option == JOptionPane.CANCEL_OPTION) { // cancel the exiting

                return;

            } else if (option == JOptionPane.OK_OPTION) {

                System.exit(0);

            }

        }

    }

}
