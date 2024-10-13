package com.dt181g.laboration_3.view;

import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.DebugLogger;

import java.awt.event.MouseAdapter;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JPanel;



public class SnakeView extends JPanel implements GameView{
    DebugLogger logger = DebugLogger.INSTANCE;

    // Start menu components
    GridBagConstraints gbc = new GridBagConstraints();
    private final JLabel title = new JLabel(AppConfigLab3.SNAKE_TITLE);
    private final JLabel startBtn = new JLabel("Start Game");
    private final JLabel multiplayerBtn = new JLabel("Multiplayer");
    private final JLabel settingsBtn = new JLabel("Settings");
    private final JLabel controlsBtn = new JLabel("Controls");

    // Snake panel
    JPanel snakeGrid = new JPanel(new GridLayout(40, 40));

    public SnakeView() {
        this.setLayout(new GridBagLayout());
    }

    private void initializeStartMenu() {
        // Styles the components
        AppConfigLab3.LABEL_STYLING(title);
        AppConfigLab3.LABEL_BUTTON(startBtn, AppConfigLab3.WHITE);
        AppConfigLab3.LABEL_BUTTON(multiplayerBtn, AppConfigLab3.DARK_GREY);
        AppConfigLab3.LABEL_BUTTON(settingsBtn, AppConfigLab3.DARK_GREY);
        AppConfigLab3.LABEL_BUTTON(controlsBtn, AppConfigLab3.WHITE);

        // Adds and place the components on the grid.
        gbc.gridy = 0;
        this.add(title, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(startBtn, gbc);

        gbc.gridy++;
        this.add(multiplayerBtn, gbc);

        gbc.gridy++;
        this.add(settingsBtn, gbc);

        gbc.gridy++;
        this.add(controlsBtn, gbc);

        // Sets the background color
        this.setBackground(AppConfigLab3.DARKER_GREY);
    }

    public void startGame() {
        this.removeAll();

        // Setting up the snake grid
        this.initializeGrid();

        this.add(snakeGrid);
        this.revalidate();
        this.repaint();
    }

    private void initializeGrid() {
        snakeGrid.removeAll();
        snakeGrid.setPreferredSize(AppConfigLab3.SNAKE_GRID_SIZE);

        for (int i = 0; i < 40 * 40; i++) {
            JPanel cell = new JPanel();
            cell.setBorder(BorderFactory.createLineBorder(AppConfigLab3.DARK_GREY));
            cell.setBackground(AppConfigLab3.DARKER_GREY);
            snakeGrid.add(cell);
        }
    }

    public void showOptions() {
        // Work to be done here!
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

    public void addControlsBtnListener(MouseAdapter controlsBtnListener) {
        this.controlsBtn.addMouseListener(controlsBtnListener);
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

    public JLabel getControlsBtn() {
        return this.controlsBtn;
    }
    /*==============================
     * Override methods (not Override getters)
     ==============================*/
    @Override
    public void resetGame() {
        this.removeAll();
        initializeStartMenu();
    }
}
