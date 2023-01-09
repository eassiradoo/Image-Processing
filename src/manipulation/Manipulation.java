package manipulation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import imagetypes.ImageUtil;

/**
 * Represents a class that will help deal with image
 * manipulation.
 */
public class Manipulation {


  /**
   * Represents a method that takes in a bufferedImage, a file's path name,
   * and an intensity in order to get a buffered image to return.
   * @param image represnts a buffered image.
   * @param filePath represents the file's path name.
   * @return a bufferedimage
   */
  public static BufferedImage getBImg(BufferedImage image, String filePath) {
    BufferedImage newImg = null;

    try {
      //make a new file
      File file = new File(filePath);


      ImageOutputStream out = ImageIO.createImageOutputStream(file);
      //wonky because of comrpession issues yay
      ImageWriter imgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
      imgWriter.setOutput(out);

      JPEGImageWriteParam write = new JPEGImageWriteParam(null);
      write.setCompressionMode(JPEGImageWriteParam.MODE_DEFAULT);

      //increse quality because she is just so horrendous!!
      write.setCompressionQuality(1.0f);


      imgWriter.write(null, new IIOImage(image, null, null), write);
      //bye
      imgWriter.dispose();

      //Read re-compressed jpg to stream
      newImg = ImageIO.read(new File(filePath));
      Path newPath = Paths.get(filePath);

      try {
        Files.delete(newPath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    //return the buffered image!!!
    return newImg;
  }



  /**
   * Represents a method that takes in two buffered images and returns
   * a new, modified buffered image that is a result of their comrpession.
   * @param b represents the OG image woohoo --> not compressed yet
   * @param newB reprsents that image but compressed woop
   * @return a new buffered image with new RGB based on the discrepancies found
   *        between the two buffered images
   */
  public static BufferedImage produceNewImg(BufferedImage b, BufferedImage newB) {
    //new image starts off as null yay
    BufferedImage discrepancyBuff = null;
    //establsih height
    int height = b.getHeight();
    //establsih width
    int width = b.getWidth();

    //if have same dimensions,
    if (height == newB.getHeight() && width == newB.getWidth()) {
      int[][][] startingImg = ImageUtil.threeColorArray(b);
      int[][][] compressedImg = ImageUtil.threeColorArray(newB);
      int[][][] newImgDiscrepancy = new int[height][width][3];
      int[] chanMaxDiff = new int[3];

      //iterate through rows
      for (int r = 0; r < height; r++) {
        //iterate through columns
        for (int column = 0; column < width; column++) {
          for (int channel = 0; channel < 3; channel++) {
            int discrep = Math.abs(startingImg[r][column][channel] -
                    compressedImg[r][column][channel]);

            newImgDiscrepancy[r][column][channel] = discrep;
            chanMaxDiff[channel] = Math.max(discrep, chanMaxDiff[channel]);
          }
        }
      }

      int biggestPossibleDiscrepancy = 0;

      for (int i = 0; i < 3; i++) {
        biggestPossibleDiscrepancy = Math.max(chanMaxDiff[i], biggestPossibleDiscrepancy);
      }

      double reformatToFit = 255.0 / biggestPossibleDiscrepancy;

      //iterate thru rows
      for (int row = 0; row < height; row++) {

        //iterate thru rows
        for (int column = 0; column < width; column++) {

          //iterate again why not
          for (int channels = 0; channels < 3; channels++) {
            newImgDiscrepancy[row][column][channels] *= reformatToFit;
            newImgDiscrepancy[row][column][channels]  =
                    (newImgDiscrepancy[row][column][channels] > 255)
                            ? 255 : Math.max(newImgDiscrepancy[row][column][channels], 0);
          }
        }
      }

      discrepancyBuff = ImageUtil.colorImage(newImgDiscrepancy);
    }

    //return new buff based on differnces
    return discrepancyBuff;
  }
}