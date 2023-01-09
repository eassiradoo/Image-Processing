import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.addinstructions.AddInstructions;
import controller.addinstructions.IAddInstructions;
import model.IModel;
import model.Model;
import view.IView;
import view.Views;

import static org.junit.Assert.assertEquals;

/**
 * Represents a testing class for the AddCommands class.
 */
public class AddInstructionsTest {
  Appendable appendable = new StringBuilder();
  IModel model = new Model();
  IView view = new Views(appendable);

  IAddInstructions addCommands = new AddInstructions(model, view);

  @Test
  public void testConstructors() {
    try {
      addCommands = new AddInstructions(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("View need not be null.", e.getMessage());
    }
    try {
      addCommands = new AddInstructions(model, null);
    } catch (IllegalArgumentException e) {
      assertEquals("View need not be null.", e.getMessage());
    }
    try {
      addCommands = new AddInstructions(null, view);
    } catch (IllegalArgumentException e) {
      assertEquals("Model need not be null.", e.getMessage());
    }
  }

  @Test
  public void testPerform() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add(null);
    try {
      addCommands.doCommand(commands);
    } catch (IllegalArgumentException e) {
      assertEquals("Unrecognizable instruction given. Please try again.", e.getMessage());
    }
    commands.remove(null);
    commands.add("blur");
    try {
      addCommands.doCommand(commands);
    } catch (IllegalArgumentException e) {
      assertEquals("Please provide two arguments: the old image's " +
              "name and the new image's name.", e.getMessage());
    }
    commands.add("image");
    commands.add("image2");
    System.out.println(commands);
    System.out.println(addCommands);
    //addCommands.doCommand(commands);
  }
}

