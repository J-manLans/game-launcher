package com.dt181g.laboration_3.controller;

import com.dt181g.laboration_3.model.GameModel;
import com.dt181g.laboration_3.model.SnakeModel;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.GameView;
import com.dt181g.laboration_3.view.SnakeView;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class SnakeCtrl implements GameCtrl{
    private final SnakeView snakeView;
    private final SnakeModel snakeModel;

    public SnakeCtrl(GameView snakeView, GameModel snakeModel) {
        this.snakeView = (SnakeView) snakeView;
        this.snakeModel = (SnakeModel) snakeModel;
        this.initializeListeners();
    }

    public void initializeListeners() {
        snakeView.addStartBtnListener(new StartBtnListener(this.snakeView));
    }

    private void updateButtonAppearance(JLabel button, boolean isHovered) {
        button.setOpaque(isHovered);
        button.setForeground(isHovered ? AppConfigLab3.DARKER_GREY : AppConfigLab3.WHITE);
    }

    @Override
    public void resetGame() {
        this.snakeView.resetGame();
    }

    /*========================
     * Listeners
     =======================*/
    class StartBtnListener extends MouseAdapter {
        JLabel startBtn;
        SnakeView snakeView;

        StartBtnListener(SnakeView snakeView) {
            this.snakeView = snakeView;
            this.startBtn = snakeView.getStartBtn();
            startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // Makes sure the label button is transparent on the next startup.
            startBtn.setOpaque(false);
            snakeView.startGame();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            updateButtonAppearance(startBtn, true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            updateButtonAppearance(startBtn, false);
        }
    }

    class MultiplayerBtnListener extends MouseAdapter {
        JLabel multiplayerBtn;

        MultiplayerBtnListener(JLabel multiplayerBtn) {
            this.multiplayerBtn = multiplayerBtn;
            multiplayerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            updateButtonAppearance(multiplayerBtn, true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            updateButtonAppearance(multiplayerBtn, false);
        }
    }

    class SettingsBtnListener extends MouseAdapter {
        JLabel settingsBtn;

        SettingsBtnListener(JLabel settingsBtn) {
            this.settingsBtn = settingsBtn;
            settingsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            updateButtonAppearance(settingsBtn, true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            updateButtonAppearance(settingsBtn, false);
        }
    }
}
