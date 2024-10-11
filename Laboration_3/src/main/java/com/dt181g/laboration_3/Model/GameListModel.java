package com.dt181g.laboration_3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dt181g.laboration_3.support.AppConfigLab3;

public class GameListModel {
    List<GameModel> games = new ArrayList<GameModel>();

    public GameListModel() {
        this.games.add(new SnakeModel(AppConfigLab3.SNAKE_TITLE, AppConfigLab3.PATH_TO_ICONS + AppConfigLab3.SNAKE_ICON));
        this.games.add(new FakeTicTacToeModel(AppConfigLab3.TIC_TAC_TOE_TITLE, AppConfigLab3.PATH_TO_ICONS + AppConfigLab3.TIC_TAC_TOE_ICON));
    }

    public List<String> getTitleList() {
        return games.stream()
            .map(GameModel::getTitle)
            .collect(Collectors.toList());
    }

    public List<String> getIconPath() {
        return games.stream()
            .map(GameModel::getIconPath)
            .collect(Collectors.toList());
    }

    public GameModel getGame(String title) {
        for (GameModel gameModel : games) {
            if (gameModel.getTitle().equals(title)) {
                return gameModel;
            }
        }
        return null;
    }
}
