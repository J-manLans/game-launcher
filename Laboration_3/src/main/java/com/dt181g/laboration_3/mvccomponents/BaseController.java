package com.dt181g.laboration_3.mvccomponents;

import com.dt181g.laboration_3.support.AppConfigLab3;

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
        button.setForeground(isHovered ? AppConfigLab3.COLOR_DARKER_GREY : AppConfigLab3.COLOR_WHITE);
    }
}
