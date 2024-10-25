package com.dt181g.project.mvccomponents.games.snake.model;

import java.util.Random;

import com.dt181g.project.support.AppConfigProject;

/**
 * The CherryModel class represents the behavior and properties of a cherry
 * that can spawn on the game grid and be eaten by the snake.
 * It manages the spawning, position, and state of the cherry.
 * @author Joel Lansgren
 */
public class CherryModel extends SnakeBoostersModel {
    private final int[][] cherry = new int[1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
    private final Random randomizer = new Random();
    private int spawnCountDown;
    private boolean isCherry;

    /**
     * Initializes the cherry on startup.
     */
    public void initializeCherry() {
        // Sets the color of the cherry to background color
        this.cherry[0][2] = 0;
        // Allows the cherry to be spawned on a different cell
        this.isCherry = false;
        // Sets the countdown for allowing the new cherry to be spawned
        this.spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    }

    /**
     * Spawns a cherry at random coordinates on the game grid if the cherry is not already present.
     * The spawning occurs when the countdown reaches zero.
     * Resets the countdown when a cherry is spawned.
     */
    public void spawnCherry() {
        if (!this.isCherry && this.spawnCountDown == 0) {
            this.cherry[0][0] = this.randomizer.nextInt(this.gameGrid.length);  // Y-coordinate
            this.cherry[0][1] = this.randomizer.nextInt(this.gameGrid.length);  // X-coordinate
            this.cherry[0][2] = AppConfigProject.COLOR_CHERRY_INT;  // Color
            this.isCherry = true;
        }

        if (this.spawnCountDown > 0) { this.spawnCountDown--; }
    }

    /**
     * Utilizes the initiate method to simulate eating.
     */
    @Override
    protected void eatBooster() {
        this.initializeCherry();
    }

    /**
     * Returns the current state of the cherry.
     *
     * @return A 2D array representing the cherry's position and color.
     */
    public int[][] getCherry() {
        return cherry;
    }
}
