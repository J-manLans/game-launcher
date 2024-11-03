package com.dt181g.project.mvccomponents.games.snake.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.JLabel;

import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.support.AppConfigProject;

public class SnakeControlsView extends JPanel implements IBaseView {
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JLabel controlsMainLabel = new JLabel("CONTROLS:");
    private final JLabel controlsSubLabel = new JLabel("W,S,A,D or arrow keys to move the snake around");
    private final JLabel snakeBackBtn = new JLabel("Back");


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
     * Removes all mouse listeners from the game control buttons.
     * This method iterates through each button and removes any attached mouse listeners,
     * ensuring that no event handlers remain active on these buttons.
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
