package com.dt181g.laboration_3.view;

import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.DebugLogger;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;



public class SnakePanelView extends JPanel{
    DebugLogger logger = DebugLogger.INSTANCE;

    JLabel title = new JLabel(AppConfigLab3.SNAKE_TITLE.toUpperCase());
    JLabel startBtn = new JLabel("Start Game");
    JLabel multiplayer = new JLabel("Multiplayer");
    JLabel settings = new JLabel("Settings");

    JPanel gridPanel = new JPanel(new GridLayout(40, 40));

    public SnakePanelView() {
        this.initializeStartMenu();
    }

    private void initializeStartMenu() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        startBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // So the label button will be transparent on next startup
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
        // Clearing the JPanel
        this.removeAll();

        // Setting up the grid
        gridPanel.removeAll();
        gridPanel.setPreferredSize(AppConfigLab3.SNAKE_GRID_SIZE);
        this.initializeGrid(gridPanel);

        // Centering the grid
        this.setLayout(null);
        gridPanel.setBounds(
            (this.getWidth() - gridPanel.getPreferredSize().width) / 2,
            (this.getHeight() - gridPanel.getPreferredSize().height) / 2,
            gridPanel.getPreferredSize().width,
            gridPanel.getPreferredSize().height
        );

        // adding grid to the JPanel
        this.add(gridPanel);

        // Updating the JPanel with the grid
        this.revalidate();
        this.repaint();
    }

    private void initializeGrid(JPanel grid) {
        for (int i = 0; i < 40 * 40; i++) {
            JPanel cell = new JPanel();
            cell.setBorder(BorderFactory.createLineBorder(AppConfigLab3.DARK_GREY));
            cell.setBackground(AppConfigLab3.DARKER_GREY);
            grid.add(cell);
        }
    }

    public JPanel getPanel() {
        return this;
    }

    public void resetGame() {
        this.removeAll();
        initializeStartMenu();
    }
}
