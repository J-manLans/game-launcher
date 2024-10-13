/**
 * Core functionality for the Laboration 2 application,
 * simulating a resource pool system with producers and consumers.
 *
 * <p>
 * Classes in this package:
 * </p>
 * <ul>
 *     <li> Lab2 - Main entry point and GUI initialization.</li>
 *     <li> Manager - Manages producers and consumers in the GUI.</li>
 *     <li> Producer - Generates resources for the pool.</li>
 *     <li> Consumer - Consumes resources from the pool.</li>
 *     <li> ResourcePool - Thread-safe resource management.</li>
 *     <li> CirclePanel - Visual representation of the resource pool.</li>
 *     <li> AppConfig - Configuration settings for the application.</li>
 * </ul>
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
