package com.dt181g.laboration_3.issuer;

import com.dt181g.laboration_3.mvccomponents.launcher.controller.GameLauncherController;
import com.dt181g.laboration_3.mvccomponents.launcher.model.GameListModel;
import com.dt181g.laboration_3.mvccomponents.launcher.view.GameLauncherView;

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
    private GameLauncherController gameLauncherController;

    /**
     * Initializes and runs the game launcher.
     *
     * <p>This method creates an instance of {@link GameLauncherController}
     * if it has not already been initialized. The controller is then initialized
     * and ready to handle the game launching process.</p>
     */
    public void runLauncher() {
        if (gameLauncherController == null) {
            this.gameLauncherController = new GameLauncherController(new GameLauncherView(), new GameListModel());
            this.gameLauncherController.initialize();
        }
    }
}
