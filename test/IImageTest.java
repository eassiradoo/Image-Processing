import org.junit.Before;
import org.junit.Test;

import imagetypes.ColorImage;
import imagetypes.IImage;

import static org.junit.Assert.assertEquals;

/**
 * Represents a testing class for images.
 */
public class IImageTest {

  private IImage image;
  private IImage smallerImage;

  @Before
  public void init() {
    int[] pixelArray;
    int[] smallerPixelArray;
    pixelArray = new int[]{1, 16, 19, 22, 67, 255, 188, 76, 91, 22, 186, 187, 99, 19, 9, 70, 29,
                           138, 146, 199, 206, 4, 22, 11, 92, 100, 109};
    smallerPixelArray = new int[]{1, 16, 19, 55, 66, 30, 102, 77, 45};


    image = new ColorImage(3, 3, pixelArray);
    smallerImage = new ColorImage(1, 3, smallerPixelArray);
  }

  @Test
  public void testTotalSize() {
    assertEquals(27, this.image.imgTotalSize());
    assertEquals(9, this.smallerImage.imgTotalSize());
  }

  @Test
  public void testRetrievePixelPosn() {
    assertEquals(1, image.retrievePixelPosn(0, 0)[0]);
    assertEquals(16, image.retrievePixelPosn(0, 0)[1]);
  }

  //column + (row * width())
  @Test
  public void testPixelIndex() {
    assertEquals(0, image.pixelIndex(0, 0));
    assertEquals(8, image.pixelIndex(2, 2));
  }


  @Test
  public void testWidth() {
    assertEquals(3, this.image.width());
    assertEquals(1, this.smallerImage.width());
  }

  @Test
  public void testHeight() {
    assertEquals(3, this.image.height());
    assertEquals(3, this.smallerImage.height());
  }
}
