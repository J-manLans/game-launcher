package com.dt181g.laboration_3.mvccomponents.launcher.controller;

import com.dt181g.laboration_3.factories.GameControllerFactory;
import com.dt181g.laboration_3.factories.GameModelFactory;
import com.dt181g.laboration_3.factories.GameViewFactory;
import com.dt181g.laboration_3.mvccomponents.BaseController;
import com.dt181g.laboration_3.mvccomponents.games.snake.controller.SnakeController;
import com.dt181g.laboration_3.mvccomponents.games.snake.model.SnakeModel;
import com.dt181g.laboration_3.mvccomponents.games.snake.view.SnakeMainView;
import com.dt181g.laboration_3.mvccomponents.launcher.model.GameListModel;
import com.dt181g.laboration_3.mvccomponents.launcher.view.GameLauncherView;
import com.dt181g.laboration_3.mvccomponents.listeners.MenuButtonListener;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.TimedEventQueue;

import java.awt.Toolkit;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * The controller class for managing the game launcher.
 * <p>
 * This class acts as the intermediary between the {@link GameLauncherView} and
 * {@link GameListModel}, coordinating interactions and ensuring that user
 * actions in the view trigger the appropriate responses in the model.
 * </p>
 *
 * @author Joel lansgren
 */
public class GameLauncherController implements BaseController{
    private final GameLauncherView gameLauncherView;
    private final GameListModel gameListModel;

    /**
     * Constructs a new GameLauncherCtrl with the specified view and model.
     *
     * @param gameLauncherView the view component for the game launcher
     * @param gameListModel the model component containing the list of games that the launcher will display
     */
    public GameLauncherController() {
        this.gameLauncherView = new GameLauncherView();
        this.gameListModel = new GameListModel();
    }

    /**
     * Initializes the game launcher using various helper methods.
     * First it sets up the game icons, then attach listeners to them,
     * displays the launcher and finally pushes a {@link TimedEventQueue}
     * onto the system event queue to monitor event dispatch times.
     */
    public void initialize() {
        this.gameLauncherView.initializeView();
        this.addGameIcons();
        this.initializeListeners();
        this.gameLauncherView.showLauncher();
        if (AppConfigLab3.DEBUG_MODE) { Toolkit.getDefaultToolkit().getSystemEventQueue().push(new TimedEventQueue()); }
    }

    /**
     * Helper method that sets up the game icons in the launcher view.
     */
    private void addGameIcons() {
        this.gameLauncherView.addGameIcons(
            this.gameListModel.getIconPaths(),
            this.gameListModel.getTitleList()
        );
    }

    /**
     * Helper method that initialize listeners.
     */
    private void initializeListeners() {
        // For game icons so they start the game when clicked
        this.gameLauncherView.addGameIconListeners(new GameIconListener(this, this.gameListModel));

        // For scroll pane speed
        this.gameLauncherView.addScrollPaneListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(final MouseWheelEvent e) {
                gameLauncherView.handleScroll(e.getWheelRotation());
            }
        });

        // For opening the about panel
        this.gameLauncherView.addAboutBtnListener(new MenuButtonListener(
            this.gameLauncherView.getAboutBtn(),
            gameLauncherView::displayAboutPanel
        ));

        // For closing the about panel
        this.gameLauncherView.addBackBtnListener(new MenuButtonListener(
            this.gameLauncherView.getInstructionsBackBtn(),
            gameLauncherView::displayStartScreen
        ));

        // For exiting the launcher
        this.gameLauncherView.addQuitBtnListener(new MenuButtonListener(
            this.gameLauncherView.getQuitBtn(),
            this::exitLauncher
        ));
    }

    /**
     * Exits the game launcher application.
     * This method terminates the application by calling System.exit(0).
     */
    public void exitLauncher() {
        System.exit(0);
    }

    /*=========================================================
     * Methods used by the game icons through their listener
     ========================================================*/

    /**
     * Switches to the specified game by identifying and closing the active game,
     * starting the new game if necessary, and updating the view to display the new game.
     *
     * @param selectedGame the name of the game to switch to
     */
    void switchToSelectedGame(final String selectedGame) {
        this.identifyActiveGame(selectedGame);
        this.startNewGame(selectedGame);
        this.displayGame(selectedGame);
    }

    /**
     * Identifies the currently active game and closes it if it is different
     * from the selected game.
     *
     * @param selectedGame the name of the game that the user wishes to switch to
     */
    private void identifyActiveGame(final String selectedGame) {
        this.gameListModel.getTitleList().stream()
        .filter(inactiveGame -> !inactiveGame.equals(selectedGame))
        .forEach(this::closeGame);
    }

    /**
     * Starts the specified game if it is not already instantiated.
     * This method also adds an exit listener to the game.
     *
     * @param selectedGame the name of the game to start
     */
    private void startNewGame(final String selectedGame) {
        // Re-instantiate (if necessary) and adds listener to the exit button.
        if (this.gameListModel.getGameModel(selectedGame) == null) {
            this.startGame(selectedGame);
            this.addGameExitListener(selectedGame);
        }
        //initiates the game, and lets the launcher display it.
        this.gameListModel.getGameController(selectedGame).initiateGame();
    }

    /**
     * Updates the view to display the specified game. This includes setting the
     * game panel used by the game view to manage its closure gracefully.
     *
     * @param selectedGame the name of the game to be displayed in the view
     */
    private void displayGame(final String selectedGame) {
        this.gameLauncherView.displayGame(this.gameListModel.getGameView(selectedGame));
        // Hands the game panel to the view, used by the game view to gracefully close itself.
        this.gameListModel.getGameView(selectedGame).setGamePanel(this.gameLauncherView.getGamePanel());
    }

    /**
     *  Helper method to close game.
     * @param game the game to be closed.
     */
    private void closeGame(final String game) {
        // Checks that the game corresponding to the title
        // actually is instantiated. If so, it's deleted.
        // This protects against the first time a game gets started,
        // since no game is present in the system.
        if (this.gameListModel.getGameController(game) != null) {
            this.gameListModel.getGameController(game).closeGame();
            this.removeGame(game);
        }
    }

    /**
     * A listener that closes the game that gets attached to whatever game that is being loaded.
     * @param game the loaded game
     */
    private void addGameExitListener(final String game) {
        this.gameListModel.getGameView(game).addQuitBtnListener(
            new MenuButtonListener(
                this.gameListModel.getGameView(game).getQuitBtn(),
                () -> {
                    this.closeGame(game);
                    this.gameLauncherView.displayStartScreen();
                }
            )
        );
    }

    /*=====================================
     * Methods to start and remove games
     ====================================*/

    /**
     * Starts the game clicked in the games list in the launcher.
     * Whenever new games are added they need a switch case in this method.
     * @param game the clicked games title.
     */
    private void startGame(final String game) {
        switch (game) {
            case AppConfigLab3.SNAKE_TITLE -> {
                this.instantiateMVCPattern(SnakeModel::new, SnakeMainView::new, SnakeController::new, game);
            }
        }
    }

    /**
     * Method that instantiates a view and a controller for a game based on the provided factories and title.
     *
     * @param gameModelFactory A functional interface used to create the game model.
     * In this case it's a method reference to a game models constructor.
     * @param gameViewFactory A functional interface used to create the game view.
     * In this case it's a method reference to a game views constructor.
     * @param gameControllerFactory A functional interface used to create the game controller.
     * In this case it's a method reference to a game controllers constructor.
     * @param title The title of the game, which is used for initializing both the view and the controller.
     */
    private void instantiateMVCPattern(
        final GameModelFactory gameModelFactory,
        final GameViewFactory gameViewFactory,
        final GameControllerFactory gameControllerFactory,
        final String title
    ) {
        this.gameListModel.addGameModel(gameModelFactory.create(title));
        this.gameListModel.addGameView(gameViewFactory.create(title));
        this.gameListModel.addGameController(gameControllerFactory.create(
            title,
            this.gameListModel.getGameView(title),
            this.gameListModel.getGameModel(title)
        ));
        ((BaseController) this.gameListModel.getGameController(title)).initialize();
    }

    /**
     * Clears each game list since a new one is about to be re-instantiated.
     * @param game the games title.
     */
    private void removeGame(final String game) {
        this.gameListModel.getGameModels().clear();
        this.gameListModel.getGameViews().clear();
        this.gameListModel.getGameControllers().clear();
    }
}
