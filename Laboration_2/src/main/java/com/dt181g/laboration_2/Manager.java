package com.dt181g.laboration_2;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Manager extends JFrame{
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
    private final LinkedList<Thread> clients = new LinkedList<>();
    private final LinkedList<Thread> waitingProducers = new LinkedList<>();
    private final LinkedList<Thread> waitingConsumers = new LinkedList<>();

    private int producers = 6;  // These are the initial clients for the setup
    private int consumers = 5;  // They will be modified continuously to update the GUI
    private final ResourcePool resourcePool = ResourcePool.INSTANCE;
    private final int startingPoolSize = resourcePool.getResources();
    private int currentPoolSize;
    private final Runnable producer = new Producer(resourcePool);
    private final Runnable consumer = new Consumer(resourcePool);

    /*=====================
     *  Constructor
     ======================*/
    Manager() {
        final int largerQuantity = Math.max(producers, consumers);

        for (int i = 1; i <= largerQuantity; i++) {
            if (i <= producers) {
                this.clients.add(new Thread(producer, "Producer"));
            }

            if (i <= consumers) {
                this.clients.add(new Thread(consumer, "Consumer"));
            }
        }
    }

    /*=====================
     *  GUI Methods
     ======================*/
    private void setAndCenterLabel(JPanel panel, JLabel label, String labelText, int fontSize, Color textColor) {
        label.setText(labelText);
        label.setFont(new Font("Monospace", Font.BOLD, fontSize));
        label.setForeground(textColor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
    }

    void setupAndStartGUI() {
        // ====== Setting up the panels ======
        int sidePanelFontSize = 15;
        int centerPanelFontSize = 25;
        int size1 = 200;
        int size2 = 400;

        // Layout settings
        this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        this.rightPanel.setLayout(new BoxLayout(this.rightPanel, BoxLayout.Y_AXIS));

        this.leftPanel.setPreferredSize(new Dimension(size1, size2));
        this.centerPanel.setPreferredSize(new Dimension(size2, size2));
        this.rightPanel.setPreferredSize(new Dimension(size1, size2));

        // Adding components and styling
        this.setAndCenterLabel(this.leftPanel, this.producerLabel, "PRODUCERS", sidePanelFontSize, Color.WHITE);
        this.setAndCenterLabel(this.leftPanel, this.producerCount, Integer.toString(this.producers), sidePanelFontSize, Color.ORANGE);
        this.leftPanel.setBackground(Color.DARK_GRAY);

        centerPanel.drawCircle(this.startingPoolSize);
        this.setAndCenterLabel(this.centerPanel, this.resourceLabel, Integer.toString(resourcePool.getResources()), centerPanelFontSize, Color.ORANGE);
        this.centerPanel.setBackground(Color.GRAY);

        this.setAndCenterLabel(this.rightPanel, this.consumerLabel, "CONSUMERS", sidePanelFontSize, Color.WHITE);
        this.setAndCenterLabel(this.rightPanel, this.consumerCount, Integer.toString(this.consumers), sidePanelFontSize, Color.ORANGE);
        this.rightPanel.setBackground(Color.DARK_GRAY);

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
     *
     * @author Joel Lansgren
     */
    private class CirclePanel extends JPanel {
        private int currentPoolSize;
        private Color circleColor;

        private void drawCircle(int currentPoolSize) {
            this.currentPoolSize = currentPoolSize;
            this.circleColor = setColor(currentPoolSize);
            repaint();
        }

        /**
         *
         * @param resourceAmount
         * @return
         */
        private Color setColor(int currentPoolSize) {
            return switch (currentPoolSize / 50) {
                case 2 -> Color.PINK; // 100 to 149
                case 1 -> Color.ORANGE;   // 50 to 99
                case 0 -> Color.WHITE; // 0 to 49
                default -> Color.DARK_GRAY; // 150 and above
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
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x = (getWidth() / 2) - (currentPoolSize / 2);
            int y = (getHeight() / 2) - (currentPoolSize / 2);
            g.setColor(circleColor);
            g.fillOval(x, y, currentPoolSize, currentPoolSize);
        }
    }

    /*=====================
     *  Resource Methods
     ======================*/
    void startThreads () {
        for (Thread thread : clients) {
            thread.start();
        }
    }

    void refreshGUI() {
        this.currentPoolSize = resourcePool.getResources();
        this.modifyClients();
        this.updateClientCount();
        this.reDrawGUI();
    }

    private void modifyClients() {
        switch (this.currentPoolSize / 50) {
            case 0 -> this.modifyClientLists(this.producer, "Producer");  // Below 50
            case 4 -> this.modifyClientLists(this.consumer, "Consumer");  // 200 and above
        }
    }

    private void modifyClientLists(Runnable client, String typeToAdd) {
        int i = 0;
        boolean switchedClient = false;

        for (Thread thread : clients) {
            if (!thread.getName().equals(typeToAdd)) {
                thread.interrupt();  // Make the client enter a wait state

                switch (thread.getName()) {  // Adds client to correct waiting list
                    case "Producer" -> this.waitingProducers.add(this.clients.get(i));
                    case "Consumer" -> this.waitingConsumers.add(this.clients.get(i));
                }

                this.clients.remove(i);  // Removes client from client list
                break;
            }
            i++;
        }

        if (clients.size() < 11) {
            switch(typeToAdd) {
                case "Producer" -> {
                    if (waitingProducers.size() > 0) {
                        this.clients.add(this.waitingProducers.getFirst());  // Adds producer to client list
                        this.waitingProducers.peekFirst().interrupt();  // Restarts the producer
                        this.waitingProducers.removeFirst();  // Removes the producer from waiting list
                        switchedClient = true;
                    }
                } case "Consumer" -> {
                    if (waitingConsumers.size() > 0) {
                        this.clients.add(this.waitingConsumers.getFirst());  // Adds consumer to client list
                        this.waitingConsumers.peekFirst().interrupt();  // Restarts the consumer
                        this.waitingConsumers.removeFirst();  // Removes the consumer from waiting list
                        switchedClient = true;
                    }
                }
            }

            // Adds new client thread and starts it up if no clients have been switched
            if (!switchedClient) {
                this.clients.add(new Thread(client, typeToAdd));
                this.clients.peekLast().start();
            } else {
                switchedClient = false;
            }

            System.out.printf("Waiting Producers: %d | Waiting Consumers; %d | Total in Wait: %d | Active Clients: %d\n",
                waitingProducers.size(),
                waitingConsumers.size(),
                waitingProducers.size() + waitingConsumers.size(),
                clients.size()
            );
        }
    }

    private void updateClientCount() {
        this.producers = this.consumers = 0;

        for (Thread thread : clients) {
            switch (thread.getName()) {
                case "Producer" -> this.producers++;
                case "Consumer" -> this.consumers++;
            };
        }
    }

    private void reDrawGUI() {
        this.producerCount.setText(Integer.toString(this.producers));
        this.resourceLabel.setText(Integer.toString(this.resourcePool.getResources()));
        centerPanel.drawCircle(this.currentPoolSize);
        this.consumerCount.setText(Integer.toString(this.consumers));
    }
}
