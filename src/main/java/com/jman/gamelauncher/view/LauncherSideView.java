package com.jman.gamelauncher.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.jman.gamelauncher.support.AppConfig;
import com.jman.gamelauncher.support.AppConfigLauncher;

/**
 * This is the side view of the launcher.
 *
 * <p>
 * It holds all buttons that will start respective game inside a {@link JScrollPane} to enable
 * multiple games to be listed. It also adds the buttons dynamically through the controller
 * and attaches a {@link ButtonListener} to each one.
 * </p>
 * <p>
 * In addition to this it attaches
 * listeners for the scrollPane to adjust its speed and to the exit button to enable
 * closing the launcher.
 * </p>
 * @author Joel Lansgren
 */
public class LauncherSideView implements IView {
    private final JPanel mainPanel = new JPanel();
    private GridBagConstraints gbc = new GridBagConstraints();
    private final JLabel gameLabel = new JLabel("GAMES:");
    private final JPanel gamesListPanel = new JPanel();
    private final JScrollPane scrollPane = new JScrollPane(gamesListPanel);
    private final JButton exitBtn = new JButton("Exit");

    /**
     * Constructs the LauncherSideView on the EDT.
     * It holds a gamesListPanel inside a scrollPane and has an exit button as well.
     */
    public LauncherSideView() {
        SwingUtilities.invokeLater(() -> {
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.setPreferredSize(AppConfigLauncher.GAME_SELECTOR_PANEL_DIMENSIONS);
            mainPanel.setBackground(AppConfig.COLOR_DARK_GREY);

            // Initiates components
            labelStyling(gameLabel, AppConfig.TEXT_SIZE_NORMAL);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getVerticalScrollBar().setUnitIncrement(8);
            gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
            gamesListPanel.setBackground(AppConfig.COLOR_DARK_GREY);
            buttonStyler(exitBtn, AppConfig.COLOR_WHITE);

            // Adds and place the components on the grid.
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = AppConfig.INSET_TOP_30_BOTTOM_20;
            mainPanel.add(gameLabel, gbc);

            gbc.gridy++;  // Advances constraints in the y axis
            gbc.weighty = 1;  // Expands the whole scroll pane to take all available space in y axis
            gbc.weightx = 1;  // Expands the whole scroll pane to take all available space in x axis
            gbc.fill = GridBagConstraints.BOTH;  // Fills the actual expanded space with the content
            mainPanel.add(scrollPane, gbc);

            gbc = resetGbc(gbc.gridy, gbc.gridx);
            gbc.gridy++;
            gbc.insets = AppConfig.INSET_TOP_20_BOTTOM_30;
            mainPanel.add(exitBtn, gbc);
        });
    }

    /**
     * Adds a game button to the gamesListPanel.
     * This is called for each game in the {@link com.jman.gamelauncher.model.LauncherModel}
     * <p>
     * Takes a game title, and creates a clickable button for each game.
     * Each button is styled and set up with the appropriate action command,
     * and finally a listener get attached.
     * </p>
     * @param title the title corresponding to each game button
     * @param addSpacer a boolean dictating if a spacer should be added beneath
     * @param createGame the callback that will be attached to the {@link ButtonListener}
     */
    public void addGameBtn(
        final String title,
        final boolean addSpacer,
        final Runnable createGame
    ) {
        // Set up the game button.
        final JButton gameBtn = new JButton(title);
        buttonStyler(gameBtn, AppConfig.COLOR_WHITE);

        // Add button to the panel.
        gamesListPanel.add(gameBtn);

        // Add a spacer if it's not the last game.
        if (addSpacer) {
            gamesListPanel.add(Box.createRigidArea(AppConfig.HIGHT_20));
        }

        // Attaches the listener
        gameBtn.addMouseListener(new ButtonListener(createGame, gameBtn.getForeground()));
    }

    /*========================================
    * Listener Methods
    ========================================*/

    /**
     * Sets a listener for the exitBtn to quit the game when clicked.
     * @param exitLauncher a callback that will exit the launcher when the exitBtn is clicked
     */
    public void addExitBtnListener(final Runnable exitLauncher) {
        exitBtn.addMouseListener(new ButtonListener(exitLauncher, exitBtn.getForeground()));
    }

    /*========================================
    * Visibility Methods
    ========================================*/

    /** Shows the side view */
    public void showPanel() {
        mainPanel.setVisible(true);
    }

    /** Hides the side view */
    public void hidePanel() {
        mainPanel.setVisible(false);
    }

    /*========================================
    * Getters
    ========================================*/

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
