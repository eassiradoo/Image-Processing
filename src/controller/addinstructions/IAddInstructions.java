package controller.addinstructions;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Represents an interface that allows for
 * commands to be implemented.
 */
public interface IAddInstructions {

  /**
   * Represents a method that takes in the given commands and
   * performs them yay.
   * @param instructions represents the list of instructions given.
   * @throws NoSuchElementException if the given instruction cannot be located.
   * @throws IOException if execution cannot be transmitted.
   * @throws IllegalArgumentException if the list of instructions is null.
   */
  void doCommand(List<String> instructions) throws NoSuchElementException, IOException,
          IllegalArgumentException;
}
