package com.dt181g.laboration_3.view;

import javax.swing.JPanel;

public interface GameView {
    void startGame();
    JPanel getPanel();
    void resetGame();
    String getTitle();
}