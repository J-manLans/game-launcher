package com.dt181g.laboration_3.support;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
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
    public static final String SNAKE_TITLE = "Snake(s)";

    public static final String TIC_TAC_TOE_TITLE = "Tic Tac Toe";

    /* ---------------------------------------
    GameLauncherView Settings
    ------------------------------------------ */
    public static final String PICK_A_GAME = "PICK A GAME";

    public static final Border BOTTOM_BORDER_30 = BorderFactory.createEmptyBorder(20, 0, 30, 0);

    public static final Border REMOVE_BORDER = BorderFactory.createEmptyBorder();

    public static final Dimension GAME_SELECTOR_PANEL_DIMENSIONS = new Dimension(400,900);

    public static final Dimension GAME_PANEL_DIMENSIONS = new Dimension(1300,900);

    public static final Dimension GAME_ICON_SIZE = new Dimension(200,90);

    public static final Dimension HIGHT_20 = new Dimension(0,20);

    public static final Dimension HIGHT_40 = new Dimension(0,40);


    /* ---------------------------------------
    Fonts.
    ------------------------------------------ */
    public static final Font MONOSPACE_BOLD = new Font("Monospace", Font.BOLD, 15);

    /* ---------------------------------------
    Colors.
    ------------------------------------------ */
    public static final Color DARK_GREY = new Color(40,40,40);

    public static final Color DARKER_GREY = new Color(30,30,30);

    public static final Color WHITE = new Color(255, 245, 238);


    /* ---------------------------------------
    Helper methods.
    ------------------------------------------ */
    public static final void LABEL_BUTTON(JLabel labelToBtn, Color lightClr, Color darkClr) {
        labelToBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelToBtn.setForeground(lightClr);
        labelToBtn.setBackground(lightClr);
        labelToBtn.setFont(MONOSPACE_BOLD);

        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border matteBorder = BorderFactory.createMatteBorder(3, 3, 3, 3, lightClr);
        labelToBtn.setBorder(BorderFactory.createCompoundBorder(matteBorder, emptyBorder));


        labelToBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                labelToBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                labelToBtn.setOpaque(true);
                labelToBtn.setForeground(darkClr);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelToBtn.setOpaque(false);
                labelToBtn.setForeground(lightClr);
            }
        });
    }

    public static final void LABEL_STYLING(JLabel label) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(MONOSPACE_BOLD);
        label.setForeground(WHITE);
        label.setBorder(BOTTOM_BORDER_30);
    }

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
