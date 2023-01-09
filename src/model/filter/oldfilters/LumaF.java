package model.filter.oldfilters;

import imagetypes.IImage;

/**
 * Represents the luma filter, which represents
 * the weighted sum of the three components for each pixel.
 * Assigns this weighted sum value to each of the three pixels :)
 */
public class LumaF extends APixelF {

  /**
   * Represents a method that applies the luma filter to the given
   * image by altering its pixels. Finds the weighted sum of the pixels
   * and sets each pixel value to that weighted sum.
   * @param input the current image being modified.
   * @param pixel the current pixel used to calculate the new channel value.
   * @return the new int array.
   */
  @Override
  protected int[] filteredPixel(IImage input, int[] pixel) {
    // find weighted sum
    int lumaVal = (int)((0.2126 * pixel[0]) + (0.7152 * pixel[1]) + (0.0722 * pixel[2]));

    // assign to all pixels
    return new int[]{lumaVal, lumaVal, lumaVal};
  }

}


