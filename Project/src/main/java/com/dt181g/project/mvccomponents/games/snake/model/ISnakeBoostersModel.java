package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.mvccomponents.IBaseModel;

/**
 * Interface representing a booster in the Snake game.
 * <p>
 * This interface defines the essential operations for any booster class,
 * including methods to apply effects to the snake model, retrieve the
 * booster's representation, its color, and check if the booster is active.
 * </p>
 *
 * @author Joel Lansgren
 */
public interface ISnakeBoostersModel extends IBaseModel {
    /**
     * Applies the effect of the booster to the specified snake model.
     *
     * @param snakeModel The snake model to which the booster effect will be applied.
     */
    void applyBoosterEffect(SnakeModel snakeModel);

    /**
     * Retrieves the representation of the booster as a 2D array,
     * including its position and color.
     *
     * @return A 2D array representing the booster.
     */
    int[][] getBooster();

    /**
     * Returns the color associated with the booster.
     *
     * @return An integer representing the color of the booster.
     */
    int getBoosterColor();

    /**
     * Checks if the booster is currently active.
     *
     * @return {@code true} if the booster is active; {@code false} otherwise.
     */
    boolean isBoosterActive();
}
