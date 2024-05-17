package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;

import javax.swing.*;

//import cosc202.andie.EditActions.RedoAction;
//import cosc202.andie.EditActions.UndoAction;


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
        actions.add(new CropAction(bundle.getString("menu_edit_crop_image"), null, "Click and drag to crop image", null));
        actions.add(new DrawRectangleAction(bundle.getString("menu_edit_drawRectangle"), null, bundle.getString("menu_edit_drawRectangle_desc"), null));
        actions.add(new DrawOvalAction(bundle.getString("menu_edit_drawOval"), null, bundle.getString("menu_edit_drawOval_desc"), null));
        actions.add(new DrawLineAction("Draw Line", null, "Click and drag to draw line", null));


    
        
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
        
        setShortcuts();

        return editMenu;
    }

    /** Sets the keyboard shortcuts for filterMenu */
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

        int cropStartX = 0;
        int cropStartY = 0;
        int cropWidth = 1;
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

            //System.out.println("clicky");

            cropStartX = click.getX(); 
            cropStartY = click.getY();

            // draw a rectangle, this acts as the selection box
            target.getImage().apply(new DrawRectangle(cropStartX, cropStartY, Math.abs(cropStartX - Math.max(click.getX(), 0)), Math.abs(cropStartY - Math.max(click.getY(), 0)),true));
            target.repaint();
            target.getParent().revalidate();

        }

        @Override
        public void mouseReleased(MouseEvent unclick) {

            //System.out.println("un-clicky");

            // remove the selection box
            target.getImage().undo();

            target.removeMouseListener(this); // removes the mouse listner so crops dont keep happeneing
            target.removeMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // returs the cursor to default

            cropWidth = Math.abs(cropStartX - Math.max(unclick.getX(), 0)); // gets the distance x & y between the click and the unclick
            cropHeight = Math.abs(cropStartY - Math.max(unclick.getY(), 0));

            cropStartX = Math.min(cropStartX, unclick.getX()); // gets the most top left x, y corner of the selected rectangle
            cropStartY = Math.min(cropStartY, unclick.getY());

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

            int initialX = Math.min(Math.max(cropStartX, 0), target.getImage().getCurrentImage().getWidth());  // start point of the selection, clamped inside the image
            int initialY = Math.min(Math.max(cropStartY, 0), target.getImage().getCurrentImage().getHeight());

            int currentX = Math.min(Math.max(drag.getX(), 0), target.getImage().getCurrentImage().getWidth()); // the position of the mouse, clamped to inside the image
            int currentY = Math.min(Math.max(drag.getY(), 0), target.getImage().getCurrentImage().getHeight());

            int topLeftX  = Math.min(initialX, currentX); // the top left corner of the selection x & y
            int topLeftY = Math.min(initialY, currentY);

            int width = Math.max(Math.abs(initialX - currentX), 1); // size of the selection, clamped to at least one
            int height = Math.max(Math.abs(initialY - currentY), 1);

            //System.out.println(currentX + ", " + currentY + ",     " + topLeftX + ", " + topLeftY + ",     " + width + ", " + height);

            target.getImage().undo(); // remove the preivious selection box and draw a new one
            target.getImage().apply(new DrawRectangle(topLeftX, topLeftY, width, height,true));
            target.repaint();
            target.getParent().revalidate();
            
            // draws the blueish selection box
            target.getGraphics().drawImage(new MakeLookSelected().apply(target.getImage().getCurrentImage().getSubimage(topLeftX, topLeftY, width, height)), topLeftX, topLeftY, null);

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

    /**
     * <p>
     * Action to draw a rectangle on an image.
     * </p>
     * 
     * @see DrawRectangle
     */
    public class DrawRectangleAction extends ImageAction implements MouseListener, MouseMotionListener {

        int rectStartX = 0;
        int rectStartY = 0;
        int rectWidth = 1;
        int rectHeight = 1;
        Color lineColor = Color.black;
        Color fillColor = Color.black;

        /**
         * <p>
         * Create a new DrawRectangleAction.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        DrawRectangleAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the DrawRectangleAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawRectangleAction is triggered.
         * It draws a rectangle 
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.addMouseListener(this);
            target.addMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // changes the cursor to a cross
            JColorChooser colorChooser = new JColorChooser();
            lineColor = JColorChooser.showDialog(null, "Pick a color of line", Color.black);
            fillColor = JColorChooser.showDialog(null, "Pick a color of fill", Color.black);


        }

        @Override
        public void mousePressed(MouseEvent click) { // gets the position of the mouse when it is clicked

            //System.out.println("clicky");

            rectStartX = click.getX(); 
            rectStartY = click.getY();

            // draw a rectangle, this acts as the selection box
            target.getImage().apply(new DrawRectangle(rectStartX, rectStartY, Math.abs(rectStartX - Math.max(click.getX(), 0)), Math.abs(rectStartY - Math.max(click.getY(), 0)),lineColor, fillColor));
            target.repaint();
            target.getParent().revalidate();

        }

        @Override
        public void mouseReleased(MouseEvent unclick) { // when the mouse is released

            //System.out.println("un-clicky");

            // remove the selection box
            target.getImage().undo();

            target.removeMouseListener(this); // removes the mouse listner
            target.removeMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // returs the cursor to default

            rectWidth = Math.abs(rectStartX - Math.max(unclick.getX(), 0)); // gets the distance x & y between the click and the unclick, clamping for left and up
            rectHeight = Math.abs(rectStartY - Math.max(unclick.getY(), 0));

            rectStartX = Math.min(rectStartX, unclick.getX()); // gets the most top left x, y corner of the selected rectangle
            rectStartY = Math.min(rectStartY, unclick.getY());

            target.getImage().apply(new DrawRectangle(rectStartX, rectStartY, rectWidth, rectHeight,lineColor, fillColor)); // draw the final rectangle
            target.repaint();
            target.getParent().revalidate();

        }

        @Override
        public void mouseDragged(MouseEvent drag) { // whenever the mouse is dragged

            target.getImage().undo(); // remove the preivious selection box and draw a new one
            target.getImage().apply(new DrawRectangle(Math.min(rectStartX, drag.getX()), Math.min(rectStartY, drag.getY()), Math.abs(rectStartX - Math.max(drag.getX(), 0)), Math.abs(rectStartY - Math.max(drag.getY(), 0)),lineColor, fillColor));
            target.repaint();
            target.getParent().revalidate();

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

    /**
     * <p>
     * Action to draw an oval on an image.
     * </p>
     * 
     * @see DrawOval
     */
    public class DrawOvalAction extends ImageAction implements MouseListener, MouseMotionListener {

        int ovalStartX = 0;
        int ovalStartY = 0;
        int ovalWidth = 1;
        int ovalHeight = 1;
        Color lineColor = Color.black;
        Color fillColor = Color.black;

        /**
         * <p>
         * Create a new DrawOvalAction.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        DrawOvalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the DrawOvalAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawOvalAction is triggered.
         * It draws an oval
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.addMouseListener(this);
            target.addMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // changes the cursor to a cross
            JColorChooser colorChooser = new JColorChooser();
             lineColor = JColorChooser.showDialog(null, "Pick a color of line", Color.black);
             fillColor = JColorChooser.showDialog(null, "Pick a color of fill", Color.black);

        }

        @Override
        public void mousePressed(MouseEvent click) { // gets the position of the mouse when it is clicked

            //System.out.println("clicky");
           
            ovalStartX = click.getX(); 
            ovalStartY = click.getY();


            // draw an oval, this acts as the preview of where the oval will be drawn
            target.getImage().apply(new DrawOval(ovalStartX, ovalStartY, Math.abs(ovalStartX - Math.max(click.getX(), 0)), Math.abs(ovalStartY - Math.max(click.getY(), 0)),lineColor, fillColor));
            target.repaint();
            target.getParent().revalidate();

        }

        @Override
        public void mouseReleased(MouseEvent unclick) { // when the mouse is released

            //System.out.println("un-clicky");

            // remove the preview oval
            target.getImage().undo();

            target.removeMouseListener(this); // removes the mouse listner
            target.removeMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // returs the cursor to default

            ovalWidth = Math.abs(ovalStartX - Math.max(unclick.getX(), 0)); // gets the distance x & y between the click and the unclick, clamping for left and up
            ovalHeight = Math.abs(ovalStartY - Math.max(unclick.getY(), 0));

            ovalStartX = Math.min(ovalStartX, unclick.getX()); // gets the most top left x, y corner of the selected area
            ovalStartY = Math.min(ovalStartY, unclick.getY());

            target.getImage().apply(new DrawOval(ovalStartX, ovalStartY, ovalWidth, ovalHeight,lineColor, fillColor)); // draw the final oval
            target.repaint();
            target.getParent().revalidate();

        }

        @Override
        public void mouseDragged(MouseEvent drag) { // whenever the mouse is dragged

            target.getImage().undo(); // remove the preivious previwe oval and draw a new one
            target.getImage().apply(new DrawOval(Math.min(ovalStartX, drag.getX()), Math.min(ovalStartY, drag.getY()), Math.abs(ovalStartX - Math.max(drag.getX(), 0)), Math.abs(ovalStartY - Math.max(drag.getY(), 0)),lineColor, fillColor));
            target.repaint();
            target.getParent().revalidate();

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

    /**
     * <p>
     * Action to draw a line on an image.
     * </p>
     * 
     * @see DrawLine
     */
    public class DrawLineAction extends ImageAction implements MouseListener, MouseMotionListener {

        int lineStartX = 0;
        int lineStartY = 0;
        int lineEndX = 1;
        int lineEndY = 1;
        Color lineColor = Color.black;

        /**
         * <p>
         * Create a new DrawLineAction.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        DrawLineAction(String name, ImageIcon icon, String desc, Integer mnemonic) {

            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the DrawLineAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawLineAction is triggered.
         * It draws a line
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.addMouseListener(this);
            target.addMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // changes the cursor to a cross
            JColorChooser colorChooser = new JColorChooser();
            lineColor = JColorChooser.showDialog(null, "Pick a color of line", Color.black);

        }

        @Override
        public void mousePressed(MouseEvent click) { // gets the position of the mouse when it is clicked

            //System.out.println("clicky");

            lineStartX = click.getX(); 
            lineStartY = click.getY();

            // draw a line, this acts as a preview of where the line will be drawn
            target.getImage().apply(new DrawLine(lineStartX, lineStartY, lineStartX, lineStartY,  lineColor));
            target.repaint();
            target.getParent().revalidate();

        }

        @Override
        public void mouseReleased(MouseEvent unclick) { // when the mouse is released

            //System.out.println("un-clicky");

            // remove the preview line
            target.getImage().undo();

            target.removeMouseListener(this); // removes the mouse listner
            target.removeMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // returs the cursor to default

            lineEndX = unclick.getX();
            lineEndY = unclick.getY();

            target.getImage().apply(new DrawLine(lineStartX, lineStartY, lineEndX, lineEndY,lineColor)); // draw the final line
            target.repaint();
            target.getParent().revalidate();

        }

        @Override
        public void mouseDragged(MouseEvent drag) { // whenever the mouse is dragged

            target.getImage().undo(); // remove the preivious preview line and draw a new one
            target.getImage().apply(new DrawLine(lineStartX, lineStartY, drag.getX(), drag.getY(),lineColor));
            target.repaint();
            target.getParent().revalidate();

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