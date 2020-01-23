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
