# README
#### By: Emma Assiradoo and Avery Leiss

## We created an Image Processing application that allows
users to upload/select an image and perform various
manipulations on it, from flipping the image to
applying filters and altering its color.
---
## Interfaces
###### - IAddInstructions:
The IAddInstructions interface represents an interface
that allows for commands to be implemented. We made this
an interface so that we could add commands using
the doCommand() method, and it could be implemented in the
command classes.
###### - IController:
The IController interface represents the controller interface
that hosts the startProgram() method to start the
our Image Processing system. We made this an interface so that
it could be implemented in the Controller class.
###### - IRunCommand:
The IRunCommand interface represents an interface that allows for
commands to be run. It contains a mandatory execute() method that is
used to execute the given command. We made this an interface so that the
execute() method had to be called in every class that implements
this interface.
###### - IImage:
The IImage interface represents an interface for the image
types and their methods. Every method in this interface applies to
every image that will run through our program. We made this an interface
so that we could access information about any image processed through
our system (width, height, maxRGB, etc.).
###### - IFilter:
The IFilter interface represents an interface that holds the
function that performs a filter on an image, alter(). We made this an interface
so that every filter class that implements this interface was required
to implement the alter() method.
###### - IModel:
The IModel interface represents the interface for the model of
MVC, which represents the methods needed to output a successful
model that communicates with the controller. We made this an interface
so that the Model class would have to implement the necessary methods
like addFilter(), getImg(), and takeInImage().
###### - IView:
The IView interface represents the View of MVC (Model, View, Controller). It
allows us to display to the user the actions they are performing on
their images and communicates with the controller. We made this an interface
so that it could be implemented in the View class.
---
## Abstract Classes
###### - APixelF:
The abstract class APixelF represents an abstract class that holds the alter() method
for the old (assignment 4). We made this an abstract class so that the
filter classes could extend this abstract class and therefore ensure that
said classes would implement the alter() method in order for our
program to run.

---
## Classes
### Controller Package
#### addCommands package
###### - AddInstructions:
The AddInstructions class represents a class that adds all the given commands
so that they can be executed. We made this a class so that we could have a
succinct place to organize all the commands in our program. It also includes the
doCommand() that will execute said commands.
#### commands package --> controller package
###### - Controller:
The Controller class represents the C of the MVC. The controller
communicates with the model and the view to parse through our Image
Processing System. We made this a class because every part of the MVC
needs to have its own class.
###### - GUIController:
The GUIController class represents the controller for the GUI of
our program. We made this a class because our GUI needed a separate
controller in addition to the controller already implemented
in our program.
#### commands package --> effects package
###### - ExposureC:
The ExposureC class represents the exposure effect command that
can either brighten or darken an image based on the user's input. We made this
a class so that we could call each of these classes in the addCommands
class so that we could execute this given command. Making this an independent
class will allow for possible extension in later versions
if need be.
###### - FilterC:
The FilterC class represents a filter command class that applies
the given filter to an image. We made this a class so that we could call
each of these classes in the addCommands class so that we could execute this
given command. Making this an independent class will allow for possible extension in later versions
if need be.
###### - Load:
The LoadC class represents a load command that allows a user to load
an image into the Image Processing system. We made this a class so that we could call
each of these classes in the addCommands class so that we could execute this
given command. Making this an independent class will allow for possible extension in later versions
if need be.
###### - Save:
The SaveC class represents a save command that allows a user to save an image
into the Image Processing system. We made this a class so that we could call
each of these classes in the addCommands class so that we could execute this
given command. Making this an independent class will allow for possible extension in later versions
if need be.
#### imageTypes package
###### - ColorImage:
The ColorImage class represents an image (of general type) with colors, and it
extends the abstract class APixelImage. We made this a class to represent an image
to be altered.
###### - ImageUtil:
The ImageUtils class represents a utility class that can be used
to read and write images. We made this class in order to read and write images.
### manipulation package
###### - Manipulation:
The Manipulation class represents image manipulation. Most of the
methods take in buffered images and try to manipulate these
images. We made this a class so that it could be open
to extension later on in the project as well as prevent
interference with the code we had previously written.
### model Package
#### filter package --> iFilers package
###### - AlterColorF:
The AlterColorF class represents a class that applies a matrix filter
to an image. It holds the filteredPixel() method by which this alteration
can be processed. We made this class in order to house the filteredPixel() method
and confirm that the kernel passed though is valid.
###### - ColorF:
The ColorF class represents a class that applies a colored RGB filter to
an image. It also implements the IFilter interface, thus it utilitzes the
alter() method. We made this a class in
order to apply a color (RGB) filter to an image.
###### - Kernel:
The Kernel class represents a kernel which can be used to apply
filters to various images. It also implements the IFilter
interface, therefore it utilizes the alter() method. We made this a class
in order to organize where and how we utilize kernels for the
newer implementation of our program (assignment 5).
#### newfilters package
###### - NewEffectsF:
The NewEffectsF class represents a class with the filters blur, sharpen,
sepia, and greyscale. The class holds the matrices for the new effects
as introduced in assignment 5. We made this a class so we could
have all the new matrices organized under one class.
#### oldfilters package
###### - ExposureF:
The ExposureF class represents the class holding the brighten filter.
It also extends the abstract class PixelF (which holds the filteredPixel()
method). We made this a class so that the brighten filter could implement
the filteredPixel() method and apply the brighten filter to an image.
###### - FlipHorF:
The FlipHorF class represents the class holding the flip horizontal filter.
It also extends the abstract class PixelF (which holds the filteredPixel() method)).
We made this a class so that the horizontal filter could implement
the filteredPixel() method and apply the horizontal filter to an image.
###### - FlipVerF:
The FlipVerF class represents the class holding the flip vertical filter.
It also extends the abstract class PixelF (which holds the filteredPixel() method)).
We made this a class so that the vertical filter could implement
the filteredPixel() method and apply the vertical filter to an image.
###### - IntensityF:
The IntensityF class represents the intensity filter, which represents
the average of the three components for each pixel; it assigns
the average value to each of the three pixels. It extends the abstract
class APixelF (which holds the filteredPixel() method)).
We made this a class so that the intensity filter could implement
the filteredPixel() method and apply the intensity filter to an image.
###### - LumaF:
The LumaF class represents the luma filter, which represents the weighted sum of the
three components for each pixel; it assigns this weighted sum value to
each of the three pixel. It extends the abstract
class APixelF (which holds the filteredPixel() method)).
We made this a class so that the luma filter could implement
the filteredPixel() method and apply the luma filter to an image.
###### - ValueF:
The ValueF class represents the value filter, which represents the maximum value
of the three components for each pixel; assigns this max value to each of
the three pixels. It extends the abstract
class APixelF (which holds the filteredPixel() method)).
We made this a class so that the value filter could implement
the filteredPixel() method and apply the value filter to an image.
### model package
###### - Model:
The Model class represents the M of MVC. The model contains a HashMap to take
in an image's old ID and creating a new IImage. We made this a class because every part of the MVC
needs to have its own class.
### view package
###### - Histogram:
The Histogram class represents the histogram that can be seen by the user
when they import an image into the program. It renders a histogram
for the color channels and intensity of an RGB image given its bitmap. We made
this a class so that all of the functionality of the histogram was organized in one
area and could be implemented into our program.
###### - View:
The View class represents the V of MVC. The view contains an appendable
to concatenate strings and produce visuals. We made this a class because every part of the MVC
needs to have its own class.
---
## Change Justifications
Our model implementation for assignment 6 was the same as
our model implementation for assignment 5. The main things we changed
were outside the model class.

---
## Image Citations
The main image that we used for testing purposes in our project
is a lil duck that we got from google from Getty Images. This image was
approved for use in educational settings.
