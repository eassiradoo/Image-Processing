package controller.commands.effects;

import model.IModel;
import model.filter.ifilters.IFilter;
import java.io.IOException;
import java.util.List;
import view.IView;

/**
 * Represents a filter command class that applies a
 * filter to an image.
 */
public class FilterC implements IRunCommand {

  // variables
  private IModel model;

  // IFilter filter
  protected IFilter filter;

  // constructor

  /**
   * Represents a version of the filter command that
   * takes in a model, a view, and a filter to be applied.
   *
   * @param model  represents an IModel.
   * @param view   represents an IView.
   * @param filter represents an IFilter.
   * @throws IllegalArgumentException if any of the parameters are null.
   */
  public FilterC(IModel model, IView view, IFilter filter)
          throws IllegalArgumentException {
    // view is null
    if (view == null) {
      throw new IllegalArgumentException("View need not be null");
    }
    // model is null
    if (model == null) {
      throw new IllegalArgumentException("Model need not be null");
    }
    // filter is null
    if (filter == null) {
      throw new IllegalArgumentException("Filter need not be null");
    }

    // initialize
    this.model = model;
    this.filter = filter;
  }

  // methods

  /**
   * Represents a method that takes in a list of string commands,
   * allowing for the image to take in a new identifier so that it can
   * be appropriately referred to.
   *
   * @param commands what the user enters --> commands to execute a given filteer -->
   *                 includes the old image's name and the new image's name.
   * @throws IOException              if execution cannot be transmitted.
   * @throws IllegalArgumentException if not enough arguments are provided
   *                                  to perform a filter.
   */
  @Override
  public void doCommand(List<String> commands) throws IOException, IllegalArgumentException {

    // if there aren't enough commands...
    if (commands.size() < 2) {
      throw new IllegalArgumentException("Please provide two arguments: the old image's name and "
              + "the new image's name.");
    }

    // first arg
    String oldName = commands.get(0);
    // second arg
    String newName = commands.get(1);

    this.model.addFilter(filter, oldName, newName);
  }
}
