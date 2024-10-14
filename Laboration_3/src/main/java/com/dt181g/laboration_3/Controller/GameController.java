package com.dt181g.laboration_3.controller;

/**
 * Interface representing the controllers for the game in the game launcher application.
 *
 * <p>
 * This interface defines the methods required for initializing event listeners,
 * and resetting the game's state. Implementing classes should provide specific
 * functionality to handle user inputs and reset the game when necessary.
 * </p>
 */
public interface GameController {
    /**
     * Initializes the event listeners for user interactions in the game.
     */
    void initializeListeners();

    /**
     * Resets the game to its initial state.
     * This method should clear any current game data, restore initial settings,
     * and prepare the game for a new session.
     * It may also update the user interface to reflect the reset state.
     */
    void resetGame();

    Object getTitle();
}
