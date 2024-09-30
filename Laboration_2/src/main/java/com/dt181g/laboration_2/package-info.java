/**
 * Core functionality for the Laboration 2 application,
 * simulating a resource pool system with producers and consumers.
 *
 * <p>
 * Classes in this package:
 * <ul>
 *     <li>{@link Lab2} - Main entry point and GUI initialization.</li>
 *     <li>{@link Manager} - Manages producers and consumers in the GUI.</li>
 *     <li>{@link Producer} - Generates resources for the pool.</li>
 *     <li>{@link Consumer} - Consumes resources from the pool.</li>
 *     <li>{@link ResourcePool} - Thread-safe resource management.</li>
 *     <li>{@link CirclePanel} - Visual representation of the resource pool.</li>
 *     <li>{@link AppConfig} - Configuration settings for the application.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The application uses Swing for the GUI, with a timer for real-time updates
 * and employs the Singleton pattern for resource management.
 * </p>
 *
 * @author Joel Lansgren
 * @version 1.0
 * @since 2023-09-29
 */
package com.dt181g.laboration_2;
