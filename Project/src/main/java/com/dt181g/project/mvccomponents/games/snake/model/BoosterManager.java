package com.dt181g.project.mvccomponents.games.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dt181g.project.support.AppConfigProject;

public enum BoosterManager {
    INSTANCE;

    private final List<SnakeBoostersModel> boosters = new ArrayList<>();
    private int boosterPosition;
    private int[][] gameGrid;
    private final Random randomizer = new Random();
    private boolean isBoosterAvailable = true;
    private int spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    private int[][] booster;
    private SnakeBoostersModel currentBoosterModel;

    public void initializeBooster(SnakeBoostersModel boosterModel) {
        // Sets the color of the booster to background color
        boosterModel.getBooster()[0][2] = 0;
        // Allows the booster to be spawned on a different cell
        this.isBoosterAvailable = true;
        //
        this.boosterPosition = randomizer.nextInt(boosters.size());
        this.setCurrentBooster(boosters.get(this.boosterPosition));
        // Sets the countdown for allowing the new booster to be spawned
        this.spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    }

    public void spawnRandomBooster(int[][] snake) {
        this.booster = currentBoosterModel.getBooster();

        if (this.isBoosterAvailable && this.spawnCountDown == 0) {
            randomizeBoosterLocation();
            while (boosterSpawnedOnSnake(snake)) {
                randomizeBoosterLocation();
            }

            this.isBoosterAvailable = false;
        }

        if (this.spawnCountDown > 0) { this.spawnCountDown--; }
    }

    private void randomizeBoosterLocation() {
        this.booster[0] = new int[]{
            this.randomizer.nextInt(gameGrid.length),
            this.randomizer.nextInt(gameGrid.length),
            this.boosters.get(boosterPosition).getBoosterColor()
        };
    }

    private boolean boosterSpawnedOnSnake(final int[][] snake) {
        for (int[] bodyPart : snake) {
            if (bodyPart[0] == this.booster[0][0] && bodyPart[1] == this.booster[0][1]) {
                return true;
            }
        }
        return false;
    }

    /*============================
     * Setters
     ===========================*/

    public void addBoosters(SnakeBoostersModel booster) {
        this.boosters.add(booster);
    }

    public void setCurrentBooster(SnakeBoostersModel boosterModel) {
        this.currentBoosterModel = boosterModel;
    }

    public void setGameGrid(final int[][] gameGrid) {
        this.gameGrid = gameGrid;
    }

    public void setIsBoosterAvailable(boolean isBoosterAvailable) {
        this.isBoosterAvailable = isBoosterAvailable;
    }

    public void resetSpawnCountDown() {
        this.spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    }

    public void resetBooster() {
        this.currentBoosterModel.getBooster()[0][2] = 0;
    }

    /*============================
     * Getters
     ===========================*/

    public SnakeBoostersModel getCurrentBooster() {
        return this.currentBoosterModel;
    }
}
