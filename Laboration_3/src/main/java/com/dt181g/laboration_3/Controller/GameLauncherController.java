package com.dt181g.laboration_3.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

import com.dt181g.laboration_3.model.GameListModel;
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
public class GameLauncherController {
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
     * The selected game is then reset and displayed in the game launcher view.
     * </p>
     */
    class GameIconListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            // Get the game title from the clicked button's action command set earlier.
            String title = e.getActionCommand();

            // Reset the game and load it into the launcher.
            gameListModel.getGameController(title).resetGame();
            gameLauncherView.loadGame(gameListModel.getGameView(title).getPanel());
        }
    }
}
