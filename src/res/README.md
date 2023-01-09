# README
#### By: Emma Assiradoo and Avery Leiss

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
Here is the image citation:

VXLOMEGAV6 | Getty images

<a href="data:image/jpeg;base64,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">link to duck image</a>