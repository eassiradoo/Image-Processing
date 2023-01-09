package controller.commands.effects;

import java.io.IOException;
import java.util.List;

/**
 * Represents an interface that allows for commands to
 * be run. Contains a mandatory execute method
 * to execute the given command.
 */
public interface IRunCommand {

  /**
   * Represents a method that takes in a list of
   * commands and executes accordingly according to
   * the needed implementation.
   *
   * @param commands represents the given arguments.
   * @throws IOException              if execution cannot be transmitted.
   * @throws IllegalArgumentException if the list of commands is null.
   */
  void doCommand(List<String> commands) throws IOException, IllegalArgumentException;
}
