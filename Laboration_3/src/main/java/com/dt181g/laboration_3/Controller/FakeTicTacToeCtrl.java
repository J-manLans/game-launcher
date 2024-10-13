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
public class FakeTicTacToeCtrl implements GameCtrl {
    private final GameView ticTacToePanelView;
    private final GameModel ticTacToeModel;

    /**
     * Constructs a FakeTicTacToeCtrl with the specified game view and model.
     *
     * @param ticTacToePanelView the view representing the Tic Tac Toe game
     * @param ticTacToeModel the model representing the game's state
     */
    public FakeTicTacToeCtrl(final GameView ticTacToePanelView, final GameModel ticTacToeModel) {
        this.ticTacToePanelView = ticTacToePanelView;
        this.ticTacToeModel = ticTacToeModel;
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

}
