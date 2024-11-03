package com.dt181g.project.mvccomponents.games.snake.model;

/**
 * Interface representing the effect of a booster in the Snake game.
 * <p>
 * Any class implementing this interface must provide a concrete implementation
 * for resetting the effects of a booster on the {@link SnakeModel}.
 * </p>
 *
 * @author Joel Lansgren
 */
public interface IBoosterEffect {
    /**
     * Resets the booster effect on the specified snake model, restoring its
     * state to what it was before the booster was applied.
     *
     * @param snakeModel The snake model to which the booster effect will be reset.
     */
    public void resetBoosterEffect(SnakeModel snakeModel);
}
