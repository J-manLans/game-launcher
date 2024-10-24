package com.dt181g.project.mvccomponents.games.snake.controller;

import com.dt181g.project.mvccomponents.listeners.MenuButtonListener;
import com.dt181g.project.mvccomponents.BaseController;
import com.dt181g.project.mvccomponents.games.GameController;
import com.dt181g.project.mvccomponents.games.GameModel;
import com.dt181g.project.mvccomponents.games.GameView;
import com.dt181g.project.mvccomponents.games.listeners.SnakeMovementListener;
import com.dt181g.project.mvccomponents.games.snake.model.SnakeGridModel;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeMainView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeSinglePlayerView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeStartMenuView;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.DebugLogger;

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
public class SnakeController implements GameController, BaseController {
    DebugLogger logger = DebugLogger.INSTANCE;

    private final SnakeMainView snakeView;
    private final SnakeGridModel snakeGridModel;
    private final SnakeStartMenuView startMenuView;
    private final SnakeSinglePlayerView singlePlayerView;
    private final String title;
    private Timer gameLoop;
    private boolean restart;
    private boolean isRunning = true;

    /**
     * Constructs a SnakeCtrl with the specified game title, SnakeView and SnakeModel.
     * It also initialize the listeners.
     *
     * @param title the snake game title
     * @param snakeView the view associated with the Snake game
     * @param snakeModel the model representing the game's logic
     */
    public SnakeController(final String title, final GameView snakeView, final GameModel snakeModel) {
        this.title = title;
        this.snakeView = (SnakeMainView) snakeView;
        this.snakeGridModel = (SnakeGridModel) snakeModel;
        this.startMenuView = new SnakeStartMenuView(this.snakeView);
        this.singlePlayerView = new SnakeSinglePlayerView(this.snakeView);

        this.initializeListeners();
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

        // this.snakeView.addMultiplayerBtnListener(this.multiplayerBtnListener);

        // this.snakeView.addSettingsBtnListener(this.settingsBtnListener);

        this.startMenuView.addControlsBtnListener(
            new MenuButtonListener(
                this.startMenuView.getControlsBtn(),
                snakeView::showControlsMenu
            )
        );

        this.snakeView.addSnakeBackBtnListener(
            new MenuButtonListener(
                this.snakeView.getSnakeBackBtn(),
                this::initiateGame
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
        // Shows the start menu
        this.snakeView.ShowStartMenu();
        // Resets the snake models 2D array
        this.snakeGridModel.clearGameGrid();
        // stops the EDT thread from executing
        this.restart = true;
        // Set default direction of the snake
        this.snakeGridModel.getSnakeModel().setDirection(AppConfigProject.RIGHT, restart);

        logger.logInfo(title + " has been initiated.\n");
    }

    /**
     * Gets attached as a listener for the Start button. It starts the game and manages the game loop.
     */
    private void startGame() {
        // Grid initialization.
        this.snakeGridModel.getSnakeModel().initializeSnake(AppConfigProject.SNAKE_ITEMS_PART_CONTENT);
        this.snakeGridModel.getCherryModel().initializeCherry();
        this.snakeGridModel.overlayGameItemsOnGrid(this.snakeGridModel.getSnakeModel().getSnake());
        this.snakeView.startGame(this.snakeGridModel.getGameAssets());

        // Key listener for the game
        this.singlePlayerView.addSnakeKeyListener(new SnakeMovementListener(this));

        // For controlling the loop
        this.restart = false;

        // Game loop.
        this.gameLoop = new Timer(AppConfigProject.SNAKE_TICK_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!restart) {
                    snakeGridModel.updateGameGrid();
                    singlePlayerView.updateGameGrid();
                } else {
                    gameLoop.stop();
                }
            }
        });

        this.gameLoop.start();
        this.setGameLoop(gameLoop);
    }

    @Override
    public void closeGame() {
        this.stopGameLoop();
        this.removeListeners();
        this.snakeView.closeGameView();
        this.isRunning = false;
    }

    private void stopGameLoop() {
        if (gameLoop != null) {
            this.gameLoop.stop();
            this.gameLoop = null;
        }
    }

    private void setGameLoop(Timer gameLoop) {
        this.gameLoop = gameLoop;
    }

    @Override
    public Timer getGameLoop() {
        return gameLoop;
    }

    private void removeListeners() {
        this.snakeView.removeListeners();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean getIsRunning() {
        return isRunning;
    }

    public SnakeGridModel getSnakeGridModel() {
        return snakeGridModel;
    }

    @Override
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public JLabel getQuitBtn() {
        return this.startMenuView.getQuitBtn();
    }

}
