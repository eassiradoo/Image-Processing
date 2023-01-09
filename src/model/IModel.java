package model;

import model.filter.ifilters.IFilter;
import imagetypes.IImage;

/**
 * Represents the model of MVC that represents
 * the methods needed to output a successful
 * model that communicates with the controller.
 */
public interface IModel {

  /**
   * Represents a method that takes in a filter,
   * an old image's name and a new image's name and applies
   * the given filter to the old image.
   * @param filter represents an IFilter.
   * @param oldImg represents the name of the old image.
   * @param newImg represents the name of the new image.
   * @throws IllegalArgumentException if any parameters are null.
   */
  void addFilter(IFilter filter, String oldImg, String newImg)
          throws IllegalArgumentException;

  /**
   * Represents a method that returns an IImage. Checks if
   * the given image's name is contained in the hashmap. Gets
   * the image's name from the hashmap + returns the IImage if contained.
   * @param imageName represents the image's String name
   * @return the IImage.
   * @throws IllegalArgumentException if any parameters are null.
   */
  IImage getImg(String imageName) throws IllegalArgumentException;

  /**
   * Represents a method that takes in an image and an image's name
   * and adds the image to the hashmap.
   * @param image represents the given image.
   * @param imageId represents the name of the image.
   * @throws IllegalArgumentException if any parameters are null.
   */
  void takeInImage(IImage image, String imageId) throws IllegalArgumentException;


}
