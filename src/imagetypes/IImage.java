package imagetypes;

import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

/**
 * Represents the interface for the image types and their methods.
 */
public interface IImage {

  // methods

  /**
   * gets the width of the image.
   *
   * @return the width of the image.
   */
  int width();

  /**
   * gets the height of the image.
   *
   * @return the height of the image.
   */
  int height();


  /**
   * gets the total size of the image.
   *
   * @return the total size of the image.
   */
  int imgTotalSize();

  /**
   * gets the stream.
   *
   * @return the stream.
   */
  IntStream stream();

  /**
   * gets the pixel from the given row and col in a 1D array.
   *
   * @param row the row of the pixel.
   * @param col the col of the pixel.
   * @return the pixel from the given row and col.
   * @throws IndexOutOfBoundsException if given an invalid row or col.
   */
  int[] retrievePixelPosn(int row, int col) throws IndexOutOfBoundsException;

  /**
   * gets the pixel from the given index.
   *
   * @param row    the row of the pixel.
   * @param column the col of the pixel.
   * @return the value of the column + (row * width())
   * @throws IllegalArgumentException if given an invalid row or col.
   */
  int pixelIndex(int row, int column) throws IllegalArgumentException;

  /**
   * gets the position of the pixel.
   *
   * @param index the index of the pixel.
   * @return the position of the pixel.
   * @throws IllegalArgumentException if given an invalid index.
   */
  int[] pixelPosn(int index) throws IllegalArgumentException;

  /**
   * converts an object to a buffered image.
   *
   * @return the object as a buffered image.
   */
  BufferedImage convertToBuffImage();

  /**
   * gets the channels (RGB).
   *
   * @return the channels.
   */
  int[] getChannels();
}
