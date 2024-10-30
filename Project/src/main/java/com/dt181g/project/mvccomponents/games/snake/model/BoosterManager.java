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
    private boolean isBoosterEaten = true;
    private int spawnCountDown;
    private int[][] booster;
    private SnakeBoostersModel currentBoosterModel;

    public void initializeBooster(SnakeBoostersModel boosterModel) {
        // Sets the color of the booster to background color
        boosterModel.getBooster()[0][2] = 0;
        // Allows the booster to be spawned on a different cell
        this.isBoosterEaten = true;
        //
        this.boosterPosition = randomizer.nextInt(boosters.size());
        this.currentBoosterModel = boosters.get(this.boosterPosition);
        // Sets the countdown for allowing the new booster to be spawned
        this.spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    }

    public void spawnRandomBooster(int[][] snake) {
        currentBoosterModel.getBooster();

        if (this.isBoosterEaten && this.spawnCountDown == 0) {
            randomizeBoosterLocation();
            while (boosterSpawnedOnSnake(snake)) {
                randomizeBoosterLocation();
            }

            this.isBoosterEaten = false;
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

    public void setBooster(SnakeBoostersModel booster) {
        this.boosters.add(booster);
    }

    public void setCurrentBooster(SnakeBoostersModel boosterModel) {
        this.currentBoosterModel = boosterModel;
    }

    public void setGameGrid(final int[][] gameGrid) {
        this.gameGrid = gameGrid;
    }
}
