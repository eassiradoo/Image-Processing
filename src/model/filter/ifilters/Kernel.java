package model.filter.ifilters;

import imagetypes.IImage;
import imagetypes.ColorImage;

/**
 * Represents a kernel, which can be used to
 * apply filters to images. Implements IFilter and
 * its corresponding method alter.
 */
public class Kernel implements IFilter {

  // variables

  // 2D array of doubles --> kernel
  private double[][] kernel;

  // constructor

  /**
   * Represents a kernel that takes in a 2D array of
   * doubles and initializes if valid.
   *
   * @param kernel represents a 2D array of doubles.
   * @throws IllegalArgumentException if the kernel is null.
   */
  public Kernel(double[][] kernel) throws IllegalArgumentException {
    kernelHelper(kernel);
    // iteratre through until you reach the end (kernel length)
    for (int i = 0; i < kernel.length; i++) {
      // kernel row [i] row, [j] column
      if (kernel[i] == null) {
        throw new IllegalArgumentException("Kernel row need not be null.");
      }
    }
    // initialize
    this.kernel = kernel;
  }

  // methods

  private void kernelHelper(double[][] kernel) throws IllegalArgumentException {
    // null kernel
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel need not be null.");
    }
    // invalid kernel size --> even or kernel length < 3
    if ((kernel.length % 2 == 0) || (3 > kernel.length)) {
      throw new IllegalArgumentException("Invalid kernel. Please try again.");
    }
  }

  /**
   * Represents an array of integers. Takes in an IImage and
   * a row and column. Returns a 2D array (takes img's pixels) and
   * assigns them the new row + column.
   * @param image represents an IImage.
   * @param row represents a row.
   * @param column represents a column.
   * @return a 2D array with new row + col values.
   */
  private int[] assignNewPixels(IImage image, int row, int column) {
    // invalid row or column
    if (column < 0 || row < 0 || column >= image.width() || row >= image.height()) {
      // set all to 0
      return new int[]{0, 0, 0};
    }

    return image.retrievePixelPosn(row, column);
  }

  @Override
  public IImage alter(IImage input) {
    int[] intArray = new int[input.imgTotalSize()];
    for (int i = 0; i < input.imgTotalSize(); i = i + 3) {

      // start off with 0
      int updatedRed = 0;
      int updatedGreen = 0;
      int updatedBlue = 0;

      // for each row and column,
      for (int row = 0; row < kernel.length; row++) {
        for (int col = 0; col < kernel[0].length; col++) {
          int[] pixArray = input.pixelPosn(i / 3);
          int middle = kernel.length / 2;

          int newRow = pixArray[0] + row - middle;
          int newCol = pixArray[1] + col - middle;

          int[] array = assignNewPixels(input, newRow, newCol);
          // gets row + col value
          double val = kernel[row][col];

          // new RGB values
          updatedRed += Math.round(array[0] * val);
          updatedGreen += Math.round(array[1] * val);
          updatedBlue += Math.round(array[2] * val);
        }
      }
      // if greater than 255, gets 255
      intArray[i] = Math.min(255, updatedRed);
      intArray[i + 1] = Math.min(255, updatedGreen);
      intArray[i + 2] = Math.min(255, updatedBlue);
    }

    // return new ColorImg
    return new ColorImage(input.width(), input.height(), intArray);
  }
}


