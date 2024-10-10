package com.dt181g.laboration_3.support;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public final class AppConfigLab3 {
    // make sure the class cannot be instantiated.
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
    public static final String SNAKE_TITLE = "Snake";

    public static final String TIC_TAC_TOE_TITLE = "Tic Tac Toe";

    /* ---------------------------------------
    GameLauncherView Settings
    ------------------------------------------ */
    public static final String PICK_A_GAME = "PICK A GAME";

    public static final Border PICK_A_GAME_BORDER = BorderFactory.createEmptyBorder(20, 0, 30, 0);

    public static final Border REMOVE_BORDER = BorderFactory.createEmptyBorder();

    public static final Dimension GAME_SELECTOR_PANEL_DIMENSIONS = new Dimension(400,900);

    public static final Dimension GAME_PANEL_DIMENSIONS = new Dimension(1300,900);

    public static final Dimension GAME_ICON_SIZE = new Dimension(200,90);

    public static final Dimension HIGHT_20 = new Dimension(0,20);

    public static final Color DARK_GREY = new Color(40,40,40);

    public static final Color DARKER_GREY = new Color(30,30,30);

    /* ---------------------------------------
    Fonts.
    ------------------------------------------ */
    public static final Font MONOSPACE_BOLD = new Font("Monospace", Font.BOLD, 15);

    /* ---------------------------------------
    ANSI COLOR CODES. Used to colorize debug output.
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

    private enum DebugColor {
        BLACK("\u001B[30m"), RED("\u001B[31m"), GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"), BLUE("\u001B[34m"),CYAN("\u001B[36m"),
        WHITE("\u001B[37m"), MAGENTA("\u001b[35m"), RESET("\u001B[0m");

        private final String ansiCode;

        /**
         * Constructs a new color with the given ANSI escape code.
         * @param ansiCode the ANSI escape code for the color
         */
        DebugColor(final String ansiCode) {
            this.ansiCode = ansiCode;
        }
    }
}
