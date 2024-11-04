package com.dt181g.laboration_3.mvccomponents.launcher.view;
import com.dt181g.laboration_3.mvccomponents.BaseView;
import com.dt181g.laboration_3.mvccomponents.games.GameView;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.BackgroundPanel;
import com.dt181g.laboration_3.support.ImageManager;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseWheelListener;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

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
public class GameLauncherView extends JFrame implements BaseView {
    // Game selector panel

    private final JPanel gameSelectorPanel = new JPanel();
    private final JPanel gamesListPanel = new JPanel();
    private final JLabel pickAGameLabel = new JLabel("PICK A GAME");
    private final JScrollPane scrollPane = new JScrollPane(gamesListPanel);
    private final List<JButton> gameIcons = new ArrayList<JButton>();

    // Game panel
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final CardLayout gamePanelCL = new CardLayout();
    private final JPanel gamePanel = new JPanel();
    private final BackgroundPanel backgroundPanel = new BackgroundPanel(AppConfigLab3.PATH_TO_IMAGES, AppConfigLab3.LAUNCHER_BACKGROUNDS);
    private final JPanel startScreen = backgroundPanel;
    private final JLabel quitBtn = new JLabel("Exit");

    /**
     * Constructs the GameLauncherView and sets up its components.
     * <p>
     * Initializes the game selection panel, the scroll pane that holds it, and the game display panel.
     * Also sets the layout for the JFrame and configures general window properties.
     * </p>
     */
    public GameLauncherView() {
        // GameSelectorPanel
        this.gameSelectorPanel.setLayout(new GridBagLayout());
        this.gameSelectorPanel.setPreferredSize(AppConfigLab3.GAME_SELECTOR_PANEL_DIMENSIONS);
        this.gameSelectorPanel.setBackground(AppConfigLab3.COLOR_DARK_GREY);
        this.gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
        this.gamesListPanel.setBackground(AppConfigLab3.COLOR_DARK_GREY);
        labelStyling(pickAGameLabel, AppConfigLab3.TEXT_SIZE_NORMAL, false);
        this.pickAGameLabel.setBorder(AppConfigLab3.BOTTOM_SPACER_30);
        this.gamesListPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));
        this.gamesListPanel.add(pickAGameLabel);
        // this.scrollPane.setBorder(BorderFactory.createLineBorder(AppConfigLab3.COLOR_WHITE)); <- not needed?

        // ScrollPane (handles size of gamesPanel)
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scrollPane.setBorder(AppConfigLab3.REMOVE_BORDER);

        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.gbc.weighty = 1;
        this.gbc.weightx = 1;
        this.gbc.insets = AppConfigLab3.INSET_BOTTOM_20;
        this.gbc.fill = GridBagConstraints.BOTH;
        this.gameSelectorPanel.add(scrollPane, this.gbc);

        labelBtn(quitBtn, AppConfigLab3.COLOR_WHITE);
        this.gbc.gridy++;
        this.gbc.weighty = 0;
        this.gbc.fill = GridBagConstraints.NONE;
        this.gbc.insets = AppConfigLab3.INSET_BOTTOM_30;
        this.gameSelectorPanel.add(quitBtn, this.gbc);

        // GamePanel
        this.gamePanel.setLayout(this.gamePanelCL);
        this.gamePanel.add(startScreen, "Start Screen");

        // For when the launcher is complete (cant resize it and troubleshoot with this on)
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // this.setSize(screenSize);
        // this.setUndecorated(true);

        // JFrame
        this.setLayout(new BorderLayout());
        this.setSize(AppConfigLab3.GAME_LAUNCHER_DIMENSIONS);
        this.add(gameSelectorPanel, BorderLayout.WEST);
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
    public void addGameIcons(final List<String> iconPaths, final List<String> titles) {
        for (int i = 0; i < iconPaths.size(); i++) {
            // Set up the icon.
            JButton gameIcon = new JButton();
            gameIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameIcon.setBorder(AppConfigLab3.REMOVE_BORDER);
            gameIcon.setContentAreaFilled(false);
            gameIcon.setBorderPainted(false);
            gameIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // Fetch  and sets the icon image.
            gameIcon.setIcon(ImageManager.INSTANCE.loadIcon(iconPaths.get(i)));
            // Set the action command of the icon to the game title,
            // to let the action listener know which game was clicked.
            gameIcon.setActionCommand(titles.get(i));

            // Add icon to the panel, plus a distance to create some air.
            gamesListPanel.add(gameIcon);
            gamesListPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));

            // Add the icon to the icon list so action listeners can be attached at a later stage.
            gameIcons.add(gameIcon);
        }
        // Adds flexible space below the icons,
        // allowing the panel to respect the preferred size of the buttons.
        gamesListPanel.add(Box.createVerticalGlue());
    }

    /**
     * Makes the game launcher view visible on the Event Dispatch Thread (EDT).
     */
    public void showLauncher() {
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
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

    /**
     * Returns the game panel.
     * @return the {@link JPanel} representing the game panel.
     */
    public JPanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Returns the games panel.
     * @return the {@link JPanel} representing the games panel.
     */
    public JPanel getGamesListPanel() {
        return gamesListPanel;
    }

    /**
     * Returns the scroll pane.
     * @return the {@link JScrollPane} used in the user interface.
     */
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * Returns the quit button label.
     * @return the {@link JLabel} representing the quit button.
     */
    public JLabel getQuitBtn() {
        return quitBtn;
    }

    /**
     * Adds a mouse listener to the quit button.
     * @param quitBtnListener the {@link MouseAdapter} that listens for events on the quit button.
     */
    @Override
    public void addQuitBtnListener(MouseAdapter quitBtnListener) {
        this.quitBtn.addMouseListener(quitBtnListener);
    }

    /**
     * Adds an action listener to a specific game icon button.
     * @param gameIcon the {@link JButton} representing the game icon to which the listener is added.
     * @param gameIconListener the {@link ActionListener} that listens for action events on the game icon button.
     */
    public void addGameIconListeners(ActionListener gameIconListener) {
        // For game icons so they start the game when clicked
        for (JButton gameIcon : this.gameIcons) {
            gameIcon.addActionListener(gameIconListener);
        }
    }

    /**
     * Adds a mouse wheel listener to the scroll pane.
     * @param scrollPaneListener the {@link MouseWheelListener} that listens for mouse wheel events on the scroll pane.
     */
    public void addScrollPaneListener(MouseWheelListener scrollPaneListener) {
        scrollPane.addMouseWheelListener(scrollPaneListener);
    }

    /**
     * Method that controls the scroll speed of the scroll pane.
     * @param notches The direction of the mouse wheel (1 or -1).
     */
    public void handleScroll(int notches) {
        JScrollBar vertical = this.getScrollPane().getVerticalScrollBar();
        // Sets the new position either up (-1) or down (1) depending on scroll direction.
        vertical.setValue(vertical.getValue() + (notches * AppConfigLab3.SCROLL_SPEED_MULTIPLIER));
    }

    /**
     * Displays the game panel by toggling the background repaint and showing the start screen.
     */
    public void display() {
        backgroundPanel.toggleBackgroundRepaint(true);
        this.gamePanelCL.show(gamePanel, "Start Screen");
    }

    /**
     * Exits the game launcher application.
     * This method terminates the application by calling System.exit(0).
     */
    public void exitLauncher() {
        System.exit(0);
    }
}
