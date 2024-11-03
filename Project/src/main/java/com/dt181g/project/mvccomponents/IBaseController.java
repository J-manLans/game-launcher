package com.dt181g.project.mvccomponents;

/**
 * Base interface for all controllers in the application.
 * <p>
 * This interface defines the basic structure for controllers, which manage interactions
 * between the model and the view. Implementing classes should provide their own
 * initialization logic through the {@link #initialize()} method.
 * </p>
 */
public interface IBaseController {
    /**
     * Initializes the controller, setting up any necessary components
     * and preparing it for use.
     */
    void initialize();
}
