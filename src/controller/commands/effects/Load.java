package controller.commands.effects;

import java.io.IOException;
import java.util.List;

import imagetypes.ImageUtil;
import model.IModel;
import view.IView;
import imagetypes.IImage;

/**
 * Represents a load command in order for
 * users to be able to load an image into
 * the Image Processing system.
 */
public class Load implements IRunCommand {

  // variables
  private IModel model;
  private IView view;

  // constructor

  /**
   * Represents a load command that takes in a model and
   * a view for the load command to be performed upon.
   * @param model represents an IModel.
   * @param view represents an IView.
   * @throws IllegalArgumentException if either the model or view are null.
   */
  public Load(IModel model, IView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("View need not be null");
    }
    //model is null
    if (model == null) {
      throw new IllegalArgumentException("Model need not be null");
    }

    //initialize
    this.model = model;
    this.view = view;
  }

  // methods

  /**
   * Represents a method that loads an image as long
   * as it is provided a load instruction, a file path,
   * the name of the image, and the image's format.
   * @param commands list of commands entered by the user.
   * @throws IOException if the load method cannot be transmitted.
   * @throws IllegalArgumentException if there are not sufficient arguments
   *                                  to perform the command.
   */
  @Override
  public void doCommand(List<String> commands) throws IOException, IllegalArgumentException {
    if (3 > commands.size()) {
      throw new IllegalArgumentException("Load command must include: a path name, " +
              "an ImageName, and a file format. Please try again.");
    }

    String pathName = commands.get(0);
    String imageName = commands.get(1);
    String fileFormat = commands.get(2);
    IImage image;

    //2D array of pixels,
    //

    IImage imageRead = ImageUtil.readImage(pathName);
    // IImage ppmRead = ImageUtil.readPPM(pathName);

    switch (fileFormat) {
      case "bmp" :
        image = imageRead;
        break;
      case "ppm":
        image = imageRead;
        break;
      case "jpg":
        image = imageRead;
        break;
      case "png" :
        image = imageRead;
        break;
      default:
        throw new IllegalArgumentException("Unrecognized file format. Please try again.");
    }
    model.takeInImage(image, imageName);
    this.view.renderMessage("Loaded a " + fileFormat.toUpperCase() + " image from "  + "'"
            + pathName + "'" + " to " + "'" + imageName + "'" );
  }
}
