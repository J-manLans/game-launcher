package com.dt181g.laboration_3.controller;

import com.dt181g.laboration_3.model.GameModel;
import com.dt181g.laboration_3.view.GameView;

public class FakeTicTacToeCtrl implements GameCtrl{
    private final GameView ticTacToePanelView;
    private final GameModel ticTacToeModel;

    public FakeTicTacToeCtrl(GameView TicTacToePanelView, GameModel ticTacToeModel) {
        this.ticTacToePanelView = TicTacToePanelView;
        this.ticTacToeModel = ticTacToeModel;
    }

    @Override
    public void resetGame() {
        this.ticTacToePanelView.resetGame();
    }

    @Override
    public void initializeListeners() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initializeListeners'");
    }

}
