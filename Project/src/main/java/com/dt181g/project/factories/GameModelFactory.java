package com.dt181g.project.factories;

import com.dt181g.project.mvccomponents.BaseModel;
import com.dt181g.project.mvccomponents.games.GameModel;

/**
 * A factory interface for creating instances of {@link GameModel}.
 *
 * @author Joel Lansgren
 */
@FunctionalInterface
public  interface GameModelFactory <T extends BaseModel>{
    /**
     * Creates a new instance of {@link GameModel}.
     *
     * @return A new instance of {@link GameModel}.
     */
    T create();
}
