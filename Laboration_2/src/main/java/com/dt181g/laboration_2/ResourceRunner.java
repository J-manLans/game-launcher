package com.dt181g.laboration_2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

/**
 * The {@code ResourceRunner} class is responsible for initializing and running the application.
 * This class sets up the GUI, initiates background threads through the manager,
 * and uses a timer to periodically refresh the GUI.
 */
public class ResourceRunner {
    final Manager manager = Manager.INSTANCE;
    final ResourceFrame resourceFrame = new ResourceFrame();

    /**
     * Initializes the GUI and starts the necessary background processes.
     *
     * This method performs the following:
     * <ul>
     *     <li>Uses {@link SwingUtilities#invokeAndWait} to safely set up the GUI on the Event Dispatch Thread (EDT).</li>
     *     <li>Starts the background threads managed by the {@code Manager} instance.</li>
     *     <li>Sets up a timer to periodically refresh the GUI by invoking the {@code Manager}'s refresh method.</li>
     * </ul>
     */
    void runRunner() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                resourceFrame.setupAndStartGUI();
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }

        manager.initiateAndStartThreads();

        Timer resourceCheckTimer = new Timer(AppConfig.EDT_REFRESH_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                manager.refreshGUI(resourceFrame);
            }
        });
        resourceCheckTimer.start();
    }
}
