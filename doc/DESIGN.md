# Simulation Design Final
### Names

## Team Roles and Responsibilities

 * Caleb Sanford
    - View package

 * Team Member #2

 * Team Member #3


## Design goals 
- We kept the Model completely separate from the View.

#### What Features are Easy to Add

- Easy to add new simulation


## High-level Design

- The visualization (View), handles all of the JavaFX code used for building 
the GUI and displaying the simulation.
    

#### Core Classes

- Visualizer: 
    - This class handles the main loop for the GUI, it is responsible for displaying the 
    user buttons as well as updating the simulation.
- GridAnimator: 
    - This class is responsible for keeping track of all the individual cells to 
    display for a simulation, as well as updating the graph.
- CellAnimator:
    - This code represents and draws a single cell of the simulation on the screen.

## Assumptions that Affect the Design

- The simulation will be displayed on a GridPane.

#### Features Affected by Assumptions

- Shapes other than squares are not easy to display

## New Features HowTo

#### Easy to Add Features

- New graphs
- Different style GUIs

#### Other Features not yet Done

- Displaying multiple simulations at once. 
- Changing simulation parameters in GUI

