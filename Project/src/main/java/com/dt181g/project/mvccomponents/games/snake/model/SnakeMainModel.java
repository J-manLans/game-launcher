package com.dt181g.project.mvccomponents.games.snake.model;

import java.util.ArrayList;
import java.util.List;

import com.dt181g.project.mvccomponents.games.IGameMainModel;
import com.dt181g.project.support.AppConfigProject;

/**
 * The SnakeMainModel class represents the model for the Snake game.
 * It maintains the state of the snake and the game grid.
 *
 * <p>
 * This class implements the IGameMainModel interface and provides
 * methods to initialize the snake, update the game grid, and clear the grid.
 * It also includes getters for game metadata such as title, icon path,
 * and grid size.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeMainModel implements IGameMainModel {
    private final String gameTitle = AppConfigProject.SNAKE_TITLE;
    private final String iconPath = AppConfigProject.PATH_TO_ICONS + AppConfigProject.ICON_SNAKE;
    private final int gridSize = AppConfigProject.SNAKE_CELL_COUNT;
    private final List<Object> gameAssets = new ArrayList<>();
    private final int[][] gameGrid = new int[gridSize][gridSize];

    /**
     * Constructs a new SnakeMainModel, initializing the game assets
     * with an empty game grid.
     */
    public SnakeMainModel() {
        this.gameAssets.add(this.gameGrid);
    }

    /**
     * Starts a new game by clearing the game grid and initializing
     * the snake model.
     *
     * @param snakeModel the SnakeModel instance used to manage the snake's state.
     */
    public void startNewGame(SnakeModel snakeModel) {
        this.clearGameGrid();
        snakeModel.initializeSnakeModel(this.gameGrid);
        ManagerSnakeBooster.INSTANCE.initializeBoosterManager(this.gameGrid, snakeModel);
        // Updates the grid with the snake created in the initializeSnakeModel method above
        this.overlayGameItemsOnGrid(snakeModel.getSnake());
    }

    /**
     * Updates the game grid based on the current state of the snake.
     *
     * <p>
     * This method first updates the position of the snake using
     * the {@link #updateSnake()} method. It then clears the current
     * state of the snake grid and redraws the snake in its new position.
     * </p>
     *
     * @param snakeModel the SnakeModel instance used to manage the snake's state.
     */
    public void updateGameGrid(SnakeModel snakeModel) {
        snakeModel.updateSnakeOnGrid(this.gameGrid);
        // Clears the grid and then draws the new items on it.
        this.clearGameGrid();
        // Output the snake
        this.overlayGameItemsOnGrid(snakeModel.getSnake());
        // This can be done after the grid is cleared but must also be after the snake is laid
        // on the grid, otherwise boosters can spawn on the snake
        ManagerSnakeBooster.INSTANCE.trySpawnRandomBooster(snakeModel);
        // Outputs possible boosters
        if (ManagerSnakeBooster.INSTANCE.getCurrentBooster() != null) {
            this.overlayGameItemsOnGrid(ManagerSnakeBooster.INSTANCE.getCurrentBooster());
        }
    }

    /**
     * Helper method to overlay the snake and items on the grid.
     *
     * @param gameItem a 2D array representing the items to overlay on the game grid.
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
     *
     * @param gameItem a 2D array representing the items to place on the game grid.
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

    /**
     * Returns the current game grid.
     *
     * @return a 2D array representing the current game grid.
     */
    public int[][] getGameGrid() {
        return gameGrid;
    }

    /**
     * Cleans up resources used by the game.
     */
    public void cleanup() {
        ManagerSnakeBooster.INSTANCE.cleanup();
    }
}
