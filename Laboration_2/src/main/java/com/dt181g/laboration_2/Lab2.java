package com.dt181g.laboration_2;

/**
 * The {@code Lab2} class serves as the entry point for the application.
 * It contains the {@code main} method that starts the execution of the program.
 *
 * <p>
 * This class is marked as {@code final} to prevent inheritance and serves as a utility class.
 * The constructor is private to prevent instantiation, as the class should only be used
 * to run the application, not to create objects.
 * </p>
 *
 * <p>
 * The {@code main} method creates an instance of {@link ResourceRunner} and calls the
 * {@code runRunner} method to initialize and start the resource management GUI.
 * </p>
 */
public final class Lab2 {
    private Lab2() {  // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

     /**
     * The main method that serves as the entry point of the application.
     *
     * <p>
     * This method initializes a new {@link ResourceRunner} instance and
     * calls its {@code runRunner} method to set up and start the GUI for managing resources.
     * </p>
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(final String... args) {
        ResourceRunner.INSTANCE.runRunner();
    }
}
