package com.dt181g.project.mvccomponents.games.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dt181g.project.support.AppConfigProject;

public enum BoosterManager {
    INSTANCE;

    private final List<SnakeBoostersModel> boosters = new ArrayList<>();
    private int[][] gameGrid;
    private final Random randomizer = new Random();
    // Makes the initial booster to spawn a cherry
    private int currentBoosterModelIndex = AppConfigProject.CHERRY_INDEX;
    // initial state, since no booster is on the grid at start they are available
    private boolean isBoostersAvailable = true;
    // Initial random countdown for spawning a booster
    private int spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    private int[][] currentBooster;

    public void initializeBooster(SnakeBoostersModel boosterModel) {
        // Sets the color of the old booster to background color, effectively
        // devouring it from the grid since the snake interact with it's color,
        // not coordinates.
        boosterModel.getBooster()[0][2] = 0;
        // Allows the booster to be spawned on a different cell
        this.isBoostersAvailable = true;
        // Sets the countdown for allowing the new booster to be spawned
        this.spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    }

    public void spawnRandomBooster(int[][] snake) {
        if (this.isBoostersAvailable && this.spawnCountDown == 0) {
            do {
                this.currentBoosterModelIndex = randomizer.nextInt(boosters.size());
            } while (this.boosters.get(this.currentBoosterModelIndex).isActive());
            this.currentBooster = this.boosters.get(this.currentBoosterModelIndex).getBooster();

            do {
                randomizeBoosterLocation();
            } while (boosterSpawnedOnSnake(snake));

            this.isBoostersAvailable = false;
        }

        if (this.spawnCountDown > 0) { this.spawnCountDown--; }
    }

    private void randomizeBoosterLocation() {
        this.currentBooster[0] = new int[]{
            this.randomizer.nextInt(gameGrid.length),
            this.randomizer.nextInt(gameGrid.length),
            this.boosters.get(this.currentBoosterModelIndex).getBoosterColor()
        };
    }

    private boolean boosterSpawnedOnSnake(final int[][] snake) {
        for (int[] bodyPart : snake) {
            if (bodyPart[0] == this.currentBooster[0][0] && bodyPart[1] == this.currentBooster[0][1]) {
                return true;
            }
        }
        return false;
    }

    void addSpeed(SnakeModel snakeModel, int speed) {
        snakeModel.setSpeed(speed);
    }

    /*============================
     * Setters
     ===========================*/

    public void addBoosters(SnakeBoostersModel booster) {
        this.boosters.add(booster);
    }

    public void setGameGrid(final int[][] gameGrid) {
        this.gameGrid = gameGrid;
    }

    public void setIsBoosterAvailable(boolean isBoosterAvailable) {
        this.isBoostersAvailable = isBoosterAvailable;
    }

    public void resetSpawnCountDown() {
        this.spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    }

    public void resetBooster() {
        this.currentBooster = null;
    }

    /*============================
     * Getters
     ===========================*/

    public SnakeBoostersModel getCurrentBoosterModel() {
        return this.boosters.get(this.currentBoosterModelIndex);
    }

    public int[][] getCurrentBooster() {
        return this.currentBooster;
    }
}
