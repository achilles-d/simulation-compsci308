package controller;

import Model.FireGrid;
import Model.ForagingAntsGrid;
import Model.GameOfLifeGrid;
import Model.Grid;
import Model.PercolationGrid;
import Model.PredatorPreyGrid;
import Model.RockPaperScissorsGrid;
import Model.SegregationGrid;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.ResourceBundle;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
Add try and catch or exception handler to save XML
 */
public class Controller {

    public static final String REGULAR_INIT_TYPE = "Regular";
    public static final String PERCOLATION = "PERCOLATION";
    public static final String GAME_OF_LIFE = "GAME OF LIFE";
    public static final String SEGREGATION = "SEGREGATION";
    public static final String PREDATOR_PREY = "PREDATOR/PREY";
    public static final String SPREADING_FIRE = "SPREADING FIRE";
    public static final String ROCK_PAPER_SCISSORS = "ROCK PAPER SCISSORS";
    public static final String FORAGING_ANTS = "FORAGING ANTS";
    public static final String[] SIMULATION_TYPES_ARRAY = {PERCOLATION,GAME_OF_LIFE,SEGREGATION,PREDATOR_PREY,
                                                            SPREADING_FIRE,ROCK_PAPER_SCISSORS,FORAGING_ANTS};
    private static int GRID_WIDTH;
    private static int GRID_HEIGHT;
    private static int NUMBER_OF_CELLS;
    String [][] cellStatesGrid;
    private Grid myGrid;
    private String simulationType;
    private String configurationType;
    private String simulationCellShapes;
    private String simulationWrapStyle;

    private  String[] INIT_CONFIGS;
    private String cellInputType;  //Either "Regular", "Random", or "Weighted"
    private final String REGULAR = "Regular";
    private final String RANDOM = "Random";
    private final String WEIGHTED = "Weighted";
    private final String [] INIT_CONFIG_TYPES_ARRAY = {REGULAR,RANDOM,WEIGHTED};
    private HashMap<String, String> parameters = new HashMap<>();
    private ResourceBundle cellTypeResources;
    private ResourceBundle simulationConfigurationResources;
    private ResourceBundle exceptionMessagesResources;
    private static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";


    public Controller(){
        cellTypeResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Colors");
        simulationConfigurationResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "SimulationConfiguration");
        exceptionMessagesResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ControllerExceptionMessages");
        simulationCellShapes = simulationConfigurationResources.getString("SimulationCellShapes");
        simulationWrapStyle = simulationConfigurationResources.getString("WrapStyle");

        String mypath = "/Users/Cemal/Desktop/2019_Spring_Classes/2020 Spring/CS308/simulation_team06/resources/initial_configuration_types/outputWeighted.xml";
        File delete = new File(mypath);
        Document del = parseXmlFile(delete);
        readParamsAndInitialize(del);
        printPretty(myGrid);
        //String mypath = "/Users/Cemal/Desktop/2019_Spring_Classes/2020 Spring/CS308/simulation_team06/resources/initial_configuration_types/outputWeighted.xml";
        //File delete = new File(mypath);
        //Document del = parseXmlFile(delete);
        //readParamsAndInitialize(del);
        //printPretty(myGrid);
        //saveAsXml();
    }

    public void printPretty(Grid grid) {
        for(int i = 0; i < cellStatesGrid.length; i++){
            for(int j = 0; j < cellStatesGrid[0].length; j++){
                String padded = String.format("%15s", myGrid.getCellState(i,j)).replace(' ', ' ');
                System.out.print(padded);
                //System.out.print(grid.getCellStates(i,j));
            }
            System.out.println("");
        }
        System.out.println("");
    }


    public String getCellState(int i, int j){
        return myGrid.getCellState(i,j);
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
            configurationType = getInitialConfigurationType(doc);
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
        NUMBER_OF_CELLS = GRID_HEIGHT*GRID_WIDTH;
    }

    private String getInitialConfigurationType(Document doc){
        String initialConfigurationType = doc.getElementsByTagName("init_config_type").item(0).getTextContent();
        checkValidityOfConfigurationType(initialConfigurationType);
        return initialConfigurationType;
    }

    private void cycleCellState(int i, int j){
        ArrayList<String> possibleStates = myGrid.getCellStates();
        String cellsState = myGrid.getCellState(i,j);
        int stateIndex = possibleStates.indexOf(cellsState);
        String nextPossibleState = possibleStates.get((stateIndex+1)%possibleStates.size());
        myGrid.setCellState(i,j,nextPossibleState);
    }

    private void assignCellStates(Document doc) {
        if(configurationType.equals(REGULAR_INIT_TYPE)) {
            assignCellStatesRegularlyByParsingXml(doc);
        }
        else if(configurationType.equals("Random")){
            assignCellStatesRandomly(doc);
        }
        else if(configurationType.equals("Weighted")){
            assignCellStatesUsingWeights(doc);
        }
    }

    private void assignCellStatesUsingWeights(Document doc){

        String[] stateTypes = getStateTypes(doc);
        Double[] stateWeights = getStateWeights(doc);

        Double[] cumulativeWeights  = cumulativeSumOperationForWeights(stateWeights);
        for(int i=0; i<NUMBER_OF_CELLS; i++){
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
        return cumulativeWeights;
    }
    private String getWeightedRandomChoice(String[] stateTypes, Double[] cumulativeWeights){
        double r = new Random().nextDouble();
        for(int i=0; i<stateTypes.length; i++){
            if(r<=cumulativeWeights[i]){
                return stateTypes[i];
            }
        }
        return "";
    }

    private  void assignCellStatesRandomly(Document doc){
        String[] stateTypes = getStateTypes(doc);
        for(int i=0; i<NUMBER_OF_CELLS; i++){
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
                checkIfValueIsBetweenZeroAndOne(stateWeightValue, "Occurance rate weight");
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
        DocumentBuilder outputDocumentBuilder = null;
        try {
            outputDocumentBuilder = outputDocumentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

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
        createAndAppendParameters(docOut, simulationTypeElement);
        createAndAppendElement(docOut, simulationTypeElement, "init_config_type", "regular");


        for(int i=0;i<NUMBER_OF_CELLS;i++) {
            Element cellElement = docOut.createElement("cell");
            simulationTypeElement.appendChild(cellElement);

            Attr cellAttribute = docOut.createAttribute("id");
            cellAttribute.setValue(Integer.toString(i));
            cellElement.setAttributeNode(cellAttribute);
            createAndAppendElement(docOut, cellElement, "state", cellStatesGrid[i/GRID_HEIGHT][i%GRID_WIDTH]);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformerDoc = null;
        try {
            transformerDoc = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(docOut);
            String outPathFolder = "/Users/Cemal/Desktop/2019_Spring_Classes/2020 Spring/CS308/simulation_team06/output/";
            String date = new SimpleDateFormat("dd-MM-yyyy:HH-mm").format(new Date());
            String outFileName = "outputXML_" + date;
            String mypath = outPathFolder + outFileName;
            StreamResult result = new StreamResult(new File(mypath));
        }
        catch (Exception e){
            throw new ControllerException(exceptionMessagesResources.getString("XmlSaveError"));
        }

        System.out.println("File saved!");


    }

    private void createAndAppendParameters(Document docOut, Element simulationTypeElement) {
        Iterator parameterIterator = parameters.entrySet().iterator();
        while (parameterIterator.hasNext()) {
            HashMap.Entry<String,String> pair = (HashMap.Entry)parameterIterator.next();
            createAndAppendElement(docOut,simulationTypeElement,pair.getKey(),pair.getValue());
            parameterIterator.remove(); // avoids a ConcurrentModificationException
        }
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
        System.out.println(simulationType);
        switch(simulationType){
            case PERCOLATION:
                myGrid = new PercolationGrid(cellStatesGrid,simulationCellShapes,simulationWrapStyle);
                break;
            case GAME_OF_LIFE:
                myGrid = new GameOfLifeGrid(cellStatesGrid,simulationCellShapes,simulationWrapStyle);
                break;
            case SEGREGATION:
                setParamsAndInitializeSegregation(doc);
                break;
            case PREDATOR_PREY:
                setParamsAndInitializePredatorPrey(doc);
                break;
            case SPREADING_FIRE:
                setParamsAndInitializeSpreadingFire(doc);
                break;
            case ROCK_PAPER_SCISSORS:
                setParamsAndInitializeRockPaperScissors(doc);
                break;
            case FORAGING_ANTS:
                myGrid = new ForagingAntsGrid(cellStatesGrid,simulationCellShapes,simulationWrapStyle);

        }
    }

    private void setParamsAndInitializeSegregation(Document doc) {
        double satisfactionPercentage = readDoubleParameter(doc, "satisfaction_percentage");
        checkIfValueIsBetweenZeroAndOne(satisfactionPercentage, "satisfaction percentage");
        parameters.put("satisfaction_percentage",Double.toString(satisfactionPercentage));
        myGrid = new SegregationGrid(cellStatesGrid,simulationCellShapes,simulationWrapStyle,satisfactionPercentage);
    }

    private void setParamsAndInitializeRockPaperScissors(Document doc){
        int threshold = readIntegerParameter(doc,"threshold");
        checkIfIntegerIsOneOrHigher(threshold,"threshold");
        parameters.put("threshold",Integer.toString(threshold));
        myGrid = new RockPaperScissorsGrid(cellStatesGrid,simulationCellShapes,simulationWrapStyle,threshold);
    }

    private void deletePrintStringArr(String[][] in){
        for(int i=0;i<in.length;i++){
            for(int j=0;j<in[0].length;j++){
                String padded = String.format("%15s", in[i][j]).replace(' ', ' ');
                System.out.print(padded);
                //System.out.print(grid.getCellStates(i,j));
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private void setParamsAndInitializeSpreadingFire(Document doc) {
        double probCatch = readDoubleParameter(doc, "prob_catch");
        double probGrow = readDoubleParameter(doc, "prob_grow");
        checkIfValueIsBetweenZeroAndOne(probCatch, "Probability of catching fire");
        checkIfValueIsBetweenZeroAndOne(probGrow, "Probability of growing tree");
        parameters.put("prob_catch",Double.toString(probCatch));
        parameters.put("prob_grow",Double.toString(probGrow));
        myGrid = new FireGrid(cellStatesGrid,simulationCellShapes,simulationWrapStyle,probCatch,probGrow);
    }

    private void setParamsAndInitializePredatorPrey(Document doc) {
        int minFishTurnToBreed = readIntegerParameter(doc, "min_fish_turn_to_breed");
        int maxSharkTurns = readIntegerParameter(doc, "max_shark_turns");
        int minSharkTurnsToBreed = readIntegerParameter(doc, "min_shark_turns_to_breed");
        checkIfIntegerIsOneOrHigher(minFishTurnToBreed, "minFishTurnToBreed");
        checkIfIntegerIsOneOrHigher(maxSharkTurns, "maxSharkTurns");
        checkIfIntegerIsOneOrHigher(minSharkTurnsToBreed, "minSharkTurnsToBreed");
        parameters.put("min_fish_turn_to_breed",Integer.toString(minFishTurnToBreed));
        parameters.put("max_shark_turns",Integer.toString(maxSharkTurns));
        parameters.put("min_shark_turns_to_breed",Integer.toString(minSharkTurnsToBreed));
        myGrid = new PredatorPreyGrid(cellStatesGrid,simulationCellShapes,simulationWrapStyle,minFishTurnToBreed,maxSharkTurns,minSharkTurnsToBreed);
        
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
        if(numberOfCells!=NUMBER_OF_CELLS){
            throw new ControllerException(exceptionMessagesResources.getString("CellNoMismatch"));
        }
    }

    private void checkValidityOfCellState(String givenCellState){
        try {
            cellTypeResources.getString(givenCellState);
        }
        catch (Exception e){
            throw new ControllerException(exceptionMessagesResources.getString("IllegalCellState"));
        }
    }

    private void checkValidityOfSimulationType(String simulationTypeInput) {
        if(Arrays.stream(SIMULATION_TYPES_ARRAY).anyMatch(simulationTypeInput::equals)){
            throw new ControllerException(exceptionMessagesResources.getString("SimulationTypeError"));

        }
    }
    private void checkValidityOfConfigurationType(String initialConfigTypeInput) {
        if(Arrays.stream(INIT_CONFIG_TYPES_ARRAY).anyMatch(initialConfigTypeInput::equals)) {
            throw new ControllerException(exceptionMessagesResources.getString("ConfigurationTypeError"));
        }
    }

    private void checkWidthAndHeightValues(){
        if(GRID_WIDTH<=0 || GRID_HEIGHT<=0){
            throw new ControllerException(exceptionMessagesResources.getString("NegativeDimensionError"));
        }
    }

    private void checkIfValueIsBetweenZeroAndOne(Double valueIn, String variableName){
        if(valueIn<0 || valueIn>1){
            throw new ControllerException(exceptionMessagesResources.getString("WrongDoubleProvided") +": "+ variableName);
        }
    }

    private void checkIfIntegerIsOneOrHigher(int integerInput, String variableName){
        if(integerInput<=0){
            throw new ControllerException(exceptionMessagesResources.getString("WrongIntegerProvided")+": "+variableName);
        }
    }

}
