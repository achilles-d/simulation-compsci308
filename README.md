simulation
====

This project implements a cellular automata (CA) simulator.

Names: Achilles Dabrowski, Caleb Sanford, Cemal Yagcioglu

### Timeline

Start Date: 25th January, 2020.

Finish Date: 3rd February, 2020.

Hours Spent: 80 

### Primary Roles
Caleb Sanford: all View classes
Achilles Dabrowski: all Model classes and enums 
Cemal Yagcioglu: Controller class, Control Exception class, Xml files, python xml generator. 

### Resources Used
For exception class, got help from BrowserException.java from browser project.
[For saving Xml File as output - was some help] (https://mkyong.com/java/how-to-create-xml-file-in-java-dom/) 
[How to implement a Hex grid] (https://www.redblobgames.com/grids/hexagons/)
[Creating shapes in JavaFX] (https://www.tutorialspoint.com/javafx/2dshapes_polygon.htm)


### Running the Program

Main class: /src/View/Main.java

Data files needed: 
- View/ViewResources/English.properties
- Controller/resources/Colors.properties
- Controller/resources/ControllerExceptionMessages.properties
- Controller/resources/SimulationConfiguration.properties
    SimulationCellShapes -> Options: "SQUARE_DIAGONAL" - square grid with eight-cell neighbors that includes diagonals.
     "SQUARE_NO_DIAGONAL" - square grid with four-cell neighbors that do **not** include diagonals.
     "HEXAGONAL" - hexagonal grid with six-cell neighbors that in
    WrapStyle -> Options: 
- resources/<USER CREATED>.xml


Features implemented:
- Simulation
    - Five simulations from basic along with Rock, Paper, Scissors and Foraging Ants
    - Hexagonal grid location option
    - Toroidal grid edges
    - Square cell neighborhoods with diagonals included
    - Square cell neighborhoods without diagonals 
    - The last four features can be changed in Controller/resources/SimulationConfiguration.properties 
- Controller:
    - Allows simulation initial configurations to be set by (These three can be tested using files in resources/initial_configuration_types/):
        -list of specific locations and states
        -completely randomly based on a total number of locations to occupy
        -randomly based on given probabilities
   - Allows user to save simulation as XML file that can be loaded back. (OutputDuringSimulationExample.xml)
   - It can be styled using SimulationConfiguration.properties. 
   - It does error checking both with file reading, and the values provided (whether they are integer, within the range etc) yet these error are not
      fully handled by the rest of the program. (So it is not a fully functioning feature.)
- Visualizer
    - Display Hex and Square Grids
    - Display a graph of stats about the populations of all of the "kinds" of cells over the time of the simulation
    - Allow users to interact with the simulation dynamically to create or change a state at a grid location by clicking
    on cells 



### Notes/Assumptions

Assumptions or Simplifications:

- In the foraging ants simulation, the top left cell of the grid is considered the location of the food. The bottom right cell
is considered the location of the ants' nest. 

Interesting data files:
In resources, folder initial_configuration_types includes 3 different types of files that can be used to initiate a simulation:
    - Regular, where each cell state is explicitly given.
    - Random, where each cell state is randomly chosen for the given width and height.
    - Weighted, where the user inputs the frequency of each possible state, and simulation is set up accordingly.
    
OutputDuringSimulationExample.xml file is an example xml created by Save Simulation button during simulation. 

Known Bugs:
- Error Handling
- New Tree in Spreading Fire Simulation - should not display in GUI; meant to be a marker on trees that had grown from empty
in the previous turn and could not catch fire in their first turn after appearing. NEW_TREE and TREE are displayed the same
way on the grid. 

Extra credit:
- XML python generator in resources file.  (Run in terminal with command: python python python_XML_generator.py)
  It offers a quick way of generating all different simulation types by setting up parameters and the configuration type.
  The output file is created on resources folder with the chosen file name.


### Impressions

Caleb: This project allowed me to learn a lot about coding 
with a group. Overall a good experience.

Cemal: This project had great combination of group work and individual work due to compartialized class
structure it offered. Great hands-on learning experience.

Achilles: This project greatly improved my ability to use Java idioms on the fly and compartmentalize different
"canonical" solutions to common design tasks. It also opened my eyes to the importance of using inheritance. 