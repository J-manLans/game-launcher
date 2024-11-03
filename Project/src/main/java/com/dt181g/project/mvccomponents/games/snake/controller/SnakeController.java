package com.dt181g.project.mvccomponents.games.snake.controller;

import com.dt181g.project.mvccomponents.listeners.MenuButtonListener;
import com.dt181g.project.factories.BaseModelFactory;
import com.dt181g.project.factories.BaseViewFactory;
import com.dt181g.project.mvccomponents.IBaseModel;
import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.mvccomponents.games.IGameMainController;
import com.dt181g.project.mvccomponents.games.IGameMainModel;
import com.dt181g.project.mvccomponents.games.IGameMainView;
import com.dt181g.project.mvccomponents.games.listeners.SnakeMovementListener;
import com.dt181g.project.mvccomponents.games.snake.model.BoosterCherryModel;
import com.dt181g.project.mvccomponents.games.snake.model.SnakeMainModel;
import com.dt181g.project.mvccomponents.games.snake.model.SnakeModel;
import com.dt181g.project.mvccomponents.games.snake.model.BoosterSpeedModel;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeControlsView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeMainView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeSinglePlayerView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeStartMenuView;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.AudioManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * The SnakeController class manages the interaction between the Snake game view and the model.
 * It handles user inputs, updates game state, and renders changes to the view.
 *
 * <p>
 * This class defines various mouse listeners for game controls, including starting the game,
 * showing controls, and managing the game loop.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeController implements IGameMainController {
    private final String gameTitle = AppConfigProject.SNAKE_TITLE;

    private final SnakeMainView snakeMainView;
    private SnakeStartMenuView startMenuView;
    private SnakeSinglePlayerView singlePlayerView;
    private SnakeControlsView controlsView;
    private final SnakeMainModel snakeMainModel;
    private SnakeModel snakeModel;

    private Timer gameLoop;
    private boolean gameOn;

    /**
     * Constructs a SnakeController with the specified main view and model.
     *
     * @param snakeMainView the view associated with the Snake game
     * @param snakeModel    the model representing the game's logic
     */
    public SnakeController(final IGameMainView snakeMainView, final IGameMainModel snakeModel) {
        this.snakeMainView = (SnakeMainView) snakeMainView;
        this.snakeMainModel = (SnakeMainModel) snakeModel;
    }

    /**
     * Initializes the controller, instantiates views and models,
     * and sets up the necessary listeners for user input.
     */
    public void initialize() {
        this.instantiateViewsAndModels(
            SnakeStartMenuView::new,
            SnakeSinglePlayerView::new,
            SnakeControlsView::new,
            SnakeModel::new,
            BoosterCherryModel::new,
            BoosterSpeedModel::new
        );
        this.snakeMainView.setViews(this.startMenuView, this.singlePlayerView, this.controlsView);
        this.createGameLoop();
        this.initializeListeners();
    }

    /**
     * Instantiates the views and models required for the game.
     *
     * @param startMenuView        factory for creating the start menu view
     * @param singlePlayerView     factory for creating the single-player view
     * @param controlsView         factory for creating the controls view
     * @param snakeModel           factory for creating the snake model
     * @param cherryBoosterModel   factory for creating the cherry booster model
     * @param speedBoosterModel    factory for creating the speed booster model
     */
    public void instantiateViewsAndModels(
        final BaseViewFactory<IBaseView> startMenuView,
        final BaseViewFactory<IBaseView> singlePlayerView,
        final BaseViewFactory<IBaseView> controlsView,
        final BaseModelFactory<IBaseModel> snakeModel,
        final BaseModelFactory<IBaseModel> cherryBoosterModel,
        final BaseModelFactory<IBaseModel> speedBoosterModel
    ) {
        this.startMenuView = (SnakeStartMenuView) startMenuView.create();
        this.singlePlayerView = (SnakeSinglePlayerView) singlePlayerView.create();
        this.controlsView = (SnakeControlsView) controlsView.create();
        this.snakeModel = (SnakeModel) snakeModel.create();
        cherryBoosterModel.create();
        speedBoosterModel.create();
    }

    /*======================
    * Start Methods
    =====================*/

    /**
     * Creates the game loop that updates the game state at regular intervals.
     */
    private void createGameLoop() {
        this.gameLoop = new Timer(AppConfigProject.SNAKE_TICK_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (gameOn) {
                    snakeMainModel.updateGameGrid(snakeModel);

                    if (snakeModel.getGameOverState()) {
                        gameLoop.stop();
                        singlePlayerView.updateGameGrid();
                        singlePlayerView.showGameOver(snakeModel.getSnake().length, (AppConfigProject.SECOND_IN_MS / (int) snakeModel.getSpeed()));
                    } else {
                        // Adjusting speed if necessary
                        gameLoop.setDelay(Math.max(50, (int) snakeModel.getSpeed())); // Minimum delay of 50ms
                        singlePlayerView.updateGameGrid();
                    }

                } else {
                    gameLoop.stop();
                }
            }
        });
    }

    /**
     * Resets the game state and re-initializes the start menu.
     * Stops the game loop and prepares the view for restarting.
     */
    @Override
    public void initiateGame() {
        this.gameOn = false;  // stops the EDT thread from executing
        this.snakeMainView.showStartMenu();  // Shows the start menu
        this.singlePlayerView.hideGameOver();  // Hides the game over screen
    }

    /**
     * Starts the game and initializes the game loop.
     * Prepares the game state and updates the view for gameplay.
     */
    private void startGame() {
        // Game initialization.
        this.snakeMainModel.startNewGame(this.snakeModel);
        this.snakeMainView.showGame();
        this.singlePlayerView.initializeView(this.snakeMainModel.getGameAssets());

        // The key listener for the game is only added once
        if (this.singlePlayerView.getSnakeGrid().getKeyListeners().length == 0) {
            this.singlePlayerView.addSnakeKeyListener(new SnakeMovementListener(this.snakeModel));
        }

        // For controlling the loop
        this.gameOn = true;
        this.gameLoop.start();
    }

    /*======================
    * Close Methods
    =====================*/

    /**
     * Closes the game, stopping the game loop and cleaning up resources.
     */
    @Override
    public void closeGame() {
        this.stopGameLoop();
        this.removeListeners();
        this.snakeMainView.closeGameView();
        this.snakeMainModel.cleanup();
        AudioManager.INSTANCE.shutdownAudio();
    }

    /**
     * Stops the game loop timer if it is currently running when closing the game.
     */
    private void stopGameLoop() {
        if (this.gameLoop != null) {
            this.gameLoop.stop();
            this.gameLoop = null;
        }
    }

    /*======================
    * Listeners
    =====================*/

    /**
     * Initializes the listeners for user interactions with the game.
     */
    @Override
    public void initializeListeners() {
        // Button listeners for the start menu
        this.startMenuView.addStartBtnListener(
            new MenuButtonListener(
                this.startMenuView.getStartBtn(),
                this::startGame
            )
        );

        this.startMenuView.addControlsBtnListener(
            new MenuButtonListener(
                this.startMenuView.getControlsBtn(),
                this.snakeMainView::showControlsMenu
            )
        );

        // Button listeners for single player view
        this.singlePlayerView.addSnakeBackBtnListener(
            new MenuButtonListener(
                this.singlePlayerView.getSnakeBackBtn(),
                this::initiateGame
            )
        );

        // Button listeners for controls view
        this.controlsView.addSnakeBackBtnListener(
            new MenuButtonListener(
                this.controlsView.getSnakeBackBtn(),
                this.snakeMainView::showStartMenu
            )
        );
    }

    /**
     * Adds a listener for the quit button.
     * Attached by the GameIconListener.
     *
     * @param quitBtnListener the listener for the quit button
     */
    @Override
    public void addQuitBtnListener(final MouseAdapter quitBtnListener) {
        this.startMenuView.addQuitBtnListener(quitBtnListener);
    }

    /**
     * Removes all listeners from the views.
     */
    private void removeListeners() {
        this.singlePlayerView.removeListeners();
        this.startMenuView.removeListeners();
        this.controlsView.removeListeners();
    }

    /*========================
     * Getters
     =======================*/

    /**
     * Returns the title of the game.
     *
     * @return the game title
     */
    @Override
    public String getGameTitle() {
        return this.gameTitle;
    }

    /**
     * Returns the quit button from the start menu view.
     *
     * @return the quit button
     */
    public JLabel getQuitBtn() {
        return this.startMenuView.getQuitBtn();
    }
}
