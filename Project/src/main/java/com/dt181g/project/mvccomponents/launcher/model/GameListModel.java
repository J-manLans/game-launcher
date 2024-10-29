package com.dt181g.project.mvccomponents.launcher.model;

import java.util.ArrayList;
import java.util.List;

import com.dt181g.project.factories.GameControllerFactory;
import com.dt181g.project.factories.GameModelFactory;
import com.dt181g.project.factories.GameViewFactory;
import com.dt181g.project.mvccomponents.BaseModel;
import com.dt181g.project.mvccomponents.games.GameMainController;
import com.dt181g.project.mvccomponents.games.GameMainModel;
import com.dt181g.project.mvccomponents.games.GameMainView;
import com.dt181g.project.mvccomponents.games.snake.controller.SnakeController;
import com.dt181g.project.mvccomponents.games.snake.model.SnakeGridModel;
import com.dt181g.project.mvccomponents.games.snake.view.SnakeMainView;
import com.dt181g.project.mvccomponents.games.tictactoe.controller.FakeTicTacToeCtrl;
import com.dt181g.project.mvccomponents.games.tictactoe.model.FakeTicTacToeModel;
import com.dt181g.project.mvccomponents.games.tictactoe.view.FakeTicTacToeView;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.DebugLogger;

/**
 * The GameListModel class maintains a list of game models, their corresponding views and controllers.
 *
 * <p>
 * It initializes the game models, views and controllers at construction and provides methods
 * to access the titles, icon paths, and specific game models views or controllers by title.
 * </p>
 *
 * @author Joel Lansgren
 */
public class GameListModel implements BaseModel {
    private final List<String> iconPaths = new ArrayList<>();
    private final List<String> gameTitles = new ArrayList<>();
    private final List<GameMainModel> gameModels = new ArrayList<>();
    private final List<GameMainView> gameViews = new ArrayList<>();
    private final List<GameMainController> gameControllers = new ArrayList<>();

    /**
     * Constructs a GameListModel and initializes the game icons and titles.
     */
    public GameListModel() {
        // TODO: Will refactor this in the future to fetch info from the games themselves.
        this.iconPaths.add(AppConfigProject.PATH_TO_ICONS + AppConfigProject.SNAKE_ICON);
        this.iconPaths.add(AppConfigProject.PATH_TO_ICONS + AppConfigProject.TIC_TAC_TOE_ICON);
        this.gameTitles.add(AppConfigProject.SNAKE_TITLE);
        this.gameTitles.add(AppConfigProject.TIC_TAC_TOE_TITLE);
    }

    /**
     * Starts the game clicked in the games list in the launcher.
     * @param gameTitle the clicked games title.
     */
    public void startGame(final String gameTitle) {
        switch (gameTitle) {
            case AppConfigProject.SNAKE_TITLE -> {
                this.instantiateGame(SnakeGridModel::new, SnakeMainView::new, SnakeController::new, gameTitle);
            } case AppConfigProject.TIC_TAC_TOE_TITLE -> {
                this.instantiateGame(FakeTicTacToeModel::new, FakeTicTacToeView::new, FakeTicTacToeCtrl::new, gameTitle);
            }
        }

        DebugLogger.INSTANCE.logWarning(gameTitle + " has been instantiated.");
    }

    /**
     * Helper method that instantiates a view and a controller for a game based on the provided factories and title.
     *
     * @param gameModelFactory A functional interface used to create the game model.
     * In this case it's a method reference to a game models constructor.
     * @param gameViewFactory A functional interface used to create the game view.
     * In this case it's a method reference to a game views constructor.
     * @param gameControllerFactory A functional interface used to create the game controller.
     * In this case it's a method reference to a game controllers constructor.
     * @param gameTitle The title of the game, which is used for initializing both the view and the controller.
     */
    private void instantiateGame(
        final GameModelFactory<GameMainModel> gameModelFactory,
        final GameViewFactory<GameMainView> gameViewFactory,
        final GameControllerFactory<GameMainController, GameMainView, GameMainModel> gameControllerFactory,
        final String gameTitle
    ) {
        this.gameModels.add(gameModelFactory.create());
        this.gameViews.add(gameViewFactory.create());
        this.gameControllers.add(
            gameControllerFactory.create(this.getGameView(gameTitle), this.getGameModel(gameTitle))
        );
        this.getGameController(gameTitle).initialize();
    }

    /**
     * Clears each game list since a new one is about to be re-instantiated.
     * @param gameTitle the games title.
     */
    public void removeGame(final String gameTitle) {
        this.gameModels.clear();
        this.gameViews.clear();
        this.gameControllers.clear();

        DebugLogger.INSTANCE.logWarning(gameTitle + " has been removed\n");
    }

    /**
     * Returns the icon paths to the games.
     * @return a list of icon paths.
     */
    public List<String> getIconPaths() {
        return this.iconPaths;
    }

    /**
     * Returns the title of the games.
     * @return a list of game titles.
     */
    public List<String> getTitleList() {
        return this.gameTitles;
    }

    /**
     * Retrieves the GameModel associated with the specified title.
     *
     * <p>
     * If the title isn't present null is returned.
     * </p>
     * @param gameTitle the title of the game to retrieve the model for.
     * @return the GameModel associated with the title, or null if not found.
     */
    public GameMainModel getGameModel(final String gameTitle) {
        // As of now the list always contain only one element.
        // But for the project this will change since I will
        // initiate all games in this class constructor so I can
        // fetch the images and icons from them.
        return gameModels.stream()
        .filter(gameModel -> gameModel.getGameTitle().equals(gameTitle))
        .findFirst()
        .orElse(null);
    }

    /**
     * Retrieves the GameView associated with the specified title.
     *
     * <p>
     * This method searches through a collection of game views to find one that matches
     * the provided title. If a matching game view is found, it is returned; otherwise,
     * this method returns null.
     * </p>
     * @param gameTitle the title of the game to retrieve the view for.
     * @return the view associated with the title, or null if not found.
     */
    public GameMainView getGameView(final String gameTitle) {
        // As of now the list always contain only one element.
        // But for the project this will change since I will
        // initiate all games in this class constructor so I can
        // fetch the images and icons from them.
        return gameViews.stream()
        .filter(gameView -> gameView.getGameTitle().equals(gameTitle))
        .findFirst()
        .orElse(null);
    }

    /**
     * Retrieves the GameController associated with the specified title.
     *
     * <p>
     * This method searches through a collection of game controllers to find one that matches
     * the provided title. If a matching game controller is found, it is returned; otherwise,
     * this method returns null.
     * </p>
     * @param gameTitle the title of the game to retrieve the view for.
     * @return the controller associated with the title, or null if not found.
     */
    public GameMainController getGameController(final String gameTitle) {
        // As of now the list always contain only one element.
        // But for the project this will change since I will
        // initiate all games in this class constructor so I can
        // fetch the images and icons from them.
        return gameControllers.stream()
        .filter(gameController -> gameController.getGameTitle().equals(gameTitle))
        .findFirst()
        .orElse(null);
    }
}
