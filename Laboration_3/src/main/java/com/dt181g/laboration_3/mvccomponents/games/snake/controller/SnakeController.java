package com.dt181g.laboration_3.mvccomponents.games.snake.controller;

import com.dt181g.laboration_3.mvccomponents.listeners.MenuButtonListener;
import com.dt181g.laboration_3.mvccomponents.BaseController;
import com.dt181g.laboration_3.mvccomponents.games.GameController;
import com.dt181g.laboration_3.mvccomponents.games.GameModel;
import com.dt181g.laboration_3.mvccomponents.games.GameView;
import com.dt181g.laboration_3.mvccomponents.games.snake.model.SnakeModel;
import com.dt181g.laboration_3.mvccomponents.games.snake.view.SnakeView;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.DebugLogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private final SnakeView snakeView;
    private final SnakeModel snakeModel;
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
        this.snakeView = (SnakeView) snakeView;
        this.snakeModel = (SnakeModel) snakeModel;

        this.initializeListeners();
    }


    @Override
    public void initializeListeners() {
        this.snakeView.addStartBtnListener(
            new MenuButtonListener(
                this.snakeView.getStartBtn(),
                () -> {
                    this.startGame();
                }
            )
        );

        // this.snakeView.addMultiplayerBtnListener(this.multiplayerBtnListener);

        // this.snakeView.addSettingsBtnListener(this.settingsBtnListener);

        this.snakeView.addControlsBtnListener(
            new MenuButtonListener(
                this.snakeView.getControlsBtn(),
                () -> { snakeView.showControlsMenu(); }
            )
        );

        this.snakeView.addSnakeBackBtnListener(
            new MenuButtonListener(
                this.snakeView.getSnakeBackBtn(),
                () -> { this.initiateGame(); }
            )
        );
    }

    /**
     * Resets the game state and re-initializes the start menu.
     * Clears the snake model's grid and sets the restart flag to true.
     * The restart flag is utilized in the game loop to stop the
     * EDT timer thread when the game is exited.
     */
    @Override
    public void initiateGame() {
        // Clears and reinitialize the start menu
        this.snakeView.initializeStartMenu();
        // Resets the snake models 2D array
        this.snakeModel.clearSnakeGrid();
        // stops the EDT thread from executing
        this.restart = true;

        logger.logInfo(title + " has been initiated.\n");
    }

    /**
     * Gets attached as a listener for the Start button. It starts the game and manages the game loop.
     */
    public void startGame() {
        // Grid initialization.
        this.snakeModel.initializeSnake();
        this.snakeView.startGame(this.snakeModel.getGameAssets());

        // For controlling the loop
        this.restart = false;

        // Game loop.
        this.gameLoop = new Timer(AppConfigLab3.NUM_200, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!restart) {
                    snakeModel.updateGameGrid();
                    snakeView.updateGameGrid(snakeModel.getGameAssets());
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

    private void removeListeners() {
        this.snakeView.removeListeners();
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

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean getIsRunning() {
        return isRunning;
    }

    @Override
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
