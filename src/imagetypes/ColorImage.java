package imagetypes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Represents an image with colors (JPG, etc).
 */

public class ColorImage implements IImage {

  // variables

  // width
  private final int width;
  // height
  private final int height;
  // pixels
  private final int[] intArray;

  private int[][] image;

  // constructor

  /**
   * First constructor, takes in 3 arguments (width, height, intArray).
   * @param width the width of the image.
   * @param height the height of the image.
   * @param intArray the integer array of the image.
   * @throws IllegalArgumentException if the width or height are < 1 or if the intArray is null.
   */
  public ColorImage(int width, int height, int[] intArray)
          throws IllegalArgumentException {
    // width < 1
    if (width < 1) {
      throw new IllegalArgumentException("Width need not be less than 1.");
    }
    // height < 1
    if (height < 1) {
      throw new IllegalArgumentException("Height need not be less than 1.");
    }
    // null intArray
    if (intArray == null) {
      throw new IllegalArgumentException("Integer array need not be null");
    }

    this.width = width;
    this.height = height;

    // if length is not equal to total size of the image...
    if (intArray.length != this.imgTotalSize()) {
      throw new IllegalArgumentException("PPM has invalid size. Please try again.");
    }

    this.intArray = intArray;
  }

  // methods

  @Override
  public int width() {
    return width;
  }

  @Override
  public int height() {
    return height;
  }

  @Override
  public int imgTotalSize() {
    // 3 == number of color channels (RGB)
    return width() * height() * 3;
  }

  @Override
  public int pixelIndex(int row, int column) throws IllegalArgumentException {
    // invalid arguments
    if (row < 0 || column < 0 || column >= width() || row >= height()) {
      throw new IllegalArgumentException("Rows and columns need not be negative.");
    }
    return column + (row * width());
  }

  @Override
  public IntStream stream() {
    return Arrays.stream(intArray);
  }

  @Override
  public int[] retrievePixelPosn(int row, int col) throws IndexOutOfBoundsException {
    // invalid arguments
    if (row < 0 || row >= height() || col < 0 || col >= width) {
      throw new IndexOutOfBoundsException("Invalid row or column. Please try again.");
    }
    int firstIndex = (row * width() + col) * 3;

    return Arrays.copyOfRange(intArray, firstIndex, firstIndex + 3);
  }

  @Override
  public int[] pixelPosn(int index) throws IllegalArgumentException {
    // invalid arguments
    if (index < 0 || index >= imgTotalSize() / 3) {
      throw new IllegalArgumentException("Invalid index given. Please try again.");
    }

    return new int[]{index / width(), index % width()};
  }


  @Override
  public BufferedImage convertToBuffImage() {
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    int[] channels = Arrays.stream(getChannels()).toArray();
    bufferedImage.getRaster().setPixels(0, 0, width, height, channels);

    return bufferedImage;
  }

  /**
   * Represents a method that gets an int array of channels
   * from a given arraylist.
   * @return an int array of channels.
   */
  public int[] getChannels() {
    ArrayList<Integer> newchannels = new ArrayList<>();


    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        newchannels.add(this.retrievePixelPosn(row, col)[0]);
        newchannels.add(this.retrievePixelPosn(row, col)[1]);
        newchannels.add(this.retrievePixelPosn(row, col)[2]);
      }
    }

    int[] converted = new int[this.imgTotalSize()];
    // convert to int array
    for (int i = 0; i < newchannels.size(); i++) {
      converted[i] = newchannels.get(i);
    }

    return converted;
  }
}
