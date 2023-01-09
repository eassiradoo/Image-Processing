import org.junit.Test;

import model.filter.ifilters.AlterColorF;
import model.filter.ifilters.IFilter;
import model.filter.ifilters.Kernel;
import model.filter.oldfilters.APixelF;

import static org.junit.Assert.assertEquals;

/**
 * Represents a testing class for colored images.
 */
public class AlterColorTest {
  double[][] kernel = new double[3][3];
  APixelF kernelArray = new AlterColorF(kernel);

  @Test
  public void testConstructor() {
    try {
      kernelArray = new AlterColorF(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Kernel need not be null.", e.getMessage());
    }

    try {
      kernelArray = new AlterColorF(new double[0][0]);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid kernel. Please try again.", e.getMessage());
    }
  }


  @Test
  public void testConstructors() {
    double[][] kernelArray = new double[7][3];
    IFilter kernel2 = new Kernel(kernelArray);
    try {
      kernel2 = new Kernel(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Kernel need not be null.", e.getMessage());
    }
  }
}

