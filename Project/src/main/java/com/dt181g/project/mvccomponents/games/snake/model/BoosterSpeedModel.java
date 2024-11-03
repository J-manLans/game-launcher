package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.AudioManager;

public class BoosterSpeedModel implements ISnakeBoostersModel, IBoosterEffect {
    private int[][] speedBooster = new int[1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
    private final int boosterColor = AppConfigProject.COLOR_SPEED_INT;
    private double oldSnakeSpeed;
    private double oldHalfSpeed;
    private boolean isActive;
    private String soundEffect = AppConfigProject.PATH_TO_SOUNDS + AppConfigProject.SOUND_EFFECT_SPEED;

    public BoosterSpeedModel() {
        ManagerSnakeBooster.INSTANCE.addBoosters(this);
    }

    @Override
    public void applyBoosterEffect(SnakeModel snakeModel) {
        this.oldSnakeSpeed = snakeModel.getSpeed();
        this.oldHalfSpeed = this.oldSnakeSpeed / 2;

        ManagerSnakeBooster.INSTANCE.setBoosterDuration(oldSnakeSpeed);
        ManagerSnakeBooster.INSTANCE.setSpeed(snakeModel, (this.oldHalfSpeed));
        AudioManager.INSTANCE.playSound(soundEffect);

        this.isActive = true;
    }

    @Override
    public void resetBoosterEffect(SnakeModel snakeModel) {
        ManagerSnakeBooster.INSTANCE.setSpeed(snakeModel, (this.oldSnakeSpeed - (this.oldHalfSpeed - snakeModel.getSpeed())));
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
    public boolean isBoosterActive() {
        return this.isActive;
    }
}
