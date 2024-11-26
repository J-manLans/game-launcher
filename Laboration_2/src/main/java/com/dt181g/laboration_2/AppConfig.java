package com.dt181g.laboration_2;

import java.awt.Color;

/**
 * The AppConfig class provides a collection of constants used throughout
 * the producer-consumer application. These include settings for resource
 * management, GUI layout, and color schemes.
 * The class is final and cannot be instantiated, as all constants are static.
 *
 * @author Joel Lansgren
 */
public final class AppConfig {
    // make sure the class cannot be instantiated.
    private AppConfig() { throw new IllegalStateException("Utility class"); }

    /*=================================
     * Resource Pool Settings
    =================================*/

    /** The initial size of the resource pool. */
    public static final int STARTING_RESOURCES = 50;

    /** The minimum bound for the resource pool. */
    public static final int MIN_RESOURCE_BOUND = 0;

    /** The maximum bound for the resource pool. */
    public static final int MAX_RESOURCE_BOUND = 200;

    /*=================================
     * Producer/Consumer Settings
    =================================*/

    /** Name of the producers. */
    public static final String PRODUCER = "Producer";

    /** Name of the consumers. */
    public static final String CONSUMER = "Consumer";

    /** Starting number of producers. */
    public static final int STARTING_PRODUCERS = 6;

    /** Starting number of consumers. */
    public static final int STARTING_CONSUMERS = 5;

    /** Maximum allowed number of active clients. */
    public static final int MAX_ACTIVE_CLIENTS = 11;

    /** The minimum resources the clients can try to add. */
    public static final int CLIENT_MIN_ADD = 1;

    /** The max amount of resources the producers can add. */
    public static final int PRODUCER_MAX_ADD = 10;

    /** The operation the producer (addition). */
    public static final int PRODUCER_OPERATION = 1;

    /** The max amount of resources the consumers can add. */
    public static final int CONSUMER_MAX_ADD = 20;

    /** The operation the consumer (subtraction). */
    public static final int CONSUMER_OPERATION = -1;

    /** Minimum time a client can sleep in between adding resources. */
    public static final long CLIENTS_MIN_SLEEP = 1000L;

    /** Maximum time a client can sleep in between adding resources. */
    public static final long CLIENTS_MAX_SLEEP = 5000L;

    /*=================================
     * GUI Settings
    =================================*/

    // --- Panel Setup

    /** The width of the side panels. */
    public static final int SIDE_PANEL_WIDTH = 200;

    /** The width of the center panel. */
    public static final int CENTER_PANEL_WIDTH = 400;

    /** The height of the panels. */
    public static final int PANEL_HEIGHT = 400;

    // --- Font Settings

    /** Label text for the producers. */
    public final static String PRODUCERS_LABEL = "PRODUCERS";

    /** Label text for the consumers. */
    public final static String CONSUMERS_LABEL = "CONSUMERS";

    /** Label font size for the side panels. */
    public static final int SIDE_LABEL_FONT_SIZE = 15;

    /** Label font size for the center panel. */
    public static final int CENTER_LABEL_FONT_SIZE = 25;

    // --- Thresholds used for different switch cases that affects the GUI as well as active amounts of clients

    /** 0 - 49. */
    public static final int THRESHOLD_LOW = 0;

    /** 50 - 99. */
    public static final int THRESHOLD_MID = 1;

    /** 100 - 149. */
    public static final int THRESHOLD_HIGH = 2;

    /** 150 - 199. */
    public static final int THRESHOLD_MAX = 3;

    // --- EDT Settings

    /** The delay in milliseconds for the action listener that executes code on the EDT. */
    public static final int EDT_REFRESH_DELAY = 150;

    /*=================================
    * Color scheme
    =================================*/

    public static final Color WHITE = Color.WHITE;
    public static final Color ORANGE = Color.ORANGE;
    public static final Color DARK_GRAY = Color.DARK_GRAY;
    public static final Color GRAY = Color.GRAY;
    public static final Color PINK = Color.PINK;
}
