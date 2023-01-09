import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import controller.commands.controller.Controller;
import controller.commands.controller.IController;
import model.IModel;
import model.Model;
import view.IView;
import view.Views;

/**
 * Represents a final class with one method,
 * main, that will run the image processing system yay.
 */
public final class Main {

  /**
   * Starts the program --> main method to begin running the
   * image processor.
   *
   * @param args represents arguments main takes in to
   *             begin running according to said arguments.
   *             Can specify what the image processor will do.
   */
  public static void main(String[] args) {

    if (args[0].equalsIgnoreCase("-interactive")) {
      Views.setDefaultLookAndFeelDecorated(false);
      Views frame = new Views();

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
    } else if (args[0].equalsIgnoreCase("-script")) {
      // model
      IModel model = new Model();
      // view default to console
      IView view = new Views(System.out);
      // readable to read user inputs in system.in
      Readable readable = new InputStreamReader(System.in);

      // controller that takes in MVC
      IController control = new Controller(model, view, readable);


      // try to start the program,
      try {
        // begin image processor
        control.startProgram();
        // if cannot start, throw an IOException
      } catch (IOException e) {
        // get the stack trace in this instance
        e.getStackTrace();
      }
    } else {
      throw new IllegalArgumentException("Invalid command line entry. Please try again.");
    }
  }
}


