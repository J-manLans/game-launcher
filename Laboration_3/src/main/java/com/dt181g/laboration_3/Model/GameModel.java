package com.dt181g.laboration_3.model;

/**
 * An interface representing the essential model of a game.
 * <p>
 * This interface provides methods to retrieve the game's icon path,
 * title, and grid size. Any class implementing this interface
 * must provide concrete implementations for these methods.
 * </p>
 */
public interface GameModel {
   String getIconPath();
   String getTitle();
   int getGridSize();
}
