[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/k-uhza1_)
# MP1a: The Land of Erehwon

In the land of Erehwon, which can be represented using a square grid, there are two competing factions, the Reds and the Blues. Each cell in the Erehwon grid is occupied by either a Red or a Blue or is vacant. 

Any resident of Erehwon is happy if and only if a certain fraction of their neighbours are like them (Red or Blue). The neighbourhood of a cell $C$ is all the cells that can be reached in a fixed number of steps from $C$ and one can travel vertically, horizontally or diagonally for each step. If the neighbourhood is the set of cells that are one step away then $C$ may have up to 8 neighbours. If the happiness threshold is 0.375 and $C$ has 8 neighbouring cells then the resident of $C$ is happy if and only if 3 of the neighbouring cells are occupied by someone from the same faction.

An unhappy person simply decides to move. At time $t$, if there are 8 vacant cells and 7 unhappy people then each of them moves to one of the vacant cells (chosen at random). If the number of vacant cells is fewer than the number of unhappy people then one first selects as many people as there are vacant slots and then moves these people to those vacant slots, at random.

*We want to understand the migration patterns of the residents of Erehwon, and whether they eventually reach a stable arrangement (and what such an arrangement looks like). To this end, you will complete several tasks that eventually result in a simulation — and visualization — of life in Erehwon.*

<aside>
☝🏽 Almost all the code that you will have to write would go in `RedBlueGrid.java`. In some cases, it may be helpful to create some additional files for helper code. You should write tests for ensuring correctness and completeness of each task.

</aside>

### Task 1: Constructing a RedBlueGrid

The first task is to construct an instance of Erehwon. You should implement the constructor and the `getColor` method as well as the `setColor` method. 

```java
    public RedBlueGrid(int size,
                       int neighborhoodDistance,
                       double fractionVacant,
                       double fractionRed,
                       double happinessThreshold) {
				// TODO: Implement this constructor
    }

    // do nothing for cells that are not on the grid
    public Color getColor(int row, int col) {
        // TODO: Implement this method
        return null;
    }

		// can only set a valid colour for this project
    // do nothing for cells that are not on the grid
		// this method may violate the fractional allocation
		// of space but we are still implementing it
    public boolean setColor(int row, int col, Color color) {
        // TODO: Implement this method
        return false; // you may need to change this
    }
```

### Task 2: Adding Related Methods

Implement `shiftColor` and `reset`.

```java
		// for rotating through the colours in the order
    // WHITE -> RED -> BLUE -> WHITE -> ...
    public void shiftColor(int row, int col) {
        // TODO: Implement this method
    }

    // recolour the cells with the given
    // fraction of vacant cells, the fraction
    // of non-vacant cells that are red
    // (the rest are blue cells),
    // and a possibly new happiness threshold
    public void reset(double fractionVacant,
                      double fractionRed,
                      double happinessThreshold) {
        // TODO: Implement this method
    }
```

### Task 3: Determining Happiness

After setting the stage, you should implement methods to determine the happiness level in Erehwon.

```java
    // is the resident at the given cell happy?
    public boolean isHappy(int row, int col) {
        // TODO: Implement this method
        return false;
    }

    // what fraction of the erehwon residents are happy?
    public double fractionHappy() {
        // TODO: Implement this method
        return -1;
    }
```

### Task 4: Simulating the Movement of Erehwon’s Residents

Now you simulate the quest for happiness on Erehwon. After setting up the initial conditions, the unhappy residents are going to move to (randomly chosen) vacant cells at each time step. You should implement a single step simulation and a multi-step simulation.

```java
    // simulate exactly one time step of movement
    public void oneTimeStep() {
        // TODO: Implement this method
    }

    // simulate multiple time steps
    public void simulate(int numSteps) {
        // TODO: Implement this method
    }
```

### Task 5: Optimizing the Movement Strategy

After implementing the random move strategy, consider how long it takes to reach a state when everyone is happy. (Is this always possible?) What happens when the happiness threshold is set to 0.7? What happens when one faction is more dominant (0.75 vs. 0.25 presence)? After some analysis, determine a strategy that will lead to happiness as soon as possible. Residents can only move to vacant cells but the choice does not have to be random.

For this task, you can change `simulate` but leave `oneTimeStep` unchanged (`oneTimeStep` should use the randomization approach).

### Comments

1. **GUI**: You have been provided with a simple graphical user interface for visualizing Erehwon. The GUI is implemented in `RedBlueGridUI` and can be invoked by running `main`. As you complete the methods in `RedBlueGrid`, you will be able to use the GUI. The GUI is rudimentary and you are welcome to improve on it.
2. **Random numbers**: This mini-project relies on generating random numbers. You can easily do so by importing `java.util.Random`. You can then create and use a random number generator as follows (and consult the [documentation](https://docs.oracle.com/javase/8/docs/api/java/util/Random.html)):

```java
Random rng = new Random(); // create a random number generator
int x = rng.nextInt(100); // generate a random number between 0 and 99, incl.
```

1. **Focus on testing and writing specifications**: Very rudimentary outlines have been provided. You have to sharpen the specifications and add test cases. Your test cases should cover most of your code. Read about how you can measure the code coverage of your tests [with IntelliJ](https://www.jetbrains.com/help/idea/code-coverage.html) and with a Gradle plugin called [JaCoCo](https://www.jetbrains.com/help/idea/running-test-with-coverage.html#run-config-with-coverage).

### Outcomes and Assessment

See Notion.