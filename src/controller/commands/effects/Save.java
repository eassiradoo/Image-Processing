package controller.commands.effects;

import java.io.IOException;
import java.util.List;
import imagetypes.ImageUtil;
import model.IModel;
import view.IView;
import imagetypes.IImage;

/**
 * Class that represents the save command.
 * Implements the IRunCommand interface.
 */
public class Save implements IRunCommand {

  // variables
  private IModel model;
  private IView view;

  // constructor

  /**
   * Represents a save command that takes in a model and
   * a view for the save command to be performed upon.
   * @param model represents an IModel.
   * @param view represents an IView.
   * @throws IllegalArgumentException if either the model or view are null.
   */
  public Save(IModel model, IView view) throws IllegalArgumentException {
    // view null
    if (view == null) {
      throw new IllegalArgumentException("View need not be null");
    }
    // model null
    if (model == null) {
      throw new IllegalArgumentException("Model need not be null");
    }

    // initialize
    this.model = model;
    this.view = view;
  }

  // methods

  /**
   * Represents a method that saves an image as long
   * as it is provided a save instruction, a file path,
   * the name of the image, and the image's format.
   * @param commands list of commands entered by the user.
   * @throws IOException if the save method cannot be transmitted.
   * @throws IllegalArgumentException if there are not sufficient arguments
   *                                  to perform the command.
   */
  @Override
  public void doCommand(List<String> commands) throws IOException, IllegalArgumentException {
    // need at least 3 args
    if (3 > commands.size()) {
      throw new IllegalArgumentException("Save command must include: a path name, " +
              "an ImageName, and a file format. Please try again.");
    }

    // first arg is the file path
    String filePath = commands.get(0);
    // second arg is the image-to-be-saved's name
    String imageName = commands.get(1);
    // third arg is the type of file (ppm, jpg, etc)
    String fileFormat = commands.get(2);
    IImage image = this.model.getImg(imageName);

    // based on the file type...
    switch (fileFormat) {
      case "bmp" :
        ImageUtil.writeImage(image, filePath);
        break;
      // if ppm,
      case "ppm":
        ImageUtil.writeImage(image, filePath);
        break;
      // if jpg,
      case "jpg":
        ImageUtil.writeImage(image, filePath);
        break;
      case "png" :
        ImageUtil.writeImage(image, filePath);
        break;
      // file format not found
      default:
        throw new IllegalArgumentException("File format not recognized. Please try again.");
    }

    this.view.renderMessage("Success! Image was saved as a "
            + fileFormat.toUpperCase()
            + " image called " + "'"
            + imageName + "'"
            + " to " + "'"
            + filePath + "'");
  }
}