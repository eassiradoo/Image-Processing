import org.junit.Test;

import imagetypes.ColorImage;
import imagetypes.IImage;
import model.IModel;
import model.Model;
import model.filter.oldfilters.ExposureF;

import static org.junit.Assert.assertEquals;

/**
 * Represents a testing class for the model.
 */
public class ModelTest {

  private IModel model = new Model();
  private int[] imgArray = new int[]{100, 50, 45, 33, 67, 293, 87, 98, 122};
  private IImage image = new ColorImage(3, 1, imgArray);

  @Test
  public void testAddFilterIllegal() {
    try {
      model.addFilter(null, null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Filter need not be null.", e.getMessage());
    }
    try {
      model.addFilter(new ExposureF(10), null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Old image's name need not be null.", e.getMessage());
    }
    try {
      model.addFilter(new ExposureF(10), "cat", null);
    } catch (IllegalArgumentException e) {
      assertEquals("New image's name need not be null.", e.getMessage());
    }
  }

  @Test
  public void testTakeInAndGetImg() {
    model.takeInImage(this.image, "image1");
    assertEquals(this.image, this.model.getImg("image1"));
  }
}
