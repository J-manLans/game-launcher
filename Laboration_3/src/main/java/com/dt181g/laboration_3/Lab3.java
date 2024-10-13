package com.dt181g.laboration_3;

import com.dt181g.laboration_3.issuer.GameLauncherInitializer;

/**
 * The main starting point for laboration 3.
 * @author Joel Lansgren
 */
public final class Lab3 {
    private Lab3() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * The main method serves as the entry point for the application.
     * It invokes the singleton instance of the game launcher issuer,
     * which encapsulates the logic required to start the game launcher.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(final String... args) {
        GameLauncherInitializer.INSTANCE.runLauncher();
    }
}
