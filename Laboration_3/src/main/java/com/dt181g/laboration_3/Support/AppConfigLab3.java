package com.dt181g.laboration_3.support;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * A utility class that provides application-wide configuration constants
 * and settings for the game launcher.
 * <p>
 * This class includes paths to game icons, game titles, UI settings,
 * snake game configurations, font styles, color definitions, and helper methods
 * for styling UI components.
 * </p>
 * <p>
 * The class is not meant to be instantiated; all fields and methods are static.
 * </p>
 */
public final class AppConfigLab3 {
    // Prevent instantiation
    private AppConfigLab3() { throw new IllegalStateException("Utility class"); }

    /* ---------------------------------------
    Paths to game icons.
    ------------------------------------------ */
    public static final String PATH_TO_ICONS = "Laboration_3\\src\\main\\resources\\icons\\";
    public static final String SNAKE_ICON = "snake.jpeg";
    public static final String TIC_TAC_TOE_ICON = "tictactoe.jpg";

    /* ---------------------------------------
    Game titles.
    ------------------------------------------ */
    public static final String SNAKE_TITLE = "SNAKE(S)";
    public static final String TIC_TAC_TOE_TITLE = "TIC TAC TOE";

    /* ---------------------------------------
    GameLauncherView Settings.
    ------------------------------------------ */
    public static final String PICK_A_GAME = "PICK A GAME";
    public static final Border BOTTOM_BORDER_30 = BorderFactory.createEmptyBorder(0, 0, 30, 0);
    public static final Border REMOVE_BORDER = BorderFactory.createEmptyBorder();
    public static final Dimension GAME_SELECTOR_PANEL_DIMENSIONS = new Dimension(400,900);
    public static final Dimension GAME_PANEL_DIMENSIONS = new Dimension(1300,900);
    public static final Dimension GAME_ICON_SIZE = new Dimension(200,90);
    public static final Dimension HIGHT_20 = new Dimension(0,20);
    public static final Dimension HIGHT_40 = new Dimension(0,40);
    public static final int SCROLL_SPEED_MULTIPLIER = 20;

    /* ---------------------------------------
    Snake settings.
    ------------------------------------------ */
    public static final Dimension SNAKE_GRID_SIZE = new Dimension(760,760);
    public static final Dimension SNAKE_CONTROLS_SIZE = new Dimension(400,400);
    public static final int SNAKE_CELL_COUNT = 38;
    public static final Insets RESET_INSETS = new Insets(0, 0, 0, 0);
    public static final Insets BOTTOM_20_INSET = new Insets(0, 0, 20, 0);

    /* ---------------------------------------
    Fonts.
    ------------------------------------------ */
    public static final Font MONOSPACE_BOLD = new Font("Monospace", Font.BOLD, 15);

    /* ---------------------------------------
    Colors.
    ------------------------------------------ */
    public static final Color COLOR_DARK_GREY = new Color(40,40,40);
    public static final Color COLOR_DARKER_GREY = new Color(30,30,30);
    public static final Color COLOR_WHITE = new Color(255, 245, 238);
    public static final Color COLOR_SNAKE_GAME_ACCENT = new Color(255, 184, 77);

    /* ---------------------------------------
    Helper methods.
    ------------------------------------------ */

    /**
     * Styles a label to look like a button with specific color and border.
     *
     * @param labelToBtn the JLabel to style
     * @param lightClr the color used for the label's foreground and background
     */
    public static final void LABEL_BUTTON(JLabel labelToBtn, Color lightClr) {
        labelToBtn.setFont(MONOSPACE_BOLD);
        labelToBtn.setForeground(lightClr);
        labelToBtn.setBackground(lightClr); // Used for hovering effect.

        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border matteBorder = BorderFactory.createMatteBorder(3, 3, 3, 3, lightClr);
        labelToBtn.setBorder(BorderFactory.createCompoundBorder(matteBorder, emptyBorder));
    }

    /**
     * Applies styling to a label for uniform appearance.
     *
     * @param label the JLabel to style
     */
    public static final void LABEL_STYLING(JLabel label) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(MONOSPACE_BOLD);
        label.setForeground(COLOR_WHITE);
        label.setBorder(BOTTOM_BORDER_30);
    }

    /* ---------------------------------------
    ANSI COLOR CODES for console output.
    ------------------------------------------ */

    /** Reset color. */
    public static final String ANSI_RESET = DebugColor.RESET.ansiCode;

    /** Black color. */
    public static final String ANSI_BLACK = DebugColor.BLACK.ansiCode;

    /** Red color. */
    public static final String ANSI_RED = DebugColor.RED.ansiCode;

    /** Green color. */
    public static final String ANSI_GREEN = DebugColor.GREEN.ansiCode;

    /** Yellow color. */
    public static final String ANSI_YELLOW = DebugColor.YELLOW.ansiCode;

    /** Blue color. */
    public static final String ANSI_BLUE = DebugColor.BLUE.ansiCode;

    /** Cyan color. */
    public static final String ANSI_CYAN = DebugColor.CYAN.ansiCode;

    /** White color. */
    public static final String ANSI_WHITE = DebugColor.WHITE.ansiCode;

    /** Magenta color. */
    public static final String ANSI_MAGENTA = DebugColor.MAGENTA.ansiCode;

    /**
     * An enum representing ANSI color codes for console output.
     */
    private enum DebugColor {
        BLACK("\u001B[30m"), RED("\u001B[31m"), GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"), BLUE("\u001B[34m"), CYAN("\u001B[36m"),
        WHITE("\u001B[37m"), MAGENTA("\u001b[35m"), RESET("\u001B[0m");

        private final String ansiCode;

        /**
         * Constructs a new color with the given ANSI escape code.
         *
         * @param ansiCode the ANSI escape code for the color
         */
        DebugColor(final String ansiCode) {
            this.ansiCode = ansiCode;
        }
    }
}
