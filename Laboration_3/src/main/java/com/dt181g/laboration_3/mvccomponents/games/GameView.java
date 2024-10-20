package com.dt181g.laboration_3.mvccomponents.games;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.dt181g.laboration_3.support.DebugLogger;

/**
 * Interface representing a game view in the game launcher application.
 *
 * <p>
 * This interface defines the necessary methods for starting, resetting,
 * and retrieving information from a game view.
 * </p>
 */
public interface GameView {
    DebugLogger logger = DebugLogger.INSTANCE;

    /**
     * Starts the game with the provided game model and title.
     *
     * @param gamePanel A 2D array representing the current state of the game.
     *                  Each element in the array corresponds to a game entity.
     * @param text      The title or description to display for the game.
     */
    void startGame(List<Object> gameAssets);

    /**
     * Resets the game view to its initial state.
     * This method should clear any previous game data and prepare
     * the view for a new game session.
     */
    void initializeStartMenu();

    /**
     * Retrieves the title of the game.
     *
     * @return A String representing the title of the game.
     */
    String getTitle();

    /**
     * Sets game panel in the game views.
     */
    void setGamePanel(JPanel gamePanel);

    /**
     * This method removes the game view from the game panel and prep variables for garbage collection.
     */
    void closeGameView();

    /**
     * This method removes the game view from the game panel.
     */
    default void clearGameView(JPanel gamePanel, JPanel gameView, String title) {
        SwingUtilities.invokeLater(() -> {
            gamePanel.remove(gameView);
        });
        logger.logInfo(title + " view has been reset.");
    }

    /**
     * Gets the quit button from games so the launcher controller can show the start screen
     * of the view.
     * @return the games quit button
     */
    JLabel getQuitBtn();
}
