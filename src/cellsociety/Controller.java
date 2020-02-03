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
    public Controller(){
        //ReadXml("./resources/output.xml");
        PercolationGrid grid = new PercolationGrid(cellStatesGrid);
        printPretty(grid);
        grid.update();
        printPretty(grid);
    }

    private void printPretty(PercolationGrid grid) {
        for(int i = 0; i < cellStatesGrid.length; i++){
            for(int j = 0; j < cellStatesGrid[0].length; j++){
                System.out.println("Row: "+ i + " Col: "+ j + " " + grid.getCellState(i,j));
            }
        }
        System.out.println("");
    }

    public void ReadXml(String pathName){ //add a argument
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
            //File xmlDoc = new File("./resources/output.xml");
            File xmlDoc = new File(pathName);
            Document doc = builder.parse(xmlDoc);

            assignGridDimensions(doc);
            assignCellStates(doc);
            String simulationType = getSimulationType(doc);
            readParamsAndInitialize(doc, simulationType);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void readParamsAndInitialize(Document doc, String simulationType) {
        switch(simulationType){
            case "PERCOLATION":
                //initialize
                break;
            case "GAME OF LIFE":
                //initialize
                break;
            case "SEGREGATION":
                double satisfactionPercentage = readDoubleParameter(doc, "satisfaction_percentage");
                //initialize
                break;
            case "PREDATOR/PREY":
                double fishTurns = readDoubleParameter(doc, "fish_turns");
                double sharkTurns = readDoubleParameter(doc, "shark_turns");
                //initialize
                break;
            case "SPREADING FIRE":
                double probCatch = readDoubleParameter(doc, "prob_catch");
                double probGrow = readDoubleParameter(doc, "prob_grow");
                //initialize
                break;
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
        return Double.valueOf(parameter);
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
