package com.dt181g.project.mvccomponents.games.snake.view;

import com.dt181g.project.mvccomponents.games.GameView;
import com.dt181g.project.support.AppConfigProject;

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
public class SnakeMainView extends JPanel implements GameView {
    private String title = AppConfigProject.SNAKE_TITLE; //TODO: create a setter
    private final CardLayout snakeViewCL = new CardLayout();
    // Shared button between menus
    private final JLabel snakeBackBtn = new JLabel("Back");
    /** The panel this panel resides in, used for proper closing of the game. */
    private JPanel gamePanel;

    /**
     * Constructs a SnakeView with the specified title and snake cell count.
     *
     * @param title The title to be displayed on the view.
     */
    public SnakeMainView() {
        // Sets card layout
        this.setLayout(snakeViewCL);
        // Style snake components
        labelBtn(this.snakeBackBtn, AppConfigProject.COLOR_WHITE);
    }

    public void setViews() {
        //TODO: add all views from the controller here
    }

    /*===============================
     * Show views
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
        this.snakeViewCL.show(this, "Control Menu");
    }

    //===== Snake game =====

     /**
     * Starts the game.
     *
     * @param gameAssets the 2D array representing the snake and a String just for fun.
     */
    @Override
    public void startGame(final List<Object> gameAssets) {
        this.snakeViewCL.show(this, "Snake");
    }

    /*==============================
     * Listener methods
     ==============================*/

    /**
     * Adds a mouse listener to the back button in the controls menu.
     *
     * @param controlsBackBtnListener The mouse listener to be added to the back button,
     * allowing for interaction when the button is clicked.
     */
    public void addSnakeBackBtnListener(final MouseAdapter controlsBackBtnListener) {
        this.snakeBackBtn.addMouseListener(controlsBackBtnListener);
    }

    /**
     * Removes all mouse listeners from the game control buttons.
     * This method iterates through each button and removes any attached mouse listeners,
     * ensuring that no event handlers remain active on these buttons.
     */
    public void removeListeners() {
        removeAllListenersFromButton(this.snakeBackBtn);
    }

    /**
     * Helper method that removes all mouse listeners from the specified button.
     *
     * @param button the JButton from which to remove all mouse listeners.
     */
    protected void removeAllListenersFromButton(final JLabel button) {
        for (final MouseListener listener : button.getMouseListeners()) {
            button.removeMouseListener(listener);
        }
    }

    /*==============================
    * Getters
    ==============================*/

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
    public void setGamePanel(final JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void closeGameView() {
        if (this.gamePanel != null) {
            this.clearGameView(this.gamePanel, this, this.title);
        }
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
