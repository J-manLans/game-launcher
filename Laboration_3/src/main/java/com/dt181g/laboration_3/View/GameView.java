package com.dt181g.laboration_3.view;

import javax.swing.JPanel;

/**
 * Interface representing a game view in the game launcher application.
 *
 * <p>
 * This interface defines the necessary methods for starting, resetting,
 * and retrieving information from a game view.
 * </p>
 */
public interface GameView {
    void startGame(int[][] gameModel, String text);
    JPanel getPanel();
    void resetGame();
    String getTitle();
}