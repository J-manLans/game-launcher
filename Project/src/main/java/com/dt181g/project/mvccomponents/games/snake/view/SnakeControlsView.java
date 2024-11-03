package com.dt181g.project.mvccomponents.games.snake.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.JLabel;

import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.support.AppConfigProject;

/**
 * Represents the controls view for the Snake game, displaying
 * instructions on how to control the snake and providing an option
 * to return to the previous menu.
 *
 * <p>
 * This class extends {@link JPanel} and implements {@link IBaseView}.
 * It uses a grid bag layout to arrange the control instructions and the back button.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeControlsView extends JPanel implements IBaseView {
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JLabel controlsMainLabel = new JLabel("CONTROLS:");
    private final JLabel controlsSubLabel = new JLabel("W,S,A,D or arrow keys to move the snake around");
    private final JLabel snakeBackBtn = new JLabel("Back");


    /**
     * Initializes the controls view, setting up the layout
     * and displaying control instructions for the game.
     *
     * <p>
     * This constructor configures the control panel with necessary labels
     * and a back button for navigation.
     * </p>
     */
    public SnakeControlsView() {
        this.setLayout(new GridBagLayout());

        labelStyling(controlsMainLabel, AppConfigProject.TEXT_HEADING_2);
        labelStyling(controlsSubLabel, AppConfigProject.TEXT_SIZE_NORMAL);
        labelBtn(this.snakeBackBtn, AppConfigProject.COLOR_WHITE);

        this.gbc.insets = AppConfigProject.INSET_BOTTOM_20;

        this.gbc.gridy = 0;
        this.add(controlsMainLabel, gbc);

        this.gbc.gridy++;
        this.add(controlsSubLabel, gbc);

        this.gbc.gridy++;
        this.add(this.snakeBackBtn, gbc);
        this.gbc.insets = AppConfigProject.RESET_INSETS;

        this.setBackground(AppConfigProject.COLOR_DARKER_GREY);
        this.setBorder(AppConfigProject.CONTROLS_BORDER);
    }

    /**
     * Re-adds the shared back button to the menu.
     * Must be called each time the menu is displayed to ensure
     * the button appears in the correct position.
     */
    protected void setBackBtn() {
        System.err.println("adds button to controls" + snakeBackBtn.toString());
    }

    /**
     * Adds a mouse listener to the back button in the controls menu.
     *
     * @param controlsBackBtnListener The mouse listener to be added to the back button,
     * allowing for interaction when the button is clicked.
     */
    public void addSnakeBackBtnListener(final MouseAdapter controlsBackBtnListener) {
        this.snakeBackBtn.addMouseListener(controlsBackBtnListener);
    }

    /**
     * Removes mouse listeners from the game control buttons.
     */
    public void removeListeners() {
        removeAllListenersFromButton(this.snakeBackBtn);
    }

    /**
     * Returns the back button in the controls menu.
     *
     * @return The JLabel representing the back button.
     */
    public JLabel getSnakeBackBtn() {
        return this.snakeBackBtn;
    }
}
