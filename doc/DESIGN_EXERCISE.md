## Inheritance Review

#### Bricks
```java
class Brick {
    // get the Image of the brick to dispaly on the screen
    pubic ImageView getView()

    // return the health of the brick
    public int getBrickHealth ()

    // this decreases the brick health
    public int updateHealth ()
} 
```

#### Power Ups
```java
class Power {
    // get the Image of the brick to dispaly on the screen
    pubic ImageView getView()

    // drop a powerup from a brick
    public void drop (ImageView Brick)

    // return false when time is up
    public boolean checkTimeout ()
    
    // change the position of the powerup on the screen.
    public void move (int elapsedTime)
} 
```

#### Levels
```java
class Level {
    // read the brick config txt file
    public Brick[] readLevelConfig (File textFile)
} 
```


#### Simulation High Level Design
* The simulation copies the 2D matrix of cell information before updating each cells, 
and thus can refer to the old copy for each neighbor calculation. To know its neighbours, it can just call
x+-1 and y+-1 locations. 

* The main method gets the input for the type of simulation and creates the subclasses accordingly. Thus each cell,
 can call update() method. 

* Within the methods, team seemed to agree that we can use 2D array to implement the grid. However to allow flexibility within classes,
 and methods, those methods can return iterable of iterable object. Thus, developers only need to know that the return object is of iterable type.

* Everything specific to the simulation should be on configuration file. These can be the size of the grid, the start position, the probability of neighbour interaction etc.

* Simulation is updated by using the grid output 
(or the updated grid) with the simulator method. Simulator method should accept (or use the instance variable) of the grid information, thus once updated
it can be called again easily. 
