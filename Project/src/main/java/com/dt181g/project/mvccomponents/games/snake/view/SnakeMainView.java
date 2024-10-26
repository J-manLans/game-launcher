package com.dt181g.project.mvccomponents.games.snake.view;

import com.dt181g.project.mvccomponents.games.GameView;
import com.dt181g.project.support.AppConfigProject;

import java.awt.CardLayout;

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
    private String gameTitle = AppConfigProject.SNAKE_TITLE;
    private final CardLayout cardLayout = new CardLayout();

    // Shared button between menus

    /** The panel this panel resides in, used for proper closing of the game. */
    private JPanel gamePanel;

    public SnakeMainView() {
        this.setLayout(cardLayout);
    }

    public void setViews(
        final SnakeStartMenuView startMenuView,
        final SnakeSinglePlayerView singlePlayerView,
        final SnakeControlsView controlsView
    ) {
        this.add(startMenuView, "Start Menu");
        this.add(singlePlayerView, "Single Player Game On");
        this.add(controlsView, "Controls Menu");
    }

    /*===============================
     * Show views
     ==============================*/

    /**
     * Displays the start menu with game options.
     */
    @Override
    public void showStartMenu() {
        this.cardLayout.show(this, "Start Menu");
    }

    /**
     * Displays the controls menu for the game.
     */
    public void showControlsMenu() {
        this.cardLayout.show(this, "Controls Menu");
    }

    //===== Snake game =====

     /**
     * Starts the game.
     *
     * @param gameAssets the 2D array representing the snake and a String just for fun.
     */
    @Override
    public void showGame() {
        this.cardLayout.show(this, "Single Player Game On");
    }

    /*=========================================
    * Override methods (not Override getters)
    =========================================*/

    @Override
    public void setGamePanel(final JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public String getGameTitle() {
        return this.gameTitle;
    }

    @Override
    public void closeGameView() {
        if (this.gamePanel != null) {
            this.clearGameView(this.gamePanel, this, this.gameTitle);
        }
    }
}
