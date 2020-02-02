package View;

import cellsociety.Controller;
import java.util.ResourceBundle;
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

import javax.imageio.ImageIO;
import javax.swing.*;

public class Visualizer {
  private static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
  public static final String STYLESHEET = "default.css";

  private Controller myController;
  private Stage myStage;
  // get strings from resource file
  private ResourceBundle myResources;
  private Button myStartButton;
  private Button myStopButton;
  private Button myStepButton;
  private Button myFileButton;
  private Slider mySlider;

  public Visualizer (Stage stage, Controller controller, String language) {
    myController = controller;
    myStage = stage;
    // use resources for labels
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
  }

  public Scene makeScene (int width, int height) {
    BorderPane root = new BorderPane();
    // must be first since other panels may refer to page
    Node myGridPane = makeGrid();
    root.setCenter(myGridPane);
    VBox vBox = new VBox();
    vBox.getChildren().add(makeInputPanel());
    root.setRight(vBox);
    // create scene to hold UI
    Scene scene = new Scene(root, width, height);
    // activate CSS styling
    //scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_PACKAGE + STYLESHEET).toExternalForm());
    return scene;
  }

  private Node makeGrid() {
    GridPane grid = new GridPane();

    return grid;
  }

  private Node makeInputPanel() {
    VBox result = new VBox();
    result.setSpacing(10);
    result.setFillWidth(true);
    myStartButton = makeButton("StartCommand", event -> startButtonPressed());
    myStopButton = makeButton("StopCommand", event -> stopButtonPressed());
    myStepButton = makeButton("StepForwardCommand", event -> stepButtonPressed());
    myFileButton = makeButton("LoadNewFileCommand", event -> fileButtonPressed());
    mySlider = makeSlider(event -> sliderMoved());

    result.getChildren().add(myStartButton);
    result.getChildren().add(myFileButton);
    result.getChildren().add(myStopButton);
    result.getChildren().add(myStepButton);
    result.getChildren().add(new Label(myResources.getString("AnimationRateCommand")));
    result.getChildren().add(mySlider);
    return result;
  }

  // makes a button using either an image or a label
  // taken from lab_browser
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

  private Slider makeSlider (EventHandler<ActionEvent> handler) {
    Slider result = new Slider(0, 100, 50);
    // enable the marks
    result.setShowTickMarks(true);
    // enable the Labels
    result.setShowTickLabels(true);
    return result;
  }

  private void fileButtonPressed() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser.showOpenDialog(myStage);
  }

  private void startButtonPressed() {

  }

  private void stopButtonPressed() {

  }

  private void stepButtonPressed() {

  }

  private void sliderMoved() {

  }

}