package View;

import cellsociety.Controller;
import java.io.File;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.*;

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
  // get strings from resource file
  private ResourceBundle myResources;
  private Slider mySlider;

  private boolean running = false;

  public Visualizer (Stage stage, Controller controller, String language) {
    myController = controller;
    myStage = stage;
    // use resources for labels
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
   *
   * @param elapsedTime
   */
  public void step (double elapsedTime) {
    if (running) {
      myAnimator.updateCells();
    }
  }

  public Scene makeScene (int width, int height) {
    BorderPane root = new BorderPane();
    // must be first since other panels may refer to page
    GridPane myGridPane = makeGrid();
    root.setCenter(myGridPane);
    System.out.println(myGridPane.getWidth());
    System.out.println(myGridPane.getHeight());
    myAnimator = new GridAnimator(myGridPane, myController, 500);
    VBox vBox = new VBox();
    vBox.getChildren().add(makeInputPanel());
    root.setRight(vBox);
    System.out.println(vBox.getWidth());
    // create scene to hold UI
    Scene scene = new Scene(root, width, height);
    // activate CSS styling
    //scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_PACKAGE + STYLESHEET).toExternalForm());
    return scene;
  }

  private GridPane makeGrid() {
    GridPane result = new GridPane();
    result.setMaxWidth(500);
    result.setMaxHeight(500);
    return result;
  }

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
   * @param property
   * @param handler
   * @return
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
   *
   * @return
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
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File selectedFile = fileChooser.showOpenDialog(myStage);
    //myController.ReadXml(selectedFile);
  }

  /**
   * Called when the Start Button is pressed
   */
  private void startButtonPressed() {
    // Step forward at the current animation rate
    running = true;

  }

  /**
   * Called when Stop Button is pressed
   */
  private void stopButtonPressed() {
    // Pause the Current Animation
    running = false;

  }

  /**
   * Called when Step Forward button is pressed.
   */
  private void stepButtonPressed() {

  }
}