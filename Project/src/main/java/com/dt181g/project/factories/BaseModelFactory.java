package com.dt181g.project.factories;

import com.dt181g.project.mvccomponents.IBaseModel;

/**
 * A factory interface for creating instances of {@link IBaseModel}.
 *
 * @author Joel Lansgren
 */
@FunctionalInterface
public  interface BaseModelFactory <T extends IBaseModel>{
    /**
     * Creates a new instance of {@link IBaseModel}.
     *
     * @return A new instance of {@link IBaseModel}.
     */
    T create();
}
