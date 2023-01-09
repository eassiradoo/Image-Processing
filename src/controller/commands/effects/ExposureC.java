package controller.commands.effects;

import java.io.IOException;
import java.util.List;
import model.IModel;
import model.filter.oldfilters.ExposureF;
import view.IView;

/**
 * Represents the exposure effect command that can either
 * brighten or darken an image based on
 * the user's input.
 */
public class ExposureC implements IRunCommand {

  // variables
  private IModel model;


  // constructor

  /**
   * Represents a brightening command that takes in
   * a model and a view.
   *
   * @param model represents an IModel model.
   * @param view  represents an IView view.
   * @throws IllegalArgumentException if either the model or view are null.
   */
  public ExposureC(IModel model, IView view) throws IllegalArgumentException {
    // view is null
    if (view == null) {
      throw new IllegalArgumentException("View need not be null");
    }
    // model is null
    if (model == null) {
      throw new IllegalArgumentException("Model need not be null");
    }

    this.model = model;
  }


  // methods

  /**
   * Represents a method that takes in a list of string commands,
   * allowing for the image to take in a new identifier so that it can
   * be appropriately referred to.
   *
   * @param commands what the user enters --> commands to execute the brightening
   *                 filter --> should include the keyword 'brighten,' the amount to
   *                 be brightened by, the old image's name and the new image's name.
   * @throws IOException              if execution cannot be transmitted.
   * @throws IllegalArgumentException if not enough arguments are provided
   *                                  to perform a brightening filter.
   */
  @Override
  public void doCommand(List<String> commands) throws IOException, IllegalArgumentException {
    // NEED at least 3 args after 'brighten'
    if (commands.size() < 3) {
      // if args aren't sufficient in length,
      throw new IllegalArgumentException("Brighten command must include: a value to be "
              + "brightened by,"
              + "an oldImageName and a newImageName. Please try again.");
    }

    int exposureAmount;
    try {
      // checks that the next input after commands.get(0), which is 'brighten,'
      // is a number --> amount to be brightened by
      exposureAmount = Integer.parseInt(commands.get(0));
      //if not, it's not a number, throw
    } catch (NumberFormatException exception) {
      throw new IllegalArgumentException("Must brighten image by an integer. Please try again.");
    }

    // second entry should be the old image's name
    String oldImageName = commands.get(1);
    // third entry should be the new image's name
    String newImageName = commands.get(2);

    // to the model, add a new brightenFilter by the given amount on the old to produce the new
    this.model.addFilter(new ExposureF(exposureAmount), oldImageName, newImageName);

  }
}
