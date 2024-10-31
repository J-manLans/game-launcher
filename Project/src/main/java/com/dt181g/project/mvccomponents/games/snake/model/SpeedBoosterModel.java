package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.support.AppConfigProject;

public class SpeedBoosterModel extends SnakeBoostersModel {
    private int[][] speedBooster = new int[1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
    private final int boosterColor = AppConfigProject.COLOR_SPEED_INT;
    private int snakeSpeed;
    private int speedBoosterEffectTimeout;
    private boolean isActive;

    public SpeedBoosterModel() {
        BoosterManager.INSTANCE.addBoosters(this);
    }

    @Override
    protected void eatBooster(SnakeModel snakeModel) {
        BoosterManager.INSTANCE.initializeBooster(this);
        this.snakeSpeed = snakeModel.getSpeed();
        this.speedBoosterEffectTimeout = 20;
        this.isActive = true;

        BoosterManager.INSTANCE.addSpeed(snakeModel, (snakeModel.getSpeed() / 2));
    }

    @Override
    public int[][] getBooster() {
        return speedBooster;
    }

    @Override
    protected int getBoosterColor() {
        return boosterColor;
    }

    @Override
    protected boolean isActive() {
        return this.isActive;
    }

    public int getSnakeSpeed() {
        return snakeSpeed;
    }

    public int getSpeedBoosterEffectTimeout() {
        return speedBoosterEffectTimeout;
    }
}
