
package com.dt181g.laboration_3.games.tictactoe.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.dt181g.laboration_3.games.GameController;
import com.dt181g.laboration_3.games.GameModel;
import com.dt181g.laboration_3.games.GameView;
import com.dt181g.laboration_3.games.tictactoe.model.FakeTicTacToeModel;
import com.dt181g.laboration_3.games.tictactoe.view.FakeTicTacToeView;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.DebugLogger;

/**
 * A mock controller for the Tic Tac Toe game, used for testing game icons
 * in the GameLauncherView.
 *
 * <p>
 * This class implements the GameCtrl interface and provides a
 * simple way to control a Tic Tac Toe game view for testing purposes.
 * It allows for resetting the game and includes a placeholder for
 * initializing listeners.
 * </p>
 */
public class FakeTicTacToeCtrl implements GameController {
    DebugLogger logger = DebugLogger.INSTANCE;
    private final FakeTicTacToeView ticTacToeView;
    private final FakeTicTacToeModel ticTacToeModel;
    private final String title;
    private Timer gameLoop = new Timer(0, null);
    private boolean isRunning = true;
    private final QuitBtnListener quitBtnListener;

    /**
     * Constructs a FakeTicTacToeCtrl with the specified game title, view and model.
     *
     * @param title the tic tac toe game title
     * @param ticTacToePanelView the view representing the Tic Tac Toe game
     * @param ticTacToeModel the model representing the game's state
     */
    public FakeTicTacToeCtrl(final String title, final GameView ticTacToePanelView, final GameModel ticTacToeModel) {
        this.ticTacToeView = (FakeTicTacToeView) ticTacToePanelView;
        this.ticTacToeModel = (FakeTicTacToeModel) ticTacToeModel;
        this.title = title;

        this.quitBtnListener = new QuitBtnListener();
        this.initializeListeners();
    }

    /**
     * Initializes listeners for user interactions.
     */
    @Override
    public void initializeListeners() {
        this.ticTacToeView.addQuitBtnListener(this.quitBtnListener);
    }

    /**
     * Resets the Tic Tac Toe game by invoking the reset method on the game view.
     */
    @Override
    public void initiateGame() {
        this.ticTacToeView.initializeStartMenu();
        logger.logInfo(title +
            " has been initiated.\n"
        );
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void closeGame() {
        this.ticTacToeView.closeGameView();
    }

    @Override
    public boolean getIsRunning() {
        return isRunning;
    }

    @Override
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public Timer getGameLoop() {
        return gameLoop;
    }

    /**
     * Helper method to update the appearance of the specified button based on hover state.
     *
     * @param button the button to update
     * @param isHovered true if the button is hovered, false otherwise
     */
    private void updateButtonAppearance(final JLabel button, final boolean isHovered) {
        button.setOpaque(isHovered);
        button.setForeground(isHovered ? AppConfigLab3.COLOR_DARKER_GREY : AppConfigLab3.COLOR_WHITE);
    }

    /**
     * Listener for the quit button.
     */
    class QuitBtnListener extends MouseAdapter {
        private final JLabel quitBtn = ticTacToeView.getQuitBtn();

        @Override
        public void mouseEntered(final MouseEvent e) {
            updateButtonAppearance(quitBtn, true);
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            updateButtonAppearance(quitBtn, false);
        }
    }
}
