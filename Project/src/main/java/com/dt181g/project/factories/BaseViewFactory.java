package com.dt181g.project.factories;

import com.dt181g.project.mvccomponents.IBaseView;

/**
 * A factory interface for creating instances of {@link IBaseView}.
 * <p>
 * This interface defines a factory method for creating views.
 * </p>
 */
@FunctionalInterface
public interface BaseViewFactory <T extends IBaseView>{
    /**
     * Creates a new instance of {@link IBaseView}.
     *
     * @return A new instance of {@link IBaseView}.
     */
    T create();
}
