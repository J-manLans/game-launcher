package com.dt181g.project.mvccomponents.games;

import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.dt181g.project.mvccomponents.IBaseController;

/**
 * Interface representing the controllers for the game in the game launcher application.
 *
 * <p>
 * This interface defines the methods required for initializing event listeners,
 * and resetting the game's state. Implementing classes should provide specific
 * functionality to handle user inputs and reset the game when necessary.
 * </p>
 */
public interface IGameMainController extends IBaseController {
    /**
     * Initializes the event listeners for user interactions in the game.
     */
    void initializeListeners();

    /**
     * Resets the game to its initial state.
     * This method should clear any current game data, restore initial settings,
     * and prepare the game for a new session.
     * It should also update the user interface to reflect the reset state.
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

    boolean getIsRunning();

    void setIsRunning(boolean isRunning);

    /**
     * Returns the swing timer that runs the game loop.
     * @return the swing timer.
     */
    Timer getGameLoop();

    /**
     * Returns the game title
     * @return the game title
     */
    Object getGameTitle();

    JLabel getQuitBtn();

    void addQuitBtnListener(MouseAdapter menuButtonListener);
}
