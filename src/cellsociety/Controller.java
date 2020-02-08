package cellsociety;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @Author Cemal Yagcioglu
 * Parses XML File, initiates the grid.
 */

/*


Hey change xml files and python codes simulation type thing
 */
public class Controller {
    int GRID_WIDTH;
    int GRID_HEIGHT;
    String [][] cellStatesGrid;
    private Grid myGrid;
    private String simulationType;
    private String configurationType;
    private String cellInputType;  //Either "Regular", "Random", or "Weighted"
    private final String REGULAR = "Regular";
    private final String RANDOM = "Random";
    private final String WEIGHTED = "Weighted";
    private HashMap<String, Object> parameters;


    public Controller(){
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

    /**
     * This method reads the xml file, parse it, and assign the grid dimensions,
     * cell states, and simulation type to the instance variables. It returns
     * doc, which later can be used to initiate the simulation with these variables.
     * @param xmlDoc XML file.
     * @return doc
     */
    public Document parseXmlFile(File xmlDoc){ //add a argument
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlDoc);
            assignGridDimensions(doc);
            configurationType = getInitialConfigurationType(doc)
            assignCellStates(doc);
            simulationType = getSimulationType(doc);
            return(doc);
        }
         catch (ParserConfigurationException|SAXException|IOException e) {
            //Throw exception that tells that the document chosen cannot be read
        }

        return null;
    }

    private void assignGridDimensions(Document doc) {
        String widthString = doc.getElementsByTagName("width").item(0).getTextContent();
        String heightString = doc.getElementsByTagName("height").item(0).getTextContent();
        GRID_WIDTH = Integer.parseInt(widthString);
        GRID_HEIGHT = Integer.parseInt(heightString);
        checkWidthAndHeightValues();
        cellStatesGrid = new String [GRID_HEIGHT][GRID_WIDTH];
    }
    private String getInitialConfigurationType(Document doc){
        String initialConfigurationType = doc.getElementsByTagName("init_config_type").item(0).getAttributes().item(0).getTextContent();
        checkValidityOfConfigurationType(initialConfigurationType);
        return initialConfigurationType;
    }



    private void assignCellStates(Document doc) {
        if(configurationType=="Regular") {
            assignCellStatesRegularlyByParsingXml(doc);
        }
        else if(configurationType=="Random"){
            assignCellStatesRandomly(doc);
        }
        else if(configurationType=="Weighted"){
            assignCellStatesUsingWeights(doc);
        }
    }

    private void assignCellStatesUsingWeights(Document doc){
        String[] stateTypes = getStateTypes(doc);
        Double[] stateWeights = getStateWeights(doc);
        int numberOfCells = GRID_HEIGHT*GRID_WIDTH;
        Double[] cumulativeWeights  = cumulativeSumOperationForWeights(stateWeights);
        for(int i=0; i<numberOfCells; i++){
            String stateChosen = getWeightedRandomChoice(stateTypes,cumulativeWeights);
            cellStatesGrid[i/GRID_HEIGHT][i%GRID_WIDTH] = stateChosen;
        }
    }

    private Double[] cumulativeSumOperationForWeights(Double[] stateWeights){
        double sum=0;
        Double[] cumulativeWeights = new Double[stateWeights.length];
        for(int i=0; i<stateWeights.length;i++){
            sum += stateWeights[i];
            cumulativeWeights[i] = sum;
        }
    }
    private String getWeightedRandomChoice(String[] stateTypes, Double[] cumulativeWeights){
        double r = new Random().nextDouble();
        for(int i=0; i<stateTypes.length; i++){
            if(r<=cumulativeWeights[i]){
                return stateTypes[i];
            }
        }
    }

    private  void assignCellStatesRandomly(Document doc){
        String[] stateTypes = getStateTypes(doc);
        int numberOfCells = GRID_HEIGHT*GRID_WIDTH;
        for(int i=0; i<numberOfCells; i++){
            cellStatesGrid[i/GRID_HEIGHT][i%GRID_WIDTH] = stateTypes[new Random().nextInt(stateTypes.length)];
        }
    }

    private String[] getStateTypes(Document doc){
        NodeList stateTypesNodes = doc.getElementsByTagName("state_type");
        String[] stateTypes = new String [stateTypesNodes.getLength()];
        for(int i=0;i<stateTypesNodes.getLength();i++){
            Node stateNode = stateTypesNodes.item(i);
            if(stateNode.getNodeType()==Node.ELEMENT_NODE){
                Element stateElement = (Element) stateNode;
                String givenState = stateElement.getElementsByTagName("state").item(0).getTextContent();
                checkValidityOfCellState(givenState);
                stateTypes[i]=givenState;
            }
        }
        return stateTypes;
    }
    private Double[] getStateWeights(Document doc){
        NodeList stateTypesNodes = doc.getElementsByTagName("state_type");
        Double[] stateWeights = new Double [stateTypesNodes.getLength()];
        for(int i=0;i<stateTypesNodes.getLength();i++){
            Node stateNode = stateTypesNodes.item(i);
            if(stateNode.getNodeType()==Node.ELEMENT_NODE){
                Element stateElement = (Element) stateNode;
                String givenStateWeight = stateElement.getElementsByTagName("weight").item(0).getTextContent();
                Double stateWeightValue = Double.parseDouble(givenStateWeight);
                checkIfValueIsBetweenZeroAndOne(stateWeightValue);
                stateWeights[i]=stateWeightValue;
            }
        }
        return stateWeights;
    }
    private void assignCellStatesRegularlyByParsingXml(Document doc) {
        NodeList cellList = doc.getElementsByTagName("cell");
        checkNumberOfCells(cellList.getLength());
        for(int i=0; i<cellList.getLength(); i++){
            Node cellNode = cellList.item(i);
            if(cellNode.getNodeType()==Node.ELEMENT_NODE){
                Element cellElement = (Element) cellNode;
                String givenCellStateForThisIndex = cellElement.getElementsByTagName("state").item(0).getTextContent();
                checkValidityOfCellState(givenCellStateForThisIndex);
                cellStatesGrid[i/GRID_HEIGHT][i%GRID_WIDTH] = givenCellStateForThisIndex;
            }
        }
    }

    private String getSimulationType(Document doc) {
        String simulationType = doc.getElementsByTagName("simulation_type").item(0).getAttributes().item(0).getTextContent();
        checkValidityOfSimulationType(simulationType);
        return simulationType;
    }

    public void saveAsXml(){
        DocumentBuilderFactory outputDocumentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder outputDocumentBuilder = outputDocumentFactory.newDocumentBuilder();

        Document docOut = outputDocumentBuilder.newDocument();
        Element root = docOut.createElement("simulation");
        docOut.appendChild(root);

        Element simulationTypeElement = docOut.createElement("simulation_type");
        root.appendChild(simulationTypeElement);

        Attr simulationTypeAttribute = docOut.createAttribute("id");
        simulationTypeAttribute.setValue(simulationType);
        simulationTypeElement.setAttributeNode(simulationTypeAttribute);

        createAndAppendElement(docOut, simulationTypeElement, "author", "Simulation Group 6");
        createAndAppendElement(docOut, simulationTypeElement, "title", simulationType+" Simulation");
        createAndAppendElement(docOut, simulationTypeElement, "width", Integer.toString(GRID_WIDTH));
        createAndAppendElement(docOut, simulationTypeElement, "height", Integer.toString(GRID_HEIGHT));
        for key,value parameters
        createAndAppendElement(docOut, simulationTypeElement, "init_config_type", "regular");



    }

    private void createAndAppendElement(Document doc, Element simulationTypeElement, String elementTag, String elementText) {
        Element author = doc.createElement(elementTag);
        author.appendChild((doc.createTextNode(elementText)));
        simulationTypeElement.appendChild(author);
    }

    /**
     * This method uses document read by parseXmlFile
     * to initiate the correct simulation type. For some simulations
     * that require additional parameters to run, it also reads and
     * passes those arguments to the initializer.
     * @param doc Document that is parsed in parseXmlFile method.
     */
    public void readParamsAndInitialize(Document doc) {
        switch(simulationType){
            case "PERCOLATION":
                myGrid = new PercolationGrid(cellStatesGrid);
                break;
            case "GAME OF LIFE":
                myGrid = new GameOfLifeGrid(cellStatesGrid);
                break;
            case "SEGREGATION":
                setParamsAndInitializeSegregation(doc);
                break;
            case "PREDATOR/PREY":
                setParamsAndInitializePredatorPrey(doc);
                break;
            case "SPREADING FIRE":
                setParamsAndInitializeSpreadingFire(doc);
                break;
        }
    }

    private void setParamsAndInitializeSegregation(Document doc) {
        double satisfactionPercentage = readDoubleParameter(doc, "satisfaction_percentage");
        checkIfValueIsBetweenZeroAndOne(satisfactionPercentage);
        parameters.put("satisfaction_percentage",satisfactionPercentage);
        myGrid = new SegregationGrid(cellStatesGrid,satisfactionPercentage);
    }

    private void setParamsAndInitializeSpreadingFire(Document doc) {
        double probCatch = readDoubleParameter(doc, "prob_catch");
        double probGrow = readDoubleParameter(doc, "prob_grow");
        checkIfValueIsBetweenZeroAndOne(probCatch);
        checkIfValueIsBetweenZeroAndOne(probGrow);
        parameters.put("prob_catch",probCatch);
        parameters.put("prob_grow",probGrow);
        myGrid = new FireGrid(cellStatesGrid,probCatch,probGrow);
    }

    private void setParamsAndInitializePredatorPrey(Document doc) {
        int minFishTurnToBreed = readIntegerParameter(doc, "min_fish_turn_to_breed");
        int maxSharkTurns = readIntegerParameter(doc, "max_shark_turns");
        int minSharkTurnsToBreed = readIntegerParameter(doc, "min_shark_turns_to_breed");
        checkIfIntegerIsOneOrHigher(minFishTurnToBreed);
        checkIfIntegerIsOneOrHigher(maxSharkTurns);
        checkIfIntegerIsOneOrHigher(minSharkTurnsToBreed);
        parameters.put("min_fish_turn_to_breed",minFishTurnToBreed);
        parameters.put("max_shark_turns",maxSharkTurns);
        parameters.put("min_shark_turns_to_breed",minSharkTurnsToBreed);
        myGrid = new PredatorPreyGrid(cellStatesGrid,minFishTurnToBreed,maxSharkTurns,minSharkTurnsToBreed);
        
    }


    private double readDoubleParameter(Document doc, String parameterName){
        String parameter = doc.getElementsByTagName(parameterName).item(0).getTextContent();
        return Double.parseDouble(parameter);
    }
    private int readIntegerParameter(Document doc, String parameterName){
        String parameter = doc.getElementsByTagName(parameterName).item(0).getTextContent();
        return (int) Double.parseDouble(parameter);
    }


    //IMPORTANT: 1. ADD TRY AND CATCH FOR READER PARTS
    //           2. Create xml tests for all of this.

    private void checkNumberOfCells(int numberOfCells){
        if(numberOfCells!=GRID_HEIGHT*GRID_WIDTH){
            //throw error that tells that number of cells doesnot match grid size declared. (Or possibly cells are not named correctlu)
        }
    }

    private void checkValidityOfCellState(String givenCellState){
        //if(givenCellState is not in acceptableCellStates){
            //throw an error that tells XML contains an illegal cell state
       //}
    }

    private void checkValidityOfSimulationType(String simulationTypeInput) {
        //if(simulationTypeInput is not an acceptable simulationtype){
        //throw an error that tells, it is not an acceptable simulation type
        //}
    }
    private void checkValidityOfConfigurationType(String initialConfigTypeInput) {
        //if(initialConfigTypeInput is not an acceptable config type){
        //throw an error that tells, it is not an acceptable config type
        //}
    }

    private void checkWidthAndHeightValues(){
        if(GRID_WIDTH<=0 || GRID_HEIGHT<=0){
            //throw an error saying the value inputs for grid width/height contains an error
        }
    }

    private void checkIfValueIsBetweenZeroAndOne(Double valueIn){
        if(valueIn<0 || valueIn>1){
            //throw an error saying "Percentage Parameters are not within 0.0 to 1.0 range
        }
    }

    private void checkIfIntegerIsOneOrHigher(int integerInput){
        if(integerInput<=0){
            //throw an error saying invalid Integer input
        }
    }

}
