package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.ColourActions.ConvertToInverseAction;
import cosc202.andie.TransformationActions.ImageFlipHAction;
import cosc202.andie.TransformationActions.ImageFlipVAction;
import cosc202.andie.TransformationActions.Resize50Action;

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
 * and licensed under a <a href="https://github.com/akveo/eva-icons/blob/master/LICENSE.txt">MIT Licence</a>:</p>
 * <p>/cosc202/andie/images/save.png</p>
 * <p>/cosc202/andie/images/undo.png</p>
 * <p>/cosc202/andie/images/redo.png</p>
 * <p>/cosc202/andie/images/zoomOut.png</p>
 * <p>/cosc202/andie/images/zoomIn.png</p>
 * <p>/cosc202/andie/images/zoomFull.png (modified from maximize-outline.png)</p>
 * <p>/cosc202/andie/images/flipH.png</p>
 * <p>/cosc202/andie/images/flipV.png (modified from swap.png)</p>
 * <p>/cosc202/andie/images/resize50.png</p>
 * <p>/cosc202/andie/images/resize150.png</p>
 * <p>/cosc202/andie/images/invertColours.png (modified from image.png)</p>
 * 
 * @author Charlotte Cook
 */
public class Toolbar {
    /** A list of action lists to go on the left side of the toolbar. */
    protected ArrayList<ArrayList<Action>> leftActions;
    /** A list of action lists to go on right side of the toolbar. */
    protected ArrayList<ArrayList<Action>> rightActions;

    protected ArrayList<Action> fileActionSection;
    protected ArrayList<Action> editActionSection;
    protected ArrayList<Action> viewActionSection;
    protected ArrayList<Action> transformActionSection;
    protected ArrayList<Action> colourActionSection;

    protected FileActions fileActions;
    protected EditActions editActions;
    protected ViewActions viewActions;
    protected TransformationActions transformActions;
    protected ColourActions colourActions;

    protected String iconLocation = "/cosc202/andie/images/";

    //needed for languages:
    private static ResourceBundle bundle;
    
    public Toolbar(){
        //needed for multilingual support:
        bundle = Andie.LanguageSettings.getMessageBundle();

        this.fileActions = new FileActions();
        this.editActions = new EditActions();
        this.viewActions = new ViewActions();
        this.transformActions = new TransformationActions();
        this.colourActions = new ColourActions();
        

        this.leftActions = new ArrayList<ArrayList<Action>>();
        this.rightActions = new ArrayList<ArrayList<Action>>();
        this.fileActionSection = new ArrayList<Action>();
        this.editActionSection = new ArrayList<Action>();
        this.viewActionSection = new ArrayList<Action>();
        this.transformActionSection = new ArrayList<Action>();
        this.colourActionSection = new ArrayList<Action>();
        
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

        //Create icons for each button
        ImageIcon saveIcon = makeIcon("save.png");
        ImageIcon undoIcon = makeIcon("undo.png");
        ImageIcon redoIcon = makeIcon("redo.png");
        ImageIcon zoomOutIcon = makeIcon("zoomOut.png");
        ImageIcon zoomInIcon = makeIcon("zoomIn.png");
        ImageIcon zoomFullIcon = makeIcon("zoomFull.png");
        ImageIcon flipHorizontalIcon = makeIcon("flipH.png");
        ImageIcon flipVerticalIcon = makeIcon("flipV.png");
        ImageIcon resize50Icon = makeIcon("resize50.png");
        ImageIcon resize150Icon = makeIcon("resize150.png");
        ImageIcon invertIcon = makeIcon("invertColours.png");

            //Create and add buttons for each toolbar section 
            fileActionSection.add(fileActions.new FileSaveAction(null, saveIcon,bundle.getString("menu_file_save_desc"), null));
            editActionSection.add(editActions.new UndoAction(null, undoIcon, bundle.getString("menu_edit_undo"), null));
            editActionSection.add(editActions.new RedoAction(null, redoIcon, bundle.getString("menu_edit_redo"), null));
            viewActionSection.add(viewActions.new ZoomOutAction(null, zoomOutIcon, bundle.getString("menu_view_zoomOut_desc"), null));
            viewActionSection.add(viewActions.new ZoomInAction(null, zoomInIcon, bundle.getString("menu_view_zoomIn_desc"), null));
            viewActionSection.add(viewActions.new ZoomFullAction(null, zoomFullIcon, bundle.getString("menu_view_zoomFull_desc"), null));
            transformActionSection.add(transformActions.new ImageFlipHAction(null,flipHorizontalIcon, bundle.getString("menu_transform_flipVertical_desc"),null));
            transformActionSection.add(transformActions.new ImageFlipVAction(null,flipVerticalIcon,bundle.getString("menu_transform_flipHorizontal_desc"),null));
            transformActionSection.add(transformActions.new Resize50Action(null,resize50Icon, bundle.getString("menu_transform_resize_desc"),null));
            transformActionSection.add(transformActions.new Resize150Action(null,resize150Icon,bundle.getString("menu_transform_resize_desc"),null));
            //TODO: Icons for Rotate actions
            
            colourActionSection.add(colourActions.new ConvertToInverseAction(null, invertIcon, bundle.getString("menu_colour_imageInversion_desc"), null));
            //TODO: Icon for ConvertToGreyAction

            //Other:
            //TODO: update resize description in language files  

            // Add sections to go on left side of toolbar:
            leftActions.add(fileActionSection);
            leftActions.add(editActionSection);
            leftActions.add(transformActionSection);
            leftActions.add(colourActionSection);

            // Add sections to go on right side of toolbar:
            rightActions.add(viewActionSection);
        

        JToolBar toolbar = new JToolBar();
        
        int i = 0;
        // Adds each action in leftActions to the left side of toolbar, adding a separater between each section: 
        if (leftActions.size() > 1){ 
            for(i = 0; i < leftActions.size() - 1; i++){
                for(Action action: leftActions.get(i)){
                    toolbar.add(action);
                }
                toolbar.addSeparator();
            }
        }
        // final section in leftActions has no separator after it:
        for(Action action: leftActions.get(i)){ 
            toolbar.add(action);
        }
        
        // makes every action added after this go to the righthand side of the toolbar:
        toolbar.add(Box.createHorizontalGlue());
        
        int r = 0;
        // Adds each action in rightActions to the right side of toolbar, adding a separater between each section:
        if (rightActions.size() > 1){
            for (r = 0; i < rightActions.size() - 1; r++) {
                for (Action action : rightActions.get(r)) {
                    toolbar.add(action);
                }
                toolbar.addSeparator();
            }
        }
        // final section in rightActions has no separator after it:
        for(Action action: rightActions.get(r)){  
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
