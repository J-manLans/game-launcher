package com.dt181g.project.mvccomponents.games.snake.model;

import java.util.ArrayList;
import java.util.List;

import com.dt181g.project.mvccomponents.games.GameModel;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.DebugLogger;

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
public class SnakeGridModel implements GameModel {
    // Remove when game functionality is implemented
    DebugLogger logger = DebugLogger.INSTANCE;

    private final String title = AppConfigProject.SNAKE_TITLE;
    private final String iconPath = AppConfigProject.PATH_TO_ICONS + AppConfigProject.SNAKE_ICON;
    private final int gridSize = AppConfigProject.SNAKE_CELL_COUNT;
    private final List<Object> gameAssets = new ArrayList<>();
    private final int[][] gameGrid = new int[gridSize][gridSize];

    // Entities that resides in the grid.
    private final SnakeModel snake;
    private final CherryModel cherry;

    public SnakeGridModel() {
        this.snake = new SnakeModel(gameGrid, AppConfigProject.SNAKE_ITEMS_PART_CONTENT);
        this.cherry = new CherryModel(gameGrid, AppConfigProject.SNAKE_ITEMS_PART_CONTENT);
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
    public void updateGameGrid() {
        this.snake.setAllowChangesToDirection(true);
        // Removes the old grid.
        this.gameAssets.remove(gameGrid);
        this.snake.updateSnake();
        // Clears the grid and then draws the new snake on it.
        this.clearGameGrid();
    }

    /**
     * Helper method to overlay the snake and items on the grid.
     */
    public void overlayGameItemsOnGrid(int[][] gameItem) {
        switch (gameItem[0][2]) {
            case AppConfigProject.COLOR_SNAKE_INT -> {
                placeItems(gameItem, AppConfigProject.COLOR_SNAKE_INT);
            } case AppConfigProject.COLOR_APPLE_INT -> {
                placeItems(gameItem, AppConfigProject.COLOR_APPLE_INT);
            }
        }
        this.gameAssets.add(gameGrid);
    }

    /**
     * Helper method to place items on the grid.
     */
    private void placeItems(int[][] gameItem, int color) {
        for (int[] part : gameItem) {
            this.gameGrid[part[0]][part[1]] = color;
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
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the game assets used by the view
     * @return the game assets
     */
    public List<Object> getGameAssets() {
        return gameAssets;
    }

    public SnakeModel getSnakeModel() {
        return snake;
    }

    public CherryModel getCherryModel() {
        return cherry;
    }

    /**
     * Returns the path to the game icon. (not yet implemented)
     * @return the icon path as a string.
     */
    @Override
    public String getIconPath() {
       return iconPath;
    }
}
