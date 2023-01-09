import imagetypes.ColorImage;
import imagetypes.IImage;
import model.filter.ifilters.ColorF;
import model.filter.ifilters.IFilter;
import model.filter.ifilters.Kernel;
import model.filter.newfilters.NewEffectsF;
import model.filter.oldfilters.ExposureF;
import model.filter.oldfilters.FlipHorF;
import model.filter.oldfilters.FlipVerF;
import model.filter.oldfilters.IntensityF;
import model.filter.oldfilters.LumaF;
import model.filter.oldfilters.ValueF;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * represents tests for our filters.
 */
public class FilterTest {

  // variables

  private IImage image;
  private IImage smallerImage;

  // tests

  @Before
  public void init() {
    int[] pixelArray;
    int[] smallerPixelArray;

    pixelArray = new int[]{1, 16, 19, 22, 67, 255, 188, 76, 91, 22,
                           186, 187, 99, 19, 9, 70, 29, 138, 146, 199,
                           206, 4, 22, 11, 92, 100, 109};
    smallerPixelArray = new int[]{1, 16, 19, 55, 66, 30, 102, 77, 45};


    image = new ColorImage(3, 3, pixelArray);
    smallerImage = new ColorImage(1, 3, smallerPixelArray);
  }

  // testing old filters (assignment 4)

  @Test
  public void testRedGreyscale() {
    IFilter redGreyscale = new ColorF(0);
    IImage applyRedToSmall = redGreyscale.alter(smallerImage);
    assertEquals(smallerImage.retrievePixelPosn(1, 0)[0],
            applyRedToSmall.retrievePixelPosn(1, 0)[0]);
    assertEquals(smallerImage.retrievePixelPosn(0, 0)[0],
            applyRedToSmall.retrievePixelPosn(0, 0)[0]);

    IFilter redGreyscaleBig = new ColorF(0);
    IImage applyRedToBig = redGreyscaleBig.alter(image);
    assertEquals(image.retrievePixelPosn(1, 0)[0],
            applyRedToBig.retrievePixelPosn(1, 0)[0]);
    assertEquals(image.retrievePixelPosn(0, 0)[0],
            applyRedToBig.retrievePixelPosn(0, 0)[0]);
    assertEquals(image.retrievePixelPosn(2, 0)[0],
            applyRedToBig.retrievePixelPosn(2, 0)[0]);
    assertEquals(image.retrievePixelPosn(1, 2)[0],
            applyRedToBig.retrievePixelPosn(1, 2)[0]);
  }

  @Test
  public void testGreenGreyscale() {
    IFilter greenGreyScaleIndexOne = new ColorF(1);
    IImage applyGreenToSmallIndexOne = greenGreyScaleIndexOne.alter(smallerImage);

    assertEquals(smallerImage.retrievePixelPosn(0,
            0)[1], applyGreenToSmallIndexOne.retrievePixelPosn(0, 0)[1]);
    assertEquals(smallerImage.retrievePixelPosn(1,
            0)[1], applyGreenToSmallIndexOne.retrievePixelPosn(1, 0)[1]);
  }

  @Test
  public void testBlueGreyscale() {
    IFilter blueGreyScaleIndexTwoBig = new ColorF(2);
    IImage applyBlueToBigIndexTwo = blueGreyScaleIndexTwoBig.alter(image);

    assertEquals(image.retrievePixelPosn(1, 0)[2],
            applyBlueToBigIndexTwo.retrievePixelPosn(1, 0)[2]);
    assertEquals(image.retrievePixelPosn(2, 0)[2],
            applyBlueToBigIndexTwo.retrievePixelPosn(2, 0)[2]);
    assertEquals(image.retrievePixelPosn(0, 0)[2],
            applyBlueToBigIndexTwo.retrievePixelPosn(0, 0)[2]);
    assertEquals(image.retrievePixelPosn(1, 2)[2],
            applyBlueToBigIndexTwo.retrievePixelPosn(1, 2)[2]);
  }

  @Test
  public void testBrighten() {
    IFilter brightened = new ExposureF(50);
    IImage applyBrighten = brightened.alter(image);

    assertEquals(237, applyBrighten.retrievePixelPosn(1, 0)[2]);
    assertEquals(255, applyBrighten.retrievePixelPosn(2, 0)[2]);
    assertEquals(69, applyBrighten.retrievePixelPosn(0, 0)[2]);
    assertEquals(188, applyBrighten.retrievePixelPosn(1, 2)[2]);
  }

  @Test
  public void testDarken() {
    IFilter darkened = new ExposureF(-50);
    IImage applyDarken = darkened.alter(image);

    assertEquals(137, applyDarken.retrievePixelPosn(1, 0)[2]);
    assertEquals(156, applyDarken.retrievePixelPosn(2, 0)[2]);
    assertEquals(0, applyDarken.retrievePixelPosn(0, 0)[2]);
    assertEquals(88, applyDarken.retrievePixelPosn(1, 2)[2]);
  }

  @Test
  public void testFlipVertical() {
    IFilter flipVerical = new FlipVerF();
    IImage applyVertical = flipVerical.alter(image);

    assertEquals(187, applyVertical.retrievePixelPosn(1, 0)[2]);
    assertEquals(19, applyVertical.retrievePixelPosn(2, 0)[2]);
    assertEquals(206, applyVertical.retrievePixelPosn(0, 0)[2]);
    assertEquals(image.retrievePixelPosn(1, 2)[2],
            applyVertical.retrievePixelPosn(1, 2)[2]);

  }

  @Test
  public void testFlipHorizontal() {
    IFilter flipHorzontal = new FlipHorF();
    IImage applyHorizontal = flipHorzontal.alter(image);

    assertEquals(0, applyHorizontal.retrievePixelPosn(0, 1)[2]);
    assertEquals(0, applyHorizontal.retrievePixelPosn(1, 1)[1]);
    assertEquals(92, applyHorizontal.retrievePixelPosn(2, 0)[0]);
    assertEquals(187, applyHorizontal.retrievePixelPosn(1, 2)[2]);
    assertEquals(16, applyHorizontal.retrievePixelPosn(0, 2)[1]);
  }

  @Test
  public void testLuma() {
    IFilter lumaed = new LumaF();
    IImage applyLuma = lumaed.alter(image);

    assertEquals(71, applyLuma.retrievePixelPosn(0, 1)[2]);
    assertEquals(35, applyLuma.retrievePixelPosn(1, 1)[1]);
    assertEquals(188, applyLuma.retrievePixelPosn(2, 0)[0]);
    assertEquals(45, applyLuma.retrievePixelPosn(1, 2)[2]);
    assertEquals(100, applyLuma.retrievePixelPosn(0, 2)[1]);
  }

  @Test
  public void testValue() {
    IFilter valued = new ValueF();
    IImage applyValue = valued.alter(image);

    assertEquals(255, applyValue.retrievePixelPosn(0, 1)[2]);
    assertEquals(99, applyValue.retrievePixelPosn(1, 1)[1]);
    assertEquals(206, applyValue.retrievePixelPosn(2, 0)[0]);
    assertEquals(138, applyValue.retrievePixelPosn(1, 2)[2]);
    assertEquals(188, applyValue.retrievePixelPosn(0, 2)[1]);
  }

  @Test
  public void testIntensity() {
    IFilter intense = new IntensityF();
    IImage applyIntensity = intense.alter(image);

    assertEquals(114, applyIntensity.retrievePixelPosn(0, 1)[2]);
    assertEquals(42, applyIntensity.retrievePixelPosn(1, 1)[1]);
    assertEquals(183, applyIntensity.retrievePixelPosn(2, 0)[0]);
    assertEquals(79, applyIntensity.retrievePixelPosn(1, 2)[2]);
    assertEquals(118, applyIntensity.retrievePixelPosn(0, 2)[1]);
  }

  // testing new filters (assignment 5)

  @Test
  public void testBlur() {
    Kernel blurred = new Kernel(NewEffectsF.BLUR);
    IImage applyBlur = blurred.alter(image);

    assertEquals(99, applyBlur.retrievePixelPosn(0, 1)[2]);
    assertEquals(67, applyBlur.retrievePixelPosn(1, 1)[1]);
    assertEquals(47, applyBlur.retrievePixelPosn(2, 0)[0]);
    assertEquals(78, applyBlur.retrievePixelPosn(1, 2)[2]);
    assertEquals(32, applyBlur.retrievePixelPosn(0, 2)[1]);
  }

  @Test
  public void testSharpen() {
    Kernel sharpened = new Kernel(NewEffectsF.SHARPEN);
    IImage applySharpen = sharpened.alter(image);

    assertEquals(255, applySharpen.retrievePixelPosn(0, 1)[2]);
    assertEquals(194, applySharpen.retrievePixelPosn(1, 1)[1]);
    assertEquals(155, applySharpen.retrievePixelPosn(2, 0)[0]);
    assertEquals(206, applySharpen.retrievePixelPosn(1, 2)[2]);
    assertEquals(40, applySharpen.retrievePixelPosn(0, 2)[1]);
  }

  @Test
  public void testGreyscale() {
    Kernel greyscaled = new Kernel(NewEffectsF.GREYSCALE);
    IImage applyGreyscale = greyscaled.alter(image);

    assertEquals(249, applyGreyscale.retrievePixelPosn(0, 1)[2]);
    assertEquals(177, applyGreyscale.retrievePixelPosn(1, 1)[1]);
    assertEquals(127, applyGreyscale.retrievePixelPosn(2, 0)[0]);
    assertEquals(255, applyGreyscale.retrievePixelPosn(1, 2)[2]);
    assertEquals(93, applyGreyscale.retrievePixelPosn(0, 2)[1]);
  }

  @Test
  public void testSepia() {
    Kernel sepiaed = new Kernel(NewEffectsF.SEPIA);
    IImage applySepia = sepiaed.alter(image);

    assertEquals(255, applySepia.retrievePixelPosn(0, 1)[2]);
    assertEquals(234, applySepia.retrievePixelPosn(1, 1)[1]);
    assertEquals(137, applySepia.retrievePixelPosn(2, 0)[0]);
    assertEquals(255, applySepia.retrievePixelPosn(1, 2)[2]);
    assertEquals(95, applySepia.retrievePixelPosn(0, 2)[1]);
  }
}