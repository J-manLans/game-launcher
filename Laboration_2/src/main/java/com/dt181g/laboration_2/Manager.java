package com.dt181g.laboration_2;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayDeque;

/**
 * The Manager class represents the main GUI component for managing producers and consumers
 * in a resource pool system. This class extends {@link JFrame} and is responsible for
 * creating and managing the user interface, as well as controlling the lifecycle of
 * producer and consumer threads.
 * <p>
 * The Manager maintains the current state of the resource pool and updates the GUI
 * to reflect changes in resource availability. It employs separate panels for producers,
 * consumers, and a central display for resources, facilitating real-time visualization of
 * the resource pool's state.
 * </p>
 *
 * @author Joel Lansgren
 */
public class Manager extends JFrame {
    public static final Manager INSTANCE = new Manager();

    // Left panel
    private final JPanel leftPanel = new JPanel();
    private final JLabel producerLabel = new JLabel();
    private final JLabel producerCount = new JLabel();
    // Center panel
    private final CirclePanel centerPanel = new CirclePanel();
    private final JLabel resourceLabel = new JLabel();
    // Right panel
    private final JPanel rightPanel = new JPanel();
    private final JLabel consumerLabel = new JLabel();
    private final JLabel consumerCount = new JLabel();
    // Thread lists
    private final ArrayDeque<Thread> activeProducers = new ArrayDeque<>();
    private final ArrayDeque<Thread> activeConsumers = new ArrayDeque<>();
    private final ArrayDeque<Thread> waitingProducers = new ArrayDeque<>();
    private final ArrayDeque<Thread> waitingConsumers = new ArrayDeque<>();

    private int producers = AppConfig.STARTING_PRODUCERS;  // These are the initial clients for the setup
    private int consumers = AppConfig.STARTING_CONSUMERS;  // They will be modified continuously to update the GUI
    private final ResourcePool resourcePool = ResourcePool.INSTANCE;
    private final int startingPoolSize = resourcePool.pollForResource();
    private int tempPoolSize = startingPoolSize;
    private int currentPoolSize;
    private final Runnable producer = new Producer(resourcePool);
    private final Runnable consumer = new Consumer(resourcePool);

    /*=====================
     *  Constructor
     ======================*/
     /**
     * Constructs a Manager instance, initializing producer and consumer threads
     * based on the specified limits for the number of producers and consumers.
     */
    Manager() {
        final int largerQuantity = Math.max(producers, consumers);

        for (int i = 1; i <= largerQuantity; i++) {
            if (i <= producers) {
                this.activeProducers.add(new Thread(producer, AppConfig.PRODUCER));
            }

            if (i <= consumers) {
                this.activeConsumers.add(new Thread(consumer, AppConfig.CONSUMER));
            }
        }
    }

    /*=====================
     *  GUI Methods
     ======================*/
    /**
     * Sets up and configures the GUI components and layout.
     * This method initializes the various panels, labels, and their
     * properties, and adds them to the main frame.
     */
    void setupAndStartGUI() {
        // ====== Setting up the panels ======

        // Layout settings
        this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        this.rightPanel.setLayout(new BoxLayout(this.rightPanel, BoxLayout.Y_AXIS));

        this.leftPanel.setPreferredSize(new Dimension(AppConfig.SIDE_PANEL_WIDTH, AppConfig.PANEL_HEIGHT));
        this.centerPanel.setPreferredSize(new Dimension(AppConfig.CENTER_PANEL_WIDTH, AppConfig.PANEL_HEIGHT));
        this.rightPanel.setPreferredSize(new Dimension(AppConfig.SIDE_PANEL_WIDTH, AppConfig.PANEL_HEIGHT));

        // Adding components and styling
        this.setAndCenterLabel(
            this.leftPanel,
            this.producerLabel,
            AppConfig.PRODUCERS_LABEL,
            AppConfig.SIDE_LABEL_FONT_SIZE,
            AppConfig.WHITE
        );
        this.setAndCenterLabel(
            this.leftPanel,
            this.producerCount,
            Integer.toString(this.producers),
            AppConfig.SIDE_LABEL_FONT_SIZE,
            AppConfig.ORANGE
        );
        this.leftPanel.setBackground(AppConfig.DARK_GRAY);

        centerPanel.drawCircle(this.startingPoolSize);
        this.setAndCenterLabel(
            this.centerPanel,
            this.resourceLabel,
            Integer.toString(this.startingPoolSize),
            AppConfig.CENTER_LABEL_FONT_SIZE,
            AppConfig.ORANGE
        );
        this.centerPanel.setBackground(AppConfig.GRAY);

        this.setAndCenterLabel(
            this.rightPanel,
            this.consumerLabel,
            AppConfig.CONSUMERS_LABEL,
            AppConfig.SIDE_LABEL_FONT_SIZE,
            AppConfig.WHITE
        );
        this.setAndCenterLabel(
            this.rightPanel,
            this.consumerCount,
            Integer.toString(this.consumers),
            AppConfig.SIDE_LABEL_FONT_SIZE,
            AppConfig.ORANGE
        );
        this.rightPanel.setBackground(AppConfig.DARK_GRAY);

        // ====== Setting up the frame ======

        this.setLayout(new BorderLayout());
        this.add(this.leftPanel, BorderLayout.WEST);
        this.add(this.centerPanel, BorderLayout.CENTER);
        this.add(this.rightPanel, BorderLayout.EAST);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
    }

    /**
     * Sets and centers a JLabel within a specified JPanel with given properties.
     *
     * @param panel the JPanel in which the label will be added
     * @param label the JLabel to configure
     * @param labelText the text to display on the label
     * @param fontSize the size of the font for the label
     * @param textColor the color of the text for the label
     */
    private void setAndCenterLabel(
        final JPanel panel,
        final JLabel label,
        final String labelText,
        final int fontSize,
        final Color textColor
    ) {
        label.setText(labelText);
        label.setFont(new Font("Monospace", Font.BOLD, fontSize));
        label.setForeground(textColor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
    }

    /**
     * The CirclePanel class is a custom JPanel that visually represents the current
     * size of the resource pool by drawing a circle. The color and size of the circle
     * are determined by the amount of resources available in the pool. This class is
     * used within the Manager to provide a visual indication of resource levels.
     * <p>
     * The circle's size and color change dynamically based on the current resource
     * amount, making it easier for users to understand the state of the resource
     * pool at a glance.
     * </p>
     *
     * @author Joel Lansgren
     */
    private class CirclePanel extends JPanel {
        private int circleDiameter;
        private Color circleColor;

        /**
         * Updates the current pool size and redraws the circle with the new size and color.
         * To do this it utilizes {@link Manager.CirclePanel#setColor(int)}
         *
         * @param newPoolSize the current size of the resource pool to be represented
         */
        private void drawCircle(final int newPoolSize) {
            this.circleDiameter = newPoolSize;
            this.circleColor = setColor();
            repaint();
        }

        /**
         * Determines the color of the circle based on the current pool size.
         *
         * @return the Color to be used for the circle
         */
        private Color setColor() {
            return switch (this.circleDiameter / AppConfig.STARTING_RESOURCES) {
                case AppConfig.THRESHOLD_HIGH -> AppConfig.PINK; // 100 to 149
                case AppConfig.THRESHOLD_MID -> AppConfig.ORANGE;   // 50 to 99
                case AppConfig.THRESHOLD_LOW -> AppConfig.WHITE; // 0 to 49
                default -> AppConfig.DARK_GRAY;  // below 0 or 150 and above
            };
        }

        /**
         * Paints the component by clearing the previous content by calling the superclass's method.
         * This ensures a clean drawing context. Then, it renders a circle
         * representing the current resource amount.This method is automatically
         * called by the Swing framework whenever the component needs to be redrawn.
         *
         * @param g the {@link Graphics} context used for drawing the circle; provided by the Swing framework.
         */
        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            int x = (getWidth() / 2) - (this.circleDiameter / 2);
            int y = (getHeight() / 2) - (this.circleDiameter / 2);
            g.setColor(circleColor);
            g.fillOval(x, y, this.circleDiameter, this.circleDiameter);
        }
    }

    /*=====================
     *  Resource Methods
     ======================*/
     /**
     * Starts all threads that are managing the producers and consumers.
     * Each thread is initiated to begin its execution, allowing producers
     * and consumers to operate concurrently.
     */
    void startThreads() {
        for (Thread thread : this.activeProducers) {
            thread.start();
        }
        for (Thread thread : this.activeConsumers) {
            thread.start();
        }
    }

    /**
     * This method needs to be executed on the EDT since it refreshes the GUI to reflect the current state
     * of the resource pool. It checks if the resource pool size has changed,
     * and if so, updates the clients and redraws the GUI components accordingly.
     */
    void refreshGUI() {
        this.currentPoolSize = resourcePool.pollForResource();
        if (this.currentPoolSize != this.tempPoolSize) {
            this.modifyClients();
            this.updateClientCount();
            this.reDrawGUI();
        }
        this.tempPoolSize = this.currentPoolSize;
    }

    /**
     * Modifies the clients based on the current pool size. If the resource pool size
     * is below 50, it may remove a consumer and add a producer;
     * if it is 200 or above, it may remove a producer and add a new consumer.
     */
    private void modifyClients() {
        switch (this.currentPoolSize / AppConfig.STARTING_RESOURCES) {
            case AppConfig.THRESHOLD_LOW -> this.modifyClientLists(
                this.activeConsumers,
                this.waitingConsumers,
                this.activeProducers,
                this.waitingProducers,
                this.producer,
                AppConfig.PRODUCER
            );
            case AppConfig.THRESHOLD_MAX -> this.modifyClientLists(
                this.activeProducers,
                this.waitingProducers,
                this.activeConsumers,
                this.waitingConsumers,
                this.consumer,
                AppConfig.CONSUMER
            );
            default -> { } // No action needed for this value since it is inside the interval.
        }
    }

    /**
     * Modifies the client thread lists by removing and adding threads based on the current
     * resource availability. It interrupts a specified active client thread,
     * places it into the waiting list, and either starts a waiting client thread
     * if available or creates a new one. This approach allows for dynamic management
     * of active client threads, ensuring that the maximum limit is respected.
     *
     * @param removeActiveList the list of active clients where a thread is to be removed
     * @param removeWaitingList the list where interrupted clients are placed
     * @param addActiveList the list of active clients where a thread is to be added
     * @param removeWaitingList the list from which clients are drawn when adding new ones if it is not empty
     * @param newClient the Runnable instance of the client to be added (producer or consumer)
     * @param clientName the name of the client thread to be created
     */
    private void modifyClientLists(
        final ArrayDeque<Thread> removeActiveList,
        final ArrayDeque<Thread> addWaitingList,
        final ArrayDeque<Thread> addActiveList,
        final ArrayDeque<Thread> removeWaitingList,
        final Runnable newClient,
        final String clientName
    ) {
        boolean switchedClient = false;

        if (removeActiveList.size() > 0) {
            // Make the client enter a wait state
            removeActiveList.peekFirst().interrupt();
            // Moves client from its active to its waiting list
            addWaitingList.add(removeActiveList.pollFirst());
        }

        if (addActiveList.size() < AppConfig.MAX_ACTIVE_CLIENTS) {
            if (removeWaitingList.size() > 0) {
                // Moves opposite client from its waiting to its active list
                addActiveList.add(removeWaitingList.pollFirst());
                // Restarts client
                addActiveList.peekLast().interrupt();
                switchedClient = true;
            }

            // Adds new client thread and starts it up if no clients have
            // been switched from the waiting list to the client list
            if (!switchedClient) {
                addActiveList.add(new Thread(newClient, clientName));
                addActiveList.peekLast().start();
            }
        }
    }

    /**
     * Updates the count of active producers and consumers based on the current
     * name of the client threads. This method iterates through all client threads
     * and increments the respective counts.
     */
    private void updateClientCount() {
        this.producers = this.activeProducers.size();
        this.consumers = this.activeConsumers.size();
    }

    /**
     * Redraws the graphical user interface (GUI) components to display the current
     * counts of producers, consumers, and the current resource pool size, as well as
     * the circle representing the size of the resources.
     */
    private void reDrawGUI() {
        this.producerCount.setText(Integer.toString(this.producers));
        this.resourceLabel.setText(Integer.toString(this.currentPoolSize));
        centerPanel.drawCircle(this.currentPoolSize);
        this.consumerCount.setText(Integer.toString(this.consumers));
    }
}
