package com.dt181g.laboration_3.controller.launcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import com.dt181g.laboration_3.model.launcher.GameListModel;
import com.dt181g.laboration_3.support.DebugLogger;
import com.dt181g.laboration_3.view.launcher.GameLauncherView;

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
public class GameLauncherController {
    DebugLogger logger = DebugLogger.INSTANCE;
    private final GameLauncherView gameLauncherView;
    private final GameListModel gameListModel;

    /**
     * Constructs a new GameLauncherCtrl with the specified view and model.
     *
     * @param gameLauncherView the view component for the game launcher
     * @param gameListModel the model component containing the list of games that the launcher will display
     */
    public GameLauncherController(final GameLauncherView gameLauncherView, final GameListModel gameListModel) {
        this.gameLauncherView = gameLauncherView;
        this.gameListModel = gameListModel;
    }

    /**
     * Initializes the game launcher using various helper methods.
     * First it sets up the game icons, then attach listeners to them
     * and lastly displays the launcher.
     */
    public void initialize() {
        this.addGameIcons();
        this.initializeListeners();
        this.startLauncher();
    }

    /**
     * Helper method that sets up the game icons in the launcher view.
     */
    private void addGameIcons() {
        this.gameLauncherView.addGameIcons(this.gameListModel.getIconPath(),
            gameListModel.getTitleList()
        );
    }

    /**
     * Helper method that initialize listeners.
     */
    private void initializeListeners() {
        this.gameLauncherView.addGameIconListener(new GameIconListener());
    }

    /**
     * Helper method that displays the game launcher UI on the Event Dispatch Thread (EDT) using invokeLater.
     */
    private void startLauncher() {
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
     * icon from the launcher. It identifies the selected game.
     * The selected game is then re-instantiated, re-initiated and displayed in the game launcher view.
     * All other games will be disposed of.
     * </p>
     */
    class GameIconListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            // Get the game title from the clicked button's action command set earlier.
            String clickedGame = e.getActionCommand();

            this.closeUnClickedGames(clickedGame);

            // Re-instantiate (if necessary), reset the game and lets the launcher display it.
            if (gameListModel.getGameModel(clickedGame) == null) {
                gameListModel.reInstantiate(clickedGame);
            }
            gameListModel.getGameController(clickedGame).initiateGame();
            gameLauncherView.displayGame(gameListModel.getGameView(clickedGame));

            // Hands the game panel to the view, must be below the re-instantiation,
            // otherwise the game view is null.
            gameListModel.getGameView(clickedGame).setGamePanel(gameLauncherView.getGamePanel());
        }

        /**
         * Helper method that select each title from the title list and compares it to the one passed into the method.
         *
         * <p>
         * If it's not a match the games controller is used to close the game
         * and the gameListModel removes it from it's list.
         *
         * @param clickedGame The game icon clicked.
         */
        private void closeUnClickedGames(String clickedGame) {
            for (String unClickedGame : gameListModel.getTitleList()) {
                if (unClickedGame != clickedGame) {
                    gameListModel.getGameController(unClickedGame).closeGame();
                    gameListModel.removeGame(unClickedGame);
                }
            }
        }
    }
}
