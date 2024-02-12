package erehwon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class RedBlueGridUI extends JFrame {
    private static final int GRID_SIZE = 15;
    private static final double DEFAULT_VACANT = 15;
    private static final double DEFAULT_RED = 50;
    private static final int DEFAULT_NEIGHBORHOOD_DISTANCE = 1;
    private static final double DEFAULT_HAPPINESS_THRESHOLD = 0.30;

    private static final int DEFAULT_WINDOW_HEIGHT = 480;
    private static final int DEFAULT_WINDOW_WIDTH  = 480;
    private static final String DEFAULT_UI_NAME = "EREHWON";

    private final int SIMULATION_STEPS = 20;

    private static final Color[] COLORS = {Color.RED, Color.BLUE, Color.WHITE};

    private RedBlueGrid rbgrid;

    private final JButton[][] gridUI = new JButton[GRID_SIZE][GRID_SIZE];

    /**
     * Create a new GridUI for an instance of Erehwon
     */
    public RedBlueGridUI() {
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        setTitle(DEFAULT_UI_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);

        this.rbgrid = new RedBlueGrid(GRID_SIZE,
            DEFAULT_NEIGHBORHOOD_DISTANCE,
            DEFAULT_VACANT,
            DEFAULT_RED,
            DEFAULT_HAPPINESS_THRESHOLD);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton button = new JButton();
                button.setBackground(rbgrid.getColor(row, col));
                button.setOpaque(true);
                button.setBorderPainted(false);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                button.setActionCommand(row + "," + col);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String command = e.getActionCommand();
                        String[] posStr = command.split(",");
                        int row = Integer.parseInt(posStr[0]);
                        int col = Integer.parseInt(posStr[1]);
                        rbgrid.shiftColor(row, col);
                        button.setBackground(rbgrid.getColor(row, col));
                    }
                });
                gridUI[row][col] = button;
                add(button);
            }
        }
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create menu
        JMenu operationsMenu = new JMenu("menu");
        menuBar.add(operationsMenu);

        // Add menu items
        addMenuItem(operationsMenu, "reset", e -> showResetDialog());
        addMenuItem(operationsMenu, "simulate", e -> simulate());
        addMenuItem(operationsMenu, "one step", e -> oneStep());
        addMenuItem(operationsMenu, "exit", e -> exit());
    }

    private void addMenuItem(JMenu menu, String title, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);
    }

    private void showResetDialog() {
        JDialog resetDialog = new JDialog(this, "reset", true);
        resetDialog.setLayout(new GridLayout(3, 1));
        resetDialog.setSize(300, 150);

        JSlider whiteSlider = new JSlider(0, 100, 10);
        whiteSlider.setPaintLabels(true);
        JLabel selectedWhiteValueLabel = new JLabel(" %white: " + whiteSlider.getValue());
        whiteSlider.addChangeListener(e -> {
            // Update the label when the slider value changes
            selectedWhiteValueLabel.setText(" %white: " + whiteSlider.getValue());
        });

        JSlider colorSlider = new JSlider(0, 100);
        colorSlider.setPaintLabels(true);
        JLabel selectedRedValueLabel = new JLabel(" %red: " + colorSlider.getValue());
        colorSlider.addChangeListener(e -> {
            selectedRedValueLabel.setText(" %red: " + colorSlider.getValue());
        });

        JSlider happinessSlider = new JSlider(0, 100);
        happinessSlider.setPaintLabels(true);
        JLabel selectedHappinessLabel = new JLabel( " %threshold " + happinessSlider.getValue());
        happinessSlider.addChangeListener(e -> {
            selectedHappinessLabel.setText(" %threshold: " + happinessSlider.getValue());
        });

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(100, new JLabel("100"));
        colorSlider.setLabelTable(labelTable);
        whiteSlider.setLabelTable(labelTable);
        happinessSlider.setLabelTable(labelTable);

        JButton applyButton = new JButton("apply");
        applyButton.addActionListener(e -> {
            reset(colorSlider.getValue(), whiteSlider.getValue(), happinessSlider.getValue());
            resetDialog.dispose();
        });

        resetDialog.add(new JLabel("% of empty squares:"));
        resetDialog.add(whiteSlider);
        resetDialog.add(selectedWhiteValueLabel);
        resetDialog.add(new JLabel("% of non-empty red squares:"));
        resetDialog.add(colorSlider);
        resetDialog.add(selectedRedValueLabel);
        resetDialog.add(happinessSlider);
        resetDialog.add(selectedHappinessLabel);
        resetDialog.add(applyButton);

        resetDialog.setLocationRelativeTo(this);
        resetDialog.setVisible(true);
    }

    private void recolorUI() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                gridUI[row][col].setBackground(rbgrid.getColor(row, col));
            }
        }
    }

    private void reset(int redFraction, int whiteFraction, int happinessThreshold) {
        rbgrid.reset((1.0 * whiteFraction) / 100, (1.0 * redFraction) / 100, (1.0 * happinessThreshold) / 100);
        recolorUI();
    }

    private void simulate() {
        while (rbgrid.fractionHappy() != 1.0) {
            rbgrid.simulate(SIMULATION_STEPS);



            try {

                Thread.sleep(25);

            }
            catch (Exception e) {

                throw new RuntimeException("Unexpected problem with sleep");
            }




        }
        recolorUI();
    }

    private void oneStep() {
        rbgrid.oneTimeStep();
        recolorUI();
    }

    private void exit() {
        System.out.println("Exit");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RedBlueGridUI().setVisible(true);
            }
        });
    }
}
