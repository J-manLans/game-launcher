package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.support.AppConfigProject;

public class SpeedBoosterModel extends SnakeBoostersModel {
    private int[][] speedBooster = new int[1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
    private final int boosterColor = AppConfigProject.COLOR_SPEED_INT;

    public SpeedBoosterModel() {
        BoosterManager.INSTANCE.addBoosters(this);
    }

    @Override
    protected void eatBooster(SnakeModel snakeModel) {
        BoosterManager.INSTANCE.initializeBooster(this);
        super.addSpeed(snakeModel, 0);
    }

    @Override
    public int[][] getBooster() {
        return speedBooster;
    }

    @Override
    protected int getBoosterColor() {
        return boosterColor;
    }
}
