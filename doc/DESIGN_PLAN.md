# Simulation Design Plan
### Team Number: 6
### Names
Achilles Dabrowski,
Caleb Sanford,
Cemal Yagcioglu 

## Introduction
The goal of this project is to write a Java program using OpenJFX that animates any 2D CA simulation. The simulation
parameters and starting configuration will be read from an XML file. The structure of our code will be broken up into 
three high-level concepts:
- __The Model:__ This is the backend controller that manages the rules of the simulation and the data associated with
it.
- __The View:__ This controls all of the visualizations created on the screen.
- __The Controller:__ Acts as an interface between the Model and the View. 

The design goals that will govern the three sections of code above are the following:
- __Encapsulation and Open/Closed Principle:__ Using classes to hide the implementation decisions and polymorphism to 
create code that depends on abstractions instead of concrete types.
- __Inheritance and Polymorphism:__ Create abstractions within the code to define general methods that can be 
implemented in multiple ways in separate concrete classes.
- __Developing a GUI program:__ Create a properly structured GUI that responds to user events while 
still maintaining clear separation from the model.

## Overview
### CRC Diagram
![alt text](sim-CRC.png)
* __Cell:__ Instances of the Cell class will keep track of the current state of each grid cell
in the simulation. Each Cell will also have a pointer to each of its neighbors
so that it can check the state of its neighbors to help the program select the appropriate
state for the next step in the simulation based on the simulation's rules. Each
Cell will have public method *char getState()* method to allow to other classes what state the Cell
is in at the current point of the simulation by returning the character that signfies
the current state of the Cell. There will be five child classes of Cell that each
correspond to the five different types of simulations that will be implemented. 
* __Grid:__ Instances of the Grid class will contain a 2D array (or, alternatively,
and ArrayList of ArrayList<Cell>) of Cell objects
corresponding to the locations of the cells in each grid as appropriate for each
step in the simulation. The Grid class will have the public method *char getState(int i, int j)*  
that returns the character signifying the current state of the cell in the ith 
row and jth column of the grid. The Grid class will also have the public method
*void update()* to update each of the Cells in the grid according the rules of the current
simulation to move the simulation forward by one step. There will be five child 
classes of Grid that each correspond to the five different types of simulations 
that will be implemented. In addition, the Grid class will have the public method
*initialize(String configFileDir)* that will create of 2D of Cells in the initial positions
and states specified by the XML file in the directory contained in configFilDir. 
This will called by the Grid's constructor, but can also be called after the simulation
has begun if the user wishes to change the simulation to a new initial state, but 
maintain the same type of simulation as that which was just running. 
* __Visualizer__: Instances of the animator class are responsible for allowing
the user to interact with the user to move the simulation forward at the speed
specified by the user or to the amount of steps forward in time specified by the user. 
The Visualizer class will create an instance of Grid's child class that corresponds
to the type of simulation selected in the GUI, and will pass along the appropriate
XML containing the initial grid configuration to the Grid's constructor. In addition,
the Visualizer will contain public static final Paint variables that contain the appropriate
colors corresponding to each of the possible states of the cells. When the simulation
is initialized or a step is taken, the visualizer will fill the cells of the grid.
The private method *void step(int n)* will move the simulation n steps forward by
calling update() on the Grid object contained for the simulation n times with the help
of a loop. step() will be called when the user clicks the appropriate buttons
on the UI described below to move the simulation forward. 

## User Interface
We will create a user interface that follows the basic layout shown below. This will also include a file browser 
allowing the user to graphically choose the XML file to load. Any errors or other messages will be displayed on the 
GUI.

![alt text](CS308%20Simulation%20GUI.jpg)

## Design Details


## Design Considerations

#### Components

#### Use Cases


## Team Responsibilities

 * Achilles Dabrowski
 ** 

 * Caleb Sanford
 ** Visualizer, namely the actual GUI and the Visualizer class
** Animator 

 * Cemal Yagcioglu
 ** 

