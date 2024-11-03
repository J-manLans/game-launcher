package com.dt181g.project.mvccomponents;
import com.dt181g.project.support.AppConfigProject;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Color;
import java.awt.Cursor;

/**
 * Base interface for all views in the application.
 * <p>
 * This interface defines default methods for styling components and managing
 * user interactions within the application's views. Implementing classes
 * can provide specific implementations of these methods to achieve
 * distinctive styles as needed.
 * </p>
 */
public interface IBaseView {
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
     * Applies consistent styling to a JLabel for a uniform appearance across the application.
     *
     * @param label the JLabel to style
     * @param textSize the font size to apply to the label
     */
    default void labelStyling(final JLabel label, final int textSize) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Monospace", Font.BOLD, textSize));
        label.setForeground(AppConfigProject.COLOR_WHITE);
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
