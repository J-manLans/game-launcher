package com.dt181g.laboration_2;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code ResourceFrame} class is responsible for representing the graphical user interface (GUI)
 * for monitoring and managing the resources in the application. This class provides a visual layout
 * that displays producer, consumer, and resource information using three main panels: left, center, and right.
 *
 * <p>
 * The left panel displays information about producers, including their current count.
 * The center panel includes a visual representation of the resources using a circle.
 * The right panel displays information about consumers and their current count.
 * </p>
 *
 * <p>
 * The {@code ResourceFrame} also contains methods to update the GUI dynamically as the resource states change,
 * ensuring that the display remains current with the latest producer, consumer, and resource pool information.
 * </p>
 */
class ResourceFrame extends JFrame{
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

    private int oldPoolSize = AppConfig.STARTING_RESOURCES;

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
        // Left panel
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
            Integer.toString(AppConfig.STARTING_PRODUCERS),
            AppConfig.SIDE_LABEL_FONT_SIZE,
            AppConfig.ORANGE
        );
        this.leftPanel.setBackground(AppConfig.DARK_GRAY);

        // Center panel
        centerPanel.drawCircle(this.oldPoolSize);
        this.setAndCenterLabel(
            this.centerPanel,
            this.resourceLabel,
            Integer.toString(AppConfig.STARTING_RESOURCES),
            AppConfig.CENTER_LABEL_FONT_SIZE,
            AppConfig.ORANGE
        );
        this.centerPanel.setBackground(AppConfig.GRAY);

        // Right panel
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
            Integer.toString(AppConfig.STARTING_CONSUMERS),
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
     * Checks if the resource pool size has changed by comparing its current value to the old one.
     * If so it redraws the GUI components to display the current
     * counts of producers, consumers, and the current resource pool size, as well as
     * the circle representing the size of the resources.
     */
    void reDrawGUI( final int currentPoolSize, final int producers, final int consumers) {
        if (currentPoolSize != this.oldPoolSize) {
            producerCount.setText(Integer.toString(producers));
            resourceLabel.setText(Integer.toString(currentPoolSize));
            centerPanel.drawCircle(currentPoolSize);
            consumerCount.setText(Integer.toString(consumers));
            this.oldPoolSize = currentPoolSize;
        }
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
         * To do this it utilizes {@link #setColor(int)}
         *
         * @param newPoolSize the current size of the resource pool to be represented
         */
        private void drawCircle(final int currentPoolSize) {
            this.circleDiameter = currentPoolSize;
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
            // Pinpoints the start coordinate to the upper left corner of the circle rectangle
            // the circle itself will be painted in the center of the container
            final int x = (getWidth() / 2) - (this.circleDiameter / 2);
            final int y = (getHeight() / 2) - (this.circleDiameter / 2);
            g.setColor(this.circleColor);
            g.fillOval(x, y, this.circleDiameter, this.circleDiameter);
        }
    }
}
