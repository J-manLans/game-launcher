package com.dt181g.laboration_3.controller.launcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.dt181g.laboration_3.controller.games.common.IGameController;

/**
 * Listener for game icon clicks in the launcher.
 * <p>
 * This class handles actions triggered when a user clicks a game icon from the launcher.
 * It identifies the selected game and delegates the action to appropriate methods to
 * switch to the selected game and set it as active. The listener ensures that the clicked
 * game is either not currently active or needs to be restarted.
 * </p>
 * <p>
 * The class uses functional interfaces to handle game switching and state updates,
 * providing a decoupled approach for handling game interactions.
 * </p>
 *
 * @author Joel Lansgren
 */
class GameIconListener implements ActionListener {
    private final Supplier<String> getActiveGame;
    private final Function<String, IGameController> getGameController;
    private final Consumer<String> switchToSelectedGame;
    private final Consumer<String> setActiveGame;

    /**
     * Constructs a GameIconListener with the provided functional interfaces.
     *
     * @param getActiveGame A supplier that provides the title of the currently active game.
     * @param getGameController A function that retrieves the {@link IGameController} for a specified game.
     * @param switchToSelectedGame A consumer that switches to the selected game.
     * @param setActiveGame A consumer that sets the specified game as active.
     */
    public GameIconListener(
        final Supplier<String> getActiveGame,
        final Function<String, IGameController> getGameController,
        final Consumer<String> switchToSelectedGame,
        final Consumer<String> setActiveGame
    ) {
        this.getActiveGame = getActiveGame;
        this.getGameController = getGameController;
        this.switchToSelectedGame = switchToSelectedGame;
        this.setActiveGame = setActiveGame;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        // Get the game title from the clicked button's action command set earlier.
        final String selectedGame = e.getActionCommand();

        if (
            // Allow a game to be started if it is not currently active
            !selectedGame.equals(getActiveGame.get()) ||
            // Allow a game to be restarted immediately after it has been closed
            getGameController.apply(selectedGame) == null
        ) {
            switchToSelectedGame.accept(selectedGame);
        }
        setActiveGame.accept(selectedGame);
    }
}
