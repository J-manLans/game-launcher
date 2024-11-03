package com.dt181g.project.mvccomponents.launcher.view;
import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.mvccomponents.games.IGameMainView;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.BackgroundPanel;

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
 * Uses {@link AppConfigProject} for styling and layout configurations.
 * </p>
 *
 * @author Joel Lansgren
 */
public class GameLauncherView extends JFrame implements IBaseView {
    // Game selector panel

    private final JPanel gameSelectorPanel = new JPanel();
    private final JPanel gamesPanel = new JPanel();
    private final JLabel pickAGameLabel = new JLabel("PICK A GAME");
    private final JScrollPane scrollPane = new JScrollPane(gamesPanel);
    private final List<JButton> gameIcons = new ArrayList<JButton>();

    // Game panel
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final CardLayout gamePanelCL = new CardLayout();
    private final JPanel gamePanel = new JPanel();
    private final BackgroundPanel backgroundPanel = new BackgroundPanel(AppConfigProject.PATH_TO_IMAGES, AppConfigProject.LAUNCHER_BACKGROUNDS);
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
        this.gameSelectorPanel.setPreferredSize(AppConfigProject.GAME_SELECTOR_PANEL_DIMENSIONS);
        this.gameSelectorPanel.setBackground(AppConfigProject.COLOR_DARK_GREY);
        this.gamesPanel.setLayout(new BoxLayout(this.gamesPanel, BoxLayout.Y_AXIS));
        this.gamesPanel.setBackground(AppConfigProject.COLOR_DARK_GREY);
        labelStyling(this.pickAGameLabel, AppConfigProject.TEXT_SIZE_NORMAL);
        this.pickAGameLabel.setBorder(AppConfigProject.BOTTOM_SPACER_30);
        this.gamesPanel.add(Box.createRigidArea(AppConfigProject.HIGHT_20));
        this.gamesPanel.add(this.pickAGameLabel);
        // this.scrollPane.setBorder(BorderFactory.createLineBorder(AppConfigLab3.COLOR_WHITE)); <- not needed?

        // ScrollPane (handles size of gamesPanel)
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scrollPane.setBorder(AppConfigProject.REMOVE_BORDER);

        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.gbc.weighty = 1;
        this.gbc.weightx = 1;
        this.gbc.insets = AppConfigProject.INSET_BOTTOM_20;
        this.gbc.fill = GridBagConstraints.BOTH;
        this.gameSelectorPanel.add(this.scrollPane, this.gbc);

        labelBtn(this.quitBtn, AppConfigProject.COLOR_WHITE);
        this.gbc.gridy++;
        this.gbc.weighty = 0;
        this.gbc.fill = GridBagConstraints.NONE;
        this.gbc.insets = AppConfigProject.INSET_BOTTOM_30;
        this.gameSelectorPanel.add(this.quitBtn, this.gbc);

        // GamePanel
        this.gamePanel.setLayout(this.gamePanelCL);
        this.gamePanel.add(this.startScreen, "Start Screen");

        // For when the launcher is complete (cant resize it and troubleshoot with this on)
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // this.setSize(screenSize);
        // this.setUndecorated(true);

        // JFrame
        this.setLayout(new BorderLayout());
        this.setSize(AppConfigProject.GAME_LAUNCHER_DIMENSIONS);
        this.add(this.gameSelectorPanel, BorderLayout.WEST);
        this.add(this.gamePanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Adds a game icon to the selection panel.
     * <p>
     * Creates a clickable icon for the specified game using the provided image and title,
     * and stores it for later use in event listeners.
     * </p>
     *
     * @param imageIcon the {@link ImageIcon} representing the game icon
     * @param title the title of the game associated with the icon
     */
    public void addGameIcons(final ImageIcon imageIcon, final String title) {
        // Set up the icon.
        final JButton gameIcon = new JButton();
        gameIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameIcon.setBorder(AppConfigProject.REMOVE_BORDER);
        gameIcon.setContentAreaFilled(false);
        gameIcon.setBorderPainted(false);
        gameIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Fetch  and sets the icon image.
        gameIcon.setIcon(imageIcon);
        // Set the action command of the icon to the game title,
        // to let the action listener know which game was clicked.
        gameIcon.setActionCommand(title);

        // Add icon to the panel, plus a distance to create some air.
        gamesPanel.add(gameIcon);
        gamesPanel.add(Box.createRigidArea(AppConfigProject.HIGHT_20));

        // Add the icon to the icon list so action listeners can be attached at a later stage.
        gameIcons.add(gameIcon);
    }


    /**
     * Displays the selected game's view in the game panel.
     * <p>
     * This method adds the game's view to the card layout and shows it.
     * It is called from the action listener attached to the game icons.
     * </p>
     *
     * @param gameView the {@link IGameMainView} to be displayed
     */
    public void displayGame(final IGameMainView gameView) {
        // Adds the panel and show the game view.
        this.gamePanel.add((JPanel) gameView, "Game");
        this.gamePanelCL.show(this.gamePanel, "Game");
    }

    /**
     * Displays the start screen of the game launcher.
     * <p>
     * This method toggles the background repaint and shows the start screen panel.
     * </p>
     */
    public void display() {
        this.backgroundPanel.toggleBackgroundRepaint(true);
        this.gamePanelCL.show(this.gamePanel, "Start Screen");
    }

    /*====================
     * Listeners
     ===================*/

    /**
     * Adds a mouse listener to the quit button.
     * @param quitBtnListener the {@link MouseAdapter} that listens for events on the quit button.
     */
    public void addQuitBtnListener(final MouseAdapter quitBtnListener) {
        this.quitBtn.addMouseListener(quitBtnListener);
    }

    /**
     * Adds an action listener to a specific game icon button.
     * @param gameIcon the {@link JButton} representing the game icon to which the listener is added.
     * @param gameIconListener the {@link ActionListener} that listens for action events on the game icon button.
     */
    public void addGameIconListeners(final JButton gameIcon, final ActionListener gameIconListener) {
        gameIcon.addActionListener(gameIconListener);
    }

    /**
     * Adds a mouse wheel listener to the scroll pane.
     * @param scrollPaneListener the {@link MouseWheelListener} that listens for mouse wheel events on the scroll pane.
     */
    public void addScrollPaneListener(final MouseWheelListener scrollPaneListener) {
        this.scrollPane.addMouseWheelListener(scrollPaneListener);
    }

    /*=====================
     * Getters
     ====================*/

    /**
     * Returns the game panel.
     * @return the {@link JPanel} representing the game panel.
     */
    public JPanel getGamePanel() {
        return this.gamePanel;
    }

    /**
     * Returns the games panel.
     * @return the {@link JPanel} representing the games panel.
     */
    public JPanel getGamesPanel() {
        return this.gamesPanel;
    }

    /**
     * Returns the scroll pane.
     * @return the {@link JScrollPane} used in the user interface.
     */
    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }

    /**
     * Returns the quit button label.
     * @return the {@link JLabel} representing the quit button.
     */
    public JLabel getQuitBtn() {
        return this.quitBtn;
    }

    /**
     * Returns the list of game icon buttons.
     * @return a List of {@link JButton} representing the game icons.
     */
    public List<JButton> getGameIconsList() {
        return this.gameIcons;
    }
}
