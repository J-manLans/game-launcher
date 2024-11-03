package com.dt181g.project.mvccomponents.launcher.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dt181g.project.mvccomponents.launcher.model.GameListModel;
import com.dt181g.project.mvccomponents.launcher.view.GameLauncherView;
import com.dt181g.project.mvccomponents.listeners.MenuButtonListener;

/**
 * Listener for game icon clicks in the launcher.
 * <p>
 * This inner class handles actions triggered when a user clicks a game
 * icon from the launcher. It identifies the selected game.
 * The selected game is then re-instantiated, re-initiated and displayed in the game launcher view.
 * All other games will be disposed of.
 * </p>
 *
 * @author Joel  lansgren
 */
public class GameIconListener implements ActionListener {
    private final GameLauncherView view;
    private final GameListModel model;
    /**
     * The temp variable stores the title of the currently selected game icon.
     * It is static, allowing all instances of GameIconListener to share the
     * same value. This helps determine if a new game is selected in the
     * actionPerformed method and triggers the necessary game switching logic.
     */
    private static String temp = "";

    public GameIconListener(final GameLauncherView view, final GameListModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Invoked when a game icon is clicked.
     * <p>
     * This method checks if the clicked game is different from the last selected one
     * or if the game has not been instantiated, then switches to the selected game.
     * </p>
     *
     * @param e the event that triggered this action
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        // Get the game title from the clicked button's action command set earlier.
        final String selectedGame = e.getActionCommand();

        if (selectedGame != temp || model.getGameController(selectedGame) == null) {
            this.switchToSelectedGame(selectedGame);
        }
        GameIconListener.temp = selectedGame;
    }

    /**
     * Helper method that switches to the specified game, closing the active game and initializes the selected one.
     * @param selectedGame the name of the game to switch to.
     */
    private void switchToSelectedGame(final String selectedGame) {
        // Closes the game that was active.
        for (final String inactiveGame : model.getTitleList()) {
            if (inactiveGame != selectedGame) {
                closeGame(inactiveGame);
            }
        }

        // Re-instantiate (if necessary) and adds listener to the exit button.
        if (model.getGameModel(selectedGame) == null) {
            model.startGame(selectedGame);
            gameExitListener(selectedGame);
        }

        //initiates the game, and lets the launcher display it.
        model.getGameController(selectedGame).initiateGame();
        view.displayGame(model.getGameView(selectedGame));

        // Hands the game panel to the view, must be below the re-instantiation,
        // otherwise the game view is null.
        model.getGameView(selectedGame).setGamePanel(view.getGamePanel());
    }

    /**
     *  Helper method to close game.
     * @param game the game to be closed.
     */
    public void closeGame(final String game) {
        // Checks that the game corresponding to the title
        // actually is instantiated. If so, it's deleted.
        // This protects against the first time a game gets started,
        // since no game is present in the system.
        if (model.getGameController(game) != null) {
            model.getGameController(game).closeGame();
            model.removeGame(game);
        }
    }

    /**
     * Attaches a listener to the quit button of the specified game.
     * When the quit button is clicked, the game will be closed.
     *
     * @param game the name of the game whose quit button is being listened to
     */
    public void gameExitListener(final String game) {
        model.getGameController(game).addQuitBtnListener(
            new MenuButtonListener(
                model.getGameController(game).getQuitBtn(),
                () -> {
                    this.closeGame(game);
                    this.view.display();
                }
            )
        );
    }
}
