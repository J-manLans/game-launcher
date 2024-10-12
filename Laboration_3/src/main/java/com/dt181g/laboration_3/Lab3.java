package com.dt181g.laboration_3;

import javax.swing.SwingUtilities;

import com.dt181g.laboration_3.controller.GameLauncherCtrl;
import com.dt181g.laboration_3.model.GameListModel;
import com.dt181g.laboration_3.view.GameLauncherView;

/**
 * The main starting point for laboration 3.
 * @author Erik Ström
 */
public final class Lab3 {
    private Lab3() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * Simple output of the assignment's name. Be sure to replace
     * this when working with the assignment!
     * @param args command arguments.
     */
    public static void main(final String... args) {
        GameLauncherView gameLauncherView = new GameLauncherView();
        GameListModel gameListModel = new GameListModel();
        GameLauncherCtrl gameLauncherController = new GameLauncherCtrl(gameLauncherView, gameListModel);

        gameLauncherController.initializeLauncher();

        SwingUtilities.invokeLater(() -> {
            gameLauncherView.setVisible(true);
        });
    }
}
