package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.support.AppConfigProject;

public class SpeedBoosterModel implements SnakeBoostersModel, BoosterEffect {
    private int[][] speedBooster = new int[1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
    private final int boosterColor = AppConfigProject.COLOR_SPEED_INT;
    private double oldSnakeSpeed;
    private double oldHalfSpeed;
    private boolean isActive;

    public SpeedBoosterModel() {
        SnakeBoosterManager.INSTANCE.addBoosters(this);
    }

    @Override
    public void eatBooster(SnakeModel snakeModel) {
        this.oldSnakeSpeed = snakeModel.getSpeed();
        this.oldHalfSpeed = this.oldSnakeSpeed / 2;

        SnakeBoosterManager.INSTANCE.setBoosterDuration(snakeModel.getSpeed());
        SnakeBoosterManager.INSTANCE.eatAndResetBooster(this.speedBooster);
        SnakeBoosterManager.INSTANCE.setSpeed(snakeModel, (this.oldHalfSpeed));

        this.isActive = true;
    }

    @Override
    public void reset(SnakeModel snakeModel) {
        SnakeBoosterManager.INSTANCE.setSpeed(snakeModel, (this.oldSnakeSpeed - (this.oldHalfSpeed - snakeModel.getSpeed())));
        this.isActive = false;
    }

    /*============================
    * Getters
    ===========================*/

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
