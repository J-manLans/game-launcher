package com.dt181g.laboration_3.factories;

import com.dt181g.laboration_3.model.games.common.IGameModel;

/**
 * A factory interface for creating instances of {@link IGameModel}.
 *
 * @author Joel Lansgren
 */
@FunctionalInterface
public  interface GameModelFactory {
    /**
     * Creates a new instance of {@link IGameModel}.
     *
     * @return A new instance of {@link IGameModel}.
     */
    IGameModel create(final String title);
}
