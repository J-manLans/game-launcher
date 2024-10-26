package com.dt181g.project.issuer;

import com.dt181g.project.factories.GameControllerFactory;
import com.dt181g.project.factories.GameModelFactory;
import com.dt181g.project.factories.GameViewFactory;
import com.dt181g.project.mvccomponents.BaseController;
import com.dt181g.project.mvccomponents.BaseModel;
import com.dt181g.project.mvccomponents.BaseView;
import com.dt181g.project.mvccomponents.launcher.controller.GameLauncherController;
import com.dt181g.project.mvccomponents.launcher.model.GameListModel;
import com.dt181g.project.mvccomponents.launcher.view.GameLauncherView;

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
            this.instantiateLauncher(GameListModel::new, GameLauncherView::new, GameLauncherController::new);
            this.gameLauncherController.initialize();
        }
    }

    private void instantiateLauncher(
        final GameModelFactory<BaseModel> gameModelFactory,
        final GameViewFactory<BaseView> gameViewFactory,
        final GameControllerFactory<BaseController, BaseView, BaseModel> gameControllerFactory
    ) {

        gameControllerFactory.create(gameViewFactory.create(), gameModelFactory.create()).initialize();
    }
}
