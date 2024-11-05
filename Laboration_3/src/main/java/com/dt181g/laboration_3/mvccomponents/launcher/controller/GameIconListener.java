package com.dt181g.laboration_3.mvccomponents.launcher.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dt181g.laboration_3.mvccomponents.launcher.model.GameListModel;

/**
 * Listener for game icon clicks in the launcher.
 * <p>
 * This inner class handles actions triggered when a user clicks a game
 * icon from the launcher. It identifies the selected game, which is then
 * re-instantiated, re-initialized, and displayed in the game launcher view.
 * </p>
 *
 * <p>
 * It checks whether the clicked game is not currently active or if it needs
 * to be restarted, and then delegates the action to the
 * {@link GameLauncherController} to switch to the selected game.
 * </p>
 *
 * @author Joel Lansgren
 */
class GameIconListener implements ActionListener {
    private final GameLauncherController controller;
    private final GameListModel model;

    /**
     * Constructs a GameIconListener.
     *
     * @param controller The controller responsible for handling game launching actions.
     * @param model The model containing the list of games and their states.
     */
    public GameIconListener(final GameLauncherController controller, final GameListModel model) {
        this.controller = controller;
        this.model = model;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        // Get the game title from the clicked button's action command set earlier.
        final String selectedGame = e.getActionCommand();

        if (
            // Allow a game to be started if it is not currently active
            !selectedGame.equals(this.model.getActiveGame()) ||
            // Allow a game to be restarted immediately after it has been closed
            this.model.getGameController(selectedGame) == null
        ) {
            this.controller.switchToSelectedGame(selectedGame);
        }
        this.model.setActiveGame(selectedGame);
    }
}
