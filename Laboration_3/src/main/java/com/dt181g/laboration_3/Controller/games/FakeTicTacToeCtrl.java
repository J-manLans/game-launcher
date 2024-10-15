
package com.dt181g.laboration_3.controller.games;

import javax.swing.Timer;

import com.dt181g.laboration_3.controller.GameController;
import com.dt181g.laboration_3.model.GameModel;
import com.dt181g.laboration_3.support.DebugLogger;
import com.dt181g.laboration_3.view.GameView;

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
    private final GameView ticTacToePanelView;
    private final GameModel ticTacToeModel;
    private final String title;
    private Timer gameLoop = new Timer(0, null);
    private boolean isRunning = true;

    /**
     * Constructs a FakeTicTacToeCtrl with the specified game title, view and model.
     *
     * @param title the tic tac toe game title
     * @param ticTacToePanelView the view representing the Tic Tac Toe game
     * @param ticTacToeModel the model representing the game's state
     */
    public FakeTicTacToeCtrl(final String title, final GameView ticTacToePanelView, final GameModel ticTacToeModel) {
        this.ticTacToePanelView = ticTacToePanelView;
        this.ticTacToeModel = ticTacToeModel;
        this.title = title;
    }

    /**
     * Resets the Tic Tac Toe game by invoking the reset method on the game view.
     */
    @Override
    public void initiateGame() {
        this.ticTacToePanelView.initializeStartMenu();
        logger.logWarning(title + " has been restarted.\n");
    }

    /**
     * Initializes listeners for user interactions. This method is not
     * implemented and will throw an {@link UnsupportedOperationException}
     * if called.
     *
     * @throws UnsupportedOperationException if this method is called
     */
    @Override
    public void initializeListeners() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initializeListeners'");
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void closeGame() {

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
}
