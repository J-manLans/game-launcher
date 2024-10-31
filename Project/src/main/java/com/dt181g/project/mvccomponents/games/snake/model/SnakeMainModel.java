package com.dt181g.project.mvccomponents.games.snake.model;

import java.util.ArrayList;
import java.util.List;

import com.dt181g.project.mvccomponents.games.GameMainModel;
import com.dt181g.project.support.AppConfigProject;

/**
 * The SnakeModel class represents the model for the Snake game.
 * It maintains the state of the snake and the game grid.
 *
 * <p>
 * This class implements the GameModel interface and provides
 * methods to initialize the snake, update the game grid, and clear the grid.
 * It also includes getters for game metadata such as title, icon path,
 * and grid size.
 * </p>
 *
 * <p>
 * It's not yet fully implemented, as of now the snake is not controllable
 * and no food is spawning for the player to eat
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeMainModel implements GameMainModel {
    private final String gameTitle = AppConfigProject.SNAKE_TITLE;
    private final String iconPath = AppConfigProject.PATH_TO_ICONS + AppConfigProject.SNAKE_ICON;
    private final int gridSize = AppConfigProject.SNAKE_CELL_COUNT;
    private final List<Object> gameAssets = new ArrayList<>();
    final int[][] gameGrid = new int[gridSize][gridSize];

    public SnakeMainModel() {
        this.gameAssets.add(this.gameGrid);
    }

    /**
     * Updates the game grid based on the current state of the snake.
     *
     * <p>
     * This method first updates the position of the snake using
     * the {@link #updateSnake()} method. It then clears the current
     * state of the snake grid and redraws the snake in its new position.
     * </p>
     */
    public void updateGameGrid(SnakeModel snakeModel) {
        // Make taking input from keyboard possible again in the loop
        snakeModel.setAllowChangesToDirection(true);

        // This is done before clearing the grid to get the position of
        // possible boosters that the snake needs to interact with
        snakeModel.setGameGrid(this.gameGrid);
        snakeModel.moveSnake(BoosterManager.INSTANCE.getCurrentBoosterModel());
        // Clears the grid and then draws the new items on it.
        this.clearGameGrid();
        // Output the snake
        this.overlayGameItemsOnGrid(snakeModel.getSnake());
        // This can be done after the grid is cleared but must also be after the snake is laid
        // on the grid, otherwise boosters can spawn on the snake
        BoosterManager.INSTANCE.spawnRandomBooster(snakeModel.getSnake());
        // Outputs possible boosters
        if (BoosterManager.INSTANCE.getCurrentBooster() != null) {
            this.overlayGameItemsOnGrid(BoosterManager.INSTANCE.getCurrentBooster());
        }
    }

    /**
     * Helper method to overlay the snake and items on the grid.
     */
    public void overlayGameItemsOnGrid(int[][] gameItem) {
        // Removes the old grid.
        this.gameAssets.remove(this.gameGrid);
        switch (gameItem[0][2]) {
            case AppConfigProject.COLOR_SNAKE_INT -> placeItems(gameItem);
            case AppConfigProject.COLOR_CHERRY_INT -> placeItems(gameItem);
            case AppConfigProject.COLOR_SPEED_INT -> placeItems(gameItem);
        }
        // Adds the updated grid.
        this.gameAssets.add(this.gameGrid);
    }

    /**
     * Helper method to place items on the grid.
     */
    private void placeItems(int[][] gameItem) {
        for (int[] part : gameItem) {
            this.gameGrid[part[0]][part[1]] = part[2];
        }
    }

    /**
     * Helper method to clear the game grid by setting all values to zero.
     *
     * <p>
     * This method is called to reset the grid before drawing
     * the updated position of the snake.
     * </p>
     */
    public void clearGameGrid() {
        for (int i = 0; i < this.gameGrid.length; i++) {
            for (int j = 0; j < this.gameGrid[i].length; j++) {
                this.gameGrid[i][j] = 0;
            }
        }
    }

    /*==============================
     * Getters
     ==============================*/

    /**
     * Returns the title of the game.
     *
     * @return the title of the snake game.
     */
    public String getGameTitle() {
        return this.gameTitle;
    }

    /**
     * Returns the game assets used by the view
     * @return the game assets
     */
    public List<Object> getGameAssets() {
        return gameAssets;
    }

    /**
     * Returns the path to the game icon. (not yet implemented)
     * @return the icon path as a string.
     */
    @Override
    public String getIconPath() {
       return iconPath;
    }

    public int[][] getGameGrid() {
        return gameGrid;
    }
}
