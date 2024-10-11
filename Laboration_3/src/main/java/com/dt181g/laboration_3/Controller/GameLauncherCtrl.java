package com.dt181g.laboration_3.controller;

import javax.swing.Box;

import com.dt181g.laboration_3.model.GameListModel;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.GameLauncherView;

public class GameLauncherCtrl {
    private final GameLauncherView gameLauncherView;
    private final GameListModel gameListModel;

    public GameLauncherCtrl(GameLauncherView gameLauncherView, GameListModel gameListModel) {
        this.gameLauncherView = gameLauncherView;
        this.gameListModel = gameListModel;
    }

    public void initialize() {
        this.gameLauncherView.addGameButtons(this.gameListModel.getIconPath(), this.gameListModel.getTitleList());
        this.gameLauncherView.getGameSelectorPanel().add(Box.createVerticalGlue());

        SnakeCtrl snakeCtrl = new SnakeCtrl(gameLauncherView.getSnakePanelView(), gameListModel.getGame(AppConfigLab3.SNAKE_TITLE));
    }
}
