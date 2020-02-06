package View;

import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.print.attribute.standard.NumberUp;

public class Graph {
  private Map<String, Series> mySeries;
  private List<String> mySeriesKeys;
  private LineChart<Number,Number> lineChart;
  private Integer myX = 0;

  public Graph(String windowTitle, String graphTitle, int windowWidth, int windowHeight, List<String> seriesKeys){
    makeStage(windowTitle, windowWidth, windowHeight);
    lineChart.setTitle(graphTitle);
    mySeriesKeys = seriesKeys;

    mySeries = new HashMap<>();
    for (String s: mySeriesKeys){
      Series series = new Series();
      series.setName(s);
      lineChart.getData().add(series);
      mySeries.put(s, series);
    }
  }

  public void addData(String seriesName, Data dataPoint){
    if (mySeriesKeys.contains(seriesName)){
//      Data point = new Data(myX, dataPoint);
      mySeries.get(seriesName).getData().add(dataPoint);
      myX++;
    }
  }

  private Stage makeStage(String windowTitle, int windowWidth, int windowHeight) {
    Stage stage = new Stage();
    stage.setTitle(windowTitle);
    lineChart = createChart();
    Scene scene  = new Scene(lineChart,windowWidth,windowHeight);
    stage.setScene(scene);
    stage.show();
    return stage;
  }

  private LineChart<Number, Number> createChart(){
    //defining the axes
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Number of Month");
    yAxis.setLabel("Number of Month");

    return new LineChart<Number,Number>(xAxis,yAxis);
  }
}
