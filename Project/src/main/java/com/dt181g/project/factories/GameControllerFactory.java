package com.dt181g.project.factories;

import com.dt181g.project.mvccomponents.BaseController;
import com.dt181g.project.mvccomponents.BaseModel;
import com.dt181g.project.mvccomponents.BaseView;
import com.dt181g.project.mvccomponents.games.GameController;
import com.dt181g.project.mvccomponents.games.GameModel;
import com.dt181g.project.mvccomponents.games.GameView;

/**
 * A functional interface for creating instances of {@link GameController}.
 * <p>
 * This interface defines a factory method for creating controllers
 * associated with specific game title, views and models.
 * </p>
 */
@FunctionalInterface
public interface GameControllerFactory <T extends BaseController, V extends BaseView, M extends BaseModel>{
    /**
     * Creates a new instance of {@link GameController}.
     *
     * @param title the title of the game, used to identify the controller.
     * @param gameView the {@link GameView} instance associated with this controller.
     * @param gameModel the {@link GameModel} instance associated with this controller.
     * @return a new instance of {@link GameController} configured with the provided parameters.
     */
    T create(V gameView, M gameModel);
}
