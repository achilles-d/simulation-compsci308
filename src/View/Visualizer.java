package View;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Visualizer extends Application {
  private static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
  public static final String STYLESHEET = "default.css";
  public static final String TITLE = "Example JavaFX";
  public static final int WINDOW_WIDTH = 900;
  public static final int WINDOW_HEIGHT = 500;

  private Scene myScene;
  // get strings from resource file
  private ResourceBundle myResources;

  @Override
  public void start(Stage primaryStage) {
    myScene = setup(primaryStage);
    primaryStage.setScene(myScene);
    primaryStage.setTitle(TITLE);
    primaryStage.show();
  }

  private Scene setup(Stage primaryStage) {
    Group myRoot = new Group();
    addToRoot(primaryStage);

    return new Scene(myRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
  }

  /**
   * Returns scene for the browser so it can be added to stage.
   */
  public Scene makeScene (int width, int height) {
    BorderPane root = new BorderPane();
    // must be first since other panels may refer to page
    root.setCenter(new Canvas());
    VBox vBox = new VBox();
    vBox.getChildren().add(makeInputPanel());
    vBox.getChildren().add(makePreferencesPanel());
    root.setRight(vBox);
    root.setBottom(makeInformationPanel());
    // create scene to hold UI
    Scene scene = new Scene(root, width, height);
    // activate CSS styling
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    return scene;
  }

  // convenience method to create HTML page display
  private Node makePageDisplay () {
    Node myPage = new Canvas();
    return myPage;
  }

  private void addToRoot(Stage primaryStage) {
    myRoot.getChildren().add(createStartButton());
    myRoot.getChildren().add(createFileButton(primaryStage));
  }

  private Button createStartButton() {
    Button button = new Button("My Label");
    button.setLayoutX(20);
    button.setLayoutY(20);

    EventHandler<ActionEvent> event = e -> start_button_pressed();

    button.setOnAction(event);
    return button;
  }

  private Button createStopButton() {
    Button button = new Button("Stop");
    button.setLayoutX(20);
    button.setLayoutY(20);

    EventHandler<ActionEvent> event = e -> stop_button_pressed();

    button.setOnAction(event);
    return button;
  }

  private Button createFileButton(Stage primaryStage) {
    Button button = new Button("Load File");
    button.setLayoutX(100);
    button.setLayoutY(100);

    EventHandler<ActionEvent> event = e -> file_button_pressed(primaryStage);

    button.setOnAction(event);
    return button;
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


  private void file_button_pressed(Stage primaryStage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser.showOpenDialog(primaryStage);
  }

  private void start_button_pressed() {

  }

  private void stop_button_pressed() {

  }

}