package controller.commands.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import controller.addinstructions.IAddInstructions;
import controller.addinstructions.AddInstructions;
import model.IModel;
import model.Model;
import view.IView;

/**
 * Represents a controller for the MVC that
 * communicates with the model and view
 * to parse the Image Processing system.
 */
public class Controller implements IController {

  //variables


  //view
  private  IView view;

  //scanner
  private  Scanner scan;

  //IAddCommand that represents commands
  private  IAddInstructions allCommands;

  //has the program started yet?
  private boolean programInProgress;

  /**
   * Represents a version of the controller that takes in
   * a model, view, and readable and initializes
   * them appropriately.
   *
   * @param model    represents the IModel used.
   * @param view     represents the IView used.
   * @param readable represents a readable to take in
   *                 user input.
   * @throws IllegalArgumentException if any arguments are null.
   */
  public Controller(IModel model, IView view, Readable readable)
          throws IllegalArgumentException {
    //all args are null
    if (model == null && view == null && readable == null) {
      throw new IllegalArgumentException("All arguments are null. Cannot proceed.");
    }
    //model is null
    if (model == null) {
      throw new IllegalArgumentException("Model need not be null.");
    }

    //view is null
    if (view == null) {
      throw new IllegalArgumentException("View need not be null.");
    }

    //readable is null
    if (readable == null) {
      throw new IllegalArgumentException("Readable need not be null.");
    }

    //initialize

    //program has yet to be started
    programInProgress = false;
    model = new Model();
    this.view = view;
    //scanner takes in readable
    scan = new Scanner(readable);
    allCommands = new AddInstructions(model, view);
  }



  @Override
  public void startProgram() {


    startProgramBeginning();


    //while the program is in progress...
    while (programInProgress) {
      List<String> commandArgs = requestInstruction();

      //if nothing is entered,
      if (commandArgs.size() < 1) {
        //proceed as normal
        continue;
      }


      if (commandArgs.get(0).equalsIgnoreCase("help")) {
        helpOption();
      }


      //if 'q' or 'quit' in any case is entered, quit the Image Processing System
      if (commandArgs.get(0).equalsIgnoreCase("q")
              || commandArgs.get(0).equalsIgnoreCase("quit")) {
        //program is no longer in progress --> has been quit
        programInProgress = false;
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



  private void startProgramBeginning() {
    //program has now started
    programInProgress = true;
    //renders the starting message, introducing users
    try {
      view.startingMessage();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  /**
   * Represents a method that allows for the displaying of the
   * 'help' option for users to see what commands they can enter.
   */
  private void helpOption() {
    try {
      this.view.renderMessage("List of Commands:\n"
              + "load -->                  loads an image into the program\n"
              + "save -->                  saves an image to the program\n"
              + "brighten -->              brightens an image when given a positive input,"
              + "darkens an image when given a negative input\n"
              + "flip-horizontal -->        flips an image horizontally\n"
              + "flip-vertical -->          flips an image vertically\n"
              + "red-greyscale -->         alters the image to the red value in"
              + "every RGB channel\n"
              + "green-greyscale -->       alters the image to the green value in"
              + "every RGB channel\n"
              + "blue-greyscale -->        alters the image to the blue value in"
              + "every RGB channel\n"
              + "luma -->                  alters the image by adding the luma filter to it\n"
              + "value -->                 alters the image by adding the avg."
              + "RGB in every channel\n"
              + "intensity -->  alters the image by taking the highest RGB value\n"
              + "blur -->                  alters the image by applying the blur filter to it\n"
              + "sharpen -->               alters the image by applying the sharpen filter to it\n"
              + "greyscale -->             alters the image by applying the greyscale filter" +
              " to it\n"
              + "sepia -->                 alters the image by applying the sepia filter to it\n"
              + "help -->                  lists the possible commands the user can input\n"
              + "quit or q -->             quits the program\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }




  /**
   * Represents a method that takes in a command and
   * outputs a list of commands.
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
}

