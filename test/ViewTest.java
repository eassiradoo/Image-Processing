import org.junit.Test;

import java.io.IOException;
import view.IView;
import view.Views;

import static org.junit.Assert.assertEquals;

/**
 * Represents a testing class for the view.
 */
public class ViewTest {

  Appendable appendable = new StringBuilder();
  IView view = new Views(appendable);


  @Test
  public void testConstructors() {
    view = new Views(appendable);
    try {
      view = new Views((Appendable) null);
    } catch (IllegalArgumentException e) {
      assertEquals("Appendable need not be null.", e.getMessage());
    }
  }


  @Test
  public void testRenderMessage() throws IOException {
    Appendable appendable = new StringBuilder();
    IView view = new Views(appendable);
    view.renderMessage("HEYYY ");
    assertEquals("-> HEYYY ", appendable.toString());
    view.renderMessage("lovely OOD TA");
    view.renderMessage("so happy you're here :))");
    assertEquals("-> HEYYY "
            + "-> lovely OOD TA"
            + "-> so happy you're here :))", appendable.toString());
  }

  @Test
  public void testStartMessage() throws IOException {
    Appendable appendable = new StringBuilder();
    IView view = new Views(appendable);
    view.startingMessage();
    assertEquals("Welcome to Avery and Emma's Image Processing System!\n"
            + "Please enter a command to begin.\n", appendable.toString());
  }
}


