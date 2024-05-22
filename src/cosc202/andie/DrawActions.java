package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Cursor;

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
 * @author Yusei Tokito
 * @version 1.0
 */
public class DrawActions {

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
    public DrawActions() {
        // needed for multilingual support:
        bundle = Andie.LanguageSettings.getMessageBundle();

        actions = new ArrayList<Action>();
        actions.add(new DrawRectangleAction(bundle.getString("menu_draw_drawRectangle"), null, bundle.getString("menu_draw_drawRectangle_desc"), null));
        actions.add(new DrawOvalAction(bundle.getString("menu_draw_drawOval"), null, bundle.getString("menu_draw_drawOval_desc"), null));
        actions.add(new DrawLineAction(bundle.getString("menu_draw_drawline"), null,  bundle.getString("menu_draw_drawline_desc"), null));

    }

    /**
     * <p>
     * Create a menu containing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        editMenu = new JMenu(bundle.getString("menu_draw"));
        
        for (Action action: actions) {
            editMenu.add(new JMenuItem(action));
        }
        
        setShortcuts();

        return editMenu;
    }

    /** Sets the keyboard shortcuts for filterMenu */
    private void setShortcuts(){
        //Rectangle
        editMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_R, ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK)); 
        
        //Oval
        editMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_O, ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK)); 

        //Line
        editMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_L, ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK)); 
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
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_SAVE_no_image_open"));
                }
                target.getImage().undo();
                target.repaint();
                target.getParent().revalidate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
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
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                target.getImage().redo();
                target.repaint();
                target.getParent().revalidate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /* 
     * <p>
     * Action to draw a rectangle on an image.
     * </p>
     * 
     * @see DrawRectangle
     */
    public class DrawRectangleAction extends ImageAction implements MouseListener, MouseMotionListener {

        /** The starting X coordinate of the rectangle. */
        int rectStartX = 0;
        /** The starting Y coordinate of the rectangle. */
        int rectStartY = 0;
        /** The width of the rectangle. */
        int rectWidth = 1;
        /** The height of the rectangle. */
        int rectHeight = 1;
        /** The color of the line of the rectangle. */
        Color lineColor = Color.black;
        /** The fill color of the rectangle. */
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
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                target.addMouseListener(this);
                target.addMouseMotionListener(this);

                target.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // changes the cursor to a cross

                lineColor = JColorChooser.showDialog(null, bundle.getString("menu_draw_lineColor_desc"), Color.black);
                fillColor = JColorChooser.showDialog(null, bundle.getString("menu_draw_fillColor_desc"), Color.black);
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                (ex.getMessage()), bundle.getString("error_message_ERROR"),
                JOptionPane.ERROR_MESSAGE);
            }

        }

        @Override
        public void mousePressed(MouseEvent click){ // gets the position of the mouse when it is clicked
            try{
            rectStartX = (int)(click.getX() * 1 / (target.getZoom() / 100)); 
            rectStartY = (int)(click.getY() * 1 / (target.getZoom() / 100));

            // draw a rectangle, this acts as the selection box
            target.getImage().apply(new DrawRectangle(rectStartX, rectStartY, Math.abs(rectStartX - Math.max(rectStartX, 0)), Math.abs(rectStartY - Math.max(rectStartY, 0)), lineColor, fillColor));
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }

        }

        @Override
        public void mouseReleased(MouseEvent unclick) { // when the mouse is released
            try{
            int rectEndX = (int)(unclick.getX() * 1 / (target.getZoom() / 100));
            int rectEndY = (int)(unclick.getY() * 1 / (target.getZoom() / 100));

            // remove the selection box
            target.getImage().undo();

            target.removeMouseListener(this); // removes the mouse listner
            target.removeMouseMotionListener(this);

            target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // returs the cursor to default

            rectWidth = Math.abs(rectStartX - Math.max(rectEndX, 0)); // gets the distance x & y between the click and the unclick, clamping for left and up
            rectHeight = Math.abs(rectStartY - Math.max(rectEndY, 0));

            rectStartX = Math.min(rectStartX, rectEndX); // gets the most top left x, y corner of the selected rectangle
            rectStartY = Math.min(rectStartY, rectEndY);

            target.getImage().apply(new DrawRectangle(rectStartX, rectStartY, rectWidth, rectHeight, lineColor, fillColor)); // draw the final rectangle
            target.repaint();
            target.getParent().revalidate();
        
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,
                    (ex.getMessage()), bundle.getString("error_message_ERROR"),
                    JOptionPane.ERROR_MESSAGE);
        }

        }

        @Override
        public void mouseDragged(MouseEvent drag) { // whenever the mouse is dragged
            try{
            int rectEndX = (int)(drag.getX() * 1 / (target.getZoom() / 100)); 
            int rectEndY = (int)(drag.getY() * 1 / (target.getZoom() / 100));

            target.getImage().undo(); // remove the preivious selection box and draw a new one
            target.getImage().apply(new DrawRectangle(Math.min(rectStartX, rectEndX), Math.min(rectStartY, rectEndY), Math.abs(rectStartX - Math.max(rectEndX, 0)), Math.abs(rectStartY - Math.max(rectEndY, 0)), lineColor, fillColor));
            target.repaint();
            target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
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

    /**
     * <p>
     * Action to draw an oval on an image.
     * </p>
     * 
     * @see DrawOval
     */
    public class DrawOvalAction extends ImageAction implements MouseListener, MouseMotionListener {

        /** The starting X coordinate of the oval. */
        int ovalStartX = 0;
        /** The starting Y coordinate of the oval. */
        int ovalStartY = 0;
        /** The width of the oval. */
        int ovalWidth = 1;
        /** The height of the oval. */
        int ovalHeight = 1;
        /** The color of the line of the oval. */
        Color lineColor = Color.black;
        /** The fill color of the oval. */
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
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                target.addMouseListener(this);
                target.addMouseMotionListener(this);

                target.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // changes the cursor to a cross

                lineColor = JColorChooser.showDialog(null, bundle.getString(bundle.getString("menu_draw_lineColor_desc")), Color.black);
                fillColor = JColorChooser.showDialog(null, bundle.getString(bundle.getString("menu_draw_fillColor_desc")), Color.black);
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void mousePressed(MouseEvent click) { // gets the position of the mouse when it is clicked
            try{
            //System.out.println("clicky");
           
            ovalStartX = (int)(click.getX() * 1 / (target.getZoom() / 100));
            ovalStartY = (int)(click.getY() * 1 / (target.getZoom() / 100));


            // draw an oval, this acts as the preview of where the oval will be drawn
            target.getImage().apply(new DrawOval(ovalStartX, ovalStartY, Math.abs(ovalStartX - Math.max(ovalStartX, 0)), Math.abs(ovalStartY - Math.max(ovalStartY, 0)), lineColor, fillColor));
            target.repaint();
            target.getParent().revalidate();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,
                    (ex.getMessage()), bundle.getString("error_message_ERROR"),
                    JOptionPane.ERROR_MESSAGE);
        }

        }

        @Override
        public void mouseReleased(MouseEvent unclick) { // when the mouse is released
            try{
                int ovalEndX = (int)(unclick.getX() * 1 / (target.getZoom() / 100));
                int ovalEndY = (int)(unclick.getY() * 1 / (target.getZoom() / 100));

                // remove the preview oval
                target.getImage().undo();

                target.removeMouseListener(this); // removes the mouse listner
                target.removeMouseMotionListener(this);

                target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // returs the cursor to default

                ovalWidth = Math.abs(ovalStartX - Math.max(ovalEndX, 0)); // gets the distance x & y between the click and the unclick, clamping for left and up
                ovalHeight = Math.abs(ovalStartY - Math.max(ovalEndY, 0));

                ovalStartX = Math.min(ovalStartX, ovalEndX); // gets the most top left x, y corner of the selected area
                ovalStartY = Math.min(ovalStartY, ovalEndY);

                target.getImage().apply(new DrawOval(ovalStartX, ovalStartY, ovalWidth, ovalHeight, lineColor, fillColor)); // draw the final oval
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }

        }

        @Override
        public void mouseDragged(MouseEvent drag) { // whenever the mouse is dragged
            try{
                int ovalEndX = (int)(drag.getX() * 1 / (target.getZoom() / 100));
                int ovalEndY = (int)(drag.getY() * 1 / (target.getZoom() / 100));

                //System.out.println(drag.getX() + ", " + (int)(drag.getX() * 1 / (target.getZoom() / 100)));

                target.getImage().undo(); // remove the preivious previwe oval and draw a new one
                target.getImage().apply(new DrawOval(Math.min(ovalStartX, ovalEndX), Math.min(ovalStartY, ovalEndY), Math.abs(ovalStartX - Math.max(ovalEndX, 0)), Math.abs(ovalStartY - Math.max(ovalEndY, 0)), lineColor, fillColor));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
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

    /**
     * <p>
     * Action to draw a line on an image.
     * </p>
     * 
     * @see DrawLine
     */
    public class DrawLineAction extends ImageAction implements MouseListener, MouseMotionListener {

        /** The starting X coordinate of the line. */
        int lineStartX = 0;
        /** The starting Y coordinate of the line. */
        int lineStartY = 0;
        /** The ending X coordinate of the line. */
        int lineEndX = 1;
        /** The ending Y coordinate of the line. */
        int lineEndY = 1;
        /** The color of the line. */
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
            try{
                if (!target.getImage().hasImage()) {
                    throw new NullPointerException(bundle.getString("error_message_NULL_no_image_open"));
                }
                target.addMouseListener(this);
                target.addMouseMotionListener(this);

                target.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // changes the cursor to a cross

                lineColor = JColorChooser.showDialog(null, bundle.getString("menu_draw_lineColor_desc"), Color.black);
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void mousePressed(MouseEvent click) { // gets the position of the mouse when it is clicked
            try{
                lineStartX = (int)(click.getX() * 1 / (target.getZoom() / 100));
                lineStartY = (int)(click.getY() * 1 / (target.getZoom() / 100));

                // draw a line, this acts as a preview of where the line will be drawn
                target.getImage().apply(new DrawLine(lineStartX, lineStartY, lineStartX, lineStartY, lineColor));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void mouseReleased(MouseEvent unclick) { // when the mouse is released
            try{
                lineEndX = (int)(unclick.getX() * 1 / (target.getZoom() / 100));
                lineEndY = (int)(unclick.getY() * 1 / (target.getZoom() / 100));

                // remove the preview line
                target.getImage().undo();

                target.removeMouseListener(this); // removes the mouse listner
                target.removeMouseMotionListener(this);

                target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // returs the cursor to default

                target.getImage().apply(new DrawLine(lineStartX, lineStartY, lineEndX, lineEndY, lineColor)); // draw the final line
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }

        }

        @Override
        public void mouseDragged(MouseEvent drag) { // whenever the mouse is dragged
            try{
                lineEndX = (int)(drag.getX() * 1 / (target.getZoom() / 100));
                lineEndY = (int)(drag.getY() * 1 / (target.getZoom() / 100));

                target.getImage().undo(); // remove the preivious preview line and draw a new one
                target.getImage().apply(new DrawLine(lineStartX, lineStartY, lineEndX, lineEndY, lineColor));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,
                        (ex.getMessage()), bundle.getString("error_message_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
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