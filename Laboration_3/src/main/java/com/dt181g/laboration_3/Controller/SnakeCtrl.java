package com.dt181g.laboration_3.controller;

import com.dt181g.laboration_3.model.GameModel;

import javax.swing.JPanel;

public class SnakeCtrl{
    private final JPanel snakePanelView;
    private final GameModel snakeModel;

    public SnakeCtrl(JPanel snakeView, GameModel snakeModel) {
        this.snakePanelView = snakeView;
        this.snakeModel = snakeModel;
    }
}
