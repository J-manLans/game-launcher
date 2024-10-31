package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.support.AppConfigProject;

public class SpeedBoosterModel extends SnakeBoostersModel implements BoosterEffect {
    private int[][] speedBooster = new int[1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
    private final int boosterColor = AppConfigProject.COLOR_SPEED_INT;
    private int snakeSpeed;
    private int speedBoosterEffectTimeout;
    private boolean isActive;
    private int halfSpeed;

    public SpeedBoosterModel() {
        BoosterManager.INSTANCE.addBoosters(this);
    }

    @Override
    protected void eatBooster(SnakeModel snakeModel) {
        BoosterManager.INSTANCE.initializeBooster(this);
        this.snakeSpeed = snakeModel.getSpeed();
        this.halfSpeed = this.snakeSpeed / 2;
        this.speedBoosterEffectTimeout = 20;
        this.isActive = true;

        BoosterManager.INSTANCE.addSpeed(snakeModel, (this.halfSpeed));
    }

    @Override
    public void reset(SnakeModel snakeModel) {
        BoosterManager.INSTANCE.addSpeed(snakeModel, (this.snakeSpeed - (this.halfSpeed - snakeModel.getSpeed())));
        this.isActive = false;
    }

    @Override
    public int[][] getBooster() {
        return this.speedBooster;
    }

    @Override
    protected int getBoosterColor() {
        return this.boosterColor;
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public int getSpeedBoosterEffectTimeout() {
        return this.speedBoosterEffectTimeout;
    }

    public int getSnakeSpeed() {
        return this.snakeSpeed;
    }

    @Override
    public void setSpeedBoosterEffectTimeout(int countDown) {
        this.speedBoosterEffectTimeout += countDown;
    }
}
