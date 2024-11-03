package com.dt181g.project.mvccomponents.games;

import java.awt.event.MouseAdapter;
import javax.swing.JLabel;
import com.dt181g.project.mvccomponents.IBaseController;

/**
 * Interface representing the controllers for the game in the game launcher application.
 *
 * <p>
 * This interface defines the methods required for initializing event listeners,
 * resetting the game's state, and managing game lifecycle actions.
 * Implementing classes should provide specific functionality to handle user inputs
 * and reset the game when necessary.
 * </p>
 *
 * @author Joel Lansgren
 */
public interface IGameMainController extends IBaseController {
    /**
     * Initializes the event listeners for user interactions in the game.
     *
     * <p>
     * Implementing classes should set up all necessary listeners to respond to user actions,
     * such as button clicks and key presses, ensuring that the game reacts appropriately to
     * user input.
     * </p>
     */
    void initializeListeners();

    /**
     * Resets the game to its initial state.
     *
     * <p>
     * This method should clear any current game data, restore initial settings,
     * and prepare the game for a new session. It should also update the user interface
     * to reflect the reset state, ensuring a clean start for the player.
     * </p>
     */
    void initiateGame();

    /**
     * Closes the game and releases any resources.
     *
     * <p>
     * This method should handle cleanup operations such as stopping game loops,
     * removing listeners, and ensuring that any resources used by the game are properly
     * released before exiting.
     * </p>
     */
    void closeGame();

    /**
     * Returns the title of the game.
     *
     * @return the title of the game as a String.
     */
    String getGameTitle();

    /**
     * Returns the quit button associated with the game.
     *
     * @return the JLabel representing the quit button.
     */
    JLabel getQuitBtn();

    /**
     * Adds a listener for the quit button.
     *
     * @param menuButtonListener the listener to be added for quit button actions.
     */
    void addQuitBtnListener(MouseAdapter menuButtonListener);
}
