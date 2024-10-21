package com.dt181g.project.mvccomponents;

import com.dt181g.project.support.AppConfigProject;

import javax.swing.JLabel;

public interface BaseController {
    /**
     * Helper method to update the appearance of the specified button based on hover state.
     *
     * @param button the button to update
     * @param isHovered true if the button is hovered, false otherwise
     */
    default void updateButtonAppearance(final JLabel button, final boolean isHovered) {
        button.setOpaque(isHovered);
        button.setForeground(isHovered ? AppConfigProject.COLOR_DARKER_GREY : AppConfigProject.COLOR_WHITE);
    }
}
