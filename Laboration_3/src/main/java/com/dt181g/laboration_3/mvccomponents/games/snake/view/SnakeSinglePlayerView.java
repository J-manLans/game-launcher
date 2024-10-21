package com.dt181g.laboration_3.mvccomponents.games.snake.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dt181g.laboration_3.support.AppConfigLab3;

public class SnakeSinglePlayerView extends JPanel{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final SnakeView snakeView;
    private JPanel snakeGrid;
    private JPanel[][] snakeGridCells;
    private List<Object> gameAssets;

    protected SnakeSinglePlayerView(final SnakeView snakeView) {
        this.setLayout(new GridBagLayout());
        this.snakeView = snakeView;
        this.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
    }

    protected void startGame() {
        String comingSoon = (String) gameAssets.get(0);
        int[][] snake2DArray = (int[][]) gameAssets.get(gameAssets.size() - 1);
        this.removeAll();

        if (snakeGrid == null) {
            this.snakeGrid = new JPanel(new GridLayout(snake2DArray.length, snake2DArray.length));
            this.snakeGrid.setPreferredSize(AppConfigLab3.SNAKE_GRID_SIZE);
            this.snakeGridCells = new JPanel[snake2DArray.length][snake2DArray.length];
        }

        // Setting up the snake grid
        this.initializeGrid(snake2DArray, comingSoon);

        // Display settings
        this.gbc.gridy = 0;
        this.gbc.insets = AppConfigLab3.INSET_BOTTOM_20;
        this.add(this.snakeGrid, gbc);

        this.gbc.gridy++;
        this.gbc.insets = AppConfigLab3.RESET_INSETS;
        this.add(this.snakeView.getSnakeBackBtn(), gbc);
    }

    /**
     * Initializes the grid with the current state of the snake.
     *
     * @param snake2DArray  A 2D array representing the current state of the snake grid.
     * @param comingSoon A string representing the characters to display in the snake.
     */
    private void initializeGrid(final int[][] snake2DArray, final String comingSoon) {
        this.snakeGrid.removeAll();
        // used for displaying the text inside the snake
        int counter = 0;

        for (int i = 0; i < snake2DArray.length; i++) {
            for (int j = 0; j < snake2DArray.length; j++) {
                // Create cells to put in the grid
                JPanel cell = new JPanel(new BorderLayout());
                cell.setBorder(BorderFactory.createLineBorder(AppConfigLab3.COLOR_DARK_GREY));

                if (snake2DArray[i][j] == 1) {  // Displays the snake and the letters in the grid
                    JLabel comingSoonChar = new JLabel(String.valueOf(comingSoon.charAt(counter)));
                    this.styleSnakeBanner(comingSoonChar);

                    cell.setBackground(AppConfigLab3.COLOR_SNAKE_GAME_ACCENT);
                    cell.add(comingSoonChar);

                    counter++;
                } else {  // Background
                    cell.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
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
     *
     * @param gameAssets the 2D array representing the snake and a String just for fun.
     */
    protected void updateGameGrid(final List<Object> gameAssets) {
        String comingSoon = (String) gameAssets.get(0);
        int[][] snake2DArray = (int[][]) gameAssets.get(gameAssets.size() - 1);
        int counter = 0;

        for (int i = 0; i < snake2DArray.length; i++) {
            for (int j = 0; j < snake2DArray.length; j++) {
                this.snakeGridCells[i][j].removeAll();

                if (snake2DArray[i][j] == 1) {  // Displays the snake and the letters in the grid
                    JLabel comingSoonChar = new JLabel(String.valueOf(comingSoon.charAt(counter)));
                    this.styleSnakeBanner(comingSoonChar);

                    this.snakeGridCells[i][j].setBackground(AppConfigLab3.COLOR_SNAKE_GAME_ACCENT);
                    this.snakeGridCells[i][j].add(comingSoonChar);

                    counter++;
                } else {  // Background
                    this.snakeGridCells[i][j].setBackground(AppConfigLab3.COLOR_DARKER_GREY);
                }
            }
        }

        this.snakeGrid.revalidate();
        this.snakeGrid.repaint();
    }

    /**
     * Helper method that styles the letters inside the snake.
     *
     * @param snakeChar The JLabel to be styled.
     */
    protected void styleSnakeBanner(final JLabel snakeChar) {
        this.snakeView.labelStyling(snakeChar, AppConfigLab3.TEXT_SIZE_NORMAL, true);
    }

    /**
     * Sets the list with the snakes current position in the grid plus a string.
     * @param gameAssets a list containing a string and a 2D int array representing the snakes position in the grid
     */
    protected void setGameAssets(List<Object> gameAssets) {
        this.gameAssets = gameAssets;
    }
}
