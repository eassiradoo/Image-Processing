package model.filter.oldfilters;

import imagetypes.IImage;
import imagetypes.ColorImage;
import model.filter.ifilters.IFilter;

/**
 * Represents the class that allows for horizontally flipping
 * an image.
 * Implements the IFilter interface.
 */
public class FlipHorF implements IFilter {

  /**
   * Applies this filter to the given image and outputs the filtered image.
   *
   * @param image the image to perform this filter function on.
   * @return the output image with the filter applied.
   */
  @Override
  public IImage alter(IImage image) {
    int[] intArray = new int[image.imgTotalSize()];

    for (int col = 0; col < image.width() / 2; col++) {
      int mirroredIndex = image.width() - 1 - col;

      for (int row = 0; row < image.height(); row++) {
        int[] pixel1 = image.retrievePixelPosn(row, col);
        int pixel1FirstIndex = (row * image.width() + col) * 3;

        int[] pixel2 = image.retrievePixelPosn(row, mirroredIndex);
        int pixel2FirstIndex = (row * image.width() + mirroredIndex) * 3;

        // swap the pixels
        intArray[pixel1FirstIndex] = pixel2[0];
        intArray[pixel1FirstIndex + 1] = pixel2[1];
        intArray[pixel1FirstIndex + 2] = pixel2[2];

        intArray[pixel2FirstIndex] = pixel1[0];
        intArray[pixel2FirstIndex + 1] = pixel1[1];
        intArray[pixel2FirstIndex + 2] = pixel1[2];
      }
    }

    return new ColorImage(image.width(), image.height(), intArray);
  }
}

