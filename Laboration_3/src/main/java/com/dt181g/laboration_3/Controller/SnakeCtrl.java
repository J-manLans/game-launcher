package com.dt181g.laboration_3.controller;

import com.dt181g.laboration_3.model.GameModel;
import com.dt181g.laboration_3.model.SnakeModel;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.GameView;
import com.dt181g.laboration_3.view.SnakeView;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * The SnakeCtrl class serves as the controller for the Snake game.
 * It mediates the interaction between the SnakeView and SnakeModel,
 * handling user inputs, updating the game state, and rendering changes
 * to the view.
 *
 * <p>
 * This class implements the GameCtrl interface and defines various
 * mouse listeners for game controls, including starting the game and
 * showing controls.
 * </p>
 *
 * <p>
 * This program is under construction and more game mechanics will be
 * incorporated moving forward, as of now, the game only displays a
 * snake with a banner text informing more is to come.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeCtrl implements GameCtrl {
    private final SnakeView snakeView;
    private final SnakeModel snakeModel;
    private boolean restart;

    /**
     * Constructs a SnakeCtrl with the specified SnakeView and SnakeModel.
     * It also initialize the listeners.
     *
     * @param snakeView the view associated with the Snake game
     * @param snakeModel the model representing the game's logic
     */
    public SnakeCtrl(final GameView snakeView, final GameModel snakeModel) {
        this.snakeView = (SnakeView) snakeView;
        this.snakeModel = (SnakeModel) snakeModel;
        this.initializeListeners();
    }

    /**
     * Helper method that initializes action listeners for start menu buttons.
     */
    public void initializeListeners() {
        snakeView.addStartBtnListener(new StartBtnListener());
        snakeView.addControlsBtnListener(new ControlsBtnListener());
        snakeView.addControlsBackBtnListener(new SnakeBackBtnListener());
    }

    /**
     * Resets the game state and re-initializes the start menu.
     * Clears the snake model's grid and sets the restart flag to true.
     * The restart flag is utilized in the game loop to stop the
     * EDT timer thread when the game is exited.
     */
    @Override
    public void resetGame() {
        // Clears and reinitialize the start menu
        this.snakeView.resetGame();
        // Resets the snake models 2D array
        this.snakeModel.clearSnakeGrid();
        // stops the EDT thread from executing
        this.restart = true;
    }

    /*========================
    * Listeners
    =======================*/

    /**
     * Helper method to update the appearance of the specified button based on hover state.
     *
     * @param button the button to update
     * @param isHovered true if the button is hovered, false otherwise
     */
    private void updateButtonAppearance(final JLabel button, final boolean isHovered) {
        button.setOpaque(isHovered);
        button.setForeground(isHovered ? AppConfigLab3.COLOR_DARKER_GREY : AppConfigLab3.COLOR_WHITE);
    }

    /**
     * Listener for the Start button. It starts the game and manages the game loop.
     */
    class StartBtnListener extends MouseAdapter {
        private final JLabel startBtn = snakeView.getStartBtn();
        private Timer gameLoop;

        StartBtnListener() {
            startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            // Makes sure the label button is transparent and has
            // the right text color on the next startup.
            startBtn.setOpaque(false);
            startBtn.setForeground(AppConfigLab3.COLOR_WHITE);

            // Grid initialization.
            snakeModel.initializeSnake();
            snakeView.startGame(snakeModel.getSnakeGrid(), snakeModel.getComingSoon());

            // For controlling the loop
            restart = false;

            // Game loop.
            gameLoop = new Timer(AppConfigLab3.NUM_200, new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    if (!restart) {
                        snakeModel.updateGameGrid();
                        snakeView.updateGameGrid(snakeModel.getSnakeGrid(), snakeModel.getComingSoon());
                    } else {
                        gameLoop.stop();
                    }
                }
            });
            gameLoop.start();
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            updateButtonAppearance(startBtn, true);
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            updateButtonAppearance(startBtn, false);
        }
    }

    /**
     * Listener for the Multiplayer button.
     * Not yet implemented.
     */
    class MultiplayerBtnListener extends MouseAdapter {
        private final JLabel multiplayerBtn = snakeView.getMultiplayerBtn();

        MultiplayerBtnListener() {
            multiplayerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            updateButtonAppearance(multiplayerBtn, true);
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            updateButtonAppearance(multiplayerBtn, false);
        }
    }

    /**
     * Listener for the Settings button.
     * Not yet implemented.
     */
    class SettingsBtnListener extends MouseAdapter {
        private final JLabel settingsBtn = snakeView.getSettingsBtn();

        SettingsBtnListener() {
            settingsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            updateButtonAppearance(settingsBtn, true);
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            updateButtonAppearance(settingsBtn, false);
        }
    }

    /**
     * Listener for the Controls button.
     */
    class ControlsBtnListener extends MouseAdapter {
        private final JLabel controlsBtn = snakeView.getControlsBtn();


        ControlsBtnListener() {
            controlsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            // Makes sure the label button is transparent and has
            // the right text color on the next startup.
            controlsBtn.setOpaque(false);
            controlsBtn.setForeground(AppConfigLab3.COLOR_WHITE);
            snakeView.showControlsMenu();
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            updateButtonAppearance(controlsBtn, true);
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            updateButtonAppearance(controlsBtn, false);
        }
    }

    /**
     * Listener for the back button in the Snake game.
     */
    class SnakeBackBtnListener extends MouseAdapter {
        private final JLabel snakeBackBtn = snakeView.getSnakeBackBtn();


        SnakeBackBtnListener() {
            snakeBackBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            // Makes sure the label button is transparent and has
            // the right text color on the next startup.
            snakeBackBtn.setOpaque(false);
            snakeBackBtn.setForeground(AppConfigLab3.COLOR_WHITE);
            resetGame();
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            updateButtonAppearance(snakeBackBtn, true);
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            updateButtonAppearance(snakeBackBtn, false);
        }
    }
}
