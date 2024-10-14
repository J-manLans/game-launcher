package com.dt181g.laboration_3.factories;

import com.dt181g.laboration_3.model.GameModel;

/**
 * A factory interface for creating instances of {@link GameModel}.
 *
 * @author Joel Lansgren
 */
@FunctionalInterface
public  interface GameModelFactory {
    /**
     * Creates a new instance of {@link GameModel}.
     *
     * @return A new instance of {@link GameModel}.
     */
    GameModel create();
}
