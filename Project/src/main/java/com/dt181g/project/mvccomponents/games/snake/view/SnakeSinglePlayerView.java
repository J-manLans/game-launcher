package com.dt181g.project.mvccomponents.games.snake.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.dt181g.project.mvccomponents.BaseView;
import com.dt181g.project.mvccomponents.games.listeners.SnakeMovementListener;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.DebugLogger;

public class SnakeSinglePlayerView extends JPanel implements BaseView {
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private JPanel snakeGrid;
    private JPanel[][] snakeGridCells;
    // The paintComponent method is overridden to create a transparent overlay effect,
    // for some reason the setBackground method doesn't apply the alpha channel as one would wish.
    private final JPanel gameOverPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(AppConfigProject.COLOR_TRANSPARENT);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    };
    JLabel gameOver = new JLabel("GAME OVER");
    private final JLabel snakeLengthLabel = new JLabel();
    private int[][] modelGameGrid;
    private final JLabel snakeBackBtn = new JLabel("Back");

    public SnakeSinglePlayerView() {
        this.setLayout(new GridBagLayout());
        this.setBackground(AppConfigProject.COLOR_DARKER_GREY);
        labelBtn(this.snakeBackBtn, AppConfigProject.COLOR_WHITE);
        labelStyling(this.gameOver, AppConfigProject.TEXT_HEADING_2);
        labelStyling(this.snakeLengthLabel, AppConfigProject.TEXT_SIZE_NORMAL);
    }

    public void initializeView(List<Object> gameAssets) {
        this.setGameAssets(gameAssets);
        // This one is only truly utilized the first time the game starts up.
        // Every other time it just sets the focus to the panel so the keyListener
        // will work properly
        this.startGame();
        // This is to get the true state of the game at every startup
        this.updateGameGrid();
    }

    /**
     * Sets the list with the snakes starter position in the grid.
     * @param gameAssets a list containing a 2D int array representing the snakes position in the grid
     */
    private void setGameAssets(List<Object> gameAssets) {
        for (Object asset : gameAssets) {
            if (asset instanceof int[][]) {
                this.modelGameGrid = (int[][]) asset;
            }
        }
    }

    private void startGame() {
        if (this.snakeGrid == null) {
            this.snakeGrid = new JPanel(new GridLayout(this.modelGameGrid.length, this.modelGameGrid.length));
            this.snakeGrid.setBounds(0, 0, AppConfigProject.SNAKE_GRID_SIZE.width, AppConfigProject.SNAKE_GRID_SIZE.height);
            this.snakeGridCells = new JPanel[this.modelGameGrid.length][this.modelGameGrid.length];

            this.layeredPane.setPreferredSize(AppConfigProject.SNAKE_GRID_SIZE);
            this.layeredPane.add(this.snakeGrid, JLayeredPane.DEFAULT_LAYER);
            this.setupGameOverPanel();

            // Display components
            this.gbc.gridy = 0;
            this.gbc.insets = AppConfigProject.INSET_BOTTOM_20;
            this.add(this.layeredPane, gbc);

            this.gbc.gridy++;
            this.gbc.insets = AppConfigProject.RESET_INSETS;
            this.add(this.snakeBackBtn, gbc);

            // Sets up the initial snake grid
            this.initializeGrid();
        }
        // For enabling the keyListener
        this.snakeGrid.requestFocusInWindow();
    }

    private void setupGameOverPanel() {
        this.gameOverPanel.setLayout(new BoxLayout(this.gameOverPanel, BoxLayout.Y_AXIS));
        this.gameOverPanel.setBounds(0, 0, AppConfigProject.SNAKE_GRID_SIZE.width, AppConfigProject.SNAKE_GRID_SIZE.height);
        this.gameOverPanel.setOpaque(false);

        this.gameOverPanel.add(Box.createVerticalGlue());
        this.gameOverPanel.add(this.gameOver);
        this.gameOverPanel.add(this.snakeLengthLabel);
        this.gameOverPanel.add(Box.createVerticalGlue());

    }

    public void showGameOver(int snakeLength) {
        if (!this.layeredPane.isAncestorOf(gameOverPanel)) {
            this.layeredPane.add(this.gameOverPanel, JLayeredPane.POPUP_LAYER);
        }
        this.snakeLengthLabel.setText("Your snake was " + snakeLength + " body parts long.");
        this.gameOverPanel.setVisible(true);
    }

    public void hideGameOver() {
        this.gameOverPanel.setVisible(false);
    }

    /**
     * Initializes the grid with the current state of the snake.
     * @param modelGameGrid  A 2D array representing the current state of the snake grid.
     */
    private void initializeGrid() {
        for (int i = 0; i < this.modelGameGrid.length; i++) {
            for (int j = 0; j < this.modelGameGrid.length; j++) {
                // Create cells to put in the grid
                JPanel cell = new JPanel(new BorderLayout());
                cell.setBorder(BorderFactory.createLineBorder(AppConfigProject.COLOR_DARK_GREY));

                if (this.modelGameGrid[i][j] == 1) {  // Displays the snake in the grid
                    cell.setBackground(AppConfigProject.COLOR_SNAKE_GAME_ACCENT);
                } else {  // Background
                    cell.setBackground(AppConfigProject.COLOR_DARKER_GREY);
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
     */
    public void updateGameGrid() {
        for (int i = 0; i < this.modelGameGrid.length; i++) {
            for (int j = 0; j < this.modelGameGrid.length; j++) {
                this.snakeGridCells[i][j].removeAll();

                if (this.modelGameGrid[i][j] != 0) {  // Displays the snake and items in the grid
                    switch (this.modelGameGrid[i][j]) {
                        case AppConfigProject.COLOR_SNAKE_INT -> {
                            this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_GAME_ACCENT);
                        } case AppConfigProject.COLOR_CHERRY_INT -> {
                            this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_GAME_CHERRY);
                        } case AppConfigProject.COLOR_SPEED_INT -> {
                            this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_GAME_SPEED);
                        } case AppConfigProject.COLOR_SNAKE_HEAD_INT -> {
                            this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_HEAD);
                        } default -> DebugLogger.INSTANCE.logInfo("Unimplemented snake booster in " + getClass().getName());
                    }

                } else {  // Background
                    this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_DARKER_GREY);
                }
            }
        }
    }

    /*=========================
     * Listeners
     ========================*/

    /**
     * Adds a mouse listener to the back button in the controls menu.
     *
     * @param controlsBackBtnListener The mouse listener to be added to the back button,
     * allowing for interaction when the button is clicked.
     */
    public void addSnakeBackBtnListener(final MouseAdapter controlsBackBtnListener) {
        this.snakeBackBtn.addMouseListener(controlsBackBtnListener);
    }

    public void addSnakeKeyListener(SnakeMovementListener snakeKeyListener) {
        this.snakeGrid.addKeyListener(snakeKeyListener);
    }

    public void removeListeners() {
        if (this.snakeGrid != null) {
            for (KeyListener listener : this.snakeGrid.getKeyListeners()) {
                this.snakeGrid.removeKeyListener(listener);
            }
        }
    }

    /**
     * Returns the back button in the controls menu.
     *
     * @return The JLabel representing the back button.
     */
    public JLabel getSnakeBackBtn() {
        return this.snakeBackBtn;
    }

    public JPanel getSnakeGrid() {
        return this.snakeGrid;
    }
}
