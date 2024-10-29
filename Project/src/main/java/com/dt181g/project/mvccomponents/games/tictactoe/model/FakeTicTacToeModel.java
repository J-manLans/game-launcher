package com.dt181g.project.mvccomponents.games.tictactoe.model;

import com.dt181g.project.mvccomponents.games.GameMainModel;
import com.dt181g.project.support.AppConfigProject;

/**
 * A mock implementation of the class for the Tic Tac Toe game.
 * <p>
 * This class is used for testing purposes to simulate the game model when the actual
 * game logic is not yet implemented. It provides basic information such as the game title
 * and icon path, which are necessary for the game launcher to display relevant details.
 * </p>
 */
public class FakeTicTacToeModel implements GameMainModel {
    private final String iconPath = AppConfigProject.PATH_TO_ICONS + AppConfigProject.TIC_TAC_TOE_ICON;
    private final String gameTitle = AppConfigProject.TIC_TAC_TOE_TITLE;

    /**
     * May be implemented in the future.
     */
    @Override
    public String getGameTitle() {
        return gameTitle;
    }

    /**
     * May be implemented in the future.
     */
    @Override
    public String getIconPath() {
        return iconPath;
    }
}
