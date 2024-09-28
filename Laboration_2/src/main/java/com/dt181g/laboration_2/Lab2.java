package com.dt181g.laboration_2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

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

        try {
            SwingUtilities.invokeAndWait(() -> {
                manager.setupAndStartGUI();
            });
            manager.startThreads();
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }

        Timer resourceCheckTimer = new Timer(150, new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.refreshGUI();
            }
        });
        resourceCheckTimer.start();
    }
}
