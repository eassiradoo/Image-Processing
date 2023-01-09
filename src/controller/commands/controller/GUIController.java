package controller.commands.controller;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JComboBox;
import controller.addinstructions.AddInstructions;
import controller.addinstructions.IAddInstructions;
import model.IModel;
import model.Model;
import view.IView;

/**
 * represents the controller for the GUI in our program.
 */
public class GUIController extends Component implements IController, ItemListener {


  // variables

  private IView view;


  //scanner
  private Scanner scan;

  //IAddCommand that represents commands
  private IAddInstructions allCommands;

  //has the program started yet?
  private boolean programInProgress;


  // constructor

  /**
   * first constructor, takes in 2 parameters (model, view).
   * only initializes the model and the view.
   *
   * @param model the model.
   * @param view  the view.
   */
  public GUIController(IModel model, IView view) {
    // model null
    if (model == null) {
      throw new IllegalArgumentException("Model need not be null.");
    }
    // view null
    if (view == null) {
      throw new IllegalArgumentException("View need not be null.");
    }
    // MVC variables
    this.view = view;
  }

  /**
   * secondary constructor, takes in 2 parameters (view, model).
   * initializes view, sets boolean programInProgress to false,
   * creates a new model, a new list of string commands, and adds
   * instructions.
   *
   * @param view  the view.
   * @param model the model.
   */
  public GUIController(IView view, IModel model) {
    // view null
    if (view == null) {
      throw new IllegalArgumentException("View need not be null.");
    }
    // model null
    if (model == null) {
      throw new IllegalArgumentException("Model need not be null.");
    }
    programInProgress = false;
    model = new Model();
    this.view = view;
    allCommands = new AddInstructions(model, view);
  }

  public GUIController(IModel model, IView view, Readable readable) {
    //for testing purposes thank you <3
  }


  /**
   * Represents a method that takes in a command and
   * outputs a list of commands.
   *
   * @param command represents an entered command.
   * @return a list of string commands.
   */
  private List<String> iterateThroughArgs(String command) {

    List<String> inputs = new ArrayList<>();
    Readable readable = new StringReader(command);
    Scanner s = new Scanner(readable);


    // while there's another element in the scanner...
    while (s.hasNext()) {
      //assign the next element as nextElement
      String nextElement = s.next();

      //if the next element contains a pound sign
      if (nextElement.contains("#")) {
        String[] pound = nextElement.split("#");

        if (0 < pound.length) {
          inputs.add(pound[0]);
        }
        break;
      }
      inputs.add(nextElement);
    }
    //close scanner
    s.close();
    return inputs;
  }


  /**
   * Represents a method that asks for a commands and
   * outputs a list of the given commands.
   *
   * @return a list of strings as entered by the user.
   */
  private List<String> requestInstruction() {
    //try parsing arguments
    try {
      String followingLine = scan.nextLine();

      if (followingLine.isBlank()) {
        //new ArrayList<>()
        return new ArrayList<>();
      }

      return iterateThroughArgs(followingLine);

    } catch (NoSuchElementException exception) {

      try {
        view.renderMessage("File appears to have no subsequent line. Please try again.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return new ArrayList<>();
  }


  @Override
  public void itemStateChanged(ItemEvent e) {
    String str = ((JComboBox<Object>) e.getItemSelectable()).getActionCommand();
    List<String> commands = new ArrayList<>();
    commands.add(str);
  }

  @Override
  public void startProgram() {
    programInProgress = true;

    //while the program is in progress...
    while (programInProgress) {
      List<String> commandArgs = requestInstruction();

      //if nothing is entered,
      if (commandArgs.size() < 1) {
        //proceed as normal
        continue;
      }


      try {
        allCommands.doCommand(commandArgs);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    //end the scanner
    scan.close();
  }
}

