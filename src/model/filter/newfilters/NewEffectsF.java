package model.filter.newfilters;

/**
 * Represents a class with the filters
 * of blur, sepia, greyscale, and sharpen. Class
 * holding the matrices for the new effects
 * effective of assignment 5.
 */
public class NewEffectsF {

  /**
   * Represents the matrix for the blur filter.
   */
  public static final double[][] BLUR = new double[][]{{0.0625, 0.125, 0.0625},
                                                       {0.125, 0.25, 0.125},
                                                       {0.0625, 0.125, 0.0625}};

  /**
   * Represents the matrix for the sepia filter.
   */
  public static final double[][] SEPIA = new double[][]{{0.393, 0.769, 0.189},
                                                        {0.349, 0.686, 0.168},
                                                        {0.272, 0.534, 0.131}};

  /**
   * Represents the matrix for the greyscale filter.
   */
  public static final double[][] GREYSCALE = new double[][]{{0.2126, 0.7152, 0.0722},
                                                            {0.2126, 0.7152, 0.0722},
                                                            {0.2126, 0.7152, 0.0722}};

  /**
   * Represents the matrix for the sharpen filter.
   */
  public static final double[][] SHARPEN = new double[][]{
          {-0.125, -0.125, -0.125, -0.125, -0.125},
          {-0.125, 0.25, 0.25, 0.25, -0.125},
          {-0.125, 0.25, 1.0, 0.25, 0.125},
          {-0.125, 0.25, 0.25, 0.25, -0.125},
          {-0.125, -0.125, -0.125, -0.125, -0.125}};
}
