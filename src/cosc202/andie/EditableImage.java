package cosc202.andie;

import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.management.RuntimeErrorException;
import javax.swing.JOptionPane;

/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 * 
 * <p>
 * The EditableImage represents an image with a series of operations applied to it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can be undone.
 * This is what is meant by "A Non-Destructive Image Editor" - you can always 
 * undo back to the original image.
 * </p>
 * 
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original image
 * and the result of applying the current set of operations to it.
 * The operations themselves are stored on a {@link Stack}, with a second
 * {@link Stack} being used to allow undone operations to be redone.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
class EditableImage {

    /** The original image. This should never be altered by ANDIE. */
    private BufferedImage original;
    /** The current image, the result of applying {@link ops} to {@link original}. */
    private BufferedImage current;
    /** The sequence of operations currently applied to the image. */
    private Stack<ImageOperation> ops;
    /** A memory of 'undone' operations to support 'redo'. */
    private Stack<ImageOperation> redoOps;
    /** The file where the original image is stored/ */
    private String imageFilename;
    /** The file where the operation sequence is stored. */
    private String opsFilename;

    private Stack<ImageOperation> macro;
    private Stack<ImageOperation> macroOpsRedo;
    private boolean recordingMacro;

    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     * 
     * <p>
     * A new EditableImage has no image (it is a null reference), and an empty stack
     * of operations.
     * </p>
     */
    public EditableImage() {
        original = null;
        current = null;
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        imageFilename = null;
        opsFilename = null;
        recordingMacro = false;
        macro = new Stack<ImageOperation>();
        macroOpsRedo = new Stack<ImageOperation>();

    }

    /**
     * <p>
     * Check if there is an image loaded.
     * </p>
     * 
     * @return True if there is an image, false otherwise.
     */
    public boolean hasImage() {
        return current != null;
    }

    /**
     * <p>
     * Check if the image has been edited.
     * </p>
     * 
     * @return True if the image has been edited, false otherwise.
     */
    public boolean imageHasBeenEdited() {
        return !ops.empty();
    }

    /**
     * <p>
     * Make a 'deep' copy of a BufferedImage.
     * </p>
     * 
     * <p>
     * Object instances in Java are accessed via references, which means that
     * assignment does not copy an object, it merely makes another reference to the original.
     * In order to make an independent copy, the {@code clone()} method is generally used.
     * {@link BufferedImage} does not implement {@link Cloneable} interface, and so the
     * {@code clone()} method is not accessible.
     * </p>
     * 
     * <p>
     * This method makes a cloned copy of a BufferedImage.
     * This requires knowledge of some details about the internals of the BufferedImage,
     * but essentially comes down to making a new BufferedImage made up of copies of
     * the internal parts of the input.
     * </p>
     * 
     * <p>
     * This code is taken from StackOverflow:
     * <a href="https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
     * in response to
     * <a href="https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
     * Code by Klark used under the CC BY-SA 2.5 license.
     * </p>
     * 
     * <p>
     * This method (only) is released under
     * <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
     * </p>
     * 
     * @param bi The BufferedImage to copy.
     * @return A deep copy of the input.
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * <p>
     * Open an image from a file.
     * </p>
     * 
     * <p>
     * Opens an image from the specified file.
     * Also tries to open a set of operations from the file with <code>.ops</code> added.
     * So if you open <code>some/path/to/image.png</code>, this method will also try
     * to read the operations from <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param filePath The file to open the image from.
     * @throws Exception If something goes wrong.
     */
    public void open(String filePath) throws Exception {
        imageFilename = filePath;
        opsFilename = imageFilename + ".ops";
        File imageFile = new File(imageFilename);
        original = ImageIO.read(imageFile);
        current = deepCopy(original);

        try {
            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            redoOps.clear();
            objIn.close();
            fileIn.close();
        } catch (Exception ex) {
            // Could be no file or something else. Carry on for now.
            ops.clear();
            redoOps.clear();
        }
        this.refresh();
    }

    /**
     * <p>
     * Save an image to file.
     * </p>
     * 
     * <p>
     * Saves an image to the file it was opened from, or the most recent file saved as.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @throws Exception If something goes wrong.
     */
    public void save() throws Exception {
        if (this.opsFilename == null) {
            this.opsFilename = this.imageFilename + ".ops";
        }
        // Write image file based on file extension
        String extension = imageFilename.substring(1 + imageFilename.lastIndexOf(".")).toLowerCase();
        ImageIO.write(original, extension, new File(imageFilename));
        // Write operations file
        FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(this.ops);
        objOut.close();
        fileOut.close();
    }

/**
     * <p>
     * Export an image to a specified file.
     * </p>
     * 
     * <p>
     * Exports an image to the file provided as a parameter.
     * </p>
     * 
     * <p>
     * Does not save a set of operations.
     * </p>
     * 
     * @author Charlotte Cook
     * 
     * @param imageFilename The file location to export the image to.
     * @throws Exception If something goes wrong.
     */
    public void exportAs(String imageFilename) throws Exception {
        String extension = this.imageFilename.substring(1 + this.imageFilename.lastIndexOf(".")).toLowerCase();
        
        // if the new file name for the image does not include an extension, the original extension of image
        // is concatenated to the end of the new file name and the image is written to file
        if (imageFilename.indexOf(".") == -1) {

            ImageIO.write(current, extension, new File(imageFilename.concat(".").concat(extension)));
            
        } else {
            // Else, the extension of the new file is changed to that of the original image and the image is written to file 
            // (whether or not the extension differs between original and new file name)
            ImageIO.write(current, extension,
                    new File(imageFilename.substring(0, 1 + imageFilename.lastIndexOf(".")).concat(extension)));
        }
    }

    /**
     * <p>
     * Save an image to a specified file.
     * </p>
     * 
     * <p>
     * Saves an image to the file provided as a parameter.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     * @throws Exception If something goes wrong.
     */
    public void saveAs(String imageFilename) throws Exception {
        this.imageFilename = imageFilename;
        this.opsFilename = imageFilename + ".ops";
        save();
    }

    /**
     * <p>
     * Apply an {@link ImageOperation} to this image.
     * </p>
     * 
     * @param op The operation to apply.
     */
    public void apply(ImageOperation op) {
        current = op.apply(current);
        ops.add(op);
        if(recordingMacro){
            macro.add(op);
        }
    }

    /**
     * <p>
     * Starts recording a new macro.
     * </p>
     * 
     * <p>
     * Starts recording a new macro.
     * Creates a new Stack of ImageOperations to later be saved to file by {@link #saveMacro(String)}.
     * 
     * </p>
     * 
     * @author Charlotte Cook
     * 
     * @throws Exception If something goes wrong.
     */
    public void recordMacro() throws Exception{
        if(!macro.isEmpty()){
            macro.clear();
        }
        macro = new Stack<ImageOperation>();
        recordingMacro = true;
        
    }
    
/**
     * <p>
     * Ends the recording of a macro and saves it to a specified file.
     * </p>
     * 
     * <p>
     * Ends recording of operations to a new macro file, and saves it 
     * to the file specified by the user
     * </p>
     * 
     * @author Charlotte Cook
     * 
     * @param macroFilename The file location to save the macro to.
     * @throws Exception If something goes wrong.
     */
    public void saveMacro(String macroFilename) throws Exception{
        if(!recordingMacro){
            throw new RuntimeException("Cannot save macro if operations were not being recorded");
        } 
        if(macro.isEmpty()){
            throw new Exception("Cannot save empty macro");
        }
        recordingMacro = false;
        if(!macroFilename.contains(".ops")){
            macroFilename = macroFilename.concat(".ops");
        }
        FileOutputStream fileOut = new FileOutputStream(macroFilename);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(this.macro);

        objOut.writeObject(current.getWidth()); //
        objOut.writeObject(current.getHeight()); //

        objOut.close();
        fileOut.close();
        macro.clear();
        
    }

    /**
     * <p>
     * Applies a macro to an image from a file.
     * </p>
     * 
     * <p>
     * Applies a macro (a series of operations) to an image, from the specified file.
     * </p>
     * 
     * @param filePath The file to open the macro from.
     * @throws Exception If something goes wrong.
     */
    public void applyMacro(String filePath) throws Exception{
        // boolean sameSize = false;
        try {
            // FileInputStream fileIn = new FileInputStream(this.opsFilename);
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromMacro = (Stack<ImageOperation>) objIn.readObject();
            // int originalMacroWidth = (int) objIn.readObject();
            // int originalMacroHeight = (int) objIn.readObject();
            
            // if(original.getWidth() == originalMacroWidth && original.getHeight() == originalMacroHeight){
                
                
                while(!opsFromMacro.isEmpty()){
                    //ops.push(opsFromMacro.remove(0));
                    apply(opsFromMacro.remove(0));
                }

                // for(ImageOperation op: opsFromMacro){
                //     apply(op);
                // }
                // sameSize = true;
            // } else { // doesn't do anything I think...
            //     int thisImageWidth = current.getWidth();
            //     int thisImageHeight = current.getHeight();

            //     this.current = new Resize().apply(current, originalMacroWidth, originalMacroHeight);

            //     while(!opsFromMacro.isEmpty()){
            //         ops.push(opsFromMacro.remove(0));
            //     }
            //     this.refresh();
            //     this.current = new Resize().apply(current, thisImageWidth, thisImageHeight);
                
            // }
            redoOps.clear();
            objIn.close();
            fileIn.close();
        } catch (Exception ex) {
            // Could be no file or something else. Carry on for now.
            ops.clear();
            redoOps.clear();
        }
        //if(sameSize == true){
            this.refresh();
        //}
    }

    /**
     * <p>
     * Undo the last {@link ImageOperation} applied to the image.
     * </p>
     */
    public void undo() {

        if(ops.empty()) { // checks if there is anything to undo

            JOptionPane.showMessageDialog(null, "Error: nothing to undo", "Error", JOptionPane.ERROR_MESSAGE);

            throw new RuntimeException();

        }

        redoOps.push(ops.pop());
        if(recordingMacro){
            macroOpsRedo.push(macro.pop());
        }
        refresh();
    }

    /**
     * <p>
     * Reapply the most recently {@link undo}ne {@link ImageOperation} to the image.
     * </p>
     */
    public void redo()  {

        if(redoOps.empty()) { // checks if there is anything to redo

            JOptionPane.showMessageDialog(null, "Error: nothing to redo", "Error", JOptionPane.ERROR_MESSAGE);

            throw new RuntimeException();

        }

        apply(redoOps.pop());
        if(recordingMacro){
            macro.push(macroOpsRedo.pop());
        }
    }

    /**
     * <p>
     * Get the current image after the operations have been applied.
     * </p>
     * 
     * @return The result of applying all of the current operations to the {@link original} image.
     */
    public BufferedImage getCurrentImage() {
        return current;
    }

    /**
     * <p>
     * Reapply the current list of operations to the original.
     * </p>
     * 
     * <p>
     * While the latest version of the image is stored in {@link current}, this
     * method makes a fresh copy of the original and applies the operations to it in
     * sequence.
     * This is useful when undoing changes to the image, or in any other case where
     * {@link current} cannot be easily incrementally updated.
     * </p>
     */
    private void refresh() {
        current = deepCopy(original);
        for (ImageOperation op : ops) {
            current = op.apply(current);
        }
    }

}
