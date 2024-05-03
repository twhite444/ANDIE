package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

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

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public EditActions() {
        // needed for multilingual support:
        bundle = Andie.LanguageSettings.getMessageBundle();

        actions = new ArrayList<Action>();
        actions.add(new UndoAction(bundle.getString("menu_edit_undo"), null, bundle.getString("menu_edit_undo"), Integer.valueOf(KeyEvent.VK_Z)));
        actions.add(new RedoAction(bundle.getString("menu_edit_redo"), null, bundle.getString("menu_edit_redo"), Integer.valueOf(KeyEvent.VK_Y)));
        actions.add(new CropAction("crop or whatever", null, "lookit me!", null));

    }

    /**
     * <p>
     * Create a menu containing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu(bundle.getString("menu_edit"));

        for (Action action : actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
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

        int cropStartX = 0;
        int cropStartY = 0;
        int cropWidth = 1;
        int cropHeight = 1;

        SelectionBoxPanel cropSelection = new SelectionBoxPanel(target);

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

            //System.out.println("clicky");

            cropStartX = click.getX(); 
            cropStartY = click.getY();

        }

        @Override
        public void mouseReleased(MouseEvent unclick) {

            //System.out.println("un-clicky");

            target.removeMouseListener(this); // removes the mouse listner so crops dont keep happeneing
            target.removeMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // returs the cursor to default

            cropWidth = Math.abs(cropStartX - unclick.getX()); // gets the distance x & y between the click and the unclick
            cropHeight = Math.abs(cropStartY - unclick.getY());

            cropStartX = Math.min(cropStartX, unclick.getX()); // gets the most top left x, y corner of the selected ractangle
            cropStartY = Math.min(cropStartY, unclick.getY());

            target.getImage().apply(new Crop(cropStartX, cropStartY, cropWidth, cropHeight));
            target.repaint();
            target.getParent().revalidate();

        }

        @Override
        public void mouseDragged(MouseEvent drag) { // whenever the mouse is clicked and moved

            cropSelection.draw(cropStartX, cropStartY, drag.getX(), drag.getY());

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

    public class SelectionBoxPanel extends JPanel {// draws a selection box above the image

        static BufferedImage selectionBoxImage;
        static Graphics2D selectionBoxImageGraphics;
        static ImageIcon selectionBoxIcon;
        static JLabel selectionBoxLabel;
        static JPanel SelectionBoxPanel;
        static ImagePanel target;

        public SelectionBoxPanel(ImagePanel target) { 

            this.target = target;

            selectionBoxImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
            
            selectionBoxImageGraphics = selectionBoxImage.createGraphics();

            selectionBoxImageGraphics.setComposite(AlphaComposite.Clear);   // makes the image completley clear
            selectionBoxImageGraphics.fillRect(0, 0, selectionBoxImage.getWidth(), selectionBoxImage.getHeight());

            selectionBoxIcon = new ImageIcon(selectionBoxImage); // puts the image in an icon
            selectionBoxLabel = new JLabel(selectionBoxIcon); // puts the icon in a label
            SelectionBoxPanel = new JPanel();

            SelectionBoxPanel.add(selectionBoxLabel);
            target.add(SelectionBoxPanel); // adds the label to the panel

        }

        private static void draw( int x1, int y1, int x2, int y2) {

            selectionBoxImageGraphics.setColor(Color.RED);
            selectionBoxImageGraphics.fillRect(x1, y1, x2, y2);

            System.out.println(x1 + " " + y1 + " " + x2 + " " + y2 + " " + selectionBoxImage.getWidth() + " " + selectionBoxImage.getHeight());

        }

    }

}