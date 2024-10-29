package com.dt181g.project.factories;

import com.dt181g.project.mvccomponents.BaseModel;

/**
 * A factory interface for creating instances of {@link BaseModel}.
 *
 * @author Joel Lansgren
 */
@FunctionalInterface
public  interface GameModelFactory <T extends BaseModel>{
    /**
     * Creates a new instance of {@link BaseModel}.
     *
     * @return A new instance of {@link BaseModel}.
     */
    T create();
}
