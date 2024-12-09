package com.dt181g.laboration_3.factories;

import com.dt181g.laboration_3.controller.games.common.IGameController;
import com.dt181g.laboration_3.model.games.common.IGameModel;
import com.dt181g.laboration_3.view.games.common.IGameView;


/**
 * A functional interface for creating instances of {@link IGameController}.
 * <p>
 * This interface defines a factory method for creating controllers
 * associated with specific game title, views and models.
 * </p>
 */
@FunctionalInterface
public interface GameControllerFactory {
    /**
     * Creates a new instance of {@link IGameController}.
     *
     * @param title the title of the game, used to identify the controller.
     * @param gameView the {@link IGameView} instance associated with this controller.
     * @param gameModel the {@link IGameModel} instance associated with this controller.
     * @return a new instance of {@link IGameController} configured with the provided parameters.
     */
    IGameController create(final String title, final IGameView gameView, final IGameModel gameModel);
}
