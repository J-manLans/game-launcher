package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.AudioManager;

/**
 * Represents a speed booster in the Snake game, which temporarily increases
 * the snake's speed when consumed. The booster can be activated and reset,
 * affecting the snake's movement dynamics.
 *
 * <p>
 * This class implements the {@link ISnakeBoostersModel} and {@link IBoosterEffect}
 * interfaces to manage the application and resetting of the speed boost effect.
 * </p>
 *
 * @author Joel Lansgren
 */
public class BoosterSpeedModel implements ISnakeBoostersModel, IBoosterEffect {
    private int[][] speedBooster = new int[1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
    private final int boosterColor = AppConfigProject.COLOR_SPEED_INT;
    private double oldSnakeSpeed;
    private double oldDoubleSpeed;
    private boolean isActive;
    private String soundEffect = AppConfigProject.PATH_TO_SOUNDS + AppConfigProject.SOUND_EFFECT_SPEED;

    /**
     * Constructor that initializes the speed booster model and registers it
     * with the booster manager.
     */
    public BoosterSpeedModel() {
        ManagerSnakeBooster.INSTANCE.addBoosters(this);
    }

    /**
     * Applies the speed boost effect to the specified snake model, temporarily
     * increasing its speed and storing the original speed for later resetting.
     *
     * @param snakeModel The snake model to which the speed effect is applied.
     */
    @Override
    public void applyBoosterEffect(SnakeModel snakeModel) {
        this.oldSnakeSpeed = snakeModel.getSpeed();
        this.oldDoubleSpeed = this.oldSnakeSpeed / 2;

        ManagerSnakeBooster.INSTANCE.setBoosterDuration(oldSnakeSpeed);
        ManagerSnakeBooster.INSTANCE.setSpeed(snakeModel, (this.oldDoubleSpeed));
        AudioManager.INSTANCE.playSound(soundEffect);

        this.isActive = true;
    }

    /**
     * Resets the speed boost effect on the specified snake model, restoring
     * its speed to the original value before the booster was applied.
     *
     * @param snakeModel The snake model whose speed is reset.
     */
    @Override
    public void resetBoosterEffect(SnakeModel snakeModel) {
        ManagerSnakeBooster.INSTANCE.setSpeed(snakeModel, (this.oldSnakeSpeed - (this.oldDoubleSpeed - snakeModel.getSpeed())));
        this.isActive = false;
    }

    /*============================
    * Getters
    ===========================*/

    /**
     * Returns the speed booster object, represented as a 2D array, with its
     * position and color.
     *
     * @return A 2D array representing the speed booster.
     */
    @Override
    public int[][] getBooster() {
        return this.speedBooster;
    }

    /**
     * Returns the color associated with the speed booster.
     *
     * @return An integer representing the color of the speed booster.
     */
    @Override
    public int getBoosterColor() {
        return this.boosterColor;
    }

    /**
     * Indicates whether the speed booster is currently active.
     *
     * @return A boolean value indicating if the booster is active.
     */
    @Override
    public boolean isBoosterActive() {
        return this.isActive;
    }
}
