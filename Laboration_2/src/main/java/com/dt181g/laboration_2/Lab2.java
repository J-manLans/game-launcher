package com.dt181g.laboration_2;


/**
 * The main starting point for laboration 2.
 * @author Joel Lansgren
 */
public final class Lab2 {
    private Lab2() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param args command arguments.
     */
    public static void main(final String... args) {
        final Manager manager = Manager.INSTANCE;

        while (true) {
            manager.checkResources();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
