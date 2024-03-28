## Contributions to features
Sharpen Filter - Yusei  
Gaussian blur filter - Charlotte  
Median Filter - Cushla, Liam, (Yusei wrote his own version of the code)  
Image inversion - Liam  
Colour channel cycling - Liam  
Multi-Language support - Charlotte  
Image resize - Tommo  
Image rotations - Tommo  
Image flip - Yusei  
Image export - Charlotte  
Exception handling - Liam, Tommo, Charlotte, Yusei  
Other error avoidance/prevention - Liam  
    -Error dialogue boxes/throwing exceptions for save and open functions, and applying methods to no image  
Junit Testing and Gradle build (testing for rotations and flips) - Tommo  
Transformation actions menu - Tommo (added to by Yusei)  
User Guide - Cushla (modified by everyone)  




## How our code was tested (in general, not a multi-page exhaustive list).
Uploaded an image to the program and compared the results of running the functions to the correct outcome that was in the lab book. All actions correctly manipulated the image, so we tested a couple of images and no unexpected outcomes came about. 


Built Gradle and created JUnit Testing but not extensively used for testing other than running the program through Gradle to be quicker.


We did use Junit testing for transformation actions such as rotating and flipping. However, Junit testing was difficult for filters so resorted to manually checking through GUI. In the next deliverables we will look to add at least Junit testing for colour changes as that will be easier to implement.


## A list of any known issues or bugs.
For filter methods (Guassian, Mean, Median), applying a filter with a radius greater than 10 will not work. It doesn’t crash the program. For any value greater than 10 it currently chooses a radius of 1. (potentially could use ListView instead of spinner or throw an exception)  


Need to combine ImageFlipH and ImageFlipV into one java file  


If you open up a very large image it doesn’t automatically resize the GUI around it
Limits on zoom in and zoom out features  




## User Guide for ANDIE (A Non Destructive Image Editor)
### Introduction
**ANDIE** is an image-processing application that contains 12 different functions. Each function can be accessed through the tools bar along the top of the app, and each function uniquely manipulates the imported image.  


### Launching ANDIE
To launch ANDIE the Andie.java file is run through any Java application. Doing this opens a graphical interactive interface, which acts as an app for which the functions run through.  


Once you have launched ANDIE you will see a toolbar along the top with many components that you can press on to manipulate your image, but first, you need to import an image in. You will also notice a large void in the middle of the app; once you have opened your image this space will display the stages of manipulation that your photo goes through.  To open go into the File section and import any saved image from your computer.  


### Menu Options
There are 6 menu sections that all contain different manipulations:  
**File**: Select ‘Open’ and you can import your desired image, or ‘Save’ to update the changes that you have made to your image into the current file or ‘Save As’ is another option that allows you to make a new version of the file (with stack of operations included) and name it. ‘Export’ allows you to export just the image.  
**Edit**: Selecting the ‘Undo’ function will undo the last changes that you have made to the image, ‘Redo’ does the opposite.  
**View**: The ‘Zoom in’ function makes the image larger to a percentage that the user inputs and ‘Zoom Out’ does the same but makes it smaller. ‘Zoom Full’ zooms to the default.   
**Filters**: Mean Filter, Soft Blur, sharpen filter, median filter, and Gaussian blur are all filters that change the way that the pixels and image appear  
**Colors**: greyscale, invert colours, colour cycling  
**Transformations**: Contains horizontal, vertical, and inverted ways to flip your image and change the composition
Language   
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





