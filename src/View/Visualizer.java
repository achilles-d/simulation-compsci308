package View;

import cellsociety.Controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.w3c.dom.Document;

/**
 * Class used to display the GUI for the simulation
 *
 * @author Caleb Sanford
 */
public class Visualizer {
  private static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
  public static final String STYLESHEET = "default.css";
  public static final int FRAMES_PER_SECOND = 25;
  public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  private Controller myController;
  private Stage myStage;
  private GridAnimator myAnimator;
  private ResourceBundle myResources;
  private Slider mySlider;
  private double currTime = 0;

  private boolean running = false;
  private boolean fileLoaded = false;

  /**
   * Constructor for Visualizer class. Sets instance variables and
   * creates the continual loop for updating the simulation
   *
   * @param stage This is the main stage for the Application
   * @param controller Instance of the Controller of the simulation
   * @param language Determines which resource file to use for the GUI strings
   */
  public Visualizer (Stage stage, Controller controller, String language) {
    myController = controller;
    myStage = stage;
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);

    // call step() method repeatedly forever
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step(SECOND_DELAY);
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * This method is called time interval based on FRAMES_PER_SECOND.
   * Responsible for animating the simulation
   *
   * @param elapsedTime time between calls to step()
   */
  public void step (double elapsedTime) {
    currTime += elapsedTime;
    if (running && currTime*10 > mySlider.getValue()) {
      myController.getGrid().update();
      myAnimator.updateCells();
      currTime = 0;
    }
  }

  /**
   * Creates the scene that displays the GUI
   *
   * @param width width in pixels of the GUI
   * @param height height in pixels of the GUI
   * @return the main Scene for the GUI
   */
  public Scene makeScene (int width, int height) {
    BorderPane root = new BorderPane();

    // must be first since other panels may refer to page
    GridPane myGridPane = makeGrid();
    root.setCenter(myGridPane);
    VBox vBox = new VBox();
    vBox.getChildren().add(makeInputPanel());
    vBox.setAlignment(Pos.CENTER);
    root.setRight(vBox);
    myAnimator = new GridAnimator(myGridPane, myController, 500);

    // create scene to hold UI
    Scene scene = new Scene(root, width, height);
    // activate CSS styling
    //scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_PACKAGE + STYLESHEET).toExternalForm());
    return scene;
  }

  /**
   *
   * @return The GridPane that the cells will be displayed on
   */
  private GridPane makeGrid() {
    GridPane result = new GridPane();
    result.setMaxWidth(500);
    result.setMaxHeight(500);
    return result;
  }

  /**
   * @return Node that contains the User input Buttons and Slider
   */
  private Node makeInputPanel() {
    VBox result = new VBox();
    result.setSpacing(10);
    result.setFillWidth(true);
    Button myStartButton = makeButton("StartCommand", event -> startButtonPressed());
    Button myStopButton = makeButton("StopCommand", event -> stopButtonPressed());
    Button myStepButton = makeButton("StepForwardCommand", event -> stepButtonPressed());
    Button myFileButton = makeButton("LoadNewFileCommand", event -> fileButtonPressed());
    mySlider = makeSlider();

    result.getChildren().add(myStartButton);
    result.getChildren().add(myStopButton);
    result.getChildren().add(myStepButton);
    result.getChildren().add(new Label(myResources.getString("AnimationRateCommand")));
    result.getChildren().add(mySlider);
    result.getChildren().add(myFileButton);
    return result;
  }

  /**
   * makes a button using either an image or a label
   * taken from lab_browser
   *
   * @param property String to look for in the resources file
   * @param handler to handle user clicks
   * @return Button instance
   */
  private Button makeButton (String property, EventHandler<ActionEvent> handler) {
    // represent all supported image suffixes
    final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
    Button result = new Button();
    String label = myResources.getString(property);
    if (label.matches(IMAGEFILE_SUFFIXES)) {
      result.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_FOLDER + label))));
    }
    else {
      result.setText(label);
    }
    result.setOnAction(handler);
    return result;
  }

  /**
   * Create a Slider for the user to change the animation rate
   *
   * @return Slider instance
   */
  private Slider makeSlider () {
    Slider result = new Slider(0, 100, 50);
    // enable the marks
    result.setShowTickMarks(true);
    // enable the Labels
    result.setShowTickLabels(true);
    return result;
  }

  /**
   * Show file dialog and call ReadXML with
   * user selected file.
   */
  private void fileButtonPressed() {
    running = false;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File selectedFile = fileChooser.showOpenDialog(myStage);
    if (selectedFile == null) {
      System.out.println("You must select a file");
      return;
    }
    Document doc = myController.parseXmlFile(selectedFile);
    myController.readParamsAndInitialize(doc);
    myAnimator.makeCellArray();
    fileLoaded = true;
  }

  /**
   * Called when the Start Button is pressed
   */
  private void startButtonPressed() {
    // Step forward at the current animation rate
    if (!fileLoaded){
      System.out.println("You must load a file before starting");
      return;
    }
    running = true;

  }

  /**
   * Called when Stop Button is pressed
   */
  private void stopButtonPressed() {
    // Pause the Current Animation
    running = false;
//    List<String> list = new ArrayList<>();
//    list.add("test1");
//    list.add("test2");
//    new Graph("Test", "test", 200,200, list);
  }

  /**
   * Called when Step Forward button is pressed.
   */
  private void stepButtonPressed() {
    if (!fileLoaded){
      System.out.println("You must load a file before starting");
      return;
    }
    myController.getGrid().update();
    myAnimator.updateCells();
  }
}