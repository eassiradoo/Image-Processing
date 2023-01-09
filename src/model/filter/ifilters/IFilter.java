package model.filter.ifilters;

import imagetypes.IImage;

/**
 * This interface represents a filter function performed on a given image.
 */
public interface IFilter {

  /**
   * Represents a function that takes in an IImage and performs
   * a given filter on it.
   * @param image represents the inputted image.
   * @return an IImage after it has been effected.
   */
  IImage alter(IImage image);

}
