package com.dt181g.project.mvccomponents;
import com.dt181g.project.support.AppConfigProject;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Color;
import java.awt.Cursor;

public interface BaseView {
    /**
     * Styles a label to look like a button with specific color and border.
     *
     * @param labelToBtn the JLabel to style
     * @param borderAndHoverBackgroundClr the color used for the label's foreground and background
     */
    default void labelBtn(final JLabel labelToBtn, final Color borderAndHoverBackgroundClr) {
        labelToBtn.setFont(new Font("Monospace", Font.BOLD, AppConfigProject.TEXT_SIZE_NORMAL));
        labelToBtn.setForeground(borderAndHoverBackgroundClr);
        labelToBtn.setBackground(borderAndHoverBackgroundClr); // Used for hovering effect.
        labelToBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final Border matteBorder = BorderFactory.createMatteBorder(
            AppConfigProject.LABEL_BTN_OUTER_BORDER_WIDTH,
            AppConfigProject.LABEL_BTN_OUTER_BORDER_WIDTH,
            AppConfigProject.LABEL_BTN_OUTER_BORDER_WIDTH,
            AppConfigProject.LABEL_BTN_OUTER_BORDER_WIDTH,
            borderAndHoverBackgroundClr
        );

        labelToBtn.setBorder(BorderFactory.createCompoundBorder(matteBorder, AppConfigProject.LABEL_BTN_INNER_SPACE));
    }

    /**
     * Applies styling to a label for uniform appearance.
     *
     * @param label the JLabel to style
     */
    default void labelStyling(final JLabel label, final int textSize, final boolean snakeText) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Monospace", Font.BOLD, textSize));
        label.setForeground(AppConfigProject.COLOR_WHITE);
        if (snakeText) {
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
        }
    }

    /**
     * Helper method that removes all mouse listeners from the specified button.
     *
     * @param button the JButton from which to remove all mouse listeners.
     */
    default void removeAllListenersFromButton(final JLabel button) {
        for (final MouseListener listener : button.getMouseListeners()) {
            button.removeMouseListener(listener);
        }
    }
}
