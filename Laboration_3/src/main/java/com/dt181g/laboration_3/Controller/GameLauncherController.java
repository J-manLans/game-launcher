package com.dt181g.laboration_3.controller;

import javax.swing.Box;

import com.dt181g.laboration_3.model.GameListModel;
import com.dt181g.laboration_3.view.GameLauncherView;

public class GameLauncherController {
    private GameLauncherView gameLauncherView;
    private GameListModel gameListModel;

    public GameLauncherController(GameLauncherView gameLauncherView, GameListModel gameListModel) {
        this.gameLauncherView = gameLauncherView;
        this.gameListModel = gameListModel;
    }

    public void initialize() {
        this.gameLauncherView.addGameButtons(this.gameListModel.getIconPath(), this.gameLauncherView::loadGame);
        this.gameLauncherView.getGameSelectorPanel().add(Box.createVerticalGlue());
    }
}
