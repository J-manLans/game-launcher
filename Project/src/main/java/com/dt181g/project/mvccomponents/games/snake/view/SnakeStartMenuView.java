package com.dt181g.project.mvccomponents.games.snake.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dt181g.project.mvccomponents.BaseView;
import com.dt181g.project.support.AppConfigProject;

public class SnakeStartMenuView extends JPanel implements BaseView {
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JLabel startBtn = new JLabel("Start Game");
    private final JLabel multiplayerBtn = new JLabel("Multiplayer");
    private final JLabel settingsBtn = new JLabel("Settings");
    private final JLabel controlsBtn = new JLabel("Controls");
    private final JLabel quitBtn = new JLabel("Quit");

    /**
     * Initializes and displays the start menu with game options.
     *
     * <p>
     * This constructor sets up the layout and adds the necessary buttons and labels
     * to allow the user to start the game, access multiplayer, settings, and controls.
     * </p>
     */
    public SnakeStartMenuView() {
        this.setLayout(new GridBagLayout());

        labelBtn(startBtn, AppConfigProject.COLOR_WHITE);
        labelBtn(multiplayerBtn, AppConfigProject.COLOR_DARK_GREY);
        labelBtn(settingsBtn, AppConfigProject.COLOR_DARK_GREY);
        labelBtn(controlsBtn, AppConfigProject.COLOR_WHITE);
        labelBtn(quitBtn, AppConfigProject.COLOR_WHITE);

        // Adds and place the components on the grid.
        this.gbc.gridy = 1;
        this.gbc.insets = AppConfigProject.INSET_BOTTOM_20;

        this.add(startBtn, this.gbc);

        this.gbc.gridy++;
        this.add(multiplayerBtn, this.gbc);

        this.gbc.gridy++;
        this.add(settingsBtn, this.gbc);

        this.gbc.gridy++;
        this.add(controlsBtn, this.gbc);

        this.gbc.gridy++;
        this.add(quitBtn, this.gbc);

        // Sets the background color
        this.setBackground(AppConfigProject.COLOR_DARKER_GREY);
    }

    protected void setTitle(JLabel title) {
        this.gbc.gridy = 0;
        this.add(title, this.gbc);
    }

    /*==============================
     * Listener methods
     ==============================*/

    /**
     * Adds a mouse listener to the start button.
     *
     * @param startBtnListener The mouse listener to be added to the start button,
     * allowing for interaction when the button is clicked.
     */
    public void addStartBtnListener(final MouseAdapter startBtnListener) {
        this.startBtn.addMouseListener(startBtnListener);
    }

    /**
     * Adds a mouse listener to the multiplayer button.
     *
     * @param multiplayerBtnListener The mouse listener to be added to the multiplayer button,
     * allowing for interaction when the button is clicked.
     */
    protected void addMultiplayerBtnListener(final MouseAdapter multiplayerBtnListener) {
        this.multiplayerBtn.addMouseListener(multiplayerBtnListener);
    }

    /**
     * Adds a mouse listener to the settings button.
     *
     * @param settingsBtnListener The mouse listener to be added to the settings button,
     * allowing for interaction when the button is clicked.
     */
    protected void addSettingsBtnListener(final MouseAdapter settingsBtnListener) {
        this.settingsBtn.addMouseListener(settingsBtnListener);
    }

    /**
     * Adds a mouse listener to the controls button.
     *
     * @param controlsBtnListener The mouse listener to be added to the controls button,
     * allowing for interaction when the button is clicked.
     */
    public void addControlsBtnListener(final MouseAdapter controlsBtnListener) {
        this.controlsBtn.addMouseListener(controlsBtnListener);
    }

    /**
     * Adds a mouse listener to the quit button.
     *
     * @param quitBtnListener The mouse listener to be added to the quit button,
     * allowing for interaction when the button is clicked.
     */
    public void addQuitBtnListener(final MouseAdapter quitBtnListener) {
        this.quitBtn.addMouseListener(quitBtnListener);
    }

    public void removeListeners() {
        removeAllListenersFromButton(this.startBtn);
        removeAllListenersFromButton(this.multiplayerBtn);
        removeAllListenersFromButton(this.settingsBtn);
        removeAllListenersFromButton(this.quitBtn);
    }

    /*==============================
     * Getters
     ==============================*/

    public JLabel getStartBtn() {
        return startBtn;
    }

    protected JLabel getMultiplayerBtn() {
        return multiplayerBtn;
    }

    protected JLabel getSettingsBtn() {
        return settingsBtn;
    }

    public JLabel getControlsBtn() {
        return controlsBtn;
    }

    public JLabel getQuitBtn() {
        return quitBtn;
    }
}
