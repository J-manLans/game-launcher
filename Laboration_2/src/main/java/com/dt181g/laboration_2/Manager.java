package com.dt181g.laboration_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class Manager extends JFrame{
    public static final Manager INSTANCE = new Manager();

    private JLabel producerCount = new JLabel();
    private JLabel consumerCount = new JLabel();
    private JLabel centerLabel = new JLabel();

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
        JPanel leftPanel = new JPanel();
        JLabel producerLabel = new JLabel();
        JPanel centerPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JLabel consumerLabel = new JLabel();

        // Layout settings
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        leftPanel.setPreferredSize(new Dimension(200, 400));
        centerPanel.setPreferredSize(new Dimension(400, 400));
        rightPanel.setPreferredSize(new Dimension(200, 400));

        // Adding components and styling
        setAndCenterLabel(leftPanel, producerLabel, "PRODUCERS", 15, Color.WHITE);
        setAndCenterLabel(leftPanel, producerCount, String.valueOf(this.initialProducers), 15, Color.WHITE);
        leftPanel.setBackground(Color.DARK_GRAY);

        centerPanel.add(Box.createVerticalGlue());
        setAndCenterLabel(centerPanel, this.centerLabel, "O", 50, Color.DARK_GRAY);
        centerPanel.setBackground(Color.ORANGE);
        centerPanel.add(Box.createVerticalGlue());

        setAndCenterLabel(rightPanel, consumerLabel, "CONSUMERS", 15, Color.WHITE);
        setAndCenterLabel(rightPanel, this.consumerCount, String.valueOf(this.initialConsumers), 15, Color.WHITE);
        rightPanel.setBackground(Color.DARK_GRAY);

        // ====== Setting up the frame ======

        this.setLayout(new BorderLayout());
        this.add(leftPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /*=====================
     *  Resource Methods
     ======================*/
    void startResourceChecking() {
        this.resourceCheckTimer = new Timer(150, new ActionListener() {
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
        } else {
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
        this.centerLabel.setFont(new Font("Monospace", Font.BOLD, currentPoolSize));
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

    synchronized void shutdown() {
        for (Thread thread : clients) {
            thread.interrupt();
        }

        for (Thread thread : clients) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        clients.clear();
    }
}
