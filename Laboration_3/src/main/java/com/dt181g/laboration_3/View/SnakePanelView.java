package com.dt181g.laboration_3.view;

import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.DebugLogger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JPanel;



public class SnakePanelView extends JPanel{
    DebugLogger logger = DebugLogger.INSTANCE;

    JLabel title = new JLabel(AppConfigLab3.SNAKE_TITLE.toUpperCase());
    JLabel startBtn = new JLabel("Start Game");
    JLabel multiplayer = new JLabel("Multiplayer");
    JLabel settings = new JLabel("Settings");

    public SnakePanelView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.initializeStartMenu();
    }

    private void initializeStartMenu() {

        startBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // So the label button will be transparent on startup
                startBtn.setOpaque(false);
                startGame(startBtn);
            }
        });

        AppConfigLab3.LABEL_STYLING(title);
        AppConfigLab3.LABEL_BUTTON(startBtn, AppConfigLab3.WHITE, AppConfigLab3.DARKER_GREY);
        AppConfigLab3.LABEL_BUTTON(multiplayer, AppConfigLab3.DARK_GREY, AppConfigLab3.DARKER_GREY);
        // Remove when feature is implemented
        for (MouseListener listener : multiplayer.getMouseListeners()) {
            multiplayer.removeMouseListener(listener);
        }
        AppConfigLab3.LABEL_BUTTON(settings, AppConfigLab3.DARK_GREY, AppConfigLab3.DARKER_GREY);
        // Remove when feature is implemented
        for (MouseListener listener : settings.getMouseListeners()) {
            settings.removeMouseListener(listener);
        }

        this.setBackground(AppConfigLab3.DARKER_GREY);
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(startBtn);
        this.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));
        this.add(multiplayer);
        this.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));
        this.add(settings);
        this.add(Box.createVerticalGlue());
    }

    private void startGame(JLabel btn) {
        this.removeAll();
        this.revalidate();
        this.repaint();
    }

    public JPanel getPanel() {
        return this;
    }

    public void resetGame() {
        this.removeAll();
        initializeStartMenu();
    }
}
