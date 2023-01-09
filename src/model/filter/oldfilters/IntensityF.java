package model.filter.oldfilters;

import imagetypes.IImage;

/**
 * Represents the intensity filter, which represents
 * the average of the three components for each pixel.
 * Assigns this average value to each of the three pixels :)
 */
public class IntensityF extends APixelF {

  /**
   * Every channel of the newPixel is equal to the average of the given Pixel's channels.
   *
   * @param input the current image being modified.
   * @param pixel the current pixel used to calculate the new channel value.
   * @return the new value of every channel in the given pixel.
   */
  @Override
  protected int[] filteredPixel(IImage input, int[] pixel) {
    // find the avg color component of the three pixels
    int avg = (pixel[0] + pixel[1] + pixel[2]) / 3;
    // assign to all pixels
    return new int[]{avg, avg, avg};
  }
}
