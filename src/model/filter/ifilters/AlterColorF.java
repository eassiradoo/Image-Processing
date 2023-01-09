package model.filter.ifilters;

import imagetypes.IImage;
import model.filter.oldfilters.APixelF;

/**
 * Represents the alter color filter class.
 * extends abstract class PixelF.
 */
public class AlterColorF extends APixelF {

  // variables
  public double[][] kernel;

  // constructor

  /**
   * First constructor, takes in 1 parameter (kernel).
   * @param kernel the 2d double array of kernels.
   */
  public AlterColorF(double[][] kernel) {
    alterColorFHelper(kernel);
    // iterate through
    for (int row = 0; row < kernel.length; row++) {
      if (kernel[row] == null) {
        throw new IllegalArgumentException("Kernel row need not be null.");
      }
    }

    this.kernel = kernel;
  }

  // methods

  private void alterColorFHelper(double[][] kernel) {
    // null kernel
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel need not be null.");
    }
    // invalid kernel size --> even or kernel length < 3
    if ((kernel.length % 2 == 0) || (3 > kernel.length)) {
      throw new IllegalArgumentException("Invalid kernel. Please try again.");
    }
  }

  @Override
  protected int[] filteredPixel(IImage input, int[] pixel) {
    int red = filteredRed(input, pixel);
    int green = filteredGreen(input, pixel);
    int blue = filteredBlue(input, pixel);
    return new int[] {red, green, blue};
  }

  private int filteredRed(IImage input, int[] pixel) {
    return Math.min(255, (int) (Math.round(this.kernel[0][0] * pixel[0])
            + (this.kernel[0][1] * pixel[1])
            + (this.kernel[0][2] * pixel[2])));
  }

  private int filteredGreen(IImage input, int[] pixel) {
    return Math.min(255, (int) (Math.round(this.kernel[1][0] * pixel[0])
            + (this.kernel[1][1] * pixel[1])
            + (this.kernel[1][2] * pixel[2])));
  }

  private int filteredBlue(IImage input, int[] pixel) {
    return Math.min(255, (int) (Math.round(this.kernel[2][0] * pixel[0])
            + (this.kernel[2][1] * pixel[1])
            + (this.kernel[2][2] * pixel[2])));
  }
}
