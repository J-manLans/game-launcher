package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.support.AppConfigProject;

/**
 * Represents a cherry booster in the snake game, which the snake can eat to
 * gain speed and grow in length. When activated, it applies an effect that
 * increases the snake's speed and adds a segment to its body.
 * @author Joel Lansgren
 */
public class CherryBoosterModel extends SnakeBoostersModel {
    private int[][] cherry = new int[1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
    private final int boosterColor = AppConfigProject.COLOR_CHERRY_INT;

    public CherryBoosterModel() {
        BoosterManager.INSTANCE.addBoosters(this);
    }

    /**
     * Applies the cherry's effect to the snake model. The effect includes
     * initializing the booster on the game grid, adding speed to the snake,
     * and growing the snake by one segment.
     *
     * @param snakeModel The snake model to which the effect is applied.
     */
    @Override
    protected void eatBooster(SnakeModel snakeModel) {
        BoosterManager.INSTANCE.initializeBooster(this);
        super.addSpeed(snakeModel, (int) (snakeModel.getSpeed() * AppConfigProject.SNAKE_SPEED_MULTIPLIER));
        this.grow(snakeModel);
    }

    /**
     * Adds a new segment to the snake, expanding its body array by one
     * element and setting the position of the new segment to follow the
     * current position of the snake's tail.
     *
     * @param snakeModel The snake model whose body will be expanded.
     */
    private void grow(final SnakeModel snakeModel) {
        final int[][] snake = snakeModel.getSnake();
        final int[][] expandedSnake = new int[snake.length + 1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];

        // Copy existing snake body to the expanded array
        System.arraycopy(snake, 0, expandedSnake, 1, snake.length);

        // The tail is re-added here
        expandedSnake[0] = new int[]{snake[0][0], snake[0][1], AppConfigProject.COLOR_SNAKE_INT};

        // Updates the snake with the new copy and headIndex is updated
        snakeModel.setSnake(expandedSnake);
        snakeModel.setHeadIndex(snakeModel.getSnake().length - 1);
    }

    /**
     * Returns the cherry booster object, represented as a 2D array, with its
     * position and color.
     *
     * @return A 2D array representing the cherry booster.
     */
    public int[][] getBooster() {
        return cherry;
    }

    protected int getBoosterColor() {
        return boosterColor;
    }
}
