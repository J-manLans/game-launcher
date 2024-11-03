package com.dt181g.project.mvccomponents.launcher.controller;

import com.dt181g.project.issuer.GameLauncherInitializer;
import com.dt181g.project.mvccomponents.IBaseController;
import com.dt181g.project.mvccomponents.IBaseModel;
import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.mvccomponents.launcher.listeners.GameIconListener;
import com.dt181g.project.mvccomponents.launcher.model.GameListModel;
import com.dt181g.project.mvccomponents.launcher.view.GameLauncherView;
import com.dt181g.project.mvccomponents.listeners.MenuButtonListener;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.AudioManager;
import com.dt181g.project.support.DebugLogger;

import java.awt.event.MouseWheelEvent;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;
import javax.swing.JScrollBar;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The controller class for managing the game launcher.
 * <p>
 * This class acts as the intermediary between the {@link GameLauncherView} and
 * {@link GameListModel}, coordinating interactions and ensuring that user
 * actions in the view trigger the appropriate responses in the model.
 * </p>
 *
 * <p>
 * It is responsible for initializing the launcher, loading game icons,
 * setting up event listeners, and managing the launcher lifecycle.
 * </p>
 *
 * @author Joel lansgren
 */
public class GameLauncherController implements IBaseController {
    private final GameLauncherView gameLauncherView;
    private final GameListModel gameListModel;

    /**
     * Constructs a new GameLauncherCtrl with the specified view and model.
     *
     * @param gameLauncherView the view component for the game launcher
     * @param gameListModel the model component containing the list of games that the launcher will display
     */
    public GameLauncherController(final IBaseView gameLauncherView, final IBaseModel gameListModel) {
        this.gameLauncherView = (GameLauncherView) gameLauncherView;
        this.gameListModel = (GameListModel) gameListModel;
    }

    /**
     * Initializes the game launcher by setting up game icons, attaching listeners,
     * and displaying the launcher.
     * This method also ensures that the audio is maintained during the launchers lifecycle.
     */
    public void initialize() {
        this.addGameIcons();
        this.initializeListeners();
        this.startLauncher();
        AudioManager.INSTANCE.keepAudioAlive();
    }

    /**
     * Helper method that sets up the game icons in the launcher view.
     */
    private void addGameIcons() {
        for (int i = 0; i < this.gameListModel.getIconPaths().size(); i++) {
            this.gameLauncherView.addGameIcons(
                this.loadIcon(this.gameListModel.getIconPaths().get(i)),
                this.gameListModel.getTitleList().get(i)
            );
        }
        // Adds flexible space below the icons,
        // allowing the panel to respect the preferred size of the buttons.
        this.gameLauncherView.getGamesPanel().add(Box.createVerticalGlue());
    }

    /**
     * Helper class for setting game icons.
     * Loads and scales an image from a given path to fit as a game icon.
     *
     * @param path the file path to the image
     * @return an ImageIcon containing the scaled image, or null if an error occurs
     */
    private ImageIcon loadIcon(final String path) {
        try {
            // Load the image from the specified path as an input stream.
            // This is necessary to ensure the image can be included in the JAR file
            // and accessed properly when the application is packaged.
            final BufferedImage originalImage = ImageIO.read((getClass().getResourceAsStream(path)));

            // Create a down-scaled version of the image to fit as a game icon.
            final Image scaledImage = originalImage.getScaledInstance(
                AppConfigProject.NUM_200,
                AppConfigProject.NUM_200,
                Image.SCALE_SMOOTH
            );

            return new ImageIcon(scaledImage);
        } catch (final IOException e) {
            DebugLogger.INSTANCE.logWarning(e + "\nSomething went wrong while loading the picture to the game icons.");
            return null;
        }
    }

    /**
     * Initializes listeners for user interactions within the launcher.
     */
    private void initializeListeners() {
        // Add listeners for game icons to start the game when clicked
        for (final JButton gameIcon : this.gameLauncherView.getGameIconsList()) {
            this.gameLauncherView.addGameIconListeners(gameIcon, new GameIconListener(this.gameLauncherView, this.gameListModel));
        }

        // Listener for scroll pane speed adjustment
        this.gameLauncherView.addScrollPaneListener((final MouseWheelEvent e) -> {
            final int notches = e.getWheelRotation();  // Gets the direction (1 or -1).
            final JScrollBar vertical = gameLauncherView.getScrollPane().getVerticalScrollBar();
            // Sets the new position either up (-1) or down (1) depending on scroll direction.
            vertical.setValue(vertical.getValue() + (notches * AppConfigProject.SCROLL_SPEED_MULTIPLIER));
        });

        // For exiting the launcher
        this.gameLauncherView.addQuitBtnListener(
            new MenuButtonListener(
                this.gameLauncherView.getQuitBtn(),
                GameLauncherInitializer.INSTANCE::countDown
            )
        );
    }

    /**
     * Helper method that displays the game launcher UI on the Event Dispatch Thread (EDT) using invokeLater.
     */
    private void startLauncher() {
        SwingUtilities.invokeLater(() -> {
            gameLauncherView.setVisible(true);
        });
    }
}
