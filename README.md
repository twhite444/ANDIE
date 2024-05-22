## Contributions to features
Block averaging - Liam
Colour channel cycling - Liam 
Colour picker for drawing - Yusei
Contrast / Brightness adjustment - Liam 
Convolution class used to rewrite most filters - Tommo
Crop to selection - Yusei / Liam
Drawing functions - Liam
Emboss and edge detection - Tommo
Exception handling - Everyone
Gaussian blur filter - Charlotte / Yusei / Tommo
Image inversion - Liam  
Image resize - Tommo  
Image rotations - Tommo  
Image flip - Yusei  
Image export - Charlotte  
Junit Testing and Gradle build (testing for rotations and flips) - Tommo  
Keyboard shortcuts - Charlotte
Language Actions Menu - Charlotte 
Macros - charlotte
Median Filter - Cushla, Liam, (Yusei wrote his own version of the code)  
Multi-Language support - Charlotte 
Random scattering - Yusei
Sharpen Filter - Yusei / Tommo
Soft Blur - Yusei / Tommo
Toolbar for common operations - Charlotte
Transformation actions menu - Tommo (added to by Yusei)  
User Guide - Cushla, Tommo (modified by everyone)  


## How our code was tested (in general, not a multi-page exhaustive list).
Uploaded an image to the program and compared the results of running the functions to the correct outcome that was in the lab book. All actions correctly manipulated the image, so we tested a couple of images and no unexpected outcomes came about. 

Built Gradle and created JUnit Testing but not extensively used for testing other than running the program through Gradle to be quicker.

We did use Junit testing for transformation actions such as rotating and flipping. However, Junit testing was difficult for filters so resorted to manually checking through GUI. In the next deliverables we will look to add at least Junit testing for colour changes as that will be easier to implement.


## A list of any known issues or bugs.
For filter methods (Guassian, Mean, Median), applying a filter with a radius greater than 10 will not work. It doesn’t crash the program. For any value greater than 10 it currently chooses a radius of 1. (potentially could use ListView instead of spinner or throw an exception)  

If you open up a very large image it doesn’t automatically resize the GUI around it
Limits on zoom in and zoom out features  

When using crop while zoomed in or out the area that is selected sometimes flickers to having no zoom, this is purely visual and does not affect what area is cropped.

When a block average filter is applied to a .jpg and either the x or y size is one a black line will appear along whatever edge 


## User Guide for ANDIE (A Non Destructive Image Editor)
### Introduction
**ANDIE** is an image-processing application that contains 12 different functions. Each function can be accessed through the tools bar along the top of the app, and each function uniquely manipulates the imported image.  


### User Interface
Once you have launched ANDIE you will see a toolbar along the top with many components that you can press on to manipulate your image, but first, you need to import an image in. You will also notice a large void in the middle of the app; once you have opened your image this space will display the stages of manipulation that your photo goes through.  To open go into the File section and import any saved image from your computer.  


### Menu Options
There are 8 differernt menus that all contain different manipulations:  
**File**: Select ‘Open’ and you can import your desired image, or ‘Save’ to update the changes that you have made to your image into the current file or ‘Save As’ is another option that allows you to make a new version of the file (with stack of operations included) and name it. ‘Export’ allows you to export just the image.  
**Edit**: Selecting the ‘Undo’ function will undo the last changes that you have made to the image, ‘Redo’ does the opposite. 
**Draw**: 
**View**: The ‘Zoom in’ function makes the image larger to a percentage that the user inputs and ‘Zoom Out’ does the same but makes it smaller. ‘Zoom Full’ zooms to the default.   
**Filter**: Mean Filter, Soft Blur, sharpen filter, median filter, and Gaussian blur are all filters that change the way that the pixels and image appear  
**Colors**: greyscale, invert colours, colour cycling  
**Transform**: Contains horizontal, vertical, and inverted ways to flip your image and change the composition
**Language**: 


### Filter Menu:  
**Mean Filter**: A mean filter calculates the average pixel value within a specified neighborhood, smoothing out the image and reducing noise. It replaces each pixel with the average of its neighboring pixels.  
**Soft Blur**: A soft blur filter is similar to the mean filter but does weaker blur, resulting in a smoother appearance. It effectively blurs the image while preserving its overall structure.  
**Sharpen Filter**: A sharpen filter enhances image clarity by increasing the contrast around edges, making them appear sharper. It accentuates high-contrast areas in the image, resulting in a crisper and more defined appearance.  
**Median filter**: A blurring filter that takes in all of the pixel values and finds a median. This median value determines the degree to which the image is blurred. It has a user interaction in which the radius is asked for, the higher the radius number the more blurred.  
**Gaussian Blur**: A Gaussian blur filter reduces image detail by convolving the image with a Gaussian function, which softens the transitions between pixels. It effectively blurs the image while preserving its overall structure, resulting in a smoother appearance with reduced noise and fine details.  


### Colour Menu:
**Greyscale**: converts image from RGB to greyscale  
**Invert**: inverts the colours of the image  
**Cycle Colours**: allows you to cycle through colour channels of all possible combinations (BGR, BRG, GBR, GRB, RBG, RGB)  

### Transformation Menu: 
**Resize to 50%**: Shrinks the image to half its original size.  
**Resize to 100%**: Keeps the image at its original size.   
**Flip Vertical/Horizontal**: Reflects the image either vertically (top becomes bottom) or horizontally (left becomes right).  
**Rotations 90 (left), 90 (right), 180**: Rotates the image by the specified angle (90 degrees left, 90 degrees right, or 180 degrees).  

### Language Menu: 
**There are two language options**: English is set as the default, but selecting Russian and rerunning the file changes it to Russian.  

## Keyboard shortcuts
### File menu shortcuts:
**Open**: `COMMAND` + `O`<br>
**Save**: `COMMAND` + `S` <br>
**Save As**: `COMMAND` + `SHIFT` + `S` <br>
**Export**: `COMMAND` + `E` <br>
**Exit**: `COMMAND` + `Q` <br>

### Edit menu shortcuts:
**Undo**: `COMMAND` + `Z` <br>
**Redo**: `COMMAND` + `Y` <br>

#### Macros submenu shortcuts:
**Apply macro**: `COMMAND` + `1` <br>
**Record macro**: `COMMAND` + `2` (shortcut only applies when macro is not being recorded) <br>
**Stop recording**: `COMMAND` + `3` (shortcut only applies when macro is  being recorded) <br>

### Draw menu shortcuts: 
**Draw rectangle**: `CTRL` + `R` <br>
**Draw oval**: `CTRL` + `O` <br>
**Draw line**: `CTRL` + `L` <br>

### View menu shortcuts:
**Zoom in**: `COMMAND` + `+` <br>
**Zoom out**: `COMMAND` + `-` <br>
**Zoom full**: `COMMAND` + `0` <br>

### Filter menu shortcuts:
**Mean filter**: `CTRL` + `COMMAND` + `M` <br>
**Soft blur filter out**: `CTRL` + `COMMAND` + `F` <br>
**Sharpen filter**: `CTRL` + `COMMAND` + `S` <br>
**Median filter**: `CTRL` + `COMMAND` + `A` <br>
**Gaussian filter**: `CTRL` + `COMMAND` + `G` <br>
**Block average filter**: `CTRL` + `COMMAND` + `B` <br>
**Random scattering**: <br>
**Emboss filter**: <br>
**Sobel filter**: <br>

### Filter menu shortcuts:
**Greyscale**: `SHIFT` + `COMMAND` + `G` <br>
**Image inversion**: `COMMAND` + `I` <br>
**Greyscale**: `SHIFT` + `COMMAND` + `G` <br>
**Cycle colours**: `CTRL` + `COMMAND` + `C` <br>
**Change contrast and brightness**: `SHIFT` + `COMMAND` + `B` <br>

### Transform menu shortcuts:
**Resize to 50%**: `SHIFT` + `COMMAND` + `-` <br>
**Resize to 150%**: `SHIFT` + `COMMAND` + `+` <br>
**Crop image**: <br>
#### Flip submenu shortcuts:
**Flip vertical**: `COMMAND` + `[` <br>
**Flip horizontal**: `COMMAND` + `]` <br>
#### Rotate submenu shortcuts:
**Rotate left**: `COMMAND` + `,` <br>
**Rotate right**: `COMMAND` + `.` <br>
**Rotate 180**: `COMMAND` + `/` <br>

