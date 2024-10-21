package com.dt181g.project.mvccomponents.games.snake.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.dt181g.project.mvccomponents.games.listeners.SnakeMovementListener;
import com.dt181g.project.support.AppConfigProject;

public class SnakeSinglePlayerView extends JPanel{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final SnakeView snakeView;
    private JPanel snakeGrid;
    private JPanel[][] snakeGridCells;
    private List<Object> gameAssets;
    private int[][] modelGameGrid;

    protected SnakeSinglePlayerView(final SnakeView snakeView) {
        this.setLayout(new GridBagLayout());
        this.snakeView = snakeView;
        this.setBackground(AppConfigProject.COLOR_DARKER_GREY);
    }

    protected void startGame() {
        this.removeAll();

        if (this.snakeGrid == null) {
            this.snakeGrid = new JPanel(new GridLayout(this.modelGameGrid.length, this.modelGameGrid.length));
            this.snakeGrid.setPreferredSize(AppConfigProject.SNAKE_GRID_SIZE);
            this.snakeGridCells = new JPanel[this.modelGameGrid.length][this.modelGameGrid.length];
        }

        // Setting up the snake grid
        this.initializeGrid();

        // Display settings
        this.gbc.gridy = 0;
        this.gbc.insets = AppConfigProject.INSET_BOTTOM_20;
        this.add(this.snakeGrid, gbc);

        this.gbc.gridy++;
        this.gbc.insets = AppConfigProject.RESET_INSETS;
        this.add(this.snakeView.getSnakeBackBtn(), gbc);
    }

    /**
     * Initializes the grid with the current state of the snake.     *
     * @param modelGameGrid  A 2D array representing the current state of the snake grid.
     */
    private void initializeGrid() {
        this.snakeGrid.removeAll();

        for (int i = 0; i < this.modelGameGrid.length; i++) {
            for (int j = 0; j < this.modelGameGrid.length; j++) {
                // Create cells to put in the grid
                JPanel cell = new JPanel(new BorderLayout());
                cell.setBorder(BorderFactory.createLineBorder(AppConfigProject.COLOR_DARK_GREY));

                if (this.modelGameGrid[i][j] == 1) {  // Displays the snake in the grid
                    cell.setBackground(AppConfigProject.COLOR_SNAKE_GAME_ACCENT);
                } else {  // Background
                    cell.setBackground(AppConfigProject.COLOR_DARKER_GREY);
                }

                // Add the cells to the grid and then add the cells to a 2D array
                // used for the consecutive updates of the grid.
                this.snakeGrid.add(cell);
                this.snakeGridCells[i][j] = cell;
            }
        }
    }

    /**
     * Updates the game grid with the current state of the snake.
     */
    protected void updateGameGrid() {
        for (int i = 0; i < this.modelGameGrid.length; i++) {
            for (int j = 0; j < this.modelGameGrid.length; j++) {
                this.snakeGridCells[i][j].removeAll();

                if (this.modelGameGrid[i][j] != 0) {  // Displays the snake in the grid
                    switch (this.modelGameGrid[i][j]) {  // TODO: need to find a way to bring the colors from the model to here.
                        case AppConfigProject.COLOR_SNAKE_INT -> {
                            this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_GAME_ACCENT);
                        } case AppConfigProject.COLOR_APPLE_INT -> {
                            this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_GAME_APPLE);
                        }
                    }

                } else {  // Background
                    this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_DARKER_GREY);
                }
            }
        }
    }

    /**
     * Sets the list with the snakes current position in the grid.
     * @param gameAssets a list containing a 2D int array representing the snakes position in the grid
     */
    protected void setGameAssets(List<Object> gameAssets) {
        this.gameAssets = gameAssets;
        for (Object asset : this.gameAssets) {
            if (asset instanceof int[][]) {
                this.modelGameGrid = (int[][]) asset;
            }
        }
    }

    /*=========================
     * Listeners
     ========================*/

    public void addSnakeKeyListener(SnakeMovementListener snakeKeyListener) {
        this.snakeGrid.addKeyListener(snakeKeyListener);
        this.snakeGrid.requestFocusInWindow();
    }
}
