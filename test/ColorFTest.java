import org.junit.Test;

import model.filter.ifilters.ColorF;
import model.filter.ifilters.IFilter;

import static org.junit.Assert.assertEquals;

/**
 * Represents a testing class for colored images.
 */
public class ColorFTest {

  IFilter filter = new ColorF(2);

  @Test
  public void testConstructors() {
    try {
      filter = new ColorF(3);
    } catch (IndexOutOfBoundsException e) {
      assertEquals("Invalid entry. Please enter a number between 0 and 2.", e.getMessage());
    }
    try {
      filter = new ColorF(-1);
    } catch (IndexOutOfBoundsException e) {
      assertEquals("Invalid entry. Please enter a number between 0 and 2.", e.getMessage());
    }
  }
}
