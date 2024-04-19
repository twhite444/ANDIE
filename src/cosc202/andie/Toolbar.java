package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * A toolbar containing commonly used operations. 
 * </p>
 * 
 * <p>
 * Used to create a toolbar that holds commonly used operations
 * to make image editing more efficient.
 * These operations can still be accessed through the menubar.
 * </p>
 * 
 * <p>
 * The following images are copyright 2018 <a href="https://github.com/akveo">Akveo</a>
 * and licensed under a <a href="https://github.com/akveo/eva-icons/blob/master/LICENSE.txt">MIT Licence</a>:
 * </p>
 * 
 * <p>/cosc202/andie/images/save.png</p>
 * <p>/cosc202/andie/images/undo.png</p>
 * <p>/cosc202/andie/images/redo.png</p>
 * <p>/cosc202/andie/images/zoomOut.png</p>
 * <p>/cosc202/andie/images/zoomIn.png</p>
 * <p>/cosc202/andie/images/zoomFull.png (modified from zoomIn.png)</p>
 * 
 * @author Charlotte Cook
 */
public class Toolbar {
    /** A list of action lists for the toolbar. */
    protected ArrayList<ArrayList<Action>> actions;

    protected ArrayList<Action> editActionSection;
    protected ArrayList<Action> fileActionSection;
    protected ArrayList<Action> viewActionSection;

    //needed for languages:
    private static ResourceBundle bundle;
    
    public Toolbar(){
        //needed for multilingual support:
        bundle = Andie.LanguageSettings.getMessageBundle();

        EditActions editActions = new EditActions();
        ViewActions viewActions = new ViewActions();
        FileActions fileActions = new FileActions();

        actions = new ArrayList<ArrayList<Action>>();
        fileActionSection = new ArrayList<Action>();
        editActionSection = new ArrayList<Action>();
        viewActionSection = new ArrayList<Action>();
        
        ImageIcon saveIcon = new ImageIcon(getClass().getResource("/cosc202/andie/images/save.png"));
        ImageIcon undoIcon = new ImageIcon(getClass().getResource("/cosc202/andie/images/undo.png"));
        ImageIcon redoIcon = new ImageIcon(getClass().getResource("/cosc202/andie/images/redo.png"));
        ImageIcon zoomOutIcon = new ImageIcon(getClass().getResource("/cosc202/andie/images/zoomOut.png"));
        ImageIcon zoomInIcon = new ImageIcon(getClass().getResource("/cosc202/andie/images/zoomIn.png"));
        ImageIcon zoomFullIcon = new ImageIcon(getClass().getResource("/cosc202/andie/images/zoomFull.png"));

        fileActionSection.add(fileActions.new FileSaveAction(null, saveIcon,bundle.getString("menu_file_save_desc"), Integer.valueOf(KeyEvent.VK_S)));
        editActionSection.add(editActions.new UndoAction(null, undoIcon, bundle.getString("menu_edit_undo"), Integer.valueOf(KeyEvent.VK_Z)));
        editActionSection.add(editActions.new RedoAction(null, redoIcon, bundle.getString("menu_edit_redo"), Integer.valueOf(KeyEvent.VK_Y)));
        viewActionSection.add(viewActions.new ZoomOutAction(null, zoomOutIcon, bundle.getString("menu_view_zoomOut_desc"), Integer.valueOf(KeyEvent.VK_MINUS)));
        viewActionSection.add(viewActions.new ZoomInAction(null, zoomInIcon, bundle.getString("menu_view_zoomIn_desc"), Integer.valueOf(KeyEvent.VK_PLUS)));
        viewActionSection.add(viewActions.new ZoomFullAction(null, zoomFullIcon, bundle.getString("menu_view_zoomFull_desc"), Integer.valueOf(KeyEvent.VK_1)));
        actions.add(fileActionSection);
        actions.add(editActionSection);
        actions.add(viewActionSection);
        
    }

    /**
     * <p>
     * Create a toolbar containing the buttons for commonly used actions.
     * </p>
     * 
     * 
     * @return The toolbar UI element.
     */
    public JToolBar createToolbar() {
        
        JToolBar toolbar = new JToolBar();
        
        int i;
        for(i = 0; i < actions.size() - 2; i++){
            for(Action action: actions.get(i)){
                toolbar.add(action);
            }
            toolbar.addSeparator();
        }
        for(Action action: actions.get(i)){ // edit actions (no separator after)
            toolbar.add(action);
        }
        toolbar.add(Box.createHorizontalGlue());// makes every action added after the 'glue'
                                                // go to the righthand side of the toolbar
        
        i++;
        for(Action action: actions.get(i)){  // view actions
            toolbar.add(action);
        } 
        
        return toolbar;
    }
}
