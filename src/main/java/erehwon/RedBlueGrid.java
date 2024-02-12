package erehwon;

import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;

public class RedBlueGrid {
    private static final Color[] COLORS = {Color.WHITE, Color.RED, Color.BLUE};
    private Color[][] grid;
    private int size;
    private int neighborhoodDistance;
    private double happinessThreshold;

    /**
     * creates a new Erehwon grid
     * @param size length and width of grid
     * @param neighborhoodDistance distance between neighbor residents
     * @param fractionVacant fraction of vacant squares in grid
     * @param fractionRed fraction of red residents in grid
     * @param happinessThreshold happiness threshold of grid
     */
    public RedBlueGrid(int size,
                       int neighborhoodDistance,
                       double fractionVacant,
                       double fractionRed,
                       double happinessThreshold) {
        grid = new Color[size][size];
        this.size = size;
        this.neighborhoodDistance = neighborhoodDistance;
        this.happinessThreshold = happinessThreshold;

        //grid parameters
        int squares = size * size;
        int reds = (int) Math.round((100.0 - fractionVacant) * squares * fractionRed * 0.0001);
        int vacants = (int) Math.round(squares * fractionVacant * 0.01);
        int blues = squares - reds - vacants;
        int bluesPlaced = 0;
        int redsPlaced = 0;
        int vacantsPlaced = 0;
        int row = 0;
        int col = 0;
        Random rng = new Random();
        int x;
        for (int i = 0; i < squares; i++) {
            x = rng.nextInt((reds - redsPlaced) + (blues - bluesPlaced) + (vacants - vacantsPlaced));
            if (x < (reds - redsPlaced) && redsPlaced < reds) {
                redsPlaced++;
                setColor(row, col, Color.RED);
                row++;
            } else if (x >= ((reds - redsPlaced) + (blues - bluesPlaced)) && vacantsPlaced < vacants) {
                vacantsPlaced++;
                setColor(row, col, Color.WHITE);
                row++;
            } else if (bluesPlaced < blues) {
                bluesPlaced++;
                setColor(row, col, Color.BLUE);
                row++;
            }

            if (row == size) {
                row = 0;
                col++;
            }

        }
    }

    /**
     * Gets the color of the square at a certain row and column
     *
     * @param row the row in which the square resides
     * @param col the column in which the square resides
     * @return the color of the square at the specified row and column
     */
    public Color getColor(int row, int col) {
        if (row >= size | col >= size | col < 0 | row < 0) {
            return null;
        }
        return grid[row][col];
    }

    /**
     * Sets the color of the square, specfied by row and col, to the passed color parameter
     *
     * @param row   the row in which the square resides
     * @param col   the column in which the square resides
     * @param color the color to set the square to; must be red, blue, or white
     * @return true if color was changed; false if the selected row and column are out of bounds of the grid
     */
    public boolean setColor(int row, int col, Color color) {
        if (row >= size | col >= size | col < 0 | row < 0) {
            return false;
        }
        if (color == Color.BLUE || color == Color.WHITE || color == Color.RED) {
            grid[row][col] = color;
            return true;
        } else {
            throw new IllegalArgumentException("Color not allowed!");
        }

    }

    /**
     * changes the color of the square at a certain row and column. follows the order: white -> red -> blue -> white ->...
     *
     * @param row the row in which the square resides; 0 <= row <= grid size - 1
     * @param col the column in which the square resides; 0 <= row <= grid size - 1
     */
    public void shiftColor(int row, int col) {
        if (grid[row][col] == Color.WHITE) {
            grid[row][col] = Color.RED;
        } else if (grid[row][col] == Color.RED) {
            grid[row][col] = Color.BLUE;
        } else {
            grid[row][col] = Color.WHITE;
        }

    }

    /**
     * resets the grid randomly with a certain fraction of white, red and blue squares, as well as a specified happiness threshold
     *
     * @param fractionVacant     fraction of squares that will be vacant in the resetted grid
     * @param fractionRed        fraction of squares that will be red in the resetted grid
     * @param happinessThreshold the happiness threshold of the resetted grid
     */
    public void reset(double fractionVacant,
                      double fractionRed,
                      double happinessThreshold) {
        this.happinessThreshold = happinessThreshold;
        int squares = size * size;

        int vacants = (int) Math.round(squares * fractionVacant);
        int reds = (int) Math.round((1.0 - fractionVacant) * squares * fractionRed);
        int blues = squares - reds - vacants;
        int bluesPlaced = 0;
        int redsPlaced = 0;
        int vacantsPlaced = 0;
        int row = 0;
        int col = 0;
        Random rng = new Random();
        int x;
        for (int i = 0; i < squares; i++) {
            x = rng.nextInt((reds - redsPlaced) + (blues - bluesPlaced) + (vacants - vacantsPlaced));
            if (x < (reds - redsPlaced) && redsPlaced < reds) {
                redsPlaced++;
                setColor(row, col, Color.RED);
                row++;
            } else if (x >= ((reds - redsPlaced) + (blues - bluesPlaced)) && vacantsPlaced < vacants) {
                vacantsPlaced++;
                setColor(row, col, Color.WHITE);
                row++;
            } else if (bluesPlaced < blues) {
                bluesPlaced++;
                setColor(row, col, Color.BLUE);
                row++;
            }

            if (row == size) {
                row = 0;
                col++;
            }
        }
    }

    /**
     * determines if the square at the specified row and column is happy; square must either be red or blue
     *
     * @param row the row in which the square resides in; o <= row <= grid size - 1
     * @param col the column in which the square resides in; o <= row <= grid size - 1
     * @return true if the square is happy; false if it is unhappy
     */
    public boolean isHappy(int row, int col) {
        Color current = getColor(row, col);
        int nonColors = 0;
        int colors = -1;
        int vacant = 0;
        for (int i = row - neighborhoodDistance >= 0 ? row - neighborhoodDistance : 0; row + neighborhoodDistance < size ? i <= row + neighborhoodDistance : i < size; i++) {
            for (int j = col - neighborhoodDistance >= 0 ? col - neighborhoodDistance : 0; col + neighborhoodDistance < size ? j <= col + neighborhoodDistance : j < size; j++) {
                if (grid[i][j] == Color.WHITE) {
                    vacant++;
                } else if (grid[i][j] == current) {
                    colors++;
                } else {
                    nonColors++;
                }

            }
        }

        double total = (double) nonColors + (double) vacant + (double) colors;
        double currentHappiness = (double) colors / total;
        if (currentHappiness >= happinessThreshold) {
            return true;
        }
        return false;
    }

    /**
     * calculates fraction of happy Erehwon residents
     *
     * @return fraction of happy Erehwon residents
     */
    public double fractionHappy() {
        double happyOnes = 0.0;
        double whites = 0.0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (getColor(i, j) == Color.WHITE) {
                    whites++;
                } else if (isHappy(i, j)) {
                    happyOnes += 1.0;
                }
            }
        }
        double total = (size * size) - whites;
        return happyOnes / total;
    }


    /**
     * simulates one time step of unhappy Erehwon residents randomly moving to a vacant spot
     */
    public void oneTimeStep() {

        ArrayList<int[]> unHappyCords = new ArrayList<>();
        ArrayList<int[]> vacantCords = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == Color.WHITE) {
                    int[] cords = {i, j};
                    vacantCords.add(cords);
                    continue;
                }
                if (!isHappy(i, j)) {
                    int[] cords = {i, j};
                    unHappyCords.add(cords);
                }
            }
        }

        Random rand = new Random();

        while (unHappyCords.size() > 0 && vacantCords.size() > 0) {
            int unHappyIdx = rand.nextInt(unHappyCords.size());
            int vacantIdx = rand.nextInt(vacantCords.size());
            int[] unHappyCord = unHappyCords.get(unHappyIdx);
            int[] vacantCord = vacantCords.get(vacantIdx);
            this.setColor(vacantCord[0], vacantCord[1], this.getColor(unHappyCord[0], unHappyCord[1]));
            this.setColor(unHappyCord[0], unHappyCord[1], Color.WHITE); /*not sure if i add this to vacantcords array since its vacant now?*/
            unHappyCords.remove(unHappyIdx);
            vacantCords.remove(vacantIdx);
        }
    }


    /**
     * iterates through vacant spots and places unhappy residents there IF they will be happy. if there are unhappy residents left after grid iteration, they will be randomly placed in remaining vanact spots
     */
    public void oneGroupStep() {

        ArrayList<int[]> unHappyCords = new ArrayList<>();
        ArrayList<int[]> vacantCords = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == Color.WHITE) {
                    int[] cords = {i, j};
                    vacantCords.add(cords);
                    continue;
                }
                if (!isHappy(i, j)) {
                    int[] cords = {i, j};
                    unHappyCords.add(cords);
                }
            }
        }
        boolean match;
        int vacantSpot;
        for (int i = 0; i < unHappyCords.size(); i++) {
            match = false;
            vacantSpot = 0;
            while (!match && vacantSpot < vacantCords.size()) {
                int[] unHappyCord = unHappyCords.get(i);
                int[] vacantCord = vacantCords.get(vacantSpot);
                this.setColor(vacantCord[0], vacantCord[1], this.getColor(unHappyCord[0], unHappyCord[1]));
                if (isHappy(vacantCord[0], vacantCord[1])) {
                    this.setColor(unHappyCord[0], unHappyCord[1], Color.WHITE); /*not sure if i add this to vacantcords array since its vacant now?*/
                    unHappyCords.remove(i);
                    vacantCords.remove(vacantSpot);
                    match = true;
                    i--;
                } else {
                    this.setColor(vacantCord[0], vacantCord[1], Color.WHITE);

                }
                vacantSpot++;

            }
        }

        Random rand = new Random();

        while (unHappyCords.size() > 0 && vacantCords.size() > 0) {
            int unHappyIdx = rand.nextInt(unHappyCords.size());
            int vacantIdx = rand.nextInt(vacantCords.size());
            int[] unHappyCord = unHappyCords.get(unHappyIdx);
            int[] vacantCord = vacantCords.get(vacantIdx);
            this.setColor(vacantCord[0], vacantCord[1], this.getColor(unHappyCord[0], unHappyCord[1]));
            this.setColor(unHappyCord[0], unHappyCord[1], Color.WHITE);
            unHappyCords.remove(unHappyIdx);
            vacantCords.remove(vacantIdx);
        }
    }

    /**
     * moves unhappy red residents to top left corner and unhappy blue residents to bottom right corner
     */
    public void efficientTimeStep() {

        ArrayList<int[]> unHappyCords = new ArrayList<int[]>();
        ArrayList<int[]> vacantCords = new ArrayList<int[]>();
        int k;

        int row;
        int col;


        for (int diag = 0; diag <= size; diag++) {
            row = 0;
            col = diag - 1;
            while (row < diag) {

                if (grid[row][col] == Color.WHITE) {
                    int[] cords = {row, col};
                    vacantCords.add(cords);
                    row++;
                    col--;
                    continue;
                }
                if (!isHappy(row, col)) {
                    if (getColor(row, col) == Color.RED) {
                        k = 1;

                    } else {
                        k = 2;

                    }

                    int[] cords = {row, col, k};
                    unHappyCords.add(cords);
                }
                row++;
                col--;
            }


        }

        for (int diag = size - 1; diag >= 0; diag--) {
            row = 0;
            col = diag - 1;
            while (row < diag) {
                if (grid[size - 1 - row][size - 1 - col] == Color.WHITE) {
                    int[] cords = {size - 1 - row, size - 1 - col};
                    vacantCords.add(cords);
                    row++;
                    col--;
                    continue;
                }
                if (!isHappy(size - 1 - row, size - 1 - col)) {
                    if (getColor(size - 1 - row, size - 1 - col) == Color.RED) {
                        k = 1;

                    } else {
                        k = 2;

                    }

                    int[] cords = {size - 1 - row, size - 1 - col, k};
                    unHappyCords.add(cords);
                }
                row++;
                col--;
            }


        }

        while (unHappyCords.size() > 0 && vacantCords.size() > 0) {


            int[] unHappyCord = unHappyCords.get(0);

            if (unHappyCord[2] == 1) {
                int[] vacantCord = vacantCords.get(0);
                this.setColor(vacantCord[0], vacantCord[1], Color.RED);
                this.setColor(unHappyCord[0], unHappyCord[1], Color.WHITE); /*not sure if i add this to vacantcords array since its vacant now?*/
                unHappyCords.remove(0);
                vacantCords.remove(0);
            } else {
                int[] vacantCord = vacantCords.get(vacantCords.size() - 1);
                this.setColor(vacantCord[0], vacantCord[1], Color.BLUE);
                this.setColor(unHappyCord[0], unHappyCord[1], Color.WHITE); /*not sure if i add this to vacantcords array since its vacant now?*/
                unHappyCords.remove(0);
                vacantCords.remove(vacantCords.size() - 1);
            }

        }
    }

    /**
     * simulates efficientTimeStep and oneGroupStep for numSteps steps
     *
     * @param numSteps number of steps
     */
    public void simulate(int numSteps) {
        int whichOne;
        for (int i = 0; i < numSteps; i++) {
            Random rand = new Random();
            whichOne = rand.nextInt(6);
            if (whichOne == 0) {
                this.efficientTimeStep();

            } else {

                this.oneGroupStep();
            }
        }

    }
}