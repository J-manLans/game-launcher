package com.dt181g.laboration_3.issuer;

import javax.swing.SwingUtilities;

import com.dt181g.laboration_3.controller.GameLauncherCtrl;
import com.dt181g.laboration_3.model.GameListModel;
import com.dt181g.laboration_3.view.GameLauncherView;

public enum GameLauncherInitializer {
    INSTANCE;

    GameLauncherView gameLauncherView = new GameLauncherView();
    GameListModel gameListModel = new GameListModel();
    GameLauncherCtrl gameLauncherController = new GameLauncherCtrl(gameLauncherView, gameListModel);

    public void runLauncher() {
        gameLauncherController.initializeLauncher();

        SwingUtilities.invokeLater(() -> {
            gameLauncherView.setVisible(true);
        });
    }
}
