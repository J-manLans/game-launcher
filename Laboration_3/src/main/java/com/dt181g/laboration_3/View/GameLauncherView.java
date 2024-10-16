package com.dt181g.laboration_3.view;
import com.dt181g.laboration_3.support.AppConfigLab3;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    // Game selector panel
    private final JPanel gameSelectorPanel = new JPanel();
    private final JLabel pickAGameLabel = new JLabel("PICK A GAME");
    private final JScrollPane scrollPane = new JScrollPane(gameSelectorPanel);
    private final List<JButton> gameIcons = new ArrayList<JButton>();

    // Game panel
    private final GridBagConstraints gbc = new GridBagConstraints();
    CardLayout gamePanelCL = new CardLayout();
    private final JPanel gamePanel = new JPanel();
    private final JPanel startScreen = new JPanel();
    private final JLabel startScreenText = new JLabel("HELLO AND WELCOME!");
    private final JLabel closeLauncher = new JLabel("Exit");

    /**
     * Constructs the GameLauncherView and sets up its components.
     * <p>
     * Initializes the game selection panel, the scroll pane that holds it, and the game display panel.
     * Also sets the layout for the JFrame and configures general window properties.
     * </p>
     */
    public GameLauncherView() {
        // GameSelectorPanel
        this.gameSelectorPanel.setLayout(new BoxLayout(gameSelectorPanel, BoxLayout.Y_AXIS));
        this.gameSelectorPanel.setBackground(AppConfigLab3.COLOR_DARK_GREY);
        AppConfigLab3.labelStyling(pickAGameLabel, AppConfigLab3.TEXT_SIZE_NORMAL, false);
        this.gameSelectorPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));
        this.gameSelectorPanel.add(pickAGameLabel);

        // ScrollPane (handles size of gameSelectorPanel)
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(AppConfigLab3.REMOVE_BORDER);
        scrollPane.setPreferredSize(AppConfigLab3.GAME_SELECTOR_PANEL_DIMENSIONS);

        // GamePanel
        this.gamePanel.setLayout(this.gamePanelCL);
        this.startScreen.setLayout(new GridBagLayout());
        AppConfigLab3.labelStyling(startScreenText, AppConfigLab3.TEXT_HEADING_1, false);
        AppConfigLab3.labelBtn(closeLauncher, AppConfigLab3.COLOR_WHITE);

        this.gbc.gridy = 0;
        this.gbc.insets = AppConfigLab3.BOTTOM_20_INSET;
        this.startScreen.add(startScreenText, this.gbc);

        this.gbc.gridy++;
        this.gbc.insets = AppConfigLab3.RESET_INSETS;

        this.startScreen.add(closeLauncher, this.gbc);

        this.startScreen.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
        this.gamePanel.add(startScreen, "Start Screen");

        // JFrame
        this.setLayout(new BorderLayout());
        this.setSize(AppConfigLab3.GAME_LAUNCHER_DIMENSIONS);
        this.add(scrollPane, BorderLayout.WEST);
        this.add(gamePanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    public void addGameIcons(final ImageIcon imageIcon, final String title) {
            // Set up the icon.
            JButton gameIcon = new JButton();
            gameIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameIcon.setBorder(AppConfigLab3.REMOVE_BORDER);
            gameIcon.setContentAreaFilled(false);
            gameIcon.setBorderPainted(false);
            gameIcon.setPreferredSize(AppConfigLab3.GAME_ICON_SIZE);
            gameIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // Fetch  and sets the icon image.
            gameIcon.setIcon(imageIcon);
            // Set the action command od the icon to the game title,
            // to let the action listener know which game was clicked.
            gameIcon.setActionCommand(title);

            // Add icon to the panel, plus a distance to create some air.
            gameSelectorPanel.add(gameIcon);
            gameSelectorPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));

            // Add the icon to the icon list so action listeners can be attached at a later stage.
            gameIcons.add(gameIcon);
    }



    /**
     * Loads the selected game's view into the game display panel.
     *
     * <p>
     * This method is called from the {@link ActionListener} attached to the game icons.
     * It adds the new game's view to the card layout panel. It then shows the view.
     * </p>
     *
     * @param gameView the {@link GameView} to be displayed in the panel
     */
    public void displayGame(final GameView gameView) {
        // Adds the panel and show the game view.
        this.gamePanel.add((JPanel) gameView, "Game");
        this.gamePanelCL.show(gamePanel, "Game");
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public JPanel getGameSelectorPanel() {
        return gameSelectorPanel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public List<JButton> getGameIconsList() {
        return this.gameIcons;
    }

    public void displayStartScreen() {
        this.gamePanelCL.show(gamePanel, "Start Screen");
    }
}
