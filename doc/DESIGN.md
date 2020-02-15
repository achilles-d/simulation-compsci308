# Simulation Design Final
### Names

## Team Roles and Responsibilities

 * Caleb Sanford
    - View package

 * Achilles Dabrowski
    - Model package

 * Team Member #3


## Design goals 
- We kept the Model completely separate from the View.

#### What Features are Easy to Add

- Easy to add new simulations to the Model by simply creating new subclasses of the Grid class. The simulation rules for how 
a cell's state should change in the next stage. Corresponding [SimulationName]Cell enums are also easy to create
to store the state of each cell. 

- Controller???? -> Cemal


## High-level Design

- The visualization (View), handles all of the JavaFX code used for building 
the GUI and displaying the simulation.

- The Model package consists of the classes responsible for facilitating the simulation logic itself by enforcing the simulation
rules upon each cell in the simulation each time the simulation moves forward a step. 

    

#### Core Classes

- Visualizer: 
    - This class handles the main loop for the GUI, it is responsible for displaying the 
    user buttons as well as updating the simulation.
- GridAnimator: 
    - This class is responsible for keeping track of all the individual cells to 
    display for a simulation, as well as updating the graph.
- CellAnimator:
    - This code represents and draws a single cell of the simulation on the screen.
- Grid:
    - This class is a template for each of the simulations and provides methods and instance variables necessary to 
    facilitate a simulation from the perspective of the backend.  
- [SimulationName]Grid:
    - Each of these classes are subclasses of Grid that facilitate a specific CA simulation. Every one of them 
    knows the simulation's grid and cells and updates them to take a step forward in the simulation according to the particular
    simulation's rules.
- [SimulationName]Cell: 
    - Each of these enums correspond to a particular simulation. They are stored in the grid contained in the corresponding
    grid subclass. Each cell can take on one of the possible states of a cell in the simulation. 

## Assumptions that Affect the Design

- The simulation will be displayed on a GridPane.

- The top left cell of the grid is the location of the food and the bottom left cell of the grid is the location of the nest
in the Foraging Ants simulation.

#### Features Affected by Assumptions

- Shapes other than squares are not easy to display

- The nest and food location in Foraging Ants cannot be changed by the user 

## New Features HowTo

#### Easy to Add Features

- New graphs
- Different style GUIs
- Square cell neighborhoods besides diagonals only or both diagonals and up, down, left, and right

#### Other Features not yet Done

- Displaying multiple simulations at once. 
- Changing simulation parameters in GUI
- Langton's Loop
- Sugarscape
- Foraging Ants (not 100% working)
- infinite grid edges
- triangular grid 

