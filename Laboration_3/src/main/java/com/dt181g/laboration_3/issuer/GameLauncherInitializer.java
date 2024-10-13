package com.dt181g.laboration_3.issuer;

import javax.swing.SwingUtilities;

import com.dt181g.laboration_3.controller.GameLauncherCtrl;
import com.dt181g.laboration_3.model.GameListModel;
import com.dt181g.laboration_3.view.GameLauncherView;

/**
 * Singleton for initializing and running the game launcher.
 * This class manages the creation and coordination of the view, model,
 * and controller for the game launcher using the singleton pattern.
 *
 * <p>
 * It uses an enum-based singleton implementation to ensure that only one
 * instance of the launcher is created during the application's lifecycle.
 * </p>
 *
 * <p>
 * This class is thread-safe because the JVM handles the creation of the
 * enum instance and guarantees its uniqueness.
 * </p>
 *
 * @author Joel Lansgren
 */
public enum GameLauncherInitializer {
    INSTANCE;

    GameLauncherView gameLauncherView = new GameLauncherView();
    GameListModel gameListModel = new GameListModel();
    GameLauncherCtrl gameLauncherController = new GameLauncherCtrl(gameLauncherView, gameListModel);

    /**
     * Initializes and runs the game launcher.
     * <p>
     * This method sets up the controller and displays the game launcher UI
     * on the Event Dispatch Thread (EDT) using invokeLater.
     * </p>
     */
    public void runLauncher() {
        gameLauncherController.initializeLauncher();

        SwingUtilities.invokeLater(() -> {
            gameLauncherView.setVisible(true);
        });
    }
}
