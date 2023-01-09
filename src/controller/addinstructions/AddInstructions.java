package controller.addinstructions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import controller.commands.effects.ExposureC;
import controller.commands.effects.IRunCommand;
import controller.commands.effects.FilterC;
import controller.commands.effects.Load;
import controller.commands.effects.Save;
import model.IModel;
import model.filter.ifilters.AlterColorF;
import model.filter.ifilters.Kernel;
import model.filter.newfilters.NewEffectsF;
import model.filter.oldfilters.FlipHorF;
import model.filter.oldfilters.IntensityF;
import model.filter.oldfilters.LumaF;
import model.filter.oldfilters.ValueF;
import model.filter.ifilters.ColorF;
import model.filter.oldfilters.FlipVerF;
import view.IView;

/**
 * Represents a class that adds all of the given
 * commands so that they can be executed.
 */
public class AddInstructions implements IAddInstructions {

  // variables

  //view
  private final IView view;
  // model
  private final IModel model;
  // puts the string commands (user-friendly) into
  // a hashmap that converts them to IRunCommands (computer-friendly)
  private final Map<String, IRunCommand> logOfAllInstructions;

  // constructor

  /**
   * Represents a version of AddInstructions that takes in a model
   * and a view, making a HashMap of the list of commands
   * initializes the model and view, and validates the provided commands.
   *
   * @param model represents the IModel used.
   * @param view  represents the IView used.
   * @throws IllegalArgumentException if any arguments are null.
   */
  public AddInstructions(IModel model, IView view) throws IllegalArgumentException {
    addInstructionsConstructorHelper(model, view);

    // initialize
    this.model = model;
    this.view = view;
    // new HashMap
    logOfAllInstructions = new HashMap<>();
    // add 'em in
    loadInAllCommands();
  }

  // methods

  /**
   * Helper to check for nullness in the constructor.
   * @param model represents the IModel used.
   * @param view  represents the IView used.
   */
  private void addInstructionsConstructorHelper(IModel model, IView view) {
    // null view
    if (view == null) {
      throw new IllegalArgumentException("View need not be null.");
    }
    // null model
    if (model == null) {
      throw new IllegalArgumentException("Model need not be null.");
    }
  }

  /**
   * Represents a method that loads in all commands -->
   * old and new.
   */
  private void loadInAllCommands() {
    addInOldHashmapInstructions();
    addInNewHashmapInstructions();
  }

  /**
   * Represents a method that takes in a string and an IRunCommand
   * and puts that HashMap of commands into the log that stores all commands.
   * This will allow for users to have access to each feature in
   * the map.
   */
  private void addInOldHashmapInstructions() {
    // old commands
    this.logOfAllInstructions.put("blue-greyscale", new FilterC(model, view,
            new ColorF(ColorF.BLUE_GREYSCALE)));
    this.logOfAllInstructions.put("brighten", new ExposureC(model, view));
    this.logOfAllInstructions.put("flip-horizontal", new FilterC(model, view, new FlipHorF()));
    this.logOfAllInstructions.put("flip-vertical", new FilterC(model, view, new FlipVerF()));
    this.logOfAllInstructions.put("green-greyscale", new FilterC(model, view,
            new ColorF(ColorF.GREEN_GREYSCALE)));
    this.logOfAllInstructions.put("intensity", new FilterC(model, view, new IntensityF()));
    this.logOfAllInstructions.put("load", new Load(model, view));
    this.logOfAllInstructions.put("luma", new FilterC(model, view, new LumaF()));
    this.logOfAllInstructions.put("red-greyscale", new FilterC(model, view,
            new ColorF(ColorF.RED_GREYSCALE)));
    this.logOfAllInstructions.put("save", new Save(model, view));
    this.logOfAllInstructions.put("value", new FilterC(model, view, new ValueF()));
  }

  /**
   * Represents a method that takes in a string and an IRunCommand
   * and puts that HashMap of commands into the log that stores all commands.
   * This will allow for users to have access to each feature in
   * the map.
   */
  private void addInNewHashmapInstructions() {
    // new commands
    this.logOfAllInstructions.put("blur", new FilterC(model, view,
            new Kernel(NewEffectsF.BLUR)));
    this.logOfAllInstructions.put("greyscale",
            new FilterC(model, view,
                    new AlterColorF(NewEffectsF.GREYSCALE)));
    this.logOfAllInstructions.put("sharpen",
            new FilterC(model, view, new Kernel(NewEffectsF.SHARPEN)));
    this.logOfAllInstructions.put("sepia",
            new FilterC(model, view, new AlterColorF(NewEffectsF.SEPIA)));
  }

  @Override
  public void doCommand(List<String> enteredInstructions) throws IOException,
          IllegalArgumentException {

    doCommandsHelper(enteredInstructions);
    // get first elem
    String command = enteredInstructions.get(0);
    List<String> restricted = enteredInstructions.subList(1, enteredInstructions.size());

    // if 'help' is entered, return as is to display
    if ("help".equalsIgnoreCase(command)) {
      return;
    }
    // if the entered command is gibberish, mistyped, etc, return that
    // the user must enter a valid starting command
    if (!this.logOfAllInstructions.containsKey(command)) {
      throw new IllegalArgumentException("Unrecognizable instruction given. Please try again.");
    }

    this.logOfAllInstructions.get(command).doCommand(restricted);
  }

  /**
   * Helper that checks if the inputs in the console
   * are sufficient in length.
   *
   * @param enteredInstructions represents the instructions
   *                            entered by the user.
   */
  private void doCommandsHelper(List<String> enteredInstructions) {
    // null instructions
    if (enteredInstructions == null) {
      throw new IllegalArgumentException("Arguments need not be null.");
    }
    // not enough inputs
    if (enteredInstructions.size() < 1) {
      throw new IllegalArgumentException("More arguments needed. Please try again.");
    }
  }
}