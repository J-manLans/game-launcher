package com.dt181g.laboration_3.view;

import javax.swing.JPanel;

/**
 * Interface representing a game view in the game launcher application.
 *
 * <p>
 * This interface defines the necessary methods for starting, resetting,
 * and retrieving information from a game view.
 * </p>
 */
public interface GameView {
    /**
     * Starts the game with the provided game model and title.
     *
     * @param gameModel A 2D array representing the current state of the game.
     *                  Each element in the array corresponds to a game entity.
     * @param text      The title or description to display for the game.
     */
    void startGame(int[][] gameModel, String text);

    /**
     * Retrieves the main panel of the game view.
     *
     * @return A JPanel that represents the game view UI.
     */
    JPanel getPanel();

    /**
     * Resets the game view to its initial state.
     * This method should clear any previous game data and prepare
     * the view for a new game session.
     */
    void resetGame();

    /**
     * Retrieves the title of the game.
     *
     * @return A String representing the title of the game.
     */
    String getTitle();
}
