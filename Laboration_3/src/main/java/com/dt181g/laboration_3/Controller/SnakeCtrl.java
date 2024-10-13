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
        snakeView.addStartBtnListener(new StartBtnListener());
        snakeView.addControlsBtnListener(new ControlsBtnListener());
    }

    @Override
    public void resetGame() {
        this.snakeView.resetGame();
    }

    /*========================
    * Listeners
    =======================*/
    private void updateButtonAppearance(JLabel button, boolean isHovered) {
        button.setOpaque(isHovered);
        button.setForeground(isHovered ? AppConfigLab3.DARKER_GREY : AppConfigLab3.WHITE);
    }

    class StartBtnListener extends MouseAdapter {
        JLabel startBtn = snakeView.getStartBtn();

        StartBtnListener() {
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
        JLabel multiplayerBtn = snakeView.getMultiplayerBtn();

        MultiplayerBtnListener() {
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
        JLabel settingsBtn = snakeView.getSettingsBtn();

        SettingsBtnListener() {
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

    class ControlsBtnListener extends MouseAdapter {
        JLabel controlsBtn = snakeView.getControlsBtn();

        ControlsBtnListener() {
            controlsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // Makes sure the label button is transparent on the next startup.
            controlsBtn.setOpaque(false);
            snakeView.showOptions();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            updateButtonAppearance(controlsBtn, true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            updateButtonAppearance(controlsBtn, false);
        }
    }
}
