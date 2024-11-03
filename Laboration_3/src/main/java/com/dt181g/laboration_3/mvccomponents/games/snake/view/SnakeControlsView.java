package com.dt181g.laboration_3.mvccomponents.games.snake.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dt181g.laboration_3.support.AppConfigLab3;

public class SnakeControlsView extends JPanel{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final SnakeView snakeView;
    private final JLabel controlsMainLabel = new JLabel("CONTROLS:");
    private final JLabel controlsSubLabel = new JLabel("W,S,A,D or arrow keys to move the snake around");

    /**
     * Displays the controls menu for the game.
     *
     * <p>
     * This constructor clears the current view and sets up the control panel with
     * instructions on how to control the snake. It also provides a back button
     * to return to the previous menu.
     * </p>
     * @param snakeView the main snake view
     */
    protected SnakeControlsView(final SnakeView snakeView) {
        this.setLayout(new GridBagLayout());

        // Initiate components
        this.snakeView = snakeView;

        this.snakeView.labelStyling(controlsMainLabel, AppConfigLab3.TEXT_HEADING_2, false);
        this.snakeView.labelStyling(controlsSubLabel, AppConfigLab3.TEXT_SIZE_NORMAL, false);

        this.gbc.insets = AppConfigLab3.INSET_BOTTOM_20;

        this.gbc.gridy = 0;
        this.add(controlsMainLabel, gbc);

        this.gbc.gridy++;
        this.add(controlsSubLabel, gbc);

        this.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
        this.setBorder(AppConfigLab3.CONTROLS_BORDER);
    }

    /**
     * Re-adds the shared back button to the menu.
     * Must be called each time the menu is displayed to ensure
     * the button appears in the correct position.
     */
    protected void setBackBtn() {
        this.gbc.gridy++;
        this.add(snakeView.getSnakeBackBtn(), gbc);
        this.gbc.insets = AppConfigLab3.RESET_INSETS;
    }
}
