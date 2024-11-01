
package com.dt181g.project.mvccomponents.games.tictactoe.controller;

import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.dt181g.project.mvccomponents.games.IGameMainController;
import com.dt181g.project.mvccomponents.games.IGameMainModel;
import com.dt181g.project.mvccomponents.games.IGameMainView;
import com.dt181g.project.mvccomponents.games.tictactoe.model.FakeTicTacToeModel;
import com.dt181g.project.mvccomponents.games.tictactoe.view.FakeTicTacToeView;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.DebugLogger;

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
public class FakeTicTacToeCtrl implements IGameMainController {
    private final FakeTicTacToeView ticTacToeView;
    private final FakeTicTacToeModel ticTacToeModel;
    private final String gameTitle = AppConfigProject.TIC_TAC_TOE_TITLE;
    private Timer gameLoop = new Timer(0, null);
    private boolean isRunning = true;

    /**
     * Constructs a FakeTicTacToeCtrl with the specified game title, view and model.
     *
     * @param title the tic tac toe game title
     * @param ticTacToePanelView the view representing the Tic Tac Toe game
     * @param ticTacToeModel the model representing the game's state
     */
    public FakeTicTacToeCtrl(final IGameMainView ticTacToeView, final IGameMainModel ticTacToeModel) {
        this.ticTacToeView = (FakeTicTacToeView) ticTacToeView;
        this.ticTacToeModel = (FakeTicTacToeModel) ticTacToeModel;
    }

    public void initialize() {
        this.initializeListeners();
    }

    /**
     * Initializes listeners for user interactions.
     */
    @Override
    public void initializeListeners() {
    }

    /**
     * Resets the Tic Tac Toe game by invoking the reset method on the game view.
     */
    @Override
    public void initiateGame() {
        this.ticTacToeView.showStartMenu();
        DebugLogger.INSTANCE.logInfo(gameTitle +
            " has been initiated.\n"
        );
    }

    @Override
    public String getGameTitle() {
        return gameTitle;
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

    @Override
    public JLabel getQuitBtn() {
        return this.ticTacToeView.getQuitBtn();
    }

    @Override
    public void addQuitBtnListener(final MouseAdapter quitBtnListener) {
        this.ticTacToeView.addQuitBtnListener(quitBtnListener);
    }
}
