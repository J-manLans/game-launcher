package com.dt181g.project.issuer;

import java.util.concurrent.CountDownLatch;

import com.dt181g.project.factories.BaseControllerFactory;
import com.dt181g.project.factories.BaseModelFactory;
import com.dt181g.project.factories.BaseViewFactory;
import com.dt181g.project.mvccomponents.IBaseController;
import com.dt181g.project.mvccomponents.IBaseModel;
import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.mvccomponents.launcher.controller.GameLauncherController;
import com.dt181g.project.mvccomponents.launcher.model.GameListModel;
import com.dt181g.project.mvccomponents.launcher.view.GameLauncherView;

/**
 * Singleton responsible for initializing and managing the game launcher.
 * This class handles the creation and coordination of the Model-View-Controller (MVC)
 * components for the game launcher, ensuring that only one instance exists.
 *
 * <p>
 * It uses an enum-based singleton implementation to ensure that only one
 * instance of the launcher is created during the application's lifecycle.
 * </p>
 *
 * <p>
 * Once initialized, the game launcher remains active until a
 * shutdown signal is triggered via {@link #countDown()}.
 * </p>
 *
 * @author Joel Lansgren
 * @see GameLauncherController
 * @see GameListModel
 * @see GameLauncherView
 */
public enum GameLauncherInitializer {
    INSTANCE;
    private GameLauncherController gameLauncherController;
    private final CountDownLatch latch = new CountDownLatch(1);


    /**
     * Initializes and starts the game launcher.
     *
     * <p>
     * If the launcher’s controller has not been initialized, this method creates
     * instances of the Model, View, and Controller components and links them to
     * manage the game launcher lifecycle.
     * </p>
     *
     * <p>The method then waits until {@link #countDown()} is called, allowing the
     * launcher to run until explicitly shut down.</p>
     */
    public void runLauncher() {

        if (this.gameLauncherController == null) {
            this.instantiateLauncher(GameListModel::new, GameLauncherView::new, GameLauncherController::new);
        }

        try {
            latch.await();
            System.exit(0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    /**
     * Creates and initializes the Model, View, and Controller components for the game launcher.
     *
     * @param gameModelFactory Factory for creating the launcher model
     * @param gameViewFactory Factory for creating the launcher view
     * @param gameControllerFactory Factory for creating the launcher controller
     */
    private void instantiateLauncher(
        final BaseModelFactory<IBaseModel> gameModelFactory,
        final BaseViewFactory<IBaseView> gameViewFactory,
        final BaseControllerFactory<IBaseController, IBaseView, IBaseModel> gameControllerFactory
    ) {

        gameControllerFactory.create(gameViewFactory.create(), gameModelFactory.create()).initialize();
    }

    /**
     * Signals the launcher to shut down.
     *
     * <p>Calling this method decreases the latch count, allowing {@link #runLauncher()}
     * to complete and initiate the application shutdown.</p>
     */
    public void countDown() {
        this.latch.countDown();
    }
}
