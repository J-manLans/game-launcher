package com.dt181g.project.mvccomponents.games.snake.controller;

import com.dt181g.project.mvccomponents.listeners.MenuButtonListener;
import com.dt181g.project.factories.GameModelFactory;
import com.dt181g.project.factories.GameViewFactory;
import com.dt181g.project.mvccomponents.games.GameController;
import com.dt181g.project.mvccomponents.games.GameModel;
import com.dt181g.project.mvccomponents.games.GameView;
import com.dt181g.project.mvccomponents.games.listeners.SnakeMovementListener;
import com.dt181g.project.mvccomponents.games.snake.model.CherryModel;
import com.dt181g.project.mvccomponents.games.snake.model.SnakeGridModel;
import com.dt181g.project.mvccomponents.games.snake.model.SnakeModel;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeControlsView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeMainView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeMultiplayerView;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeSettingsView;
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
public class SnakeController implements GameController {

    private final String gameTitle = AppConfigProject.SNAKE_TITLE;

    private final SnakeMainView snakeMainView;
    private SnakeStartMenuView startMenuView;
    private SnakeSinglePlayerView singlePlayerView;
    private SnakeMultiplayerView multiplayerView;
    private SnakeSettingsView settingsView;
    private SnakeControlsView controlsView;
    private final SnakeGridModel snakeGridModel;
    private SnakeModel snakeModel;
    private CherryModel cherryModel;

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
    public SnakeController(final GameView snakeMainView, final GameModel snakeModel) {
        this.snakeMainView = (SnakeMainView) snakeMainView;
        this.snakeGridModel = (SnakeGridModel) snakeModel;
        this.instantiateViewsAndModels(
            SnakeStartMenuView::new,
            SnakeSinglePlayerView::new,
            SnakeMultiplayerView::new,
            SnakeSettingsView::new,
            SnakeControlsView::new,
            SnakeModel::new,
            CherryModel::new
        );
        this.snakeMainView.setViews(this.startMenuView, this.singlePlayerView, this.multiplayerView, this.settingsView, this.controlsView);
        this.initializeListeners();
    }

    public void instantiateViewsAndModels(
        final GameViewFactory startMenuView,
        final GameViewFactory singlePlayerView,
        final GameViewFactory multiplayerView,
        final GameViewFactory settingsView,
        final GameViewFactory controlsView,
        final GameModelFactory snakeModel,
        final GameModelFactory cherryModel
    ) {
        this.startMenuView = (SnakeStartMenuView) startMenuView.create();
        this.singlePlayerView = (SnakeSinglePlayerView) singlePlayerView.create();
        this.multiplayerView = (SnakeMultiplayerView) multiplayerView.create();
        this.settingsView = (SnakeSettingsView) settingsView.create();
        this.controlsView = (SnakeControlsView) controlsView.create();
        this.snakeModel = (SnakeModel) snakeModel.create();
        this.cherryModel = (CherryModel) cherryModel.create();
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
                this.snakeMainView::showStartMenu
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
        // Shows the start menu
        this.snakeMainView.showStartMenu();
        // Resets the snake models 2D array
        this.snakeGridModel.clearGameGrid();
        // stops the EDT thread from executing
        this.restart = true;
        // Set default direction of the snake
        this.snakeModel.setDirection(AppConfigProject.RIGHT, restart);

        DebugLogger.INSTANCE.logInfo(gameTitle + " has been initiated.\n");
    }

    /**
     * Gets attached as a listener for the Start button. It starts the game and manages the game loop.
     */
    private void startGame() {
        // Grid initialization.
        this.snakeModel.initializeSnake(AppConfigProject.SNAKE_ITEMS_PART_CONTENT);
        this.cherryModel.initializeCherry();
        this.snakeGridModel.overlayGameItemsOnGrid(this.snakeModel.getSnake());
        this.snakeMainView.showGame();
        this.singlePlayerView.setGameAssets(this.snakeGridModel.getGameAssets());
        this.singlePlayerView.startGame();

        // Key listener for the game
        this.singlePlayerView.addSnakeKeyListener(new SnakeMovementListener(this));

        // For controlling the loop
        this.restart = false;

        // Game loop.
        this.gameLoop = new Timer(AppConfigProject.SNAKE_TICK_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!restart) {
                    snakeGridModel.updateGameGrid(snakeModel, cherryModel);
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
        this.snakeMainView.closeGameView();
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
