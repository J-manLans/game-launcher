package com.dt181g.project.mvccomponents.listeners;

import com.dt181g.project.support.AppConfigProject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class MenuButtonListener extends MouseAdapter {
    private final JLabel button;
    private final Runnable action;

    public MenuButtonListener(final JLabel button, final Runnable action) {
        this.button = button;
        this.action = action;
    }

    /**
     * Helper method to update the appearance of the specified button based on hover state.
     *
     * @param button the button to update
     * @param isHovered true if the button is hovered, false otherwise
     */
    public void updateButtonAppearance(final boolean isHovered) {
        button.setOpaque(isHovered);
        button.setForeground(isHovered ? AppConfigProject.COLOR_DARKER_GREY : AppConfigProject.COLOR_WHITE);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        // Makes sure the label button is transparent and has
        // the right text color on the next startup.
        button.setOpaque(false);
        button.setForeground(AppConfigProject.COLOR_WHITE);
        action.run();
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        updateButtonAppearance(true);
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        updateButtonAppearance(false);
    }
}
