package com.dt181g.project.mvccomponents.games.snake.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.dt181g.project.support.AppConfigProject;

/**
 * Enum representing the singleton instance of the Snake Booster Manager.
 * This class handles the management of snake boosters in the game,
 * including spawning, updating, and resetting boosters as well as
 * their effects on the snake.
 *
 * @author Joel Lansgren
 */
enum ManagerSnakeBooster {
    INSTANCE;

    private final List<ISnakeBoostersModel> boosters = new ArrayList<>();
    private int[][] gameGrid;
    private final Random randomizer = new Random();
    private int currentBoosterModelIndex;
    private boolean isBoosterSpawnAvailable;
    private int spawnCountDown;
    private int[][] currentBooster;
    private double boosterEffectDuration;

    /**
     * Initializes the booster manager with the specified game grid.
     * Sets the availability of boosters and initializes the spawn countdown.
     *
     * @param gameGrid The current state of the game grid.
     */
    void initializeBoosterManager(int[][] gameGrid, SnakeModel snakeModel) {
        this.gameGrid = gameGrid;
        this.isBoosterSpawnAvailable = true;

        // Resets all booster effects
        for (ISnakeBoostersModel booster : this.boosters) {
            if (booster instanceof IBoosterEffect) {
                ((IBoosterEffect)booster).resetBoosterEffect(snakeModel);
                snakeModel.setSpeed(AppConfigProject.SNAKE_TICK_DELAY);
            }
        }

        this.currentBooster = null;
        this.currentBoosterModelIndex = AppConfigProject.CHERRY_INDEX;
        this.spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    }

    /**
     * Consumes a booster and resets its state in the game grid, applying its effect to the snake.
     * <p>
     * This method identifies the booster by its color and applies its effect to the given snake model.
     * After applying the effect, it resets the booster by setting its color to the background color,
     * making it available for re-spawning in a different cell. It also resets the spawn countdown for the next booster.
     * </p>
     * @param snakeModel   The snake that the booster effect will be applied to.
     * @param boosterColor The color of the booster to consume, used to locate the correct booster in the list.
     */
    void eatAndResetBooster(SnakeModel snakeModel, int boosterColor) {
        for (ISnakeBoostersModel boosterModel : boosters) {
            if (boosterModel.getBooster()[0][2] == boosterColor) {
                // Sets the color of the old booster to background color, effectively
                // devouring it from the grid since the snake interact with it's color,
                // not coordinates.
                boosterModel.getBooster()[0][2] = 0;
                boosterModel.applyBoosterEffect(snakeModel);
            }
        }
        // Allows the booster to be spawned on a different cell
        this.isBoosterSpawnAvailable = true;
        // Sets the countdown for allowing the new booster to be spawned
        this.spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    }

    /**
     * Spawns a random booster on the grid based on current conditions.
     * If a booster can be spawned, it randomizes the booster model and its location.
     *
     * @param snakeModel The current snake model, used to avoid placing boosters on the snake.
     */
    void trySpawnRandomBooster(SnakeModel snakeModel) {
        this.updateActiveBoosters(snakeModel);
        if (this.isBoosterSpawnAvailable && this.spawnCountDown == 0) {

            do {
                this.currentBoosterModelIndex = randomizer.nextInt(boosters.size());
            } while (this.boosters.get(this.currentBoosterModelIndex).isBoosterActive());

            this.currentBooster = this.boosters.get(this.currentBoosterModelIndex).getBooster();

            do {
                randomizeBoosterLocation();
            } while (boosterSpawnedOnSnake(snakeModel.getSnake()));

            this.isBoosterSpawnAvailable = false;
        }

        if (this.spawnCountDown > 0) { this.spawnCountDown--; }
    }

    /**
     * Updates the state of active boosters and applies effects if necessary.
     *
     * @param snakeModel The current snake model to apply effects to.
     */
    private void updateActiveBoosters(SnakeModel snakeModel) {
        for (ISnakeBoostersModel boosterModel : boosters) {
            if (boosterModel.isBoosterActive()) {
                this.boosterEffectDuration -= 1;
                if (this.boosterEffectDuration < 0) {
                    ((IBoosterEffect) boosterModel).resetBoosterEffect(snakeModel);
                }
            }
        }
    }

    /**
     * Randomizes the location of the current booster on the game grid.
     */
    private void randomizeBoosterLocation() {
        this.currentBooster[0] = new int[]{
            this.randomizer.nextInt(gameGrid.length),
            this.randomizer.nextInt(gameGrid.length),
            this.boosters.get(this.currentBoosterModelIndex).getBoosterColor()
        };
    }

    /**
     * Checks if the current booster has spawned on any part of the snake.
     *
     * @param snake The current state of the snake.
     * @return true if the booster has spawned on the snake, false otherwise.
     */
    private boolean boosterSpawnedOnSnake(final int[][] snake) {
        return Arrays.stream(snake)
            .anyMatch(bodyPart ->
                bodyPart[0] == this.currentBooster[0][0] &&
                bodyPart[1] == this.currentBooster[0][1]
            );
    }

    /**
     * Sets the duration for the current booster effect based on the snake's speed.
     *
     * @param snakeSpeed The current speed of the snake to calculate the speed factor.
     */
    void setBoosterDuration(double snakeSpeed) {
        double speedFactor = AppConfigProject.SNAKE_TICK_DELAY / snakeSpeed;
        this.boosterEffectDuration = AppConfigProject.BASE_BOOSTER_DURATION * speedFactor;
    }

    /**
     * Sets the speed of the snake.
     *
     * @param snakeModel The snake model to update.
     * @param speed The new speed to set.
     */
    void setSpeed(SnakeModel snakeModel, double speed) {
        snakeModel.setSpeed(speed);
    }

    public void cleanup() {
        this.boosters.clear();
    }

    /*============================
    * Setters
    ===========================*/

    /**
     * Adds a booster to the manager's list of available boosters.
     *
     * @param booster The booster model to add.
     */
    void addBoosters(ISnakeBoostersModel booster) {
        this.boosters.add(booster);
    }

    /*============================
    * Getters
    ===========================*/

    /**
     * Retrieves the current booster details.
     *
     * @return A 2D array representing the current booster.
     */
    int[][] getCurrentBooster() {
        return this.currentBooster;
    }
}
