package view;

import java.io.IOException;

/**
 * Represents the V of MVC, displays to the user
 * and communicates with the controller.
 */
public interface IView {

  /**
   * Renders a message to the viewer using the
   * view.
   *
   * @param message represents the message to be rendered.
   * @throws IOException if the message cannot be transmitted.
   */
  void renderMessage(String message) throws IOException;

  /**
   * Renders a message when the program starts
   * to introduce them to the Image Processing
   * System.
   *
   * @throws IOException if the message cannot be rendered.
   */
  void startingMessage() throws IOException;



}

