package com.dt181g.project.mvccomponents.games.snake.model;

import java.util.Random;

import com.dt181g.project.support.AppConfigProject;

public class CherryModel {
    private final int[][] cherry;
    private final Random randomizer = new Random();
    private boolean cherrySpawned;
    private int[][] gameGrid;

    protected CherryModel(final int[][] gameGrid, int itemParts) {
        this.gameGrid = gameGrid;
        this.cherry = new int[1][itemParts];
    }

    /**
     * Method that spawns an apple on random y and x coordinates.
     */
    public void spawnApple() {
        if (!this.cherrySpawned) {
            this.cherry[0][0] = this.randomizer.nextInt(this.gameGrid.length);  // Y-coordinate
            this.cherry[0][1] = this.randomizer.nextInt(this.gameGrid.length);  // X-coordinate
            this.cherry[0][2] = AppConfigProject.COLOR_APPLE_INT;  // Color
            this.cherrySpawned = true;
        }
    }

    public int[][] getCherry() {
        return cherry;
    }
}
