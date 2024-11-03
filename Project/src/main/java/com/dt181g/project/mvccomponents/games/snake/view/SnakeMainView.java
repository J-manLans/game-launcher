package com.dt181g.project.mvccomponents.games.snake.view;

import com.dt181g.project.mvccomponents.games.IGameMainView;
import com.dt181g.project.support.AppConfigProject;

import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 * Represents the view component of the Snake game, responsible for rendering
 * the game interface, including the start menu, game grid, and control menu.
 *
 * <p>
 * This class extends {@link JPanel} and implements {@link IGameMainView}.
 * It uses a card layout to display the different views of the Snake game.
 * </p>
 *
 * <p>
 * This class is still under construction, with plans to implement additional
 * settings and multiplayer functionality in the future.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeMainView extends JPanel implements IGameMainView {
    private final String gameTitle = AppConfigProject.SNAKE_TITLE;
    private final CardLayout cardLayout = new CardLayout();

    // Shared button between menus

    /** The panel this panel resides in, used for proper closing of the game. */
    private JPanel gamePanel;

    /**
     * Constructs a SnakeMainView and sets the layout to CardLayout.
     */
    public SnakeMainView() {
        this.setLayout(cardLayout);
    }

    /**
     * Sets the different views for the Snake game.
     *
     * @param startMenuView the view for the start menu
     * @param singlePlayerView the view for the single-player game
     * @param controlsView the view for the controls menu
     */
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
     * Starts the game by displaying the single-player view.
     */
    @Override
    public void showGame() {
        this.cardLayout.show(this, "Single Player Game On");
    }

    /*=========================================
    * Override methods
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
