package com.dt181g.laboration_3.controller.games.common;


/**
 * Interface representing the controllers for the game in the game launcher application.
 *
 * <p>
 * This interface defines the methods required for initializing event listeners,
 * and resetting the game's state. Implementing classes should provide specific
 * functionality to handle user inputs and reset the game when necessary.
 * </p>
 *
 * @author Joel Lansgren
 */
public interface IGameController {
    /**
     * Initializes the event listeners for user interactions in the game.
     */
    void initializeListeners();

    /**
     * Resets the game to its initial state.
     *
     * <p>
     * This method should clear any current game data, restore initial settings,
     * and prepare the game for a new session. It's executed every time a game starts up
     * or when the user navigates back to the start menu.
     * </p>
     */
    void initiateGame();

    /**
     * Method that shall handle closing of the game.
     *
     * <p>
     * Stopping active threads, remove listeners, close the view and make variables ready
     * for garbage collection.
     * </p>
     */
    void closeGame();

    /**
     * Returns the game title
     * @return the game title
     */
    Object getTitle();
}
