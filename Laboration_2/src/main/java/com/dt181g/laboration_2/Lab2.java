package com.dt181g.laboration_2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

/**
 * The main entry point for the Laboration 2 application.
 * This class is responsible for initializing the GUI, starting the application threads
 * and start the refresh method for the GUI.
 *
 * @author Joel Lansgren
 */
public final class Lab2 {
    private Lab2() {  // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * The main method that starts the application.
     * This method access the singleton of the Manager to handle the application's logic,
     * sets up and starts the GUI on the Event Dispatch Thread (EDT),
     * and starts the threads for producers and consumers.
     * Additionally, it creates a timer that checks for resource updates at a fixed interval
     * and refreshes the GUI to reflect the current state of resources.
     *
     * @param args command line arguments (not used).
     */
    public static void main(final String... args) {
        final Manager manager = Manager.INSTANCE;

        try {
            SwingUtilities.invokeAndWait(() -> {
                manager.setupAndStartGUI();
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }

        manager.startThreads();

        Timer resourceCheckTimer = new Timer(AppConfig.EDT_REFRESH_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                manager.refreshGUI();
            }
        });
        resourceCheckTimer.start();
    }
}
