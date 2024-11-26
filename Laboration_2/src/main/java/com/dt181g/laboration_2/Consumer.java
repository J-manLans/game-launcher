package com.dt181g.laboration_2;

/**
 * Represents a consumer in the resource pool system.
 * This class extends the {@link Client} abstract class and is responsible
 * for consuming resources from the shared {@link ResourcePool}.
 * <p>
 * The consumer generates a random amount of resources within a specified range,
 * and periodically sleeps for a random duration between consumption cycles.
 * </p>
 * <p>
 * Upon interruption, the consumer will sleep until it is interrupted again, ensuring
 * that it does not consume CPU resources unnecessarily when inactive.
 * </p>
 *
 * @author Joel Lansgren
 */
class Consumer extends Client{
    Consumer() {
        super(AppConfig.CONSUMER_MAX_ADD, AppConfig.CONSUMER_OPERATION);
    }
}
