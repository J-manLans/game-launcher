package com.dt181g.project.factories;

import com.dt181g.project.mvccomponents.IBaseController;
import com.dt181g.project.mvccomponents.IBaseModel;
import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.mvccomponents.games.IGameMainController;
import com.dt181g.project.mvccomponents.games.IGameMainModel;
import com.dt181g.project.mvccomponents.games.IGameMainView;

/**
 * A functional interface for creating instances of {@link IGameMainController}.
 * <p>
 * This interface defines a factory method for creating controllers
 * associated with specific game title, views and models.
 * </p>
 */
@FunctionalInterface
public interface BaseControllerFactory <T extends IBaseController, V extends IBaseView, M extends IBaseModel>{
    /**
     * Creates a new instance of {@link IGameMainController}.
     *
     * @param title the title of the game, used to identify the controller.
     * @param gameView the {@link IGameMainView} instance associated with this controller.
     * @param gameModel the {@link IGameMainModel} instance associated with this controller.
     * @return a new instance of {@link IGameMainController} configured with the provided parameters.
     */
    T create(V gameView, M gameModel);
}
