package com.dt181g.laboration_3.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import com.dt181g.laboration_3.model.GameListModel;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.GameLauncherView;

public class GameLauncherCtrl {
    private final GameLauncherView gameLauncherView;
    private final GameListModel gameListModel;
    private final Map<String, GameCtrl> gameControllers = new HashMap<>();

    public GameLauncherCtrl(GameLauncherView gameLauncherView, GameListModel gameListModel) {
        this.gameLauncherView = gameLauncherView;
        this.gameListModel = gameListModel;
    }

    public void initializeLauncher() {
        this.gameLauncherView.addGameIcons(this.gameListModel.getIconPath(),
            gameListModel.getTitleList()
        );
        this.gameLauncherView.addGameIconListener(new GameIconListener());
    }

    /*========================
     * Listeners
     =======================*/
    class GameIconListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the game title from the clicked button's action command set earlier.
            String title = e.getActionCommand();

            // Create and store the game controller if it doesn't exist yet.
            switch (title) {
                case AppConfigLab3.SNAKE_TITLE -> {
                    if (!gameControllers.containsKey(title)) {
                        GameCtrl snakeCtrl = new SnakeCtrl(
                            gameListModel.getGameView(title),
                            gameListModel.getGameModel(title)
                        );
                        gameControllers.put(title, snakeCtrl);
                    }
                } case AppConfigLab3.TIC_TAC_TOE_TITLE -> {
                    if (!gameControllers.containsKey(title)) {
                        GameCtrl ticTacToeCtrl = new FakeTicTacToeCtrl(
                            gameListModel.getGameView(title),
                            gameListModel.getGameModel(title)
                        );
                        gameControllers.put(title, ticTacToeCtrl);
                    }
                }
            }

            // Reset the game and load it into the launcher.
            gameControllers.get(title).resetGame();
            gameLauncherView.loadGame(gameListModel.getGameView(title));
        }
    }
}
