package com.dt181g.laboration_3.factories;

import com.dt181g.laboration_3.mvccomponents.games.GameController;
import com.dt181g.laboration_3.mvccomponents.games.GameModel;
import com.dt181g.laboration_3.mvccomponents.games.GameView;

/**
 * A functional interface for creating instances of {@link GameController}.
 * <p>
 * This interface defines a factory method for creating controllers
 * associated with specific game title, views and models.
 * </p>
 */
@FunctionalInterface
public interface GameControllerFactory {
    /**
     * Creates a new instance of {@link GameController}.
     *
     * @param title the title of the game, used to identify the controller.
     * @param gameView the {@link GameView} instance associated with this controller.
     * @param gameModel the {@link GameModel} instance associated with this controller.
     * @return a new instance of {@link GameController} configured with the provided parameters.
     */
    GameController create(String title, GameView gameView, GameModel gameModel);
}
