package com.dt181g.laboration_3.controller.common;

/**
 * Base interface for controllers in the MVC architecture of the application.
 * <p>
 * This interface defines the essential operations that any controller must implement.
 * It is designed to ensure that controllers can be initialized properly, setting up
 * any necessary components before they are used.
 * </p>
 *
 * @author Joel Lansgren
 */
public interface IController {
    /**
     * Initializes the controller, setting up any necessary components
     * and preparing it for use.
     */
    void initialize();
}
