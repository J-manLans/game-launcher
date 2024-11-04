// package com.dt181g.laboration_3.mvccomponents.launcher.controller;


// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import javax.swing.SwingUtilities;
// import javax.swing.JScrollBar;
// import javax.swing.Box;
// import javax.swing.JButton;
// import java.awt.Image;
// import java.awt.image.BufferedImage;
// import java.io.IOException;
// import javax.swing.ImageIcon;
// import javax.imageio.ImageIO;
// import java.awt.event.MouseWheelEvent;
// import java.awt.event.MouseWheelListener;
// import com.dt181g.laboration_3.mvccomponents.launcher.model.GameListModel;
// import com.dt181g.laboration_3.support.AppConfigLab3;
// import com.dt181g.laboration_3.support.DebugLogger;
// import com.dt181g.laboration_3.mvccomponents.launcher.view.GameLauncherView;
// /**
//  * The controller class for managing the game launcher.
//  * <p>
//  * This class acts as the intermediary between the {@link GameLauncherView} and
//  * {@link GameListModel}, coordinating interactions and ensuring that user
//  * actions in the view trigger the appropriate responses in the model.
//  * </p>
//  *
//  * @author Joel lansgren
//  */
// public class oldcontroller {
//     DebugLogger logger = DebugLogger.INSTANCE;
//     private final GameLauncherView gameLauncherView;
//     private final GameListModel gameListModel;
//     /**
//      * Constructs a new GameLauncherCtrl with the specified view and model.
//      *
//      * @param gameLauncherView the view component for the game launcher
//      * @param gameListModel the model component containing the list of games that the launcher will display
//      */
//     public GameLauncherController(final GameLauncherView gameLauncherView, final GameListModel gameListModel) {
//         this.gameLauncherView = gameLauncherView;
//         this.gameListModel = gameListModel;
//     }
//     /**
//      * Initializes the game launcher using various helper methods.
//      * First it sets up the game icons, then attach listeners to them
//      * and lastly displays the launcher.
//      */
//     public void initialize() {
//         this.addGameIcons();
//         this.initializeListeners();
//         this.startLauncher();
//     }
//     /**
//      * Helper method that sets up the game icons in the launcher view.
//      */
//     private void addGameIcons() {
//         for (int i = 0; i < this.gameListModel.getIconPaths().size(); i++) {
//             this.gameLauncherView.addGameIcons(
//                 this.loadIcon(this.gameListModel.getIconPaths().get(i)),
//                 this.gameListModel.getTitleList().get(i)
//             );
//         }
//         // Adds flexible space below the icons,
//         // allowing the panel to respect the preferred size of the buttons.
//         this.gameLauncherView.getGameSelectorPanel().add(Box.createVerticalGlue());
//     }
//     /**
//      * Helper class for setting game icons.
//      * Loads and scales an image from a given path to fit as a game icon.
//      *
//      * @param path the file path to the image
//      * @return an ImageIcon containing the scaled image, or null if an error occurs
//      */
//     private ImageIcon loadIcon(final String path) {
//         try {
//             BufferedImage originalImage = ImageIO.read((getClass().getClassLoader().getResourceAsStream(path)));
//             Image scaledImage = originalImage.getScaledInstance(
//                 AppConfigLab3.SNAKE_SPEED,
//                 AppConfigLab3.SNAKE_SPEED,
//                 Image.SCALE_SMOOTH
//             );
//             return new ImageIcon(scaledImage);
//         } catch (IOException e) {
//             logger.logWarning(e + "\nSomething went wrong while loading the picture to the game icons");
//             return null;
//         }
//     }
//     /**
//      * Helper method that initialize listeners.
//      */
//     private void initializeListeners() {
//         for (JButton gameBtn : this.gameLauncherView.getGameIconsList()) {
//             gameBtn.addActionListener(new GameIconListener());
//         }
//         this.gameLauncherView.getScrollPane().addMouseWheelListener(new MouseWheelListener() {
//             @Override
//             public void mouseWheelMoved(final MouseWheelEvent e) {
//                 int notches = e.getWheelRotation();
//                 JScrollBar vertical = gameLauncherView.getScrollPane().getVerticalScrollBar();
//                 vertical.setValue(vertical.getValue() + (notches * AppConfigLab3.SCROLL_SPEED_MULTIPLIER));
//             }
//         });
//     }
//     /**
//      * Helper method that displays the game launcher UI on the Event Dispatch Thread (EDT) using invokeLater.
//      */
//     private void startLauncher() {
//         SwingUtilities.invokeLater(() -> {
//             gameLauncherView.setVisible(true);
//         });
//     }
//     /*========================
//     * Listeners
//     =======================*/
//      /**
//      * Listener for game icon clicks in the launcher.
//      * <p>
//      * This inner class handles actions triggered when a user clicks a game
//      * icon from the launcher. It identifies the selected game.
//      * The selected game is then re-instantiated, re-initiated and displayed in the game launcher view.
//      * All other games will be disposed of.
//      * </p>
//      */
//     class GameIconListener implements ActionListener {
//         @Override
//         public void actionPerformed(final ActionEvent e) {
//             // Get the game title from the clicked button's action command set earlier.
//             String selectedGame = e.getActionCommand();
//             // Close the game that was active.
//             this.closeInactiveGame(selectedGame);
//             // Re-instantiate (if necessary).
//             if (gameListModel.getGameModel(selectedGame) == null) {
//                 gameListModel.StartGame(selectedGame);
//                 gameExitListener(selectedGame);
//             }
//             //initiates the game, and lets the launcher display it.
//             gameListModel.getGameController(selectedGame).initiateGame();
//             gameLauncherView.displayGame(gameListModel.getGameView(selectedGame));
//             // Hands the game panel to the view, must be below the re-instantiation,
//             // otherwise the game view is null.
//             gameListModel.getGameView(selectedGame).setGamePanel(gameLauncherView.getGamePanel());
//         }
//         /**
//          * Helper method that select each title from the title list and compares it to the one passed into the method.
//          *
//          * <p>
//          * If it's not a match the games controller is used to close the game
//          * and the gameListModel removes it from it's list.
//          *
//          * @param selectedGame The game icon clicked.
//          */
//         private void closeInactiveGame(String selectedGame) {
//             for (String inactiveGame : gameListModel.getTitleList()) {
//                 if (inactiveGame != selectedGame) {
//                     closeGame(inactiveGame);
//                 }
//             }
//         }
//         /**
//          *  Helper method to close game.
//          * @param game the game to be closed.
//          */
//         public void closeGame(String game) {
//             if (gameListModel.getGameController(game) != null) {
//                 gameListModel.getGameController(game).closeGame();
//                 gameListModel.removeGame(game);
//             }
//         }
//         /**
//          * A listener that closes the game that gets attached to whatever game that is being loaded.
//          * @param game the loaded game
//          */
//         public void gameExitListener(String game) {
//             gameListModel.getGameView(game).getQuitBtn().addMouseListener(new MouseAdapter() {
//                 @Override
//                 public void mousePressed(MouseEvent e) {
//                     closeGame(game);
//                     gameLauncherView.displayStartScreen();
//                 }
//             });
//         }
//     }
// }