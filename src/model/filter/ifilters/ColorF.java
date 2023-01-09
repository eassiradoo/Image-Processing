package model.filter.ifilters;

import imagetypes.IImage;
import imagetypes.ColorImage;

/**
 * Represents the color filter class.
 * Implements the IFilter interface.
 */
public class ColorF implements IFilter {

  // variables
  public static final int RED_GREYSCALE = 0;
  public static final int GREEN_GREYSCALE = 1;
  public static final int BLUE_GREYSCALE = 2;

  // RGB --> 0, 1, 2, respectively (channels)
  private int index;

  // constructor

  /**
   * Represents a version of a color filter that takes in
   * an index that will represent a pixel's color.
   * @param index represents a pixel's color.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  public ColorF(int index) throws IndexOutOfBoundsException {
    // invalid indices --> should be [0], [1], or [2]
    if (index > 2 || index < 0) {
      throw new IndexOutOfBoundsException("Invalid entry. Please enter a number between 0 and 2.");
    }
    // if valid, init
    this.index = index;
  }

  @Override
  public IImage alter(IImage input) {
    int[] intArray = new int[input.imgTotalSize()];

    for (int i = 0; i < input.imgTotalSize() - 3; i += 3) {
      int column = (i / 3) % input.width();
      int row = i / (input.width() * 3);

      int color = input.retrievePixelPosn(row, column)[this.index];

      // RBG

      // red
      intArray[i] = color;
      // green
      intArray[i + 1] = color;
      // blue
      intArray[i + 2] = color;
    }

    // return new ColorImage
    return new ColorImage(input.width(), input.height(), intArray);
  }
}

