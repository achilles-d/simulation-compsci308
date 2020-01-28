package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Visualizer extends Application {

  public static final String TITLE = "Example JavaFX";
  public static final int WINDOW_WIDTH = 900;
  public static final int WINDOW_HEIGHT = 500;

  private Scene myScene;
  private Group myRoot;

  @Override
  public void start(Stage primaryStage) {
    myScene = setup(primaryStage);
    primaryStage.setScene(myScene);
    primaryStage.setTitle(TITLE);
    primaryStage.show();
  }

  private Scene setup(Stage primaryStage) {
    myRoot = new Group();
    addToRoot(primaryStage);

    return new Scene(myRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
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