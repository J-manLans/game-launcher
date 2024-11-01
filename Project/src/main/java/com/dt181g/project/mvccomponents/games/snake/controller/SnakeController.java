package com.dt181g.project.mvccomponents.games.snake.controller;

import com.dt181g.project.mvccomponents.listeners.MenuButtonListener;
import com.dt181g.project.factories.GameModelFactory;
import com.dt181g.project.factories.GameViewFactory;
import com.dt181g.project.mvccomponents.BaseModel;
import com.dt181g.project.mvccomponents.BaseView;
import com.dt181g.project.mvccomponents.games.GameMainController;
import com.dt181g.project.mvccomponents.games.GameMainModel;
import com.dt181g.project.mvccomponents.games.GameMainView;
import com.dt181g.project.mvccomponents.games.listeners.SnakeMovementListener;
import com.dt181g.project.mvccomponents.games.snake.model.CherryBoosterModel;
import com.dt181g.project.mvccomponents.games.snake.model.SnakeMainModel;
import com.dt181g.project.mvccomponents.games.snake.model.SnakeModel;
import com.dt181g.project.mvccomponents.games.snake.model.SpeedBoosterModel;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeControlsView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeMainView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeSinglePlayerView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeStartMenuView;
import com.dt181g.project.support.AppConfigProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * The SnakeCtrl class serves as the controller for the Snake game.
 * It mediates the interaction between the SnakeView and SnakeModel,
 * handling user inputs, updating the game state, and rendering changes
 * to the view.
 *
 * <p>
 * This class implements the GameCtrl interface and defines various
 * mouse listeners for game controls, including starting the game and
 * showing controls.
 * </p>
 *
 * <p>
 * This program is under construction and more game mechanics will be
 * incorporated moving forward, as of now, the game only displays a
 * snake with a banner text informing more is to come.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeController implements GameMainController {

    private final String gameTitle = AppConfigProject.SNAKE_TITLE;

    private final SnakeMainView snakeMainView;
    private SnakeStartMenuView startMenuView;
    private SnakeSinglePlayerView singlePlayerView;
    private SnakeControlsView controlsView;
    private final SnakeMainModel snakeMainModel;
    private SnakeModel snakeModel;

    private Timer gameLoop;
    private boolean restart;
    private boolean isRunning = true;



    /**
     * Constructs a SnakeCtrl with the specified game title, SnakeView and SnakeModel.
     * It also initialize the listeners.
     *
     * @param snakeMainView the view associated with the Snake game
     * @param snakeModel the model representing the game's logic
     */
    public SnakeController(final GameMainView snakeMainView, final GameMainModel snakeModel) {
        this.snakeMainView = (SnakeMainView) snakeMainView;
        this.snakeMainModel = (SnakeMainModel) snakeModel;
    }

    public void initialize() {
        this.instantiateViewsAndModels(
            SnakeStartMenuView::new,
            SnakeSinglePlayerView::new,
            SnakeControlsView::new,
            SnakeModel::new,
            CherryBoosterModel::new,
            SpeedBoosterModel::new
        );
        this.snakeMainView.setViews(this.startMenuView, this.singlePlayerView, this.controlsView);
        this.initializeListeners();
    }

    public void instantiateViewsAndModels(
        final GameViewFactory<BaseView> startMenuView,
        final GameViewFactory<BaseView> singlePlayerView,
        final GameViewFactory<BaseView> controlsView,
        final GameModelFactory<BaseModel> snakeModel,
        final GameModelFactory<BaseModel> cherryBoosterModel,
        final GameModelFactory<BaseModel> speedBoosterModel
    ) {
        this.startMenuView = (SnakeStartMenuView) startMenuView.create();
        this.singlePlayerView = (SnakeSinglePlayerView) singlePlayerView.create();
        this.controlsView = (SnakeControlsView) controlsView.create();
        this.snakeModel = (SnakeModel) snakeModel.create();
        cherryBoosterModel.create();
        speedBoosterModel.create();
    }

    @Override
    public void initializeListeners() {
        // Button listeners
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

        this.singlePlayerView.addSnakeBackBtnListener(
            new MenuButtonListener(
                this.singlePlayerView.getSnakeBackBtn(),
                this::initiateGame
            )
        );

        this.controlsView.addSnakeBackBtnListener(
            new MenuButtonListener(
                this.controlsView.getSnakeBackBtn(),
                this.snakeMainView::showStartMenu
            )
        );
    }

    @Override
    public void addQuitBtnListener(final MouseAdapter quitBtnListener) {
        this.startMenuView.addQuitBtnListener(quitBtnListener);
    }

    /**
     * Resets the game state and re-initializes the start menu.
     * Clears the snake model's grid and sets the restart flag to true.
     * The restart flag is utilized in the game loop to stop the
     * EDT timer thread when the game is exited.
     */
    @Override
    public void initiateGame() {
        // stops the EDT thread from executing
        this.restart = true;
        // Shows the start menu and hides the game over overlay
        this.snakeMainView.showStartMenu();
        this.singlePlayerView.hideGameOver();
    }

    /**
     * Gets attached as a listener for the Start button. It starts the game and manages the game loop.
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
        this.restart = false;

        // Game loop.
        this.gameLoop = new Timer(AppConfigProject.SNAKE_TICK_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!restart) {
                    snakeMainModel.updateGameGrid(snakeModel);

                    if (snakeModel.getGameState()) {
                        gameLoop.stop();
                        singlePlayerView.updateGameGrid();
                        singlePlayerView.showGameOver(snakeModel.getSnake().length);

                    } else {
                        // Speeding up the snake by reducing the delay
                        gameLoop.setDelay(Math.max(0, (int) snakeModel.getSpeed())); // Minimum delay of 0ms
                        singlePlayerView.updateGameGrid();
                    }

                } else {
                    gameLoop.stop();
                }
            }
        });
        this.gameLoop.start();
    }

    @Override
    public void closeGame() {
        this.stopGameLoop();
        this.removeListeners();
        this.snakeMainView.closeGameView();
        this.isRunning = false;
    }

    private void stopGameLoop() {
        if (gameLoop != null) {
            this.gameLoop.stop();
            this.gameLoop = null;
        }
    }

    @Override
    public Timer getGameLoop() {
        return gameLoop;
    }

    private void removeListeners() {
        this.singlePlayerView.removeListeners();
        this.startMenuView.removeListeners();
        this.controlsView.removeListeners();
    }

    @Override
    public String getGameTitle() {
        return gameTitle;
    }

    @Override
    public boolean getIsRunning() {
        return isRunning;
    }

    public SnakeModel getSnakeModel() {
        return snakeModel;
    }

    @Override
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public JLabel getQuitBtn() {
        return this.startMenuView.getQuitBtn();
    }
}
