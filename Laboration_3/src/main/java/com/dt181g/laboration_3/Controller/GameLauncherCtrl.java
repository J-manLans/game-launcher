package com.dt181g.laboration_3.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.SwingUtilities;
import java.util.Map;

import com.dt181g.laboration_3.model.GameListModel;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.GameLauncherView;

/**
 * The controller class for managing the game launcher.
 * <p>
 * This class acts as the intermediary between the {@link GameLauncherView} and
 * {@link GameListModel}, coordinating interactions and ensuring that user
 * actions in the view trigger the appropriate responses in the model.
 * </p>
 *
 * <p>
 * It also manages the creation and storage of individual game controllers,
 * allowing the selected game to be loaded and reset within the game launcher.
 * </p>
 *
 * @author Joel lansgren
 */
public class GameLauncherCtrl {
    private final GameLauncherView gameLauncherView;
    private final GameListModel gameListModel;
    private final Map<String, GameCtrl> gameControllers = new HashMap<>();

    /**
     * Constructs a new GameLauncherCtrl with the specified view and model.
     *
     * @param gameLauncherView the view component for the game launcher
     * @param gameListModel the model component containing the list of games that the launcher will display
     */
    public GameLauncherCtrl(final GameLauncherView gameLauncherView, final GameListModel gameListModel) {
        this.gameLauncherView = gameLauncherView;
        this.gameListModel = gameListModel;
    }

    /**
     * Initializes the game launcher by setting up the game icons and their listeners.
     * Then it displays the game launcher UI on the Event Dispatch Thread (EDT) using invokeLater.
     * <p>
     * This method populates the game launcher view with the icons for each game
     * retrieved from the model and sets up listeners for user interactions
     * with these icons.
     * </p>
     */
    public void initializeLauncher() {
        this.gameLauncherView.addGameIcons(this.gameListModel.getIconPath(),
            gameListModel.getTitleList()
        );
        this.gameLauncherView.addGameIconListener(new GameIconListener());

        SwingUtilities.invokeLater(() -> {
            gameLauncherView.setVisible(true);
        });
    }

    /*========================
     * Listeners
     =======================*/
     /**
     * Listener for game icon clicks in the launcher.
     * <p>
     * This inner class handles actions triggered when a user clicks a game
     * icon from the launcher. It identifies the selected game and either
     * creates a new game controller if one does not already exist, or retrieves
     * the existing one. The selected game is then reset and displayed in the
     * game launcher view.
     * </p>
     */
    class GameIconListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            // Get the game title from the clicked button's action command set earlier.
            String title = e.getActionCommand();

            // Create and store the game controller if it doesn't exist yet.
            switch (title) {
                case AppConfigLab3.SNAKE_TITLE -> {
                    if (!gameControllers.containsKey(title)) {
                        GameCtrl snakeCtrl = new SnakeCtrl(
                            gameListModel.getGameView(title),
                            gameListModel.getGameModel(title)
                        );
                        gameControllers.put(title, snakeCtrl);
                    }
                } case AppConfigLab3.TIC_TAC_TOE_TITLE -> {
                    if (!gameControllers.containsKey(title)) {
                        GameCtrl ticTacToeCtrl = new FakeTicTacToeCtrl(
                            gameListModel.getGameView(title),
                            gameListModel.getGameModel(title)
                        );
                        gameControllers.put(title, ticTacToeCtrl);
                    }
                }
            }

            // Reset the game and load it into the launcher.
            gameControllers.get(title).resetGame();
            gameLauncherView.loadGame(gameListModel.getGameView(title));
        }
    }
}
