package com.dt181g.laboration_3.view;

import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.DebugLogger;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;



public class SnakeView extends JPanel implements GameView{
    DebugLogger logger = DebugLogger.INSTANCE;

    private final JLabel title = new JLabel();
    private final JLabel startBtn = new JLabel("Start Game");
    private final JLabel multiplayerBtn = new JLabel("Multiplayer");
    private final JLabel settingsBtn = new JLabel("Settings");

    JPanel gridPanel = new JPanel(new GridLayout(40, 40));

    public SnakeView(final String title) {
        this.title.setText(title);
    }

    private void initializeStartMenu() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        AppConfigLab3.LABEL_STYLING(title);
        AppConfigLab3.LABEL_BUTTON(startBtn, AppConfigLab3.WHITE);
        AppConfigLab3.LABEL_BUTTON(multiplayerBtn, AppConfigLab3.DARK_GREY);
        AppConfigLab3.LABEL_BUTTON(settingsBtn, AppConfigLab3.DARK_GREY);

        this.setBackground(AppConfigLab3.DARKER_GREY);
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(startBtn);
        this.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));
        this.add(multiplayerBtn);
        this.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));
        this.add(settingsBtn);
        this.add(Box.createVerticalGlue());
    }

    public void startGame() {
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

    /*==============================
     * Listener methods
     ==============================*/
    public void addStartBtnListener(MouseAdapter startBtnListener) {
        this.startBtn.addMouseListener(startBtnListener);
    }

    public void addMultiplayerBtnListener(MouseAdapter multiplayerBtnListener) {
        this.multiplayerBtn.addMouseListener(multiplayerBtnListener);
    }

    public void addSettingsBtnListener(MouseAdapter settingsBtnListener) {
        this.settingsBtn.addMouseListener(settingsBtnListener);
    }

    /*==============================
     * Getters
     ==============================*/
    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public String getTitle() {
        return title.getText();
    }

    public JLabel getStartBtn() {
        return this.startBtn;
    }

    public JLabel getMultiplayerBtn() {
        return this.multiplayerBtn;
    }

    public JLabel getSettingsBtn() {
        return this.settingsBtn;
    }

    /*==============================
     * Override methods (not getters)
     ==============================*/
    @Override
    public void resetGame() {
        this.removeAll();
        initializeStartMenu();
    }
}
