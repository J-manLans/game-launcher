package com.dt181g.laboration_2;

/**
 * Represents a producer in the resource pool system.
 * This class extends the {@link Client} abstract class and is responsible
 * for producing resources and adding them to the shared {@link ResourcePool}.
 * <p>
 * The producer generates a random amount of resources within a specified range,
 * and periodically sleeps for a random duration between resource productions.
 * </p>
 * <p>
 * Upon interruption, the producer will sleep until it is interrupted again, ensuring
 * that it does not consume CPU resources unnecessarily when inactive.
 * </p>
 *
 * @author Joel Lansgren
 */
class Producer extends Client{
    Producer() {
        super(AppConfig.PRODUCER_MAX_ADD, AppConfig.PRODUCER_OPERATION);
    }
}
