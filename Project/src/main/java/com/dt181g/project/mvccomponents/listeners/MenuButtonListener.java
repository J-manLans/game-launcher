package com.dt181g.project.mvccomponents.listeners;

import com.dt181g.project.support.AppConfigProject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

/**
 * Listener for menu buttons that provides visual feedback and triggers actions on mouse events.
 * <p>
 * This class extends {@link MouseAdapter} to handle mouse interactions with a {@link JLabel}
 * representing a button. It updates the button's appearance on hover and executes a specified
 * action when the button is clicked.
 * </p>
 *
 * @author Joel Lansgren
 */
public class MenuButtonListener extends MouseAdapter {
    private final JLabel button;
    private final Runnable action;

    /**
     * Constructs a MenuButtonListener for the specified button with the associated action to execute.
     *
     * @param button the JLabel representing the button
     * @param action the action to execute when the button is clicked
     */
    public MenuButtonListener(final JLabel button, final Runnable action) {
        this.button = button;
        this.action = action;
    }

    /**
     * Helper method to update the appearance of the specified button based on hover state.
     *
     * @param isHovered true if the button is hovered, false otherwise
     */
    public void updateButtonAppearance(final boolean isHovered) {
        this.button.setOpaque(isHovered);
        this.button.setForeground(isHovered ? AppConfigProject.COLOR_DARKER_GREY : AppConfigProject.COLOR_WHITE);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        // Makes sure the label button is transparent and has
        // the right text color on the next startup.
        this.button.setOpaque(false);
        this.button.setForeground(AppConfigProject.COLOR_WHITE);
        this.action.run();
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        this.updateButtonAppearance(true);
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        this.updateButtonAppearance(false);
    }
}
