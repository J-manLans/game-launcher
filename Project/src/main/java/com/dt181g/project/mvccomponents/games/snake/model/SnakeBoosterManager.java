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
enum SnakeBoosterManager {
    INSTANCE;

    private final List<SnakeBoostersModel> boosters = new ArrayList<>();
    private int[][] gameGrid;
    private final Random randomizer = new Random();
    // Makes the initial booster to spawn a cherry
    private int currentBoosterModelIndex = AppConfigProject.CHERRY_INDEX;
    // initial state, since no booster is on the grid at start they are available
    private boolean isBoosterSpawnAvailable = true;
    // Initial random countdown for spawning a booster
    private int spawnCountDown;
    private int[][] currentBooster;
    private double boosterEffectDuration;

    /**
     * Initializes the booster manager with the specified game grid.
     * Sets the availability of boosters and initializes the spawn countdown.
     *
     * @param gameGrid The current state of the game grid.
     */
    void initializeBoosterManager(int[][] gameGrid) {
        this.gameGrid = gameGrid;
        this.isBoosterSpawnAvailable = true;
        this.currentBooster = null;
        this.spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    }

    /**
     * Resets the state of the specified booster after it has been consumed by the snake.
     *
     * @param booster The booster to reset.
     */
    void eatAndResetBooster(int[][] booster) {
        // Sets the color of the old booster to background color, effectively
        // devouring it from the grid since the snake interact with it's color,
        // not coordinates.
        booster[0][2] = 0;
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
            } while (this.boosters.get(this.currentBoosterModelIndex).isActive());

                this.currentBooster = this.boosters.get(this.currentBoosterModelIndex).getBooster();

            do {
                randomizeBoosterLocation();
            } while (boosterSpawnedOnSnake(snakeModel.getSnake()));

            this.isBoosterSpawnAvailable = false;
        }

        if (this.spawnCountDown > 0) { this.spawnCountDown--; }
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
     * Updates the state of active boosters and applies effects if necessary.
     *
     * @param snakeModel The current snake model to apply effects to.
     */
    private void updateActiveBoosters(SnakeModel snakeModel) {
        for (SnakeBoostersModel boosterModel : boosters) {
            if (boosterModel.isActive()) {
                boosterEffectDuration -= 1;
                if (boosterEffectDuration < 0) {
                    ((BoosterEffect) boosterModel).reset(snakeModel);
                }
            }
        }
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

    /*============================
    * Setters
    ===========================*/

    /**
     * Adds a booster to the manager's list of available boosters.
     *
     * @param booster The booster model to add.
     */
    void addBoosters(SnakeBoostersModel booster) {
        this.boosters.add(booster);
    }

    /*============================
    * Getters
    ===========================*/

    /**
     * Retrieves the currently active booster model.
     *
     * @return The current booster model.
     */
    SnakeBoostersModel getCurrentBoosterModel() {
        return this.boosters.get(this.currentBoosterModelIndex);
    }

    /**
     * Retrieves the current booster details.
     *
     * @return A 2D array representing the current booster.
     */
    int[][] getCurrentBooster() {
        return this.currentBooster;
    }
}
