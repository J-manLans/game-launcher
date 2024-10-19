package com.dt181g.laboration_3.games.tictactoe.model;

import com.dt181g.laboration_3.games.GameModel;
import com.dt181g.laboration_3.support.AppConfigLab3;

/**
 * A mock implementation of the class for the Tic Tac Toe game.
 * <p>
 * This class is used for testing purposes to simulate the game model when the actual
 * game logic is not yet implemented. It provides basic information such as the game title
 * and icon path, which are necessary for the game launcher to display relevant details.
 * </p>
 */
public class FakeTicTacToeModel implements GameModel {
    private final String iconPath = AppConfigLab3.PATH_TO_ICONS + AppConfigLab3.TIC_TAC_TOE_ICON;
    private final String title = AppConfigLab3.TIC_TAC_TOE_TITLE;

    /**
     * May be implemented in the future.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * May be implemented in the future.
     */
    @Override
    public String getIconPath() {
        return iconPath;
    }
}
