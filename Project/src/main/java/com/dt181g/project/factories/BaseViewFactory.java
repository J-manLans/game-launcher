package com.dt181g.project.factories;

import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.mvccomponents.games.IGameMainView;

/**
 * A factory interface for creating instances of {@link IGameMainView}.
 * <p>
 * This interface defines a factory method for creating views
 * associated with a specific title.
 * </p>
 */
@FunctionalInterface
public interface BaseViewFactory <T extends IBaseView>{
    /**
     * Creates a new instance of {@link IGameMainView} with the specified title.
     *
     * @param title the title of the game, used to identify the view.
     * @return A new instance of {@link IGameMainView}.
     */
    T create();
}
