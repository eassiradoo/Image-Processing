package model;

import java.util.HashMap;
import java.util.Map;
import model.filter.ifilters.IFilter;
import imagetypes.IImage;


/**
 * Represents the model of MVC that contains
 * a HashMap to take in an Image's old id and
 * creating a new IImage.
 */
public class Model implements IModel {

  // variables

  // hashmap that maps K String to V IImage
  private Map<String, IImage> imageHashMap;


  // constructor

  /**
   * Represents a model that initializes the hashmap as
   * a new hashmap.
   */
  public Model() {
    this.imageHashMap = new HashMap<>();
  }



  // methods

  @Override
  public void takeInImage(IImage image, String imageName) throws IllegalArgumentException {
    // if image is null, bad
    if (image == null) {
      throw new IllegalArgumentException("Image " + notNull());
    }
    // image's name is null? worse.
    if (imageName == null) {
      throw new IllegalArgumentException("Image name " + notNull());
    }

    // otherwise if you're gucci go ahead and put in the image's name and
    // image itself into this lovely hashmap we have
    this.imageHashMap.put(imageName, image);
  }




  @Override
  public IImage getImg(String imageName) throws IllegalArgumentException {
    // if null, throw
    if (imageName == null) {
      throw new IllegalArgumentException("Image name " + notNull());
    }

    return getImgHelper(imageName);
  }

  /**
   * Represents a helper that returns the IImage from
   * the given name.
   *
   * @param imageName represents the String name (K key).
   * @return an IImage.
   */
  private IImage getImgHelper(String imageName) {
    // if hashmap doesn't contain the given image, throw
    if (!imageHashMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Inputted image does not exist. Please try again.");
    }

    // get the imageName from the hashmap + return it woohoo
    return this.imageHashMap.get(imageName);
  }




  @Override
  public void addFilter(IFilter filter, String oldImgName, String newImgName)
          throws IllegalArgumentException {
    // null filter, throw
    if (filter == null) {
      throw new IllegalArgumentException("Filter " + notNull());
    }
    // old name null, throw.
    if (oldImgName == null) {
      throw new IllegalArgumentException("Old image's name " + notNull());
    }
    // new image name null? bad. null.
    if (newImgName == null) {
      throw new IllegalArgumentException("New image's name " + notNull());
    }

    addFilterHelper(filter, oldImgName, newImgName);
  }

  private void addFilterHelper(IFilter filter, String oldImgName, String newImgName) {
    // this new image is a result of alteration --> get the old image's name
    // and put the new image's name where the old image's name was ~replace~ yuh
    IImage imageAfterEffect = filter.alter(this.imageHashMap.get(oldImgName));
    this.imageHashMap.put(newImgName, imageAfterEffect);
  }

  /**
   * As we can all see, we say "need not be null" an
   * exorbitant number of times. Made a cute little
   * helper for that.
   *
   * @return "need not be null."
   */
  private String notNull() {
    return "need not be null.";
  }

}

