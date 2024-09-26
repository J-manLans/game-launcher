package com.dt181g.laboration_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Timer resourceCheckTimer;
    private final int initialProducers = 6;
    private final int initialConsumers = 5;
    final int[] clientCounter = new int[2];
    private final int largerQuantity = Math.max(initialProducers, initialConsumers);
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
            if (i <= initialProducers) {
                this.clients.add(new Thread(producer, "Producer"));
                clients.peekLast().start();
            }

            if (i <= initialConsumers) {
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

    void setupGUI() {
        // ====== Setting up the panels ======

        // Layout settings
        this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        this.rightPanel.setLayout(new BoxLayout(this.rightPanel, BoxLayout.Y_AXIS));

        this.leftPanel.setPreferredSize(new Dimension(200, 400));
        this.centerPanel.setPreferredSize(new Dimension(400, 400));
        this.rightPanel.setPreferredSize(new Dimension(200, 400));

        // Adding components and styling
        this.setAndCenterLabel(this.leftPanel, this.producerLabel, "PRODUCERS", 15, Color.WHITE);
        this.setAndCenterLabel(this.leftPanel, this.producerCount, String.valueOf(this.initialProducers), 15, Color.ORANGE);
        this.leftPanel.setBackground(Color.DARK_GRAY);

        this.centerPanel.setBackground(Color.GRAY);
        centerPanel.drawCircle(this.startingPoolSize);

        this.setAndCenterLabel(this.rightPanel, this.consumerLabel, "CONSUMERS", 15, Color.WHITE);
        this.setAndCenterLabel(this.rightPanel, this.consumerCount, String.valueOf(this.initialConsumers), 15, Color.ORANGE);
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
    void startResourceChecking() {
        this.resourceCheckTimer = new Timer(150, new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkResources();
            }
        });
        this.resourceCheckTimer.start();
    }

    private void checkResources() {
        this.currentPoolSize = resourcePool.getResources();
        clientCounter[0] = 0;
        clientCounter[1] = 0;

        if (currentPoolSize < startingPoolSize) {
            this.modifyClients(producer, "Producer");
        }

        if (currentPoolSize > startingPoolSize * 4) {
            this.modifyClients(consumer, "Consumer");
        }

        for (Thread thread : clients) {
            if (thread.getName().equals("Producer")) {
                clientCounter[0] += 1;
            } else {
                clientCounter[1] += 1;
            }
        }

        this.producerCount.setText(String.valueOf(clientCounter[0]));
        centerPanel.drawCircle(currentPoolSize);
        this.consumerCount.setText(String.valueOf(clientCounter[1]));
    }

    private void modifyClients(Runnable client, String type) {
        for (Thread thread : clients) {
            if (!thread.getName().equals(type)) {
                thread.interrupt();
                this.clients.remove(thread);
                break;
            }
        }

        if (clients.size() < initialConsumers + initialProducers) {
            this.clients.add(new Thread(client, type));
            this.clients.peekLast().start();
        }
    }
}
