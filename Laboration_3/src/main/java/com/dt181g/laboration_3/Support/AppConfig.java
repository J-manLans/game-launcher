package com.dt181g.laboration_3.Support;

public final class AppConfig {
    // make sure the class cannot be instantiated.
    private AppConfig() { throw new IllegalStateException("Utility class"); }

    /* ---------------------------------------
    ANSI COLOR CODES. Used to colorize debug output.
    ------------------------------------------ */
    /** Reset color. */
    public static final String ANSI_RESET = Color.RESET.ansiCode;

    /** Black color. */
    public static final String ANSI_BLACK = Color.BLACK.ansiCode;

    /** Red color. */
    public static final String ANSI_RED = Color.RED.ansiCode;

    /** Green color. */
    public static final String ANSI_GREEN = Color.GREEN.ansiCode;

    /** Yellow color. */
    public static final String ANSI_YELLOW = Color.YELLOW.ansiCode;

    /** Blue color. */
    public static final String ANSI_BLUE = Color.BLUE.ansiCode;

    /** Cyan color. */
    public static final String ANSI_CYAN = Color.CYAN.ansiCode;

    /** White color. */
    public static final String ANSI_WHITE = Color.WHITE.ansiCode;

    /** Magenta color. */
    public static final String ANSI_MAGENTA = Color.MAGENTA.ansiCode;

    private enum Color {
        BLACK("\u001B[30m"), RED("\u001B[31m"), GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"), BLUE("\u001B[34m"),CYAN("\u001B[36m"),
        WHITE("\u001B[37m"), MAGENTA("\u001b[35m"), RESET("\u001B[0m");

        private final String ansiCode;

        /**
         * Constructs a new color with the given ANSI escape code.
         * @param ansiCode the ANSI escape code for the color
         */
        Color(final String ansiCode) {
            this.ansiCode = ansiCode;
        }
    }
}
