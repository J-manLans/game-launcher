package com.dt181g.project.mvccomponents.games;

import com.dt181g.project.mvccomponents.IBaseModel;

/**
 * An interface representing the essential model of a game.
 * <p>
 * This interface provides methods to retrieve the game's icon path and
 * title. Any class implementing this interface
 * must provide concrete implementations for these methods.
 * </p>
 *
 * @author Joel Lansgren
 */
public interface IGameMainModel extends IBaseModel{
   /**
     * Retrieves the path to the game's icon.
     *
     * @return A {@code String} representing the file path to the game's icon.
     */
   String getIconPath();

   /**
     * Retrieves the title of the game.
     *
     * @return A {@code String} containing the title of the game.
     */
   String getGameTitle();
}
