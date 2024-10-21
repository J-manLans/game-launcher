package com.dt181g.project.mvccomponents.games.snake.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.dt181g.project.support.AppConfigProject;

public class SnakeSinglePlayerView extends JPanel{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final SnakeView snakeView;
    private JPanel snakeGrid;
    private JPanel[][] snakeGridCells;
    private List<Object> gameAssets;
    private int[][] snake2DArray;

    protected SnakeSinglePlayerView(final SnakeView snakeView) {
        this.setLayout(new GridBagLayout());
        this.snakeView = snakeView;
        this.setBackground(AppConfigProject.COLOR_DARKER_GREY);
    }

    protected void startGame() {
        this.removeAll();

        if (snakeGrid == null) {
            this.snakeGrid = new JPanel(new GridLayout(snake2DArray.length, snake2DArray.length));
            this.snakeGrid.setPreferredSize(AppConfigProject.SNAKE_GRID_SIZE);
            this.snakeGridCells = new JPanel[snake2DArray.length][snake2DArray.length];
        }

        // Setting up the snake grid
        this.initializeGrid(snake2DArray);

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
     * @param snake2DArray  A 2D array representing the current state of the snake grid.
     */
    private void initializeGrid(final int[][] snake2DArray) {
        this.snakeGrid.removeAll();

        for (int i = 0; i < snake2DArray.length; i++) {
            for (int j = 0; j < snake2DArray.length; j++) {
                // Create cells to put in the grid
                JPanel cell = new JPanel(new BorderLayout());
                cell.setBorder(BorderFactory.createLineBorder(AppConfigProject.COLOR_DARK_GREY));

                if (snake2DArray[i][j] == 1) {  // Displays the snake in the grid
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
     * @param gameAssets the 2D array representing the snake.
     */
    protected void updateGameGrid(final List<Object> gameAssets) {
        for (int i = 0; i < snake2DArray.length; i++) {
            for (int j = 0; j < snake2DArray.length; j++) {
                this.snakeGridCells[i][j].removeAll();

                if (snake2DArray[i][j] == 1) {  // Displays the snake in the grid
                    this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_GAME_ACCENT);
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
                snake2DArray = (int[][]) asset;
            }
        }
    }
}
