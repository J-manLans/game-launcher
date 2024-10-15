package com.dt181g.laboration_3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.dt181g.laboration_3.controller.FakeTicTacToeCtrl;
import com.dt181g.laboration_3.controller.GameController;
import com.dt181g.laboration_3.controller.SnakeController;
import com.dt181g.laboration_3.factories.GameControllerFactory;
import com.dt181g.laboration_3.factories.GameViewFactory;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.FakeTicTacToeView;
import com.dt181g.laboration_3.view.GameView;
import com.dt181g.laboration_3.view.SnakeView;

/**
 * The GameListModel class maintains a list of game models their corresponding views and controllers.
 *
 * <p>
 * It initializes the game models, views and controllers at construction and provides methods
 * to access the titles, icon paths, and specific game models views or controllers by title.
 * </p>
 *
 * @author Joel Lansgren
 */
public class GameListModel {
    private final List<GameModel> gameModels = new ArrayList<>();
    private final List<GameView> gameViews = new ArrayList<>();
    private final List<GameController> gameControllers = new ArrayList<>();

    /**
     * Constructs a GameListModel and initializes the game models, views and controllers.
     */
    public GameListModel() {
        // Instantiating game models
        this.gameModels.add(new SnakeModel());
        this.gameModels.add(new FakeTicTacToeModel());

        // Instantiating game views and controllers using their factories
        for (String title : this.getTitleList()) {
            switch (title) {
                case AppConfigLab3.SNAKE_TITLE -> {
                    this.instantiateViewAndController(SnakeView::new, SnakeController::new, title);
                } case AppConfigLab3.TIC_TAC_TOE_TITLE -> {
                    this.instantiateViewAndController(FakeTicTacToeView::new, FakeTicTacToeCtrl::new, title);
                }
            }
        }
    }

    /**
     * Helper method that instantiates a view and a controller for a game based on the provided factories and title.
     *
     * @param gameViewFactory A functional interface used to create the game view.
     * In this case it's a method reference to a game views constructor.
     * @param gameControllerFactory A functional interface used to create the game controller.
     * In this case it's a method reference to a game controllers constructor.
     * @param title The title of the game, which is used for initializing both the view and the controller.
     */
    private void instantiateViewAndController(
        GameViewFactory gameViewFactory,
        GameControllerFactory gameControllerFactory,
        String title
    ) {
        this.gameViews.add(gameViewFactory.create(title));
        this.gameControllers.add(
            gameControllerFactory.create(title, this.getGameView(title), this.getGameModel(title))
        );
    }

    /**
     * Retrieves a list of game titles from the game models.
     *
     * @return a list of game titles.
     */
    public List<String> getTitleList() {
        return gameModels.stream()
            .map(GameModel::getTitle)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of icon paths from the game models.
     *
     * @return a list of icon paths.
     */
    public List<String> getIconPath() {
        return gameModels.stream()
            .map(GameModel::getIconPath)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves the GameModel associated with the specified title.
     *
     * @param title the title of the game to retrieve the model for.
     * @return the GameModel associated with the title, or null if not found.
     */
    private GameModel getGameModel(final String title) {
        for (GameModel gameModel : gameModels) {
            if (gameModel.getTitle().equals(title)) {
                return gameModel; // Return the first match found
            }
        }
        return null; // Return null if no match found
    }

    /**
     * Retrieves the GameView associated with the specified title.
     *
     * @param title the title of the game to retrieve the view for.
     * @return the GameView associated with the title, or null if not found.
     */
    public GameView getGameView(final String title) {
        for (GameView gameView : gameViews) {
            if (gameView.getTitle().equals(title)) {
                return gameView;
            }
        }
        return null;
    }

    /**
     * Retrieves the GameController associated with the specified title.
     *
     * @param title the title of the game to retrieve the view for.
     * @return the GameController associated with the title, or null if not found.
     */
    public GameController getGameController(final String title) {
        for (GameController gameController : gameControllers) {
            if (gameController.getTitle().equals(title)) {
                return gameController;
            }
        }
        return null;
    }
}
