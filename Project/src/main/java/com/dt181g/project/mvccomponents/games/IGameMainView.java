package com.dt181g.project.mvccomponents.games;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.support.DebugLogger;

/**
 * Interface representing a game view in the game launcher application.
 *
 * <p>
 * This interface defines the necessary methods for starting, resetting,
 * and retrieving information from a game view.
 * </p>
 */
public interface IGameMainView extends IBaseView {
    /**
     * Starts the game with the provided game model and title.
     * @param gameAssets a list of assorted assets from the game model to the view
     */
    void showGame();

    /**
     * Resets the game view to its initial state.
     * This method should clear any previous game data and prepare
     * the view for a new game session.
     */
    void showStartMenu();

    /**
     * Retrieves the title of the game.
     * @return A String representing the title of the game.
     */
    String getGameTitle();

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
        DebugLogger.INSTANCE.logInfo(title + " view has been reset.");
    }
}
