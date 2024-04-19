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
    /** A list of action lists to go on the left side of the toolbar. */
    protected ArrayList<ArrayList<Action>> leftActions;
    /** A list of action lists to go on right side of the toolbar. */
    protected ArrayList<ArrayList<Action>> rightActions;

    protected ArrayList<Action> editActionSection;
    protected ArrayList<Action> fileActionSection;
    protected ArrayList<Action> viewActionSection;
    protected EditActions editActions;
    protected ViewActions viewActions;
    protected FileActions fileActions;
    protected String iconLocation = "/cosc202/andie/images/";

    //needed for languages:
    private static ResourceBundle bundle;
    
    public Toolbar(){
        //needed for multilingual support:
        bundle = Andie.LanguageSettings.getMessageBundle();

        this.editActions = new EditActions();
        this.viewActions = new ViewActions();
        this.fileActions = new FileActions();

        leftActions = new ArrayList<ArrayList<Action>>();
        rightActions = new ArrayList<ArrayList<Action>>();
        fileActionSection = new ArrayList<Action>();
        editActionSection = new ArrayList<Action>();
        viewActionSection = new ArrayList<Action>();
        
        this.iconLocation = "/cosc202/andie/images/";
    }

    /**
     * <p>
     * Create a toolbar containing the buttons for commonly used leftActions.
     * </p>
     * 
     * 
     * @return The toolbar UI element.
     */
    public JToolBar createToolbar() {
        ImageIcon saveIcon = makeIcon("save.png");
        ImageIcon undoIcon = makeIcon("undo.png");
        ImageIcon redoIcon = makeIcon("redo.png");
        ImageIcon zoomOutIcon = makeIcon("zoomOut.png");
        ImageIcon zoomInIcon = makeIcon("zoomIn.png");
        ImageIcon zoomFullIcon = makeIcon("zoomFull.png");

            fileActionSection.add(fileActions.new FileSaveAction(null, saveIcon,bundle.getString("menu_file_save_desc"), Integer.valueOf(KeyEvent.VK_S)));
            editActionSection.add(editActions.new UndoAction(null, undoIcon, bundle.getString("menu_edit_undo"), Integer.valueOf(KeyEvent.VK_Z)));
            editActionSection.add(editActions.new RedoAction(null, redoIcon, bundle.getString("menu_edit_redo"), Integer.valueOf(KeyEvent.VK_Y)));
            viewActionSection.add(viewActions.new ZoomOutAction(null, zoomOutIcon, bundle.getString("menu_view_zoomOut_desc"), Integer.valueOf(KeyEvent.VK_MINUS)));
            viewActionSection.add(viewActions.new ZoomInAction(null, zoomInIcon, bundle.getString("menu_view_zoomIn_desc"), Integer.valueOf(KeyEvent.VK_PLUS)));
            viewActionSection.add(viewActions.new ZoomFullAction(null, zoomFullIcon, bundle.getString("menu_view_zoomFull_desc"), Integer.valueOf(KeyEvent.VK_1)));
            leftActions.add(fileActionSection);
            leftActions.add(editActionSection);
            rightActions.add(viewActionSection);
        

        JToolBar toolbar = new JToolBar();
        
        int i = 0;
        if (leftActions.size() > 1){ // for actions that go on left side of toolbar
            for(i = 0; i < leftActions.size() - 1; i++){
                for(Action action: leftActions.get(i)){
                    toolbar.add(action);
                }
                toolbar.addSeparator();
            }
        }
        for(Action action: leftActions.get(i)){ // (no separator after)
            toolbar.add(action);
        }
        
        // makes every action added after here go to the righthand side of the toolbar:
        toolbar.add(Box.createHorizontalGlue());
        
        int r = 0;
        if (rightActions.size() > 1){ // for actions that go on right side of toolbar
            for (r = 0; i < rightActions.size() - 1; r++) {
                for (Action action : rightActions.get(r)) {
                    toolbar.add(action);
                }
                toolbar.addSeparator();
            }
        }
        for(Action action: rightActions.get(r)){  // (no separator after)
            toolbar.add(action);
        } 
        
        return toolbar;
    }

    /**
     * <p>
     * Creates and returns a new ImageIcon from the specified filename.
     * Concats the filename to the pre-set {@link #iconLocation}, therefore,
     * should NOT be given the path of a file as the filename, however file extension is still required.
     * </p>
     * @param filename the name of the image file to be made into an ImageIcon
     * @return an ImageIcon created from the specified image file
     */
    public ImageIcon makeIcon(String filename){
        try{
            return new ImageIcon(getClass().getResource(this.iconLocation.concat(filename)));
        } catch(NullPointerException ex){
            return new ImageIcon(getClass().getResource("/cosc202/andie/images/fileNotFound.png"));
        }     
    }
}
