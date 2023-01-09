package model.filter.oldfilters;

import imagetypes.IImage;
import imagetypes.ColorImage;
import model.filter.ifilters.IFilter;

/**
 * Represents the class that allows for vertical flipping
 * an image.
 */
public class FlipVerF implements IFilter {

  /**
   * Applies this filter to the given image and outputs the filtered image.
   *
   * @param input the image to perform this filter function on.
   * @return the output image with the filter applied.
   */
  @Override
  public IImage alter(IImage input) {
    int[] intArray = new int[input.imgTotalSize()];

    // iterate through
    for (int row = 0; row < input.height(); row++) {
      int mirroredIndex = input.height() - 1 - row;

      for (int col = 0; col < input.width(); col++) {
        int[] pixel1 = input.retrievePixelPosn(row, col);
        int pixel1FirstIndex = (row * input.width() + col) * 3;

        int[] pixel2 = input.retrievePixelPosn(mirroredIndex, col);
        int pixel2FirstIndex = (mirroredIndex * input.width() + col) * 3;


        intArray[pixel1FirstIndex] = pixel2[0];
        intArray[pixel1FirstIndex + 1] = pixel2[1];
        intArray[pixel1FirstIndex + 2] = pixel2[2];

        intArray[pixel2FirstIndex] = pixel1[0];
        intArray[pixel2FirstIndex + 1] = pixel1[1];
        intArray[pixel2FirstIndex + 2] = pixel1[2];
      }
    }

    return new ColorImage(input.width(), input.height(), intArray);
  }
}
