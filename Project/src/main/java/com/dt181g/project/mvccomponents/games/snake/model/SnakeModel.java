package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.AppConfigProject.Direction;

public class SnakeModel {
    // A snake that has length of the comingSoon string and stores
    // y and x-coordinates in each of its body parts.
    private final int[][] snake;
    private int headIndex;
    private Boolean allowChangesToDirection = true;
    private Direction currentDirection;
    private final int[][] gameGrid;

    protected SnakeModel(final int[][] gameGrid, int itemParts) {
        this.gameGrid = gameGrid;
        this.snake = new int[AppConfigProject.INITIAL_SNAKE_LENGTH][itemParts];
        this.headIndex = this.snake.length - 1;
    }

     /**
     * Initializes the snake's position on the game grid.
     */
    public void initializeSnake() {
        // Set snakes tail position.
        this.snake[0][0] = this.gameGrid.length / 2;  // Y-coordinate.
        this.snake[0][1] = this.gameGrid.length / 2 - (AppConfigProject.INITIAL_SNAKE_LENGTH / 2);  // X-coordinate.
        this.snake[0][2] = AppConfigProject.COLOR_SNAKE_INT;  // Color.

        // Builds body and head
        for (int i = 1; i < this.snake.length; i++) {
            this.snake[i][0] = this.snake[0][0];  // same y-coordinate as the tail.
            this.snake[i][1] = this.snake[0][1] + i;  // increase x-coordinate with 1 for each part.
            this.snake[i][2] = AppConfigProject.COLOR_SNAKE_INT;
        }
    }

    /**
     * Helper method to update the position of the snake by shifting its body parts.
     *
     * <p>
     * This method moves the tail and body of the snake forward
     * based on its current state. The head of the snake moves in the
     * current direction, and if it reaches the end of the grid, it
     * wraps around to the opposite side. This method is called by
     * {@link #updateGameGrid()} to update the snake's position before
     * refreshing the grid.
     * </p>
     */
    protected void updateSnake() {
        switch (this.currentDirection) {
            case UP -> {
                this.moveSnakeBody();

                // Move the head: wraps around if it reaches the beginning of the grid
                this.snake[headIndex][0] =
                (this.snake[headIndex][0] - 1 + this.gameGrid.length)
                % this.gameGrid.length;
            }
            case DOWN -> {
                this.moveSnakeBody();

                // Move the head: wraps around if it reaches the end of the grid
                this.snake[headIndex][0] =
                (this.snake[headIndex][0] + 1)
                % this.gameGrid.length;
            }
            case LEFT -> {
                this.moveSnakeBody();

                // Move the head: wraps around if it reaches the beginning of the grid
                this.snake[headIndex][1] =
                (this.snake[headIndex][1] - 1 + this.gameGrid.length)
                % this.gameGrid.length;
            }
            case RIGHT -> {
                this.moveSnakeBody();

                // Move the head: wraps around if it reaches the end of the grid
                this.snake[headIndex][1] =
                (this.snake[headIndex][1] + 1)
                % this.gameGrid.length;
            }
        }
    }

    /**
     * Helper method to move the snakes body, it always follows the head.
     */
    private void moveSnakeBody() {
        for (int i = 0; i < this.snake.length - 1; i++) {
            this.snake[i][0] = this.snake[i + 1][0];
            this.snake[i][1] = this.snake[i + 1][1];
        }
    }

    /**
     * Sets the current direction of the snake.
     *
     * <p>
     * This method updates the snake's direction only if the new direction
     * has been set in the snake itself ({@code AllowEntranceToChangeDirection}
     * makes sure of this) and is not opposite to the current direction
     * (e.g., UP to DOWN).
     * </p>
     *
     * @param direction the new direction for the snake (must not be the opposite
     * of the current direction).
     */
    public void setDirection(Direction direction) {
        // Prevent illegal moves.
        if (
            this.currentDirection == Direction.UP && direction == Direction.DOWN
            || this.currentDirection == Direction.DOWN && direction == Direction.UP
            || this.currentDirection == Direction.LEFT && direction == Direction.RIGHT
            || this.currentDirection == Direction.RIGHT && direction == Direction.LEFT
        ) { return; }

        // Makes sure the updates have been reflected in the game grid before allowing changes
        // in direction again.
        if (this.allowChangesToDirection) {
            this.currentDirection = direction;
            this.allowChangesToDirection = false;
        }
    }

    public int[][] getSnake() {
        return snake;
    }

    public void setAllowChangesToDirection(boolean isGridUpdated) {
        this.allowChangesToDirection = isGridUpdated;
    }
}
