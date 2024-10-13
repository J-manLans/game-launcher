package com.dt181g.laboration_3.view;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.DebugLogger;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 * The view component of the Game Launcher.
 * <p>
 * This class represents the user interface for the game launcher, providing
 * a scrollable list of game icons and a panel to display the selected game.
 * It interacts with the controller by accepting listeners for user actions.
 * </p>
 *
 * <p>
 * It extends {@link JFrame} and organizes components using {@link JPanel},
 * {@link JScrollPane}, and various layout managers for an intuitive interface.
 * </p>
 *
 * <p>
 * Uses {@link AppConfigLab3} for styling and layout configurations.
 * </p>
 *
 * @author Joel Lansgren
 */
public class GameLauncherView extends JFrame {
    private final DebugLogger logger = DebugLogger.INSTANCE;

    private final JPanel gameSelectorPanel = new JPanel();
    private final JLabel pickAGameLabel = new JLabel("PICK A GAME");
    private final JScrollPane scrollPane = new JScrollPane(gameSelectorPanel);
    private final JPanel gamePanel = new JPanel();
    private final List<JButton> gameIcons = new ArrayList<JButton>();

    /**
     * Constructs the GameLauncherView and sets up its components.
     * <p>
     * Initializes the game selection panel, the scroll pane that holds it, and the game display panel.
     * Also sets the layout for the JFrame and configures general window properties.
     * </p>
     */
    public GameLauncherView() {
        // GameSelectorPanel
        gameSelectorPanel.setLayout(new BoxLayout(gameSelectorPanel, BoxLayout.Y_AXIS));
        gameSelectorPanel.setBackground(AppConfigLab3.COLOR_DARK_GREY);
        AppConfigLab3.labelStyling(pickAGameLabel);
        gameSelectorPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));
        gameSelectorPanel.add(pickAGameLabel);

        // ScrollPane (handles size of gameSelectorPanel)
        this.setupScrollPane();

        // GamePanel
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setPreferredSize(AppConfigLab3.GAME_PANEL_DIMENSIONS);
        gamePanel.setBackground(AppConfigLab3.COLOR_DARKER_GREY);

        // JFrame
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        this.add(scrollPane);
        this.add(gamePanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Helper method to configure the scroll pane for the game selector panel.
     * <p>
     * Disables the vertical scroll bar, removes the border, and sets up a mouse wheel listener
     * to allow faster scrolling through the game selection panel.
     * </p>
     */
    private void setupScrollPane() {
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(AppConfigLab3.REMOVE_BORDER);
        scrollPane.setPreferredSize(AppConfigLab3.GAME_SELECTOR_PANEL_DIMENSIONS);

        scrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(final MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getValue() + (notches * AppConfigLab3.SCROLL_SPEED_MULTIPLIER));
            }
        });
    }

    /**
     * Adds game icons to the selection panel.
     * <p>
     * Takes a list of image paths and corresponding game titles, and creates
     * a clickable icon for each game. Each icon is set up with the appropriate
     * image and action command, and is stored for later use in event listeners.
     * </p>
     *
     * @param pathToIcon a list of file paths for the game icons
     * @param titles a list of game titles corresponding to each icon
     */
    public void addGameIcons(final List<String> pathToIcon, final List<String> titles) {
        for (int i = 0; i < pathToIcon.size(); i++) {
            // Set up the icon.
            JButton gameIcon = new JButton();
            gameIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameIcon.setBorder(AppConfigLab3.REMOVE_BORDER);
            gameIcon.setContentAreaFilled(false);
            gameIcon.setBorderPainted(false);
            gameIcon.setPreferredSize(AppConfigLab3.GAME_ICON_SIZE);
            gameIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // Fetch  and sets the icon image.
            gameIcon.setIcon(loadIcon(pathToIcon.get(i)));
            // Set the action command od the icon to the game title,
            // to let the action listener know which game was clicked.
            gameIcon.setActionCommand(titles.get(i));

            // Add icon to the panel, plus a distance to create some air.
            gameSelectorPanel.add(gameIcon);
            gameSelectorPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));

            // Add the icon to the icon list so action listeners can be attached at a later stage.
            gameIcons.add(gameIcon);
        }
        // Adds flexible space below the icons,
        // allowing the panel to respect the preferred size of the buttons.
        gameSelectorPanel.add(Box.createVerticalGlue());
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
            BufferedImage originalImage = ImageIO.read((getClass().getClassLoader().getResourceAsStream(path)));
            Image scaledImage = originalImage.getScaledInstance(
                AppConfigLab3.NUM_200,
                AppConfigLab3.NUM_200,
                Image.SCALE_SMOOTH
            );
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            logger.logWarning(e + "\nSomething went wrong while loading the picture to the game icons");
            return null;
        }
    }

    /**
     * Adds an {@link ActionListener} to each game icon for handling game selection.
     *
     * @param listenForGameIconClicks the listener to be added to each game icon
     */
    public void addGameIconListener(final ActionListener listenForGameIconClicks) {
        for (JButton gameBtn : gameIcons) {
            gameBtn.addActionListener(listenForGameIconClicks);
        }
    }

    /**
     * Loads the selected game's view into the game display panel.
     *
     * <p>
     * This method is called from the {@link ActionListener} attached to the game icons.
     * It clears the existing contents of the game panel and adds the
     * new game's view to it. It then triggers a revalidation and repaint to ensure
     * the new content is displayed correctly.
     * </p>
     *
     * @param gameView the {@link GameView} to be displayed in the panel
     */
    public void loadGame(final GameView gameView) {
        // Clears the panel and adds the game view.
        gamePanel.removeAll();
        gamePanel.add(gameView.getPanel(), BorderLayout.CENTER);

        // Redraws the panel
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}
