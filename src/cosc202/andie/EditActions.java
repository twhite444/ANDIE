package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.awt.Cursor;
import javax.swing.*;




/**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be
 * added.
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
public class EditActions {

    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    // needed for languages:
    private static ResourceBundle bundle;

    /** The menu that will hold the FileActions */
    private JMenu editMenu;
    
    static {
        bundle = Andie.LanguageSettings.getMessageBundle();
    }

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public EditActions() {

        actions = new ArrayList<Action>();
        actions.add(new UndoAction(bundle.getString("menu_edit_undo"), null, bundle.getString("menu_edit_undo"), null));
        actions.add(new RedoAction(bundle.getString("menu_edit_redo"), null, bundle.getString("menu_edit_redo"), null));
        actions.add(new CropAction(bundle.getString("menu_edit_crop_image"), null, bundle.getString("menu_edit_crop_image_desc"), null));
       
        
    }

    /**
     * <p>
     * Create a menu containing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        editMenu = new JMenu(bundle.getString("menu_edit"));
        
        for (Action action: actions) {
            editMenu.add(new JMenuItem(action));
        }
        
        MacroActions macroActions = new MacroActions();
        JMenu macrosMenu = macroActions.createMacrosMenu();
        editMenu.add(macrosMenu);

        setShortcuts();

        return editMenu;
    }

    /** Sets the keyboard shortcuts for editMenu */
    private void setShortcuts(){
        //Undo
        editMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_Z, ActionEvent.META_MASK)); 
        
        //Redo
        editMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_Y, ActionEvent.META_MASK)); 
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            try {

                target.getImage().undo();
                target.repaint();
                target.getParent().revalidate();

            } catch (Exception ex) {
            }

        }
    }

    /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#redo()
     */
    public class RedoAction extends ImageAction {

        /**
         * <p>
         * Create a new redo action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            try {

                target.getImage().redo();
                target.repaint();
                target.getParent().revalidate();

            } catch (Exception ex) {
            }

        }
    }

    /**
     * <p>
     * Action to crop an image.
     * </p>
     * 
     * @see Crop
     */
    public class CropAction extends ImageAction implements MouseListener, MouseMotionListener {

        /** The starting X coordinate of the crop area */
        int cropStartX = 0;
        /** The starting Y coordinate of the crop area */
        int cropStartY = 0;
        /** The width of the cropped area */
        int cropWidth = 1;
        /** The height of the cropped area */
        int cropHeight = 1;

        /**
         * <p>
         * Create a new crop action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        CropAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the crop action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the CropAction is triggered.
         * It crops the image
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.addMouseListener(this);
            target.addMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // changes the cursor to a cross

        }

        @Override
        public void mousePressed(MouseEvent click) { // gets the position of the mouse when it is clicked

            cropStartX = (int)(click.getX() * 1 / (target.getZoom() / 100)); // accounts for the zoom of the image to find the clicked position 
            cropStartY = (int)(click.getY() * 1 / (target.getZoom() / 100));

            // draw a rectangle, this acts as the selection box
            target.getImage().apply(new DrawRectangle(cropStartX, cropStartY, Math.abs(cropStartX - Math.max(cropStartX, 0)), Math.abs(cropStartY - Math.max(cropStartY, 0)),true));
            target.repaint();
            target.getParent().revalidate();

        }

        @Override
        public void mouseReleased(MouseEvent unclick) {

            int cropEndX = (int)(unclick.getX() * 1 / (target.getZoom() / 100));
            int cropEndY = (int)(unclick.getY() * 1 / (target.getZoom() / 100));

            // remove the selection box
            target.getImage().undoNoRedo();

            target.removeMouseListener(this); // removes the mouse listner so crops dont keep happeneing
            target.removeMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // returs the cursor to default

            cropWidth = Math.abs(cropStartX - Math.max(cropEndX, 0)); // gets the distance x & y between the click and the unclick
            cropHeight = Math.abs(cropStartY - Math.max(cropEndY, 0));

            cropStartX = Math.min(cropStartX, cropEndX); // gets the most top left x, y corner of the selected rectangle
            cropStartY = Math.min(cropStartY, cropEndY);

            target.getImage().apply(new Crop(cropStartX, cropStartY, cropWidth, cropHeight));
            target.repaint();
            target.getParent().revalidate();

        }

        /** 
        * Draws part of the image over top of the current image, made blueish to appear selected.
        * As this is not an image operation and as such is not put on the stack it
        * will go away as soon as the image is updated (the mouse is moved again or is unclicked)
        * @see MakeLookSelected
        */
        @Override
        public void mouseDragged(MouseEvent drag) { // whenever the mouse is dragged

            int currentX = Math.min(Math.max((int)(drag.getX() * 1 / (target.getZoom() / 100)), 0), target.getImage().getCurrentImage().getWidth()); // the position of the mouse, clamped to inside the image
            int currentY = Math.min(Math.max((int)(drag.getY() * 1 / (target.getZoom() / 100)), 0), target.getImage().getCurrentImage().getHeight());

            int initialX = Math.min(Math.max(cropStartX, 0), (target.getImage().getCurrentImage().getWidth()));  // start point of the selection, clamped inside the image
            int initialY = Math.min(Math.max(cropStartY, 0), (target.getImage().getCurrentImage().getHeight()));

            int topLeftX  = Math.min(initialX, currentX); // the top left corner of the selection x & y
            int topLeftY = Math.min(initialY, currentY);

            int width = Math.max(Math.abs(initialX - currentX), 1); // size of the selection, clamped to at least one
            int height = Math.max(Math.abs(initialY - currentY), 1);

            target.getImage().undoNoRedo(); // remove the preivious selection box and draw a new one
            target.getImage().apply(new DrawRectangle(topLeftX, topLeftY, width, height,true));

            target.repaint();
            target.getParent().revalidate();

            // draws the blueish selection box
            if (width > 1 && height > 1) { // makes the selection box only show up if there is some area to highlight, conviently this also stops it from trying to highlight things when the entire elected area is outside the image

                target.getGraphics().drawImage(new MakeLookSelected().apply(target.getImage().getCurrentImage().getSubimage(topLeftX, topLeftY, width, height)), topLeftX, topLeftY, null);

            }

        }

        @Override
        public void mouseMoved(MouseEvent arg0) {}

        @Override
        public void mouseClicked(MouseEvent arg0) {}

        @Override
        public void mouseEntered(MouseEvent arg0) {}

        @Override
        public void mouseExited(MouseEvent arg0) {}

    }


    
}