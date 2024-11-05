package com.dt181g.laboration_3.mvccomponents.games;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.dt181g.laboration_3.mvccomponents.BaseView;
import com.dt181g.laboration_3.support.DebugLogger;

/**
 * Interface representing a game view in the game launcher application.
 *
 * <p>
 * This interface defines the necessary methods for starting, resetting,
 * and retrieving information from a game view.
 * </p>
 *
 * @author Joel Lansgren
 */
public interface GameView extends BaseView {
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
     * Displays the start menu.
     */
    void showStartMenu();

    /**
     * Retrieves the title of the game.
     *
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
    default void clearGameView(final JPanel gamePanel, final JPanel gameView) {
        SwingUtilities.invokeLater(() -> {
            gamePanel.remove(gameView);
        });
    }

    /**
     * Gets the quit button from games so the launcher controller can show the start screen
     * of the view.
     * @return the games quit button
     */
    JLabel getQuitBtn();
}
