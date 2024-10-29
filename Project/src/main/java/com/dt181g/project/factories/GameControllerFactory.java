package com.dt181g.project.factories;

import com.dt181g.project.mvccomponents.BaseController;
import com.dt181g.project.mvccomponents.BaseModel;
import com.dt181g.project.mvccomponents.BaseView;
import com.dt181g.project.mvccomponents.games.GameMainController;
import com.dt181g.project.mvccomponents.games.GameMainModel;
import com.dt181g.project.mvccomponents.games.GameMainView;

/**
 * A functional interface for creating instances of {@link GameMainController}.
 * <p>
 * This interface defines a factory method for creating controllers
 * associated with specific game title, views and models.
 * </p>
 */
@FunctionalInterface
public interface GameControllerFactory <T extends BaseController, V extends BaseView, M extends BaseModel>{
    /**
     * Creates a new instance of {@link GameMainController}.
     *
     * @param title the title of the game, used to identify the controller.
     * @param gameView the {@link GameMainView} instance associated with this controller.
     * @param gameModel the {@link GameMainModel} instance associated with this controller.
     * @return a new instance of {@link GameMainController} configured with the provided parameters.
     */
    T create(V gameView, M gameModel);
}
