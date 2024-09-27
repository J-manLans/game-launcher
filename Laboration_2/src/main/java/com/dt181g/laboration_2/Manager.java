package com.dt181g.laboration_2;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Manager extends JFrame{
    public static final Manager INSTANCE = new Manager();

    private final JPanel leftPanel = new JPanel();
    private final JLabel producerLabel = new JLabel();
    private final JPanel rightPanel = new JPanel();
    private final JLabel consumerLabel = new JLabel();
    private final JLabel producerCount = new JLabel();
    private final CirclePanel centerPanel = new CirclePanel();
    private final JLabel consumerCount = new JLabel();

    private final LinkedList<Thread> clients = new LinkedList<>();
    private final LinkedList<Thread> waitingProducers = new LinkedList<>();
    private final LinkedList<Thread> waitingConsumers = new LinkedList<>();

    private int producers = 6;
    private int consumers = 5;
    private final int largerQuantity = Math.max(producers, consumers);
    private final ResourcePool resourcePool = ResourcePool.INSTANCE;
    final Producer producer = new Producer(resourcePool);
    final Consumer consumer = new Consumer(resourcePool);
    private final int startingPoolSize = resourcePool.getResources();
    private int currentPoolSize;

    /*=====================
     *  Constructor
     ======================*/
    Manager() {
        for (int i = 1; i <= largerQuantity; i++) {
            if (i <= producers) {
                this.clients.add(new Thread(producer, "Producer"));
                clients.peekLast().start();
            }

            if (i <= consumers) {
                this.clients.add(new Thread(consumer, "Consumer"));
                clients.peekLast().start();
            }
        }
    }

    /*=====================
     *  GUI Methods
     ======================*/
    private void setAndCenterLabel(JPanel panel, JLabel label, String text, int size, Color cText) {
        label.setText(text);;
        label.setFont(new Font("Monospace", Font.BOLD, size));
        label.setForeground(cText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
    }

    void setupAndStartGUI() {
        // ====== Setting up the panels ======

        // Layout settings
        this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        this.rightPanel.setLayout(new BoxLayout(this.rightPanel, BoxLayout.Y_AXIS));

        this.leftPanel.setPreferredSize(new Dimension(200, 400));
        this.centerPanel.setPreferredSize(new Dimension(400, 400));
        this.rightPanel.setPreferredSize(new Dimension(200, 400));

        // Adding components and styling
        this.setAndCenterLabel(this.leftPanel, this.producerLabel, "PRODUCERS", 15, Color.WHITE);
        this.setAndCenterLabel(this.leftPanel, this.producerCount, String.valueOf(this.producers), 15, Color.ORANGE);
        this.leftPanel.setBackground(Color.DARK_GRAY);

        this.centerPanel.setBackground(Color.GRAY);
        centerPanel.drawCircle(this.startingPoolSize);

        this.setAndCenterLabel(this.rightPanel, this.consumerLabel, "CONSUMERS", 15, Color.WHITE);
        this.setAndCenterLabel(this.rightPanel, this.consumerCount, String.valueOf(this.consumers), 15, Color.ORANGE);
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
        private int resourceAmount;
        private Color circleColor;

        private void drawCircle(int resourceAmount) {
            this.resourceAmount = resourceAmount;
            this.circleColor = setColor(resourceAmount);
            repaint();
        }

        /**
         *
         * @param resourceAmount
         * @return
         */
        private Color setColor(int resourceAmount) {
            return switch (resourceAmount / 50) {
                case 2 -> Color.PINK; // 100 to 149 and above
                case 1 -> Color.ORANGE;   // 50 to 99
                case 0 -> Color.YELLOW; // 0 to 49
                default -> Color.RED; // 150 and above
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
            int x = (getWidth() / 2) - (resourceAmount / 2);
            int y = (getHeight() / 2) - (resourceAmount / 2);
            g.setColor(circleColor);
            g.fillOval(x, y, resourceAmount, resourceAmount);
        }
    }

    /*=====================
     *  Resource Methods
     ======================*/
    void refreshGUI() {
        this.currentPoolSize = resourcePool.getResources();
        this.modifyClientsList();
        this.updateClientCount();
        this.reDrawGUI();
    }

    private void modifyClientsList() {
        // Lower bound
        if (this.currentPoolSize < this.startingPoolSize) {
            this.modifyClients(this.producer, "Producer");
        }
        // Upper bound
        if (this.currentPoolSize > this.startingPoolSize * 4) {
            this.modifyClients(this.consumer, "Consumer");
        }
    }

    private void modifyClients(Runnable client, String typeToAdd) {
        int i = 0;
        boolean switchedClient = false;

        for (Thread thread : clients) {
            if (!thread.getName().equals(typeToAdd)) {
                thread.interrupt();

                switch (thread.getName()) {
                    case "Producer" -> this.waitingProducers.add(this.clients.get(i));
                    case "Consumer" -> this.waitingConsumers.add(this.clients.get(i));
                };

                this.clients.remove(thread);
                break;
            }
            i += 1;
        }

        if (clients.size() < 11) {

            switch(typeToAdd) {
                case "Producer" -> {
                    if (waitingProducers.size() > 0) {
                        this.clients.add(this.waitingProducers.getFirst());
                        this.waitingProducers.peekFirst().interrupt();
                        this.waitingProducers.removeFirst();
                        switchedClient = true;
                    }
                } case "Consumer" -> {
                    if (waitingConsumers.size() > 0) {
                        this.clients.add(this.waitingConsumers.getFirst());
                        this.waitingConsumers.peekFirst().interrupt();
                        this.waitingConsumers.removeFirst();
                        switchedClient = true;
                    }
                }
            }

            System.out.println("consumers: " + waitingConsumers.size() + " producers: " + waitingProducers.size() + " clients: " + clients.size() + "\n");

            if (!switchedClient) {
                this.clients.add(new Thread(client, typeToAdd));
                this.clients.peekLast().start();
             } else {
                switchedClient = false;
             }

        }
    }

    private void updateClientCount() {
        this.producers = 0;
        this.consumers = 0;

        for (Thread thread : clients) {
            if (thread.getName().equals("Producer")) {
                this.producers += 1;
            } else {
                this.consumers += 1;
            }
        }
    }

    private void reDrawGUI() {
        this.producerCount.setText(String.valueOf(this.producers));
        centerPanel.drawCircle(this.currentPoolSize);
        this.consumerCount.setText(String.valueOf(this.consumers));
    }
}
