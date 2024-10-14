
package com.dt181g.laboration_3.controller;

import com.dt181g.laboration_3.model.GameModel;
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
    private final GameView ticTacToePanelView;
    private final GameModel ticTacToeModel;
    private final String title;

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
    public void resetGame() {
        this.ticTacToePanelView.resetGame();
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

}
