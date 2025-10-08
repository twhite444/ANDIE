## About This Project
<br>
ANDIE (A Non-Destructive Image Editor) is a Java-based image editing application built as part of the University of Otago COSC 202 Software Development paper. The goal was to design and extend a fully-featured desktop image editor while learning how to work effectively in a real software development team.
<br>
<br>
Over the semester, we followed professional workflows using Git and GitLab, Gradle builds, JUnit testing, and continuous integration pipelines. The project gave us hands-on experience with collaborative version control, feature branching, documentation, and writing reliable, maintainable code in a shared environment.
<br><br>
**My contributions:** 

<br>

I worked mainly on the **image transformation and filtering systems**, as well as **testing and build automation**. Some of the specific things I did include: <br>
	•	Implemented image resize, rotation (90°, 180°), and the transform actions menu. <br>
	•	Developed Emboss, Edge Detection, Gaussian Blur, and Soft Blur filters, and built my own custom Convolution class used across most filter operations. <br>
	•	Set up Gradle builds, JUnit test suites, and integrated them into the CI pipeline. <br>
	•	Helped with exception handling, code documentation, and contributed to the User Guide.<br>
<br>
**Skills and tools:**<br>

Through this project I gained practical experience with:<br>
	•	Java (Swing GUI) and object-oriented design<br>
	•	Gradle for builds and automation<br>
	•	JUnit testing and continuous integration on GitLab<br>
	•	Team development practices like branching, merging, and resolving conflicts<br>
<br><br>
This project was challenging, rewarding, and a great introduction to collaborative software engineering: building a working, extensible application from the ground up while learning to manage code quality, testing, and communication within a team.


------------------------------------------------------------------------------------------------------
## Download
The program can be downloaded as a single .jar file at: https://cosc202-kooky_koalas.cspages.otago.ac.nz/andie/

## Contributions to features
Block averaging - Liam<br>
Colour channel cycling - Liam <br>
Colour picker for drawing - Yusei<br>
Contrast / Brightness adjustment - Liam <br>
Convolution class used to rewrite most filters - Tommo<br>
Crop to selection - Yusei / Liam<br>
Drawing functions - Liam<br>
Emboss and edge detection - Tommo<br>
Exception handling - Everyone<br>
Gaussian blur filter - Charlotte / Yusei / Tommo<br>
Image inversion - Liam  <br>
Image resize - Tommo  <br>
Image rotations - Tommo<br>
Image flip - Yusei  <br>
Image export - Charlotte  <br>
Junit Testing and Gradle build  - Tommo / Liam / Yusei / Charlotte<br>
Keyboard shortcuts - Charlotte<br>
Language Actions Menu - Charlotte <br>
Macros - Charlotte<br>
Median Filter - Cushla, Liam, Yusei<br>
Multi-Language support - Charlotte <br>
Random scattering - Yusei<br>
Sharpen Filter - Yusei / Tommo<br>
Soft Blur - Yusei / Tommo<br>
Toolbar for common operations - Charlotte<br>
Transformation actions menu - Tommo (added to by Yusei)  <br>
User Guide - Cushla, Tommo (modified by everyone)  <br>
Selected Area Random Scattering - Yusei <br>


## How our code was tested (in general, not a multi-page exhaustive list).
Uploaded an image to the program and compared the results of running the functions to the correct outcome that was in the lab book. All actions correctly manipulated the image, so we tested a couple of images and no unexpected outcomes came about. 

Built Gradle and created JUnit Testing for all filters except random scattering. Continuous integration has been implemented through the pipeline for JUnit Testing.


## A list of any known issues or bugs.
For filter methods (Guassian, Mean, Median), applying a filter with a radius greater than 10 will not work. It doesn’t crash the program. For any value greater than 10 it currently chooses a radius of 1. (potentially could use ListView instead of spinner or throw an exception)  

If you open up a very large image it doesn’t automatically resize the GUI around it
Limits on zoom in and zoom out features  

When using crop while zoomed in or out the area that is selected sometimes flickers to having no zoom, this is purely visual and does not affect what area is cropped.

If any of the drawing tools are used when recording a macro their colours will always be black.


## User Guide for ANDIE (A Non Destructive Image Editor)
### Introduction
**ANDIE** is an image-processing application that contains 12 different functions. Each function can be accessed through the tools bar along the top of the app, and each function uniquely manipulates the imported image.  


### User Interface
Once you have launched ANDIE you will see a toolbar along the top with many components that you can press on to manipulate your image, but first, you need to import an image in. You will also notice a large void in the middle of the app; once you have opened your image this space will display the stages of manipulation that your photo goes through.  To open go into the File section and import any saved image from your computer.  


### Menu Options
There are 8 differernt menus that all contain different operations:  
**File**: Operations to open and manipulate files.
**Edit**: Operations to chage what operations are applied to an image.
**Draw**: Operations to draw various types of shapes on the image.
**View**: Operations to change how the image is viewed.
**Filter**: Operations that apply various filters to the image, changing pixels based on nearby ones.
**Colors**: Operations that changes the colours in the image.
**Transform**: Operations that change the shape of the image.
**Language**: Changes what language the program is in.


### File menu:
**Open**: Imports a selected image into the program.
**Save**: Updates the changes that you have made to your image into the current file.
**Save as**: Same as previous but allows for renaming and moving of where the file is saved.
**Export**: Allows you to export just the image.
**Exit**: Closes the program.


### Edit menu:
**Undo**: Undoes the last changes that you have made to the image.
**Redo**: Redoes the last undo.
**Macros**: ‘Apply Macro’ will apply a set opertains defined in a selected .ops file to the current image. ‘Record macro’ will record any operations that are made before ‘Stop recording’ is clicked and asve them as a .ops file.


### Draw menu:
Selecing any of **Draw rectangle**, **Draw oval** and **Draw Line** will open a colour picker, allowing you to choose the outline colour and, in the case of rectangle and oval, the fill colour, the selected shape can the be drawn by clicking and dragging.


### View menu:
**Zoom in**: Zooms the image in by 10%, up to a maximum of 200%.
**Zoom out**: Zooms the image out by 10%, down to a minimum of 50%.
**Zoom full**: Resets zoom to 100%.


### Filter Menu:  
**Mean filter**: A mean filter calculates the average pixel value within a specified neighborhood, smoothing out the image and reducing noise. It replaces each pixel with the average of its neighboring pixels.  
**Soft Blur filter**: A soft blur filter is similar to the mean filter but does weaker blur, resulting in a smoother appearance. It effectively blurs the image while preserving its overall structure.  
**Sharpen filter**: A sharpen filter enhances image clarity by increasing the contrast around edges, making them appear sharper. It accentuates high-contrast areas in the image, resulting in a crisper and more defined appearance.  
**Median filter**: A blurring filter that takes in all of the pixel values and finds a median. This median value determines the degree to which the image is blurred. It has a user interaction in which the radius is asked for, the higher the radius number the more blurred.  
**Gaussian filter**: A Gaussian blur filter reduces image detail by convolving the image with a Gaussian function, which softens the transitions between pixels. It effectively blurs the image while preserving its overall structure, resulting in a smoother appearance with reduced noise and fine details.
**Block average**: Splits the image up into blocks with each block will be coloured based on the average colour of the pixels inside the block.
**Random Scattering**: Randomly moves each pixel to some position within a radius.
**Emboss filter**:  Enhances the appearances of edges in an image to simulate a raised or embossed effect
**Sobel filter**: Detects edges in an image to show areas of rapid intensity change with higher contrast
**Motion Blur**: The Motion Blur Filter implementation applies a blur effect of a specified kernel size in a specified direction 
**Selected Area Random Scattering**: It does use both crop function and RandomScattering function so than you can apply randomscattering to the area you select as a default the radius is set to 5. 

### Colour Menu:
**Greyscale**: Converts image from RGB to greyscale.
**Invert**: Inverts the colours of the image from RGB to any ordering of R, G and B.
**Cycle Colours**: Allows you to cycle through colour channels of all possible combinations (BGR, BRG, GBR, GRB, RBG, RGB).
**Change contrast and brightness**: Changes the contrast and brightness by a specified percetage from -100% to 100%.


### Transformation Menu: 
**Resize to 50%**: Shrinks the image to half its original size.  
**Resize to 100%**: Keeps the image at its original size.
**Crop image**: Crops the image to a selected area.
**Flip Vertical/Horizontal**: Reflects the image either vertically (top becomes bottom) or horizontally (left becomes right).
**Rotations 90 (left), 90 (right), 180**: Rotates the image by the specified angle (90 degrees left, 90 degrees right, or 180 degrees).  


### Language Menu: 
**There are two language options**: English is set as the default language, but selecting Russian and reopening the program will change it to Russian.  


## Keyboard shortcuts
### File menu shortcuts:
**Open**: `CTRL` + `O`<br>
**Save**: `CTRL` + `S` <br>
**Save As**: `CTRL` + `SHIFT` + `S` <br>
**Export**: `CTRL` + `E` <br>
**Exit**: `CTRL` + `Q` <br>

### Edit menu shortcuts:
**Undo**: `CTRL` + `Z` <br>
**Redo**: `CTRL` + `Y` <br>

#### Macros submenu shortcuts:
**Apply macro**: `CTRL` + `1` <br>
**Record macro**: `CTRL` + `2` (shortcut only applies when macro is not being recorded) <br>
**Stop recording**: `CTRL` + `3` (shortcut only applies when macro is  being recorded) <br>

### Draw menu shortcuts: 
**Draw rectangle**: `CTRL` + `SHIFT` + `R` <br>
**Draw oval**: `CTRL` + `SHIFT` + `O` <br>
**Draw line**: `CTRL` + `SHIFT` + `L` <br>

### View menu shortcuts:
**Zoom in**: `CTRL` + `+` <br>
**Zoom out**: `CTRL` + `=` <br>
**Zoom full**: `CTRL` + `0` <br>

### Filter menu shortcuts:
**Mean filter**: `CTRL` + `ALT` + `M` <br>
**Soft blur filter out**: `CTRL` + `ALT` + `F` <br>
**Sharpen filter**: `CTRL` + `ALT` + `S` <br>
**Median filter**: `CTRL` + `ALT` + `A` <br>
**Gaussian filter**: `CTRL` + `ALT` + `G` <br>
**Block average filter**: `CTRL` + `ALT` + `B` <br>
**Random scattering**: `CTRL` + `ALT` + `R` <br>
**Emboss filter**: `CTRL` + `ALT` + `E`<br>
**Sobel filter**: `CTRL` + `ALT` + `O` <br>

### Filter menu shortcuts:
**Greyscale**: `CTRL` + `G` <br>
**Image inversion**: `CTRL` + `I` <br>
**Cycle colours**: `CTRL` + `SHIFT` + `C` <br>
**Change contrast and brightness**: `CTRL` + `SHIFT` + `B` <br>

### Transform menu shortcuts:
**Resize to 50%**: `CTRL` + `SHIFT` + `-` <br>
**Resize to 150%**: `CTRL` + `SHIFT` + `=` <br>
**Crop image**: `CTRL` + `C`<br>
#### Flip submenu shortcuts:
**Flip vertical**: `CTRL` + `[` <br>
**Flip horizontal**: `CTRL` + `]` <br>
#### Rotate submenu shortcuts:
**Rotate left**: `CTRL` + `,` <br>
**Rotate right**: `CTRL` + `.` <br>
**Rotate 180**: `CTRL` + `/` <br>

