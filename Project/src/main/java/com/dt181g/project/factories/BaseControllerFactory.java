package com.dt181g.project.factories;

import com.dt181g.project.mvccomponents.IBaseController;
import com.dt181g.project.mvccomponents.IBaseModel;
import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.mvccomponents.games.IGameMainController;

/**
 * A functional interface for creating instances of {@link IBaseController}.
 * <p>
 * This interface defines a factory method for creating controllers
 * associated with specific views and models.
 * </p>
 */
@FunctionalInterface
public interface BaseControllerFactory <C extends IBaseController, V extends IBaseView, M extends IBaseModel>{
    /**
     * Creates a new instance of {@link IGameMainController}.
     *
     * @param baseView the {@link IBaseView} instance associated with this controller.
     * @param baseModel the {@link IBaseModel} instance associated with this controller.
     * @return a new instance of {@link IBaseController} configured with the provided parameters.
     */
    C create(V baseView, M baseModel);
}
