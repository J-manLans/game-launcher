package com.dt181g.laboration_3.mvccomponents.launcher.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dt181g.laboration_3.mvccomponents.launcher.model.GameListModel;
import com.dt181g.laboration_3.mvccomponents.launcher.view.GameLauncherView;
import com.dt181g.laboration_3.mvccomponents.listeners.MenuButtonListener;

/**
 * Listener for game icon clicks in the launcher.
 * <p>
 * This inner class handles actions triggered when a user clicks a game
 * icon from the launcher. It identifies the selected game.
 * The selected game is then re-instantiated, re-initiated and displayed in the game launcher view.
 * All other games will be disposed of.
 * </p>
 */
public class GameIconListener implements ActionListener {
    private final GameLauncherView view;
    private final GameListModel model;

    public GameIconListener(GameLauncherView view, GameListModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        // Get the game title from the clicked button's action command set earlier.
        String selectedGame = e.getActionCommand();

        this.switchToSelectedGame(selectedGame);
    }

    /**
     * Helper method that select each title from the title list and compares it to the one passed into the method.
     *
     * <p>
     * If it's not a match the games controller is used to close the game
     * and the GameListModel removes it from it's list.
     *
     * @param selectedGame The game icon clicked.
     */
    private void switchToSelectedGame(String selectedGame) {
        // Closes the game that was active.
        for (String inactiveGame : model.getTitleList()) {
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
    public void closeGame(String game) {
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
     * A listener that closes the game that gets attached to whatever game that is being loaded.
     * @param game the loaded game
     */
    public void gameExitListener(String game) {
        model.getGameView(game).addQuitBtnListener(
            new MenuButtonListener(
                model.getGameView(game).getQuitBtn(),
                () -> {
                    closeGame(game);
                    view.display();
                }
            )
        );
    }
}
