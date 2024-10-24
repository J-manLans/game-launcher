package com.dt181g.project.mvccomponents.games.snake.view;

import com.dt181g.project.mvccomponents.games.GameView;
import com.dt181g.project.mvccomponents.games.listeners.SnakeMovementListener;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.DebugLogger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.List;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the view component of the Snake game, responsible for rendering
 * the game interface, including the start menu, game grid, and control menu.
 *
 * <p>
 * This class extends {@link JPanel} and implements {@link GameView}.
 * It uses a grid layout to display the snake's position on the board and
 * provides methods for interacting with game controls.
 * </p>
 *
 * <p>
 * It's still under construction and both settings and multiplayer will be
 * implemented further on.
 *
 * @author Joel Lansgren
 */
public class SnakeView extends JPanel implements GameView {
    DebugLogger logger = DebugLogger.INSTANCE;

    private final CardLayout snakeViewCL = new CardLayout();
    // Shared button between menus
    private final JLabel snakeBackBtn = new JLabel("Back");

    // Start menu components
    private JLabel title = new JLabel();
    private final SnakeStartMenuView startMenu = new SnakeStartMenuView(this);
    // Snake panel
    private final SnakeSinglePlayerView singlePlayer = new SnakeSinglePlayerView(this);
    // Control panel
    private final SnakeControlsView controlPanel = new SnakeControlsView(this);

    /** The panel this panel resides in, used for proper closing of the game. */
    private JPanel gamePanel;

    /**
     * Constructs a SnakeView with the specified title and snake cell count.
     *
     * @param title The title to be displayed on the view.
     */
    public SnakeView(final String title) {
        this.setLayout(snakeViewCL);
        // sets the title to the label
        this.title.setText(title);

        // Style snake components
        labelStyling(this.title, AppConfigProject.TEXT_HEADING_2, false);
        labelBtn(snakeBackBtn, AppConfigProject.COLOR_WHITE);

        this.startMenu.setTitle(this.title);
        this.add(startMenu, "Start Menu");
        this.add(singlePlayer, "Snake");
        this.add(controlPanel, "Control Menu");
    }

    /*===============================
     * Show methods
     ==============================*/

    /**
     * Displays the start menu with game options.
     */
    @Override
    public void ShowStartMenu() {
        this.snakeViewCL.show(this, "Start Menu");
    }

    /**
     * Displays the controls menu for the game.
     */
    public void showControlsMenu() {
        // Re-instate the shared back button
        this.controlPanel.setBackBtn();
        this.snakeViewCL.show(this, "Control Menu");
    }

    //===== Snake game =====

     /**
     * Starts the game.
     *
     * @param gameAssets the 2D array representing the snake and a String just for fun.
     */
    public void startGame(final List<Object> gameAssets) {
        this.snakeViewCL.show(this, "Snake");
        this.singlePlayer.setGameAssets(gameAssets);
        // The shared back button is re-instated in this method
        this.singlePlayer.startGame();
    }

    /**
     * Updates the game grid with the current state of the snake.
     *
     * @param gameAssets the 2D array representing the snake and a String just for fun.
     */
    public void updateGameGrid() {
        this.singlePlayer.updateGameGrid();
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
        this.startMenu.addStartBtnListener(startBtnListener);
    }

    /**
     * Adds a mouse listener to the multiplayer button.
     *
     * @param multiplayerBtnListener The mouse listener to be added to the multiplayer button,
     * allowing for interaction when the button is clicked.
     */
    public void addMultiplayerBtnListener(final MouseAdapter multiplayerBtnListener) {
        this.startMenu.addMultiplayerBtnListener(multiplayerBtnListener);
    }

    /**
     * Adds a mouse listener to the settings button.
     *
     * @param settingsBtnListener The mouse listener to be added to the settings button,
     * allowing for interaction when the button is clicked.
     */
    public void addSettingsBtnListener(final MouseAdapter settingsBtnListener) {
        this.startMenu.addSettingsBtnListener(settingsBtnListener);
    }

    /**
     * Adds a mouse listener to the controls button.
     *
     * @param controlsBtnListener The mouse listener to be added to the controls button,
     * allowing for interaction when the button is clicked.
     */
    public void addControlsBtnListener(final MouseAdapter controlsBtnListener) {
        this.startMenu.addControlsBtnListener(controlsBtnListener);
    }

    /**
     * Adds a mouse listener to the quit button.
     *
     * @param quitBtnListener The mouse listener to be added to the quit button,
     * allowing for interaction when the button is clicked.
     */
    public void addQuitBtnListener(final MouseAdapter quitBtnListener) {
        this.startMenu.addQuitBtnListener(quitBtnListener);
    }

    /**
     * Adds a mouse listener to the back button in the controls menu.
     *
     * @param controlsBackBtnListener The mouse listener to be added to the back button,
     * allowing for interaction when the button is clicked.
     */
    public void addSnakeBackBtnListener(final MouseAdapter controlsBackBtnListener) {
        this.snakeBackBtn.addMouseListener(controlsBackBtnListener);
    }

    public void addSnakeKeyListener(SnakeMovementListener snakeKeyListener) {
        this.singlePlayer.addSnakeKeyListener(snakeKeyListener);
    }


    /**
     * Removes all mouse listeners from the game control buttons.
     * This method iterates through each button and removes any attached mouse listeners,
     * ensuring that no event handlers remain active on these buttons.
     */
    public void removeListeners() {
        this.startMenu.removeListeners();
        this.singlePlayer.removeListeners();
        removeAllListenersFromButton(this.snakeBackBtn);
    }

    /**
     * Helper method that removes all mouse listeners from the specified button.
     *
     * @param button the JButton from which to remove all mouse listeners.
     */
    protected void removeAllListenersFromButton(JLabel button) {
        for (MouseListener listener : button.getMouseListeners()) {
            button.removeMouseListener(listener);
        }
    }

    /*==============================
    * Getters
    ==============================*/

    /**
     * Returns the title of the game.
     *
     * @return A string representing the title text displayed in the view.
     */
    @Override
    public String getTitle() {
        return title.getText();
    }

    /**
     * Returns the start button.
     *
     * @return The JLabel representing the start game button.
     */
    public JLabel getStartBtn() {
        return this.startMenu.getStartBtn();
    }

    /**
     * Returns the multiplayer button.
     *
     * @return The JLabel representing the multiplayer button.
     */
    public JLabel getMultiplayerBtn() {
        return this.startMenu.getMultiplayerBtn();
    }

    /**
     * Returns the settings button.
     *
     * @return The JLabel representing the settings button.
     */
    public JLabel getSettingsBtn() {
        return this.startMenu.getSettingsBtn();
    }

    /**
     * Returns the controls button.
     *
     * @return The JLabel representing the controls button.
     */
    public JLabel getControlsBtn() {
        return this.startMenu.getControlsBtn();
    }

    /**
     * Returns the quit button.
     *
     * @return The JLabel representing the quit button.
     */
    public JLabel getQuitBtn() {
        return this.startMenu.getQuitBtn();
    }

    /**
     * Returns the back button in the controls menu.
     *
     * @return The JLabel representing the back button.
     */
    public JLabel getSnakeBackBtn() {
        return this.snakeBackBtn;
    }

    /*=========================================
    * Override methods (not Override getters)
    =========================================*/

    @Override
    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void closeGameView() {
        if (this.gamePanel != null) {
            this.clearGameView(this.gamePanel, this, title.getText());
        }
    }
}
