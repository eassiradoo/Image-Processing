import org.junit.Before;
import org.junit.Test;
import controller.commands.controller.GUIController;
import controller.commands.controller.IController;
import model.IModel;
import model.Model;
import view.IView;
import view.Views;

import static org.junit.Assert.assertEquals;

/**
 * Represents a testing class for the GUI controller.
 */
public class GUIControllerTest {

  private IModel model = new Model();
  private IView view = new Views();

  private IController guiController = new GUIController(model, view);


  @Before
  public void init() {
    model = new Model();
    view = new Views();
    guiController = new GUIController(model, view);
  }

  @Test
  public void testConstructors() {
    try {
      guiController = new GUIController((IModel) null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Model need not be null.", e.getMessage());
    }

    try {
      guiController = new GUIController((IView) null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("View need not be null.", e.getMessage());
    }

    try {
      guiController = new GUIController(null, view);
    } catch (IllegalArgumentException e) {
      assertEquals("Model need not be null.", e.getMessage());
    }

    try {
      guiController = new GUIController(model, null);
    } catch (IllegalArgumentException e) {
      assertEquals("View need not be null.", e.getMessage());
    }
  }

}
