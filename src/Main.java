import View.Visualizer;
import Controller.Controller;
import Controller.ControllerException;
import java.awt.Dimension;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Main class for Simulation
 *
 * @author Caleb Sanford
 */
public class Main extends Application {

  public static final String TITLE = "Simulation";
  public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);


  /**
   * @see Application#start(Stage)
   */
  @Override
  public void start(Stage stage) {
    Controller control = new Controller();
    Visualizer display = new Visualizer(stage, control, "English");
    stage.setTitle(TITLE);
    try {
      stage.setScene(display.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
    } catch (Throwable throwable) {
      throw new ControllerException("Stage scene resulted in error.");
    }
    stage.show();
  }

  /**
   * Start of the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
