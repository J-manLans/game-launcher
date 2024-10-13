package com.dt181g.laboration_3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.FakeTicTacToeView;
import com.dt181g.laboration_3.view.GameView;
import com.dt181g.laboration_3.view.SnakeView;

/**
 * The GameListModel class maintains a list of game models and their corresponding views.
 *
 * <p>
 * It initializes the game models and views at construction and provides methods
 * to access the titles, icon paths, and specific game models or views by title.
 * </p>
 *
 * @author Joel Lansgren
 */
public class GameListModel {
    private final List<GameModel> gameModels = new ArrayList<GameModel>();
    private final List<GameView> gameViews = new ArrayList<GameView>();

    /**
     * Constructs a GameListModel and initializes the game models and views.
     */
    public GameListModel() {
        // Instantiating game models
        this.gameModels.add(new SnakeModel());
        this.gameModels.add(new FakeTicTacToeModel());

        // Instantiating game views
        this.gameViews.add(
            new SnakeView(
                AppConfigLab3.SNAKE_TITLE,
                this.getGameModel(AppConfigLab3.SNAKE_TITLE).getGridSize()
            )
        );
        this.gameViews.add(new FakeTicTacToeView());
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
    public GameModel getGameModel(String title) {
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
    public GameView getGameView(String title) {
        for (GameView gameView : gameViews) {
            if (gameView.getTitle().equals(title)) {
                return gameView;
            }
        }
        return null;
    }
}
