package model.filter.oldfilters;

import imagetypes.IImage;

/**
 * Represents the class holding the brighten filter.
 * extends abstract class PixelF.
 */
public class ExposureF extends APixelF {

  // variables
  int exposureAmount;

  // constructor

  /**
   * First constructor, takes in 1 parameter (constant).
   *
   * @param exposureAmount the amount to brighten an image by.
   */
  public ExposureF(int exposureAmount) {
    this.exposureAmount = exposureAmount;
  }

  // methods

  /**
   * Represents a method that brightens an image by the amount
   * provided i.e exposure amount. RGB values must be in the
   * inclusive range of 0-255.
   *
   * @param input represents the provided IImage.
   * @param pixel represents an array of pixels.
   * @return the new pixel.
   */
  @Override
  protected int[] filteredPixel(IImage input, int[] pixel) {
    int[] newPixel = new int[3];
    // iterate through until you reach pixel length --> end
    for (int channelIndex = 0; channelIndex < pixel.length; channelIndex++) {

      // if going to exceed 255, pull back down to 255
      if (pixel[channelIndex] + this.exposureAmount > 255) {
        newPixel[channelIndex] = 255;
        // if going to be negative, pull up to 0
      } else {
        newPixel[channelIndex] = Math.max(pixel[channelIndex] + this.exposureAmount, 0);
      }
    }

    return newPixel;
  }
}

