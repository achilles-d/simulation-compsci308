package cellsociety;

import java.io.File;
import java.net.URL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Controller {
    int GRID_WIDTH;
    int GRID_HEIGHT;
    String [][] cellStatesGrid;
    private Grid myGrid;
    String simulationType;

    public Controller(){
//        File xmlDoc = new File("./resources/output.xml");
//        Document doc = parseXmlFile(xmlDoc);
        //PercolationGrid grid = new PercolationGrid(cellStatesGrid);
        //readParamsAndInitialize(doc);
//        printPretty(myGrid);
//        myGrid.update();
//        printPretty(myGrid);
//        myGrid.update();
//        printPretty(myGrid);
//        myGrid.update();
//        printPretty(myGrid);
    }

    public void printPretty(Grid grid) {
        for(int i = 0; i < cellStatesGrid.length; i++){
            for(int j = 0; j < cellStatesGrid[0].length; j++){
                String padded = String.format("%15s", grid.getCellState(i,j)).replace(' ', ' ');
                System.out.print(padded);
            }
            System.out.println("");
        }
        System.out.println("");
    }


    public Grid getGrid(){
        return myGrid;
    }

    public int getGridWidth(){
        return GRID_WIDTH;
    }

    public int  getGridHeight(){
        return GRID_HEIGHT;
    }


    public Document parseXmlFile(File xmlDoc){ //add a argument
        //Reader: game of life and percolation are same,
        //        segregation: +t satisfaction percentage,
        //        predator-prey: + fish number of turns, shark number of turns
        //        Spreading-fire: + probCatch, +proGrow
        //Generator: game of life has only two states: ALIVE OR DEAD,
        //           segregation +can have more than 3 states (X + O + [possible others] + EMPTY,
        //           predator-prey: empty, shark, fish
        //           Spreading-fire: Empty/Tree/Burning, Empty cells around the original screen

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlDoc);
            assignGridDimensions(doc);
            assignCellStates(doc);
            simulationType = getSimulationType(doc);
            return(doc);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void readParamsAndInitialize(Document doc) {
        switch(simulationType){
            case "PERCOLATION":
                myGrid = new PercolationGrid(cellStatesGrid);
                //initialize
                break;
            case "GAME OF LIFE":
                myGrid = new GameOfLifeGrid(cellStatesGrid);
                //initialize
                break;
            case "SEGREGATION":
                //double satisfactionPercentage = readDoubleParameter(doc, "satisfaction_percentage");
                //grid = new SegregationGrid(cellStatesGrid,satisfactionPercentage);
                //initialize
                break;
            case "PREDATOR/PREY":
                int minFishTurnToBreed = readIntegerParameter(doc, "min_fish_turn_to_breed");
                int maxSharkTurns = readIntegerParameter(doc, "max_shark_turns");
                int minSharkTurnsToBreed = readIntegerParameter(doc, "min_shark_turns_to_breed");
                myGrid = new PredatorPreyGrid(cellStatesGrid,minFishTurnToBreed,maxSharkTurns,minSharkTurnsToBreed);
                break;
            case "SPREADING FIRE":
                double probCatch = readDoubleParameter(doc, "prob_catch");
                double probGrow = readDoubleParameter(doc, "prob_grow");
                myGrid = new FireGrid(cellStatesGrid,probCatch,probGrow);
                break;
            default:
                myGrid = new PercolationGrid(cellStatesGrid);
        }
    }
    //check this one
    public String[][] getUpdatedGrid(PercolationGrid grid){
        grid.update();
        String[][] updatedGrid = new String [cellStatesGrid.length][cellStatesGrid[0].length];
        for(int i = 0; i < cellStatesGrid.length; i++){
            for(int j = 0; j < cellStatesGrid[0].length; j++){
                updatedGrid[i][j] = grid.getCellState(i,j);
            }
        }
        return updatedGrid;
    }

    private double readDoubleParameter(Document doc, String parameterName){
        String parameter = doc.getElementsByTagName(parameterName).item(0).getTextContent();
        return Double.parseDouble(parameter);
    }
    private int readIntegerParameter(Document doc, String parameterName){
        String parameter = doc.getElementsByTagName(parameterName).item(0).getTextContent();
        return (int) Double.parseDouble(parameter);
    }

    private String getSimulationType(Document doc) {
        return doc.getElementsByTagName("simulation_type").item(0).getAttributes().item(0).getTextContent();
    }

    private void assignGridDimensions(Document doc) {
        String widthString = doc.getElementsByTagName("width").item(0).getTextContent();
        String heightString = doc.getElementsByTagName("height").item(0).getTextContent();
        GRID_WIDTH = Integer.parseInt(widthString);
        GRID_HEIGHT = Integer.parseInt(heightString);
        cellStatesGrid = new String [GRID_HEIGHT][GRID_WIDTH];
    }

    private void assignCellStates(Document doc) {
        //String[][] cellStates = new String[][];
        NodeList cellList = doc.getElementsByTagName("cell");
        for(int i=0; i<cellList.getLength(); i++){
            Node cellNode = cellList.item(i);
            //System.out.println("Node name for student " +i+ " " +cellNode.getNodeName());
            if(cellNode.getNodeType()==Node.ELEMENT_NODE){
                Element cellElement = (Element) cellNode;
                //System.out.println("STATE: " + cellElement.getElementsByTagName("state").item(0).getTextContent());
                cellStatesGrid[i/GRID_HEIGHT][i%GRID_WIDTH] = cellElement.getElementsByTagName("state").item(0).getTextContent();
            }
        }
    }
}
