package model.filter.oldfilters;

import imagetypes.ColorImage;
import imagetypes.IImage;
import model.filter.ifilters.IFilter;

/**
 * Represents an abstract class that contains pixel
 * filters.
 */
public abstract class APixelF implements IFilter {

  /**
   * Represents a method that takes in an IImage and an array of
   * pixels and makes a new pixel as a result of whatever filter
   * was applied.
   *
   * @param image represents the provided IImage
   * @param pixel represnts an array of pixels
   * @return a new array of pixels :))
   */
  protected abstract int[] filteredPixel(IImage image, int[] pixel);

  /**
   * Represents a function that takes in an IImage and performs
   * a given filter on it.
   *
   * @param image represents the inputted image.
   * @return an IImage after it has been effected.
   */
  @Override
  public IImage alter(IImage image) {
    // new array taking in total size
    int[] intArray = new int[image.imgTotalSize()];

    // for each row and column, increment
    for (int row = 0; row < image.height(); row++) {
      for (int col = 0; col < image.width(); col++) {

        // get pixel posns given row + column
        int[] pixel = image.retrievePixelPosn(row, col);
        int firstIndex = (row * image.width() + col) * 3;

        for (int pixelNum = 0; pixelNum < pixel.length; pixelNum++) {
          intArray[firstIndex + pixelNum] = this.filteredPixel(image, pixel)[pixelNum];
        }
      }
    }
    // return a new image woohoo
    return new ColorImage(image.width(), image.height(), intArray);
  }
}

