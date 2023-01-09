package model.filter.oldfilters;

import imagetypes.IImage;

/**
 * Represents the value filter, which represents
 * the maximum value of the three components for each pixel.
 * Assigns this maximum value to each of the three pixels :)
 */
public class ValueF extends APixelF {


  /**
   * Finds the maximum value of the RGB--> whichever value is
   * greatest, that value will be assiged to all three color channels.
   * @param input the current image being modified.
   * @param pixel the current pixel used to calculate the new channel value.
   * @return the new int[].
   */
  @Override
  protected int[] filteredPixel(IImage input, int[] pixel) {
    // max of the first two --> R and G
    int max = Math.max(pixel[0], pixel[1]);
    // max of the second two --> G and B
    int realMax = Math.max(max, pixel[2]);

    // return the max of RGB for all values!
    return new int[]{realMax, realMax, realMax};
  }
}
