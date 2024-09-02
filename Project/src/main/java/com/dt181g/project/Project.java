package com.dt181g.project;

/**
 * The main starting point for Project Assignment.
 * @author Erik Ström
 */
public final class Project {
    private Project() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * Simple output of the assignment's name. Be sure to replace
     * this when working with the assignment!
     * @param args command arguments.
     */
    public static void main(final String... args) {
        System.out.println("Project Assignment!");
    }
}
