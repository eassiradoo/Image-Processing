package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.JPanel;


/**
 * Represents a class that creates a histogram based on
 * the inputted image's RGB values. Creates a bar graph
 * that is representative of the quantities of these values.
 */
public class Histogram extends JPanel {

  //RGB values
  private int[] red;
  private int[] green;
  private int[] blue;
  private int[] intensity;

  private int maximum;

  //determines if graph is empty
  private boolean isNotEmpty;


  /**
   * Represents a newly made histogram. Contains arrays
   * of red, green, blue, and intensity. Starts off as an
   * empty histogram.
   */
  public Histogram() {

    maximum = 0;
    isNotEmpty = false;

    //RGBI values
    red = new int[256];
    green = new int[256];
    blue = new int[256];
    intensity = new int[256];

  }


  /**
   * Finds and returns, after iterating through
   * all RBG values, the largest value.
   *
   * @return RGB's largest value.
   */
  private int findLargestValue() {
    int maximum = 0;

    for (int channelAmt : red) {
      maximum = Math.max(maximum, channelAmt);
    }
    for (int channelAmt : green) {
      maximum = Math.max(maximum, channelAmt);
    }
    for (int channelAmt : blue) {
      maximum = Math.max(maximum, channelAmt);
    }

    return maximum;
  }


  /**
   * Resets what the RGB values in the image are. Takes
   * in channel arrays to apply new values.
   *
   * @param channelArray represents the RBG channels in an image.
   * @throws IllegalArgumentException if channel arrays are not divisible by 3 --> invalid!!!!
   */
  public void channelSetter(int[] channelArray) throws IllegalArgumentException {

    if (channelArray == null) {
      isNotEmpty = false;
      return;
    }
    //must be a multiple of 3 cuz multiplication and all
    if (channelArray.length % 3 != 0) {
      throw new IllegalArgumentException("Channel array must be divisible by 3. Please try " +
              "again.");
    }

    //fill all!!
    Arrays.fill(red, 0);
    Arrays.fill(green, 0);
    Arrays.fill(blue, 0);
    Arrays.fill(intensity, 0);

    //iterate thru
    for (int i = 0; i < channelArray.length - 3; i = i + 3) {

      //update
      red[channelArray[i]] += 1;
      green[channelArray[i + 1]] += 1;
      blue[channelArray[i + 2]] += 1;

      int intensityValue = (int) Math.round((channelArray[i] +
              channelArray[i + 1] + channelArray[i + 2]) / 3.0);
      this.intensity[intensityValue] += 1;
    }


    maximum = findLargestValue();

    isNotEmpty = true;

    //repaint v important
    this.repaint();
  }


  /**
   * Represents a method to draw each rectangular bar that will be displayed.
   *
   * @param imageGraphics represents the Graphics used.
   * @param totalWidth    represents the total width.
   * @param totalHeight   represents the total height.
   * @param channelArray  represents the RGB array that will be displayed.
   * @param color         represents the color that the  rectangular bar that will
   *                      be displayed in yay
   */
  private void drawBars(Graphics imageGraphics, int totalWidth, int totalHeight,
                        int[] channelArray, Color color) {

    //for each
    double recalibratedWidth = totalWidth / 256.0;
    double errorMargin = recalibratedWidth - (int) Math.floor(recalibratedWidth);

    double inaccuracyMargin = 0.0;

    int incrementXVals = 70;

    //new color w/ 81% opacity
    Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 81);
    imageGraphics.setColor(newColor);

    //iterate thru all 256 of 'em and incrememt
    for (int i = 0; i < 256; i++) {
      //determine width
      int width = (int) Math.floor(recalibratedWidth);

      //accounts for graph not being all the way to the edge of the screen
      if (inaccuracyMargin >= 1.0) {
        width = (int) Math.floor(recalibratedWidth) + 1;
        inaccuracyMargin -= 1.0;
      }

      inaccuracyMargin += errorMargin;

      //determine height
      int height = (int) Math.round(((double) channelArray[i] / maximum) * totalHeight);
      int y = 30 + totalHeight - height;

      imageGraphics.fillRect(incrementXVals, y, width, height);

      incrementXVals = incrementXVals + width;
    }
  }


  @Override
  protected void paintComponent(Graphics imageGraphics) {
    super.paintComponent(imageGraphics);

    //updated with and height
    int getNewWidth = this.getWidth() - 100;
    int getNewHeight = this.getHeight() - 100;

    //new dimension for visibility
    this.setSize(new Dimension(800, 300));
    //set histogram's background color
    imageGraphics.setColor(Color.WHITE);
    imageGraphics.fillRect(70, 30, getNewWidth, getNewHeight);

    if (isNotEmpty) {
      drawBars(imageGraphics, getNewWidth, getNewHeight, red, Color.RED);
      drawBars(imageGraphics, getNewWidth, getNewHeight, green, Color.GREEN);
      drawBars(imageGraphics, getNewWidth, getNewHeight, blue, Color.BLUE);
      drawBars(imageGraphics, getNewWidth, getNewHeight, intensity, Color.YELLOW);
    }
  }
}