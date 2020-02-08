import View.Visualizer;
import Model.Controller;
import java.awt.Dimension;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Main class for Simulation
 *
 * @author Caleb Sanford
 */
public class Main extends Application {
    // convenience constants
    public static final String TITLE = "Simulation";
    public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);


    /**
     * @see Application#start(Stage)
     */
    @Override
    public void start (Stage stage) {
        // create program specific components
        Controller control = new Controller();
        Visualizer display = new Visualizer(stage, control, "English");
        // give the window a title
        stage.setTitle(TITLE);
        // add our user interface components to Frame and show it
        stage.setScene(display.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
        stage.show();
    }

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
