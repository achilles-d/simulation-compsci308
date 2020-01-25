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

 * Caleb Sanford

 * Cemal Yagcioglu 

