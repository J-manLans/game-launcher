package com.dt181g.laboration_3.mvccomponents.launcher.controller;

import com.dt181g.laboration_3.mvccomponents.launcher.listeners.GameIconListener;
import com.dt181g.laboration_3.mvccomponents.launcher.model.GameListModel;
import com.dt181g.laboration_3.mvccomponents.launcher.view.GameLauncherView;
import com.dt181g.laboration_3.mvccomponents.listeners.MenuButtonListener;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

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
public class GameLauncherController{
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
        this.gameLauncherView.addGameIcons(
            this.gameListModel.getIconPaths(),
            this.gameListModel.getTitleList()
        );
    }

    /**
     * Helper method that initialize listeners.
     */
    private void initializeListeners() {
        // For game icons so they start the game when clicked
        this.gameLauncherView.addGameIconListeners(new GameIconListener(this.gameLauncherView, this.gameListModel));

        // For scroll pane speed
        this.gameLauncherView.addScrollPaneListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Delegate the event to the view for handling
                gameLauncherView.handleScroll(e.getWheelRotation());
            }
        });

        // For exiting the launcher
        this.gameLauncherView.addQuitBtnListener(new MenuButtonListener(
            this.gameLauncherView.getQuitBtn(),
            gameLauncherView::exitLauncher
        ));
    }

    /**
     * Helper method that displays the game launcher UI.
     */
    private void startLauncher() {
        this.gameLauncherView.showLauncher();
    }
}
