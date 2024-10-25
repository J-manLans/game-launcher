package com.dt181g.project.mvccomponents.games.tictactoe.view;

import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dt181g.project.mvccomponents.games.GameView;
import com.dt181g.project.mvccomponents.launcher.view.GameLauncherView;
import com.dt181g.project.support.AppConfigProject;

/**
 * A mock implementation of the {@link GameView} interface for the Tic Tac Toe game.
 *
 * <p>
 * This class serves as a placeholder for testing purposes, simulating the game view
 * when the actual game is not available. It displays a message indicating the unavailability
 * of the game and is used to verify that buttons and UI components are added correctly
 * to the {@link GameLauncherView}.
 * </p>
 *
 * <p>
 * The view is styled using configurations defined in the {@link AppConfigProject} class.
 * </p>
 *
 * <p>
 * Might be implemented in the future, who knows.
 * </p>
 * @author Joel Lansgren
 */
public class FakeTicTacToeView extends JPanel implements GameView {
    private final JLabel gameTitle = new JLabel(AppConfigProject.TIC_TAC_TOE_TITLE);
    private final JLabel quitBtn = new JLabel("Quit");
    private JPanel gamePanel;

    /**
     * May be properly implemented in the future.
     */
    @Override
    public void showStartMenu() {
        this.removeAll();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel gameLabel = new JLabel("Sorry! The game isn't available at the moment.");
        labelStyling(gameLabel, AppConfigProject.TEXT_SIZE_NORMAL, false);

        labelBtn(quitBtn, AppConfigProject.COLOR_WHITE);
        quitBtn.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);


        this.setBackground(AppConfigProject.COLOR_DARKER_GREY);
        this.add(Box.createVerticalGlue());
        this.add(gameLabel);
        this.add(Box.createRigidArea(AppConfigProject.HIGHT_20));
        this.add(quitBtn);
        this.add(Box.createVerticalGlue());
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
     * May be implemented in the future.
     */
    @Override
    public String getGameTitle() {
       return gameTitle.getText();
    }

    /**
     * May be implemented in the future.
     */
    @Override
    public void showGame() {
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    @Override
    public void closeGameView() {
        clearGameView(this.gamePanel, this, gameTitle.getText());
    }

    @Override
    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}
