package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.support.AppConfigProject;

public class SpeedBoosterModel implements SnakeBoostersModel, BoosterEffect {
    private int[][] speedBooster = new int[1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
    private final int boosterColor = AppConfigProject.COLOR_SPEED_INT;
    private double snakeSpeed;
    private double halfSpeed;
    private boolean isActive;

    public SpeedBoosterModel() {
        SnakeBoosterManager.INSTANCE.addBoosters(this);
    }

    @Override
    public void eatBooster(SnakeModel snakeModel) {
        this.snakeSpeed = snakeModel.getSpeed();
        this.halfSpeed = this.snakeSpeed / 2;
        this.isActive = true;

        SnakeBoosterManager.INSTANCE.setBoosterDuration(snakeModel);
        SnakeBoosterManager.INSTANCE.eatAndResetBoosterState(this);
        SnakeBoosterManager.INSTANCE.setSpeed(snakeModel, (this.halfSpeed));
    }

    @Override
    public void reset(SnakeModel snakeModel) {
        this.isActive = false;
        SnakeBoosterManager.INSTANCE.setSpeed(snakeModel, (this.snakeSpeed - (this.halfSpeed - snakeModel.getSpeed())));
    }

    @Override
    public int[][] getBooster() {
        return this.speedBooster;
    }

    @Override
    public int getBoosterColor() {
        return this.boosterColor;
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }
}
