package imagetypes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 * Represents a utility class that can be used
 * to read and write images.
 */
public final class ImageUtil {

  // methods


  /**
   * Helper to check for nullness + files w/ blankness or empty spaces.
   *
   * @param filePath represents the name of the file's path.
   * @throws IllegalArgumentException if the file path is null or is
   *                                  composed of only spaces.
   */
  private static void readImageHelper(String filePath) throws IllegalArgumentException {
    // path null
    if (filePath == null) {
      throw new IllegalArgumentException("File path need not be null.");
    }
    String typeOfFile = typeOfFile(filePath);

    // file type blank
    if (typeOfFile.isBlank()) {
      throw new IllegalArgumentException("Unrecognizable file type. Please try again.");
    }
  }

  /**
   * Reads an image. Takes in a filePath and reads the
   * given image.
   *
   * @param filePath the path of the file.
   * @return the image, read.
   * @throws IOException              if the image cannot be read.
   * @throws IllegalArgumentException if the file path is null.
   */
  public static IImage readImage(String filePath) throws IOException, IllegalArgumentException {
    readImageHelper(filePath);

    String typeOfFile = typeOfFile(filePath);

    // ppm? easy. we have a method for that
    if (typeOfFile.equals("ppm")) {
      return readPPM(filePath);
    }
    // new buff image
    BufferedImage b;

    // try to read, fail
    try {
      File file = new File(filePath);
      b = ImageIO.read(file);
    } catch (IOException exception) {
      throw new IOException("Cannot read image. Please try again.");
    }
    // buffered image null
    if (b == null) {
      throw new IOException("Image is null. Please try again.");
    }

    int channelNumber;
    // if there is no alpha channel, the number of channels
    // is three
    if (b.getAlphaRaster() == null) {
      channelNumber = 3;
      // else, if there is an alpha channel, then there are four channels yayyy
    } else {
      channelNumber = 4;
    }

    int[] areaWithNumColors = new int[3 * b.getHeight() * b.getWidth()];
    int[] areaWithPossibleAlpha = new int[b.getWidth() * b.getHeight() * channelNumber];
    // get raster
    b.getRaster().getPixels(0, 0, b.getWidth(), b.getHeight(), areaWithPossibleAlpha);

    // if alpha channel included
    if (channelNumber == 4) {

      // iterate through
      for (int i = 0; i < (b.getWidth() * b.getHeight() * channelNumber); i += channelNumber) {
        int index = i / channelNumber * 3;

        //R
        areaWithNumColors[index] = areaWithPossibleAlpha[i];
        //G
        areaWithNumColors[index + 1] = areaWithPossibleAlpha[i + 1];
        //B
        areaWithNumColors[index + 2] = areaWithPossibleAlpha[i + 2];
      }
    } else {
      //no aplha value present
      areaWithNumColors = areaWithPossibleAlpha;
    }

    // return new img
    return new ColorImage(b.getWidth(), b.getHeight(), areaWithNumColors);
  }


  /**
   * Represents a method to read PPM images yay.
   *
   * @param filePath represents the path of the file 'src/res' etc...
   * @return an IImage from the provided ppm image :))))
   * @throws IOException              if the image cannot be transmitted
   * @throws IllegalArgumentException if the filePath is null uh oh
   */

  public static IImage readPPM(String filePath) throws IOException, IllegalArgumentException {
    // if null...
    if (filePath == null) {
      throw new IllegalArgumentException("File path need not be null.");
    }
    //scanner that takes in the file path
    FileInputStream fileStream = new FileInputStream(filePath);
    FileInputStream stream = fileStream;
    Scanner s = new Scanner(stream);

    //instantiate a new SB
    StringBuilder sb = new StringBuilder();

    // as long as the scanner has a next line
    while (s.hasNextLine()) {
      String follwingLine = s.nextLine();

      // make a new line if not the header
      if (follwingLine.charAt(0) != '#') {
        sb.append(follwingLine + "\n");
      }
    }
    // SB as a string + assign
    s = new Scanner(sb.toString());

    // file has to begin with 'P3' --> elementOne checks first element
    String elementOne = s.next();


    // if doesn't start with 'P3'...
    if (!elementOne.equals("P3")) {
      throw new IllegalArgumentException("PPM files must begin with 'P3'. Please try again.");
    }
    // assign w, h, mv
    int width = s.nextInt();
    int height = s.nextInt();
    int maximumValue = s.nextInt();

    // iterate through all elements
    int numPixels = 3 * width * height;
    int[] intArray = new int[numPixels];

    for (int i = 0; i < numPixels; i++) {
      int nextInt = s.nextInt();
      // nums cannot be larger than img dimensions
      if (maximumValue < nextInt) {
        throw new IllegalArgumentException("Image too large. Please try again.");
      }

      intArray[i] = nextInt;
    }
    // return a new image
    return new ColorImage(width, height, intArray);
  }

  /**
   * Represents a method to write (output) PPM images.
   *
   * @param image    represents an IImage
   * @param filePath filePath represents the path of the file 'src/res' etc...
   * @throws IOException              if the image cannot be transmitted
   * @throws IllegalArgumentException if the filePath is null uh oh
   */
  public static void ppmWrite(IImage image, String filePath)
          throws IOException, IllegalArgumentException {
    // checks for null arguments
    checkNull(image, filePath);

    // write out the image
    FileOutputStream w = new FileOutputStream(filePath);
    OutputStreamWriter outStream = new OutputStreamWriter(w);

    StringBuilder sb = new StringBuilder();
    sb.append("P3\n\n").append(image.width() + " ").append(image.height() + "\n");

    image.stream().forEach(feature -> {
      sb.append(feature + "\n");
    });

    // write after making the SB have string values
    outStream.write(sb.toString());

    // close out
    outStream.close();
  }


  /**
   * Represents a method that locates the '.' in a file
   * name and returns what's after it to determine what type
   * of file has been provided (jpg, ppm, png, bmp).
   *
   * @param filePath represents the file's path.
   * @return a string of what type of file it is.
   * @throws IllegalArgumentException if the file path is null.
   */
  private static String typeOfFile(String filePath) throws IllegalArgumentException {
    // if the file path is null...
    if (filePath == null) {
      throw new IllegalArgumentException("Filename need not be null.");
    }
    // finds the index of the period
    int periodPlacement = filePath.indexOf(".");

    if (periodPlacement == filePath.length() - 1 || periodPlacement == -1) {
      return "";
    }
    String[] array = filePath.split("\\.");

    return array[array.length - 1];
  }

  /**
   * writes an image, given two inputs.
   *
   * @param image    the image to be written.
   * @param filePath the path of the file.
   * @throws IllegalArgumentException if any of the arguments are null.
   */
  public static void writeImage(IImage image, String filePath) throws IllegalArgumentException {
    // checks for invalidity
    checkNull(image, filePath);

    String format = typeOfFile(filePath);

    // if format is blank...
    if (format.isBlank()) {
      throw new IllegalArgumentException("Could not determine file format from given pathname: "
              + filePath);
    }
    // try ppm
    try {
      if (format.equals("ppm")) {
        ppmWrite(image, filePath);
        return;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    // buffered image
    BufferedImage b = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);

    int[] pixelArray = image.stream().toArray();
    b.getRaster().setPixels(0, 0, image.width(), image.height(), pixelArray);

    // try to write image
    try {
      writeImage2(b, format, filePath);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Unable to write image. Please try again.");
    }
  }

  /**
   * Helper method that checks for nullness.
   *
   * @param image    represents the given image.
   * @param filePath represents the given file path.
   */
  private static void checkNull(IImage image, String filePath) {
    // image null
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    // path null
    if (filePath == null) {
      throw new IllegalArgumentException("Pathname to image cannot be null");
    }
  }

  /**
   * writes an image, given three inputs.
   *
   * @param bufferedImage the buffered image.
   * @param format        the type of file.
   * @param pathname      the path of the file.
   * @throws IllegalArgumentException if any of the arguments are null.
   */
  private static void writeImage2(BufferedImage bufferedImage, String format, String pathname)
          throws IllegalArgumentException {
    File file = new File(pathname);

    // fixes the image quality. image quality was absolutely atrocious so here we are
    if (format.equalsIgnoreCase("jpeg") || format.equalsIgnoreCase("jpg")) {

      ImageWriter w = ImageIO.getImageWritersByFormatName("jpg").next();

      ImageWriteParam betterQualityImg = w.getDefaultWriteParam();
      betterQualityImg.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

      // improve img quality
      betterQualityImg.setCompressionQuality(0.7f);

      try {
        FileImageOutputStream stream = new FileImageOutputStream(file);
        ImageOutputStream out = stream;

        w.setOutput(out);
        w.write(null, new IIOImage(bufferedImage,
                null, null), betterQualityImg);
        //catch write's exception
      } catch (IOException e) {
        e.printStackTrace();
      }

      w.dispose();
    } else {
      try {
        boolean canBeWritten = ImageIO.write(bufferedImage, format, file);
        // if image cannot be written,
        if (!canBeWritten) {
          throw new IOException("Unable to write the given image. Please try again.");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }



  /**
   * Represents a method that takes in a buffered image and
   * sets it to an RGB array.
   * @param b represents a buffered image.
   * @return 2D array of RGB woohoo
   */
  public static int[][][] threeColorArray(BufferedImage b) {
    int[][][] arrayOfColors;
    int height = b.getHeight();
    int width = b.getWidth();

    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Width and height need not be negative.");
    }
    else {
      arrayOfColors = new int[height][width][3];

      //iterate thru rows
      for (int r = 0; r < height; r++) {

        //iterate thru columns
        for (int c = 0; c < width; c++) {
          arrayOfColors[r][c] = singularColorArray(b.getRGB(c, r));
        }
      }
    }
    //return buff
    return arrayOfColors;
  }



  /**
   * Represents a method that takes in a 3D array and returns a buffered
   * image.
   * @param colorArray represents a 3D array (RGB)
   * @return a buffered image --> using 3D array
   */
  public static BufferedImage colorImage(int[][][] colorArray) {
    BufferedImage b;

    //find height
    int height = colorArray.length;
    //find width
    int width = colorArray[0].length;

    //create new bufferedImage
    b = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    //iterate thru rows
    for (int r = 0; r < height; r++) {

      //iterate thru columns
      for (int c = 0; c < width; c++) {
        b.setRGB(c, r, (colorArray[r][c][0] << 16) | (colorArray[r][c][1] << 8)
                | (colorArray[r][c][2]));
      }
    }
    //return buff
    return b;
  }



  /**
   * Rerpesents a method that takes in an int color and produces
   * an int array.
   * @param color represnets the given color's integer value.
   * @return an integer array of RGB
   */
  private static int[] singularColorArray(int color) {

    int[] colorArray = { (color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff };

    //iterate thru
    for (int i = 0; i < 3; i++) {
      //if going to be less than 0, don't
      if (colorArray[i] < 0) {
        colorArray[i] = 0;

        //if going to be greater than 255, don't
      } else if (colorArray[i] > 255) {
        colorArray[i] = 255;
      }
    }
    //return what u find
    return colorArray;
  }



  /**
   * Represents a method that takes in a buffered image, a mask,
   * paramaters for that mask, and a limiting RGB value for other
   * RGB's to be within.
   * @param oldImg represnets the old buffered image.
   * @param maskImg represents the masking buffered image
   * @param maskColor reprsents the color to mask
   * @param limit repersents the viable RGB values
   * @return a new bufferedimage that is updated accordingly
   */
  public static BufferedImage mask(BufferedImage oldImg, BufferedImage maskImg,
                                   int[] maskColor, int limit) {
    //start off w/ buffered
    BufferedImage b = null;

    //get the height purr
    int height = oldImg.getHeight();

    //same for width cuz equality <3
    int width = oldImg.getWidth();

    //if appropriate + well behaved
    if (maskColor.length == 3 && width == maskImg.getWidth() && height == maskImg.getHeight()) {
      int[][][] imgOrig = threeColorArray(oldImg);
      int[][][] imgMask = threeColorArray(maskImg);
      int[][][] imgResult = new int[height][width][3];

      //iterate thru rows
      for (int row = 0; row < height; row++) {

        //iterate thru columns
        for (int column = 0; column < width; column++) {

          int sumMaskPixel = 0;

          for (int channels = 0; channels < 3; channels++) {
            sumMaskPixel += imgMask[row][column][channels];
          }

          //If pixel magnitude > threshold, then mask w/ color
          if (sumMaskPixel > limit) {
            imgResult[row][column] = maskColor;
          } else {
            imgResult[row][column] = imgOrig[row][column];
          }
        }
      }
      //update
      b = colorImage(imgResult);
    }

    //return what u find
    return b;
  }
}
