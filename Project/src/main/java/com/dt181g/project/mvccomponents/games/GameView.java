package com.dt181g.project.mvccomponents.games;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.dt181g.project.mvccomponents.BaseView;
import com.dt181g.project.support.DebugLogger;

/**
 * Interface representing a game view in the game launcher application.
 *
 * <p>
 * This interface defines the necessary methods for starting, resetting,
 * and retrieving information from a game view.
 * </p>
 */
public interface GameView extends BaseView {
    DebugLogger logger = DebugLogger.INSTANCE;

    /**
     * Starts the game with the provided game model and title.
     * @param gameAssets a list of assorted assets from the game model to the view
     */
    void startGame(List<Object> gameAssets);

    /**
     * Resets the game view to its initial state.
     * This method should clear any previous game data and prepare
     * the view for a new game session.
     */
    void ShowStartMenu();

    /**
     * Retrieves the title of the game.     *
     * @return A String representing the title of the game.
     */
    String getTitle();

    /**
     * Sets the game panel that holds the game views.
     * This panel is used to close the game and exit to the launcher view.
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
