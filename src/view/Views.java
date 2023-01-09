package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.commands.effects.ExposureC;
import controller.commands.effects.FilterC;
import imagetypes.IImage;
import imagetypes.ImageUtil;
import model.IModel;
import model.Model;
import model.filter.ifilters.AlterColorF;
import model.filter.ifilters.ColorF;
import model.filter.ifilters.Kernel;
import model.filter.newfilters.NewEffectsF;
import model.filter.oldfilters.FlipHorF;
import model.filter.oldfilters.FlipVerF;
import model.filter.oldfilters.IntensityF;
import model.filter.oldfilters.LumaF;
import model.filter.oldfilters.ValueF;

/**
 * Represents a viewing class to represent the
 * viewing class. Contains an appendable to
 * concatenate strings and produce visuals.
 * implements IView and ActionListener interfaces.
 */
public class Views extends JFrame implements IView, ActionListener {

  // VARIABLES

  // appendable
  private Appendable ap;


  //buttons
  private JButton loadButton;
  private JButton saveButton;


  // panels
  private JPanel mainPanel;
  private JPanel histogramPanel;
  private JPanel imagePanel;
  private JScrollPane imageScrollPane;


  //combo box
  private JComboBox effectsDropdown;


  //model
  private final IModel model;

  //histogram
  private Histogram histogram;


  // constructor

  /**
   * Represents a version of the view that takes in an
   * appendable and initializes it.
   *
   * @param ap represents an appendable.
   * @throws IllegalArgumentException if the appendable is null.
   */
  public Views(Appendable ap) throws IllegalArgumentException {
    // if null,
    if (ap == null) {
      throw new IllegalArgumentException("Appendable need not be null.");
    }

    this.ap = ap;
    this.model = new Model();
  }

  /**
   * Represents a verison of the view that takes in a model.
   *
   * @param model reprsents an IModel.
   * @throws IllegalArgumentException if the model is null.
   */
  public Views(IModel model) throws IllegalArgumentException {
    //if model null,
    if (model == null) {
      throw new IllegalArgumentException("Model need not be null.");
    }
    //else, initialize
    this.model = model;
  }

  /**
   * Represents a version of the view that is a GUI.
   * Is a GUI with a title, a dropdown menu, buttons, and
   * a designated space for a loaded image to be placed.
   * The dropdown menu contains options to implement filters
   * including those to flip, greyscale, and add effects to
   * images.
   */
  public Views() {
    super();
    this.model = new Model();

    initFrame();
    initMainPanel();
    initEffectsPanel();
    initImagePanel();
    intiHistogramPanel();

    this.setVisible(true);
  }

  // methods

  /**
   * Represents a method that sets up the GUI view.
   */
  private void initFrame() {
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setTitle("Emma and Avery's Image Processing System");
    this.setSize(1000, 2000);
    this.setResizable(true);
  }

  /**
   * Represents a method that sets up the main panel in the GUI view.
   */
  private void initMainPanel() {
    mainPanel = new JPanel();

    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);
  }

  /**
   * Represents a method that sets up the effects panel in the GUI view.
   */
  private void initEffectsPanel() {
    JPanel effectsPanel = new JPanel();
    effectsPanel.setLayout(new BoxLayout(effectsPanel, BoxLayout.LINE_AXIS));
    effectsPanel.setBorder(BorderFactory.createTitledBorder("Image Effects:"));
    mainPanel.add(effectsPanel);

    // load
    loadButton = new JButton("Load in New Image");
    loadButton.addActionListener(this);
    effectsPanel.add(loadButton);

    // save
    saveButton = new JButton("Save Altered Image");
    saveButton.addActionListener(this);
    effectsPanel.add(saveButton);

    // apply filter
    String[] effectsList = {"no filter selected", "luma", "value", "intensity",
                            "red-greyscale", "green-greyscale", "blue-greyscale",
                            "brighten", "darken", "blur", "sharpen", "sepia",
                            "flip-horizontal", "flip-vertical", "greyscale"};
    effectsDropdown = new JComboBox(effectsList);
    effectsDropdown.addActionListener(this);
    effectsPanel.add(effectsDropdown);
  }

  /**
   * Represents a method that sets up the image panel in the GUI view.
   */
  private void initImagePanel() {
    imagePanel = new JPanel();

    imageScrollPane = new JScrollPane(imagePanel);
    imageScrollPane.setPreferredSize(new Dimension(200, 300));

    mainPanel.add(imageScrollPane);
  }

  /**
   * Represents a method that sets up the histogram panel in the GUI view.
   */
  private void intiHistogramPanel() {
    histogramPanel = new JPanel();
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.PAGE_AXIS));
    histogramPanel.setPreferredSize(new Dimension(
            this.getWidth() / 2, this.getHeight() / 4));
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram Will be Displayed Here:"));
    mainPanel.add(histogramPanel);

    histogram = new Histogram();
    histogram.setPreferredSize(new Dimension(this.getWidth() / 2,
            this.getHeight() / 4));
    histogramPanel.add(histogram);
  }


  @Override
  public void actionPerformed(ActionEvent actionEvent) {

    // LOAD IMAGE
    if (actionEvent.getSource() == loadButton) {
      JFileChooser fchooser = new JFileChooser();

      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "Image Types Accepted (PPM, BMP, PNG, JPG)",
              "jpg", "png", "ppm", "bmp");
      fchooser.setFileFilter(filter);


      int response = fchooser.showOpenDialog(null);
      if (response == JFileChooser.APPROVE_OPTION) {
        File file = fchooser.getSelectedFile();

        String loadedImagePathway = file.getAbsolutePath();
        ImageIcon loadedImageIcon = new ImageIcon(loadedImagePathway);

        // images
        // HashMap inside of the model
        // original_image --> imported image
        // current_image  --> imported image
        //
        try {
          IImage loadedImage = ImageUtil.readImage(loadedImagePathway);
          this.model.takeInImage(loadedImage, "original_image");
          this.model.takeInImage(loadedImage, "current_image");

          refreshImage(loadedImageIcon, loadedImage);
        } catch (IOException e) {
          // do nothing
        }

      }
      imagePanel.repaint();
      imagePanel.revalidate();
      histogramPanel.repaint();
      histogramPanel.revalidate();

      // save button
    } else if (actionEvent.getSource() == saveButton) {
      try {
        JFileChooser fileChooser = new JFileChooser(".");
        int returnValue = fileChooser.showSaveDialog(Views.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          String filePath = fileChooser.getSelectedFile().getAbsolutePath();

          IImage imageToExport = model.getImg("current_image");
          ImageUtil.writeImage(imageToExport, filePath);
        }
      } catch (IllegalArgumentException e) {
        // fait rien <3 (translation: do nothing)
      }

      // dropdown menu
    } else if (actionEvent.getSource() == effectsDropdown) {
      try {
        String s = Arrays.toString(effectsDropdown.getSelectedObjects());
        this.viewSwitchStatements(s);

        BufferedImage image = model.getImg("current_image").convertToBuffImage();

        refreshImage(new ImageIcon(image), model.getImg("current_image"));

        imagePanel.repaint();
        imagePanel.revalidate();
        histogramPanel.repaint();
        histogramPanel.revalidate();

      } catch (IllegalArgumentException | IOException e) {
        // nothing
      }
    }
  }


  /**
   * Represents a method that takes in an icon and an IImage and
   * resets them to their initialized values :)).
   *
   * @param loadedImageIcon represents an ImageIcon
   * @param loadedImage     represents an IImage
   */
  private void refreshImage(ImageIcon loadedImageIcon, IImage loadedImage) {
    // the juice cleanse
    imagePanel.removeAll();
    histogramPanel.removeAll();

    // Setup new image display
    //jlabel
    JLabel currentImage = new JLabel();
    currentImage.setIcon(loadedImageIcon);
    imageScrollPane = new JScrollPane(currentImage);
    imageScrollPane.setPreferredSize(new Dimension(280, 175));

    // the recovery
    imagePanel.add(imageScrollPane);
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image Will be Displayed Here: "));

    // view.Histogram
    histogram.channelSetter(loadedImage.stream().toArray());
    histogramPanel.add(histogram);
  }


  /**
   * Represents a method that takes in a string argument
   * and applies the given filter.
   *
   * @param arg represents the option from the dropdown menu
   * @throws IOException if the switch statement cannot be transmitted
   */
  private void viewSwitchStatements(String arg) throws IOException {
    ArrayList<String> commands = new ArrayList<>(Arrays.asList("current_image", "current_image"));
    switch (arg) {

      case "[no filter selected]":
        break;

      case "[luma]":
        new FilterC(model, this, new LumaF()).doCommand(commands);
        break;

      case "[value]":
        new FilterC(model, this, new ValueF()).doCommand(commands);
        break;

      case "[intensity]":
        new FilterC(model, this, new IntensityF()).doCommand(commands);
        break;

      case "[red-greyscale]":
        new FilterC(model, this, new ColorF(ColorF.RED_GREYSCALE)).doCommand(commands);
        break;

      case "[green-greyscale]":
        new FilterC(model, this, new ColorF(ColorF.GREEN_GREYSCALE)).doCommand(commands);
        break;

      case "[blue-greyscale]":
        new FilterC(model, this, new ColorF(ColorF.BLUE_GREYSCALE)).doCommand(commands);
        break;

      case "[brighten]":

        String messageBrighten = "Number entered must be a positive integer.";
        String enteredValueBrighten = JOptionPane.showInputDialog(this, messageBrighten);

        int valAsNum = Integer.parseInt(enteredValueBrighten);

        if (valAsNum > 0) {

          String[] commandss = new String[]{enteredValueBrighten, "current_image", "current_image"};
          List<String> wordList = Arrays.asList(commandss);

          new ExposureC(model, this).doCommand(wordList);
        } else {
          JOptionPane.showMessageDialog(Views.this,
                  "Number entered must be a negative integer.",
                  "Invalid entry.", JOptionPane.ERROR_MESSAGE);
        }
        break;

      case "[darken]":
        String messageDarken = "Number entered must be a negative integer.";
        String enteredValue = JOptionPane.showInputDialog(this, messageDarken);

        int valAsNumDarken = Integer.parseInt(enteredValue);

        if (valAsNumDarken < 0) {

          String[] commandss = new String[]{enteredValue, "current_image", "current_image"};
          List<String> wordList = Arrays.asList(commandss);

          new ExposureC(model, this).doCommand(wordList);
        } else {
          JOptionPane.showMessageDialog(Views.this,
                  "Number entered must be a negative integer.",
                  "Invalid entry.", JOptionPane.ERROR_MESSAGE);
        }
        break;

      case "[blur]":
        new FilterC(model, this, new Kernel(NewEffectsF.BLUR)).doCommand(commands);
        break;

      case "[sharpen]":
        new FilterC(model, this, new Kernel(NewEffectsF.SHARPEN)).doCommand(commands);
        break;

      case "[sepia]":
        new FilterC(model, this, new AlterColorF(NewEffectsF.SEPIA)).doCommand(commands);
        break;

      case "[flip-horizontal]":
        new FilterC(model, this, new FlipHorF()).doCommand(commands);
        break;

      case "[flip-vertical]":
        new FilterC(model, this, new FlipVerF()).doCommand(commands);
        break;

      case "[greyscale]":
        new FilterC(model, this, new AlterColorF(NewEffectsF.GREYSCALE)).doCommand(commands);
        break;

      default:
        break;
    }
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    try {
      this.ap.append("-> " + message);
    } catch (IOException e) {
      throw new IllegalStateException("Error found in message. Please try again.\n");
    }
  }

  @Override
  public void startingMessage() throws IllegalStateException {
    try {
      this.ap.append("Welcome to Avery and Emma's Image Processing System!\n"
              + "Please enter a command to begin.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Error found in starting message. Please try again.\n");
    }
  }
}