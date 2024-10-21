package com.dt181g.project.mvccomponents.launcher.controller;

import com.dt181g.project.mvccomponents.launcher.listeners.GameIconListener;
import com.dt181g.project.mvccomponents.launcher.model.GameListModel;
import com.dt181g.project.mvccomponents.launcher.view.GameLauncherView;
import com.dt181g.project.mvccomponents.listeners.MenuButtonListener;
import com.dt181g.project.support.AppConfigProject;
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
 * @author Joel lansgren
 */
public class GameLauncherController{
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
            BufferedImage originalImage = ImageIO.read((getClass().getResourceAsStream(path)));

            // Create a down-scaled version of the image to fit as a game icon.
            Image scaledImage = originalImage.getScaledInstance(
                AppConfigProject.NUM_200,
                AppConfigProject.NUM_200,
                Image.SCALE_SMOOTH
            );

            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            logger.logWarning(e + "\nSomething went wrong while loading the picture to the game icons");
            return null;
        }
    }

    /**
     * Helper method that initialize listeners.
     */
    private void initializeListeners() {
        // For game icons so they start the game when clicked
        for (JButton gameIcon : this.gameLauncherView.getGameIconsList()) {
            this.gameLauncherView.addGameIconListeners(gameIcon, new GameIconListener(this.gameLauncherView, this.gameListModel));
        }

        // For scroll pane speed
        this.gameLauncherView.addScrollPaneListener((MouseWheelEvent e) -> {
            int notches = e.getWheelRotation();  // Gets the direction (1 or -1).
            JScrollBar vertical = gameLauncherView.getScrollPane().getVerticalScrollBar();
            // Sets the new position either up (-1) or down (1) depending on scroll direction.
            vertical.setValue(vertical.getValue() + (notches * AppConfigProject.SCROLL_SPEED_MULTIPLIER));
        });

        // For exiting the launcher
        this.gameLauncherView.addQuitBtnListener(
            new MenuButtonListener(
                this.gameLauncherView.getQuitBtn(),
                 gameLauncherView::exitLauncher
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
