package com.dt181g.project.mvccomponents.games;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.dt181g.project.mvccomponents.IBaseView;

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
     * Starts the game and displays the associated game interface.
     */
    void showGame();

    /**
     * Displays the start menu.
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
     * Clears the specified game view from the provided game panel.
     * This method should be called when the game view is no longer needed.
     *
     * @param gamePanel the JPanel containing the game views
     * @param gameView the JPanel representing the current game view to be cleared
     * @param title the title of the game for logging purposes
     */
    default void clearGameView(JPanel gamePanel, JPanel gameView, String title) {
        SwingUtilities.invokeLater(() -> {
            gamePanel.remove(gameView);
        });
    }
}
