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
        ReadXml();
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

    public void ReadXml(){

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            //File xmlDoc = new File("/Users/Cemal/Desktop/2019_Spring_Classes/2020 Spring/CS308/simulation_team06/resources/output.xml");
            File xmlDoc = new File("./resources/output.xml");
            Document doc = builder.parse(xmlDoc);
            //Read root element
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            String widthString = doc.getElementsByTagName("width").item(0).getTextContent();
            String heightString = doc.getElementsByTagName("height").item(0).getTextContent();
            GRID_WIDTH = Integer.parseInt(widthString);
            GRID_HEIGHT = Integer.parseInt(heightString);
            cellStatesGrid = new String [GRID_HEIGHT][GRID_WIDTH];
            System.out.println(widthString + " " + heightString);
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
            //
            //Node simulation = doc.getElementById("GameOfLife");
            System.out.println("CELL STATES GRID : " );
            for(int i=0; i<cellList.getLength(); i++){
                System.out.println("#######");
                System.out.println("Row: "+i/GRID_WIDTH+ " Col: "+i%GRID_WIDTH);
                System.out.println(cellStatesGrid[i/GRID_HEIGHT][i%GRID_WIDTH]);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Note from the class: Find the XMLParser.java document and check
        private DocumentBuilder getDocumentBuilder(){
            try{
                return DocumentBuilderFactory.newInstance().newDocumentBuilder();
            }
            catch (ParserConfigurationException e) {

            }



         */

    }
}
