package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.mvccomponents.IBaseModel;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.AppConfigProject.Direction;

public class SnakeModel implements IBaseModel {
    // A snake that has length of the comingSoon string and stores
    // y and x-coordinates in each of its body parts.
    private int[][] snake;
    private int headIndex = AppConfigProject.INITIAL_SNAKE_LENGTH - 1;
    private int[][] gameGrid;
    private Boolean allowChangesToDirection = true;
    private Direction currentDirection;
    private boolean isGameOver;
    private double speed = AppConfigProject.SNAKE_TICK_DELAY;

    public void initializeSnakeModel(final int[][] clearedGameGrid) {
        this.gameGrid = clearedGameGrid;
        this.initializeSnake();
        this.speed = AppConfigProject.SNAKE_TICK_DELAY;
        this.isGameOver = false;
        this.allowChangesToDirection = true;
        this.currentDirection = AppConfigProject.RIGHT;
    }

    /**
     * Initializes the snake's position on the game grid.
     */
    private void initializeSnake() {
        // Re-initialize the snake
        this.snake = new int[AppConfigProject.INITIAL_SNAKE_LENGTH][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
        this.headIndex = AppConfigProject.INITIAL_SNAKE_LENGTH - 1;
        // Set snakes tail position.
        this.snake[0][0] = this.gameGrid.length / 2;  // Y-coordinate.
        this.snake[0][1] = this.gameGrid.length / 2 - (AppConfigProject.INITIAL_SNAKE_LENGTH / 2);  // X-coordinate.
        this.snake[0][2] = AppConfigProject.COLOR_SNAKE_INT;  // Color.

        // Builds body and head
        for (int i = 1; i < this.snake.length; i++) {
            this.snake[i][0] = this.snake[0][0];  // same y-coordinate as the tail.
            this.snake[i][1] = this.snake[0][1] + i;  // increase x-coordinate with 1 for each part.
            this.snake[i][2] = AppConfigProject.COLOR_SNAKE_INT;  // Color.
        }

        this.snake[headIndex][2] = AppConfigProject.COLOR_SNAKE_HEAD_INT;
    }

    public void updateSnakeOnGrid(final int[][] updatedGameGrid) {
        // Make taking input from keyboard possible again in the game-loop
        this.allowChangesToDirection = true;
        // This is done before clearing the grid to get the position of
        // possible boosters that the snake needs to interact with
        this.gameGrid = updatedGameGrid;
        this.moveSnake();
    }

    /**
     * Moves the snake based on the current direction.
     * Adjusts the body first and then moves the head according to the direction.
     * After moving, it checks the cell the head has moved into for its content.
     */
    protected void moveSnake() {
        this.moveSnakeBody();

        switch (this.currentDirection) {
            case UP -> { this.moveSnakeHeadNegDirection(0); }
            case LEFT -> { this.moveSnakeHeadNegDirection(1); }
            case DOWN -> { this.moveSnakeHeadPosDirection(0); }
            case RIGHT -> { this.moveSnakeHeadPosDirection(1); }
        }

        checkHeadCell(ManagerSnakeBooster.INSTANCE.getCurrentBoosterModel());
    }

    /**
     * Helper method that  moves the body of the snake by shifting each segment's position
     * to the position of the next segment in the array.
     * This makes each body segment follow the one ahead of it,
     * ultimately following the head's previous position.
     * Does not move the head itself, only the body segments.
     */
    private void moveSnakeBody() {
        for (int i = 0; i < this.snake.length - 1; i++) {
            this.snake[i][0] = this.snake[i + 1][0];
            this.snake[i][1] = this.snake[i + 1][1];
        }
    }

    /**
     * Helper method that  moves the snake's head in a positive direction (DOWN or RIGHT).
     * Wraps around the grid if the head reaches the edge.
     *
     * @param yOrX The index indicating if the movement is in the Y (0) or X (1) direction.
     */
    private void moveSnakeHeadPosDirection(final int yOrX) {
        // Move the head: wraps around if it reaches the end of the grid
        this.snake[headIndex][yOrX] =
        (this.snake[headIndex][yOrX] + 1)
        % this.gameGrid.length;
    }

    /**
     * Helper method that  moves the snake's head in a negative direction (UP or LEFT).
     * Wraps around the grid if the head reaches the beginning.
     *
     * @param yOrX The index indicating if the movement is in the Y (0) or X (1) direction.
     */
    private void moveSnakeHeadNegDirection(final int yOrX) {
        // Move the head: wraps around if it reaches the beginning of the grid
        this.snake[headIndex][yOrX] =
        (this.snake[headIndex][yOrX] - 1 + this.gameGrid.length)
        % this.gameGrid.length;
    }

    /**
     * Helper method that checks the contents of the cell that the snake's head just moved into.
     * If it encounters an item or the snake body, it triggers the appropriate action.
     *
     * @param y The Y-coordinate of the cell being checked.
     * @param x The X-coordinate of the cell being checked.
     * @param oldTailY The Y-coordinate of the previous tail position, used if the snake grows.
     * @param oldTailX The X-coordinate of the previous tail position, used if the snake grows.
     */
    private void checkHeadCell(final ISnakeBoostersModel booster) {
        switch (this.gameGrid[this.snake[headIndex][0]][this.snake[headIndex][1]]) {
            case AppConfigProject.COLOR_SNAKE_INT -> {
                this.isGameOver = true;
                this.allowChangesToDirection = false;
            }
            case AppConfigProject.COLOR_CHERRY_INT -> booster.eatBooster(this);
            case AppConfigProject.COLOR_SPEED_INT -> booster.eatBooster(this);
        }
    }

    public void updateSnake(int[][] expandedSnake) {
        this.snake = expandedSnake;
        this.headIndex = this.snake.length - 1;
    }

    /**
     * Returns the current state of the snake as a 2D array.
     * Each element represents a segment of the snake, containing
     * its position coordinates and color.
     *
     * @return A 2D integer array representing the snake's segments.
     */
    public int[][] getSnake() {
        return this.snake;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean getGameState() {
        return this.isGameOver;
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
     * @param direction the new direction for the snake (must not be the opposite
     * of the current direction).
     */
    public void setDirection(final Direction direction, final boolean restart) {
        // Prevent illegal moves.
        if (
            (
                this.currentDirection == Direction.UP && direction == Direction.DOWN
                || this.currentDirection == Direction.DOWN && direction == Direction.UP
                || this.currentDirection == Direction.LEFT && direction == Direction.RIGHT
                || this.currentDirection == Direction.RIGHT && direction == Direction.LEFT
            )
            && !restart
        ) { return; }

        // Makes sure the updates have been reflected in the game grid before allowing changes
        // in direction again.
        if (this.allowChangesToDirection) {
            this.currentDirection = direction;
            this.allowChangesToDirection = false;
        }
    }

    public void setSpeed(final double speed) {
        this.speed = speed;
    }
}
