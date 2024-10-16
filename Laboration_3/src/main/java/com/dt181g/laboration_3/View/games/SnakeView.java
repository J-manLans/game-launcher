package com.dt181g.laboration_3.view.games;

import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.DebugLogger;
import com.dt181g.laboration_3.view.GameView;

import java.awt.event.MouseAdapter;
import java.util.List;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Represents the view component of the Snake game, responsible for rendering
 * the game interface, including the start menu, game grid, and control menu.
 *
 * <p>
 * This class extends {@link JPanel} and implements {@link GameView}.
 * It uses a grid layout to display the snake's position on the board and
 * provides methods for interacting with game controls.
 * </p>
 *
 * <p>
 * It's still under construction and both settings and multiplayer will be
 * implemented further on.
 *
 * @author Joel Lansgren
 */
public class SnakeView extends JPanel implements GameView {
    DebugLogger logger = DebugLogger.INSTANCE;

    // Start menu components
    private final GridBagConstraints gbc = new GridBagConstraints();
    private JLabel title;
    private final JLabel startBtn = new JLabel("Start Game");
    private final JLabel multiplayerBtn = new JLabel("Multiplayer");
    private final JLabel settingsBtn = new JLabel("Settings");
    private final JLabel controlsBtn = new JLabel("Controls");
    private final JLabel quitBtn = new JLabel("Quit");

    // Snake panel
    private JPanel snakeGrid;
    private JPanel[][] snakeGridCells;
    private final JLabel snakeBackBtn = new JLabel("Back");

    // Control panel
    private final JPanel controlPanel = new JPanel(new GridBagLayout());
    private final JLabel controlsMainLabel = new JLabel("CONTROLS:");
    private final JLabel controlsSubLabel = new JLabel("W,S,A,D or arrow keys to move the snake around");

    // The panel this panel resides in, used for proper closing of the game.
    private JPanel gamePanel;

    /**
     * Constructs a SnakeView with the specified title and snake cell count.
     *
     * @param title The title to be displayed on the view.
     */
    public SnakeView(final String title) {
        this.setLayout(new GridBagLayout());

        // Initialize instance variables
        this.title = new JLabel(title);

        // Style snake components
        AppConfigLab3.labelStyling(this.title, AppConfigLab3.TEXT_HEADING_2, false);
        AppConfigLab3.labelBtn(startBtn, AppConfigLab3.COLOR_WHITE);
        AppConfigLab3.labelBtn(multiplayerBtn, AppConfigLab3.COLOR_DARK_GREY);
        AppConfigLab3.labelBtn(settingsBtn, AppConfigLab3.COLOR_DARK_GREY);
        AppConfigLab3.labelBtn(controlsBtn, AppConfigLab3.COLOR_WHITE);
        AppConfigLab3.labelBtn(quitBtn, AppConfigLab3.COLOR_DARK_GREY);
        AppConfigLab3.labelStyling(controlsMainLabel, AppConfigLab3.TEXT_HEADING_2, false);
        AppConfigLab3.labelStyling(controlsSubLabel, AppConfigLab3.TEXT_SIZE_NORMAL, false);
        AppConfigLab3.labelBtn(snakeBackBtn, AppConfigLab3.COLOR_WHITE);

        // Panel settings
        this.controlPanel.setPreferredSize(AppConfigLab3.SNAKE_CONTROLS_SIZE);

        // Initialize the start menu
        this.initializeStartMenu();
    }

    /*===============================
     * Start menu
     ==============================*/

    /**
     * Initializes and displays the start menu with game options.
     *
     * <p>
     * This method sets up the layout and adds the necessary buttons and labels
     * to allow the user to start the game, access multiplayer, settings, and controls.
     * </p>
     */
    @Override
    public void initializeStartMenu() {
        // Clear the menu before each initialization
        this.removeAll();

        // Resets the Insets before adding each component
        this.gbc.insets = AppConfigLab3.RESET_INSETS;

        // Adds and place the components on the grid.
        this.gbc.gridy = 0;
        this.add(title, this.gbc);

        this.gbc.gridy++;
        this.gbc.insets = AppConfigLab3.BOTTOM_20_INSET;

        this.add(startBtn, this.gbc);

        this.gbc.gridy++;
        this.add(multiplayerBtn, this.gbc);

        this.gbc.gridy++;
        this.add(settingsBtn, this.gbc);

        this.gbc.gridy++;
        this.add(controlsBtn, this.gbc);

        this.gbc.gridy++;
        this.add(quitBtn, this.gbc);

        // Sets the background color
        this.setBackground(AppConfigLab3.COLOR_DARKER_GREY);

        this.revalidate();
        this.repaint();
    }

    /*===============================
     * Snake game
     ==============================*/

     /**
     * Starts the game by displaying the game grid and the back button.
     *
     * @param gameAssets the 2D array representing the snake and a String just for fun.
     */
    public void startGame(final List<Object> gameAssets) {
        String comingSoon = (String) gameAssets.getFirst();
        int[][] snake2DArray = (int[][]) gameAssets.getLast();
        this.removeAll();

        if (snakeGrid == null) {
            this.snakeGrid = new JPanel(new GridLayout(snake2DArray.length, snake2DArray.length));
            this.snakeGrid.setPreferredSize(AppConfigLab3.SNAKE_GRID_SIZE);
            this.snakeGridCells = new JPanel[snake2DArray.length][snake2DArray.length];
        }

        // Setting up the snake grid
        this.initializeGrid(snake2DArray, comingSoon);

        // Display settings
        this.gbc.gridy = 0;
        this.gbc.insets = AppConfigLab3.BOTTOM_20_INSET;
        this.add(this.snakeGrid, gbc);

        this.gbc.gridy++;
        this.gbc.insets = AppConfigLab3.RESET_INSETS;
        this.add(this.snakeBackBtn, gbc);

        // Redraw the panel
        this.revalidate();
        this.repaint();
    }

    /**
     * Initializes the grid with the current state of the snake.
     *
     * @param snake2DArray  A 2D array representing the current state of the snake grid.
     * @param comingSoon A string representing the characters to display in the snake.
     */
    private void initializeGrid(final int[][] snake2DArray, final String comingSoon) {
        this.snakeGrid.removeAll();
        // used for displaying the text inside the snake
        int counter = 0;

        for (int i = 0; i < snake2DArray.length; i++) {
            for (int j = 0; j < snake2DArray.length; j++) {
                // Create cells to put in the grid
                JPanel cell = new JPanel(new BorderLayout());
                cell.setBorder(BorderFactory.createLineBorder(AppConfigLab3.COLOR_DARK_GREY));

                if (snake2DArray[i][j] == 1) {  // Displays the snake and the letters in the grid
                    JLabel comingSoonChar = new JLabel(String.valueOf(comingSoon.charAt(counter)));
                    this.styleSnakeBanner(comingSoonChar);

                    cell.setBackground(AppConfigLab3.COLOR_SNAKE_GAME_ACCENT);
                    cell.add(comingSoonChar);

                    counter++;
                } else {  // Background
                    cell.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
                }

                // Add the cells to the grid and then add the cells to a 2D array
                // used for the consecutive updates of the grid.
                this.snakeGrid.add(cell);
                this.snakeGridCells[i][j] = cell;
            }
        }
    }

    /**
     * Updates the game grid with the current state of the snake.
     *
     * @param gameAssets the 2D array representing the snake and a String just for fun.
     */
    public void updateGameGrid(final List<Object> gameAssets) {
        String comingSoon = (String) gameAssets.getFirst();
        int[][] snake2DArray = (int[][]) gameAssets.getLast();
        int counter = 0;

        for (int i = 0; i < snake2DArray.length; i++) {
            for (int j = 0; j < snake2DArray.length; j++) {
                this.snakeGridCells[i][j].removeAll();

                if (snake2DArray[i][j] == 1) {  // Displays the snake and the letters in the grid
                    JLabel comingSoonChar = new JLabel(String.valueOf(comingSoon.charAt(counter)));
                    this.styleSnakeBanner(comingSoonChar);

                    this.snakeGridCells[i][j].setBackground(AppConfigLab3.COLOR_SNAKE_GAME_ACCENT);
                    this.snakeGridCells[i][j].add(comingSoonChar);

                    counter++;
                } else {  // Background
                    this.snakeGridCells[i][j].setBackground(AppConfigLab3.COLOR_DARKER_GREY);
                }
            }
        }

        this.snakeGrid.revalidate();
        this.snakeGrid.repaint();
    }

    /**
     * Helper method that styles the letters inside the snake.
     *
     * @param snakeChar The JLabel to be styled.
     */
    public void styleSnakeBanner(final JLabel snakeChar) {
        AppConfigLab3.labelStyling(snakeChar, AppConfigLab3.TEXT_SIZE_NORMAL, true);
    }

    /*===============================
     * Controls menu
     ==============================*/

     /**
     * Displays the controls menu for the game.
     *
     * <p>
     * This method clears the current view and sets up the control panel with
     * instructions on how to control the snake. It also provides a back button
     * to return to the previous menu.
     * </p>
     */
    public void showControlsMenu() {
        this.removeAll();

        this.gbc.gridy = 0;
        this.controlPanel.add(controlsMainLabel, gbc);

        this.gbc.gridy++;
        this.controlPanel.add(controlsSubLabel, gbc);

        this.gbc.gridy++;
        this.controlPanel.add(snakeBackBtn, gbc);

        this.controlPanel.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
        this.controlPanel.setBorder(AppConfigLab3.CONTROLS_BORDER);

        this.add(controlPanel);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void closeGameView() {
        if (this.gamePanel != null) {
            clearGameView(this.gamePanel, this, title.getText());
        }
    }


    /*==============================
     * Listener methods
     ==============================*/

    /**
     * Adds a mouse listener to the start button.
     *
     * @param startBtnListener The mouse listener to be added to the start button,
     * allowing for interaction when the button is clicked.
     */
    public void addStartBtnListener(final MouseAdapter startBtnListener) {
        this.startBtn.addMouseListener(startBtnListener);
    }

    /**
     * Adds a mouse listener to the multiplayer button.
     *
     * @param multiplayerBtnListener The mouse listener to be added to the multiplayer button,
     * allowing for interaction when the button is clicked.
     */
    public void addMultiplayerBtnListener(final MouseAdapter multiplayerBtnListener) {
        this.multiplayerBtn.addMouseListener(multiplayerBtnListener);
    }

    /**
     * Adds a mouse listener to the settings button.
     *
     * @param settingsBtnListener The mouse listener to be added to the settings button,
     * allowing for interaction when the button is clicked.
     */
    public void addSettingsBtnListener(final MouseAdapter settingsBtnListener) {
        this.settingsBtn.addMouseListener(settingsBtnListener);
    }

    /**
     * Adds a mouse listener to the controls button.
     *
     * @param controlsBtnListener The mouse listener to be added to the controls button,
     * allowing for interaction when the button is clicked.
     */
    public void addControlsBtnListener(final MouseAdapter controlsBtnListener) {
        this.controlsBtn.addMouseListener(controlsBtnListener);
    }

    /**
     * Adds a mouse listener to the quit button.
     *
     * @param quitBtnListener The mouse listener to be added to the quit button,
     * allowing for interaction when the button is clicked.
     */
    public void addQuitBtnListener(final MouseAdapter quitBtnListener) {
        this.quitBtn.addMouseListener(quitBtnListener);
    }

    /**
     * Adds a mouse listener to the back button in the controls menu.
     *
     * @param controlsBackBtnListener The mouse listener to be added to the back button,
     * allowing for interaction when the button is clicked.
     */
    public void addSnakeBackBtnListener(final MouseAdapter controlsBackBtnListener) {
        this.snakeBackBtn.addMouseListener(controlsBackBtnListener);
    }

    /*==============================
    * Getters
    ==============================*/
    /**
     * Returns the panel that represents the snake grid.
     *
     * @return The JPanel representing the grid where the snake is displayed.
     */
    public JPanel getSnakeGrid() {
        return snakeGrid;
    }

    /**
     * Returns the title of the game.
     *
     * @return A string representing the title text displayed in the view.
     */
    @Override
    public String getTitle() {
        return title.getText();
    }

    /**
     * Returns the start button.
     *
     * @return The JLabel representing the start game button.
     */
    public JLabel getStartBtn() {
        return this.startBtn;
    }

    /**
     * Returns the multiplayer button.
     *
     * @return The JLabel representing the multiplayer button.
     */
    public JLabel getMultiplayerBtn() {
        return this.multiplayerBtn;
    }

    /**
     * Returns the settings button.
     *
     * @return The JLabel representing the settings button.
     */
    public JLabel getSettingsBtn() {
        return this.settingsBtn;
    }

    /**
     * Returns the controls button.
     *
     * @return The JLabel representing the controls button.
     */
    public JLabel getControlsBtn() {
        return this.controlsBtn;
    }

    /**
     * Returns the quit button.
     *
     * @return The JLabel representing the quit button.
     */
    public JLabel getQuitBtn() {
        return this.quitBtn;
    }

    /**
     * Returns the back button in the controls menu.
     *
     * @return The JLabel representing the back button.
     */
    public JLabel getSnakeBackBtn() {
        return this.snakeBackBtn;
    }

    /*==============================
    * Override methods (not Override getters)
    ==============================*/

    @Override
    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}
