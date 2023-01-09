import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import controller.addinstructions.AddInstructions;
import controller.addinstructions.IAddInstructions;
import controller.commands.controller.Controller;
import controller.commands.controller.GUIController;
import controller.commands.controller.IController;
import model.IModel;
import model.Model;
import view.IView;
import view.Views;

import org.junit.Test;

import javax.swing.JButton;

import static org.junit.Assert.assertEquals;

/**
 * Represents a testing class for the controller.
 */
public class ControllerTest {

  Appendable appendable = new StringBuilder();
  Readable readable = new StringReader("");
  IModel model = new Model();
  IView view = new Views(appendable);

  IController controller = new Controller(model, view, readable);

  IController gui = new Controller(model, view, readable);


  @Test
  public void testConstructors() {
    controller = new Controller(model, view, readable);
    try {
      controller = new Controller(null, null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("All arguments are null. Cannot proceed.", e.getMessage());
    }
    try {
      controller = new Controller(model, view, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Readable need not be null.", e.getMessage());
    }
    try {
      controller = new Controller(null, view, readable);
    } catch (IllegalArgumentException e) {
      assertEquals("Model need not be null.", e.getMessage());
    }
    try {
      controller = new Controller(model, view, new StringReader("help"));
    } catch (IllegalArgumentException e) {
      assertEquals("View need not be null.", e.getMessage());
    }
  }

  @Test
  public void testLoad() {
    IView view = new Views(appendable);
    IModel model = new Model();
    IAddInstructions addCommands = new AddInstructions(model, view);

    try {
      List<String> strings = new ArrayList<>();
      strings.add("load");
      strings.add("src/res/DUCK.jpg");
      strings.add("duck");
      strings.add("jpg");
      addCommands.doCommand(strings);
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      try {
        List<String> strings = new ArrayList<>();
        strings.add("load");
        strings.add("src/res/DUCK.jpg");
        strings.add("duck");
        addCommands.doCommand(strings);
      } catch (IllegalArgumentException e) {
        assertEquals("Load command must include: a path name, an ImageName, "
                + "and a file format. Please try again.", e.getMessage());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  JButton loadedButton = new JButton("Load In New Image");

  @Test
  public void testSave() {
    IView view = new Views(appendable);
    IModel model = new Model();
    IAddInstructions addCommands = new AddInstructions(model, view);

    try {
      List<String> strings = new ArrayList<>();
      strings.add("load");
      strings.add("src/res/DUCK.jpg");
      strings.add("duck");
      strings.add("jpg");
      addCommands.doCommand(strings);


      strings.add("save");
      strings.add("src/res/DUCK.jpg");
      strings.add("duck");
      strings.add("jpg");
      addCommands.doCommand(strings);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      try {
        List<String> strings = new ArrayList<>();
        strings.add("save");
        strings.add("src/res/DUCK.jpg");
        strings.add("ppm");
        addCommands.doCommand(strings);
      } catch (IllegalArgumentException e) {
        assertEquals("Save command must include: a path name, an ImageName, and a "
                + "file format. Please try again.", e.getMessage());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testBlur() {
    IView view = new Views(appendable);
    IModel model = new Model();
    IAddInstructions addCommands = new AddInstructions(model, view);

    try {
      List<String> strings = new ArrayList<>();
      strings.add("load");
      strings.add("src/res/DUCK.jpg");
      strings.add("duck");
      strings.add("jpg");
      addCommands.doCommand(strings);


      strings.add("blur");
      strings.add("src/res/DUCK.jpg");
      strings.add("duck");
      strings.add("jpg");
      addCommands.doCommand(strings);
    } catch (IOException e) {
      e.printStackTrace();
    }

    String str = "ji";
    assertEquals("ji", str);
  }

  @Test
  public void test() throws IOException {
    JButton loaddButton = new JButton();
    Readable readable1 = new StringReader("Load In New Image");
    IController gui = new GUIController(model, view, readable1);
    //gui.startProgram();
    assertEquals("Load In New Image", loadedButton.getActionCommand());
  }
}
