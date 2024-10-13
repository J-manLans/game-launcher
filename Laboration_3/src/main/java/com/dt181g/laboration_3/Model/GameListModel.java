package com.dt181g.laboration_3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.FakeTicTacToeView;
import com.dt181g.laboration_3.view.GameView;
import com.dt181g.laboration_3.view.SnakeView;

public class GameListModel {
    List<GameModel> gamesModels = new ArrayList<GameModel>();
    List<GameView> gameViews = new ArrayList<GameView>();

    public GameListModel() {
        // Instantiating game models
        this.gamesModels.add(new SnakeModel(AppConfigLab3.SNAKE_TITLE, AppConfigLab3.PATH_TO_ICONS + AppConfigLab3.SNAKE_ICON));
        this.gamesModels.add(new FakeTicTacToeModel(AppConfigLab3.TIC_TAC_TOE_TITLE, AppConfigLab3.PATH_TO_ICONS + AppConfigLab3.TIC_TAC_TOE_ICON));

        // Instantiating game views
        this.gameViews.add(new SnakeView());
        this.gameViews.add(new FakeTicTacToeView());
    }

    public List<String> getTitleList() {
        return gamesModels.stream()
            .map(GameModel::getTitle)
            .collect(Collectors.toList());
    }

    public List<String> getIconPath() {
        return gamesModels.stream()
            .map(GameModel::getIconPath)
            .collect(Collectors.toList());
    }

    public GameModel getGameModel(String title) {
        for (GameModel gameModel : gamesModels) {
            if (gameModel.getTitle().equals(title)) {
                return gameModel;
            }
        }
        return null;
    }

    public GameView getGameView(String title) {
        for (GameView gameView : gameViews) {
            if (gameView.getTitle().equals(title)) {
                return gameView;
            }
        }
        return null;
    }
}
