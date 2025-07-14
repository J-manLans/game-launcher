package com.jman.gamelauncher.model;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import com.jman.gamelauncher.support.AppConfig;
import com.jman.gamelauncher.support.AppConfigSnake;
import com.jman.gamelauncher.support.AudioManager;

/**
 * Represents a cherry booster in the game, which increases the snake's size and speed when consumed.
 * This class extends {@link BoosterModel} and defines the specific behavior of a cherry booster.
 *
 * <p>Upon activation, we make sure its new position isn't occupied by the snake.
 * When consumed, it triggers the {@code grow()} and {@code increaseSpeed()} methods on
 * {@link ISnakeBoosterTarget}.</p>
 *
 * <p>The booster also manages its own sound effect, which is preloaded on creation
 * and played upon consumption.</p>
 *
 * @author Joel Lansgren
 */
public final class SnakeCherryBoosterModel extends BoosterModel {
    private final ISnakeBoosterTarget snakeTarget;
    private final Color color = AppConfigSnake.COLOR_CHERRY_BOOSTER;
    private final String soundEffect = AppConfigSnake.SOUND_EFFECT_CHERRY;
    private Random randomizer = new Random();

    /**
     * Creates a new CherryBooster instance and preload its sound effect.
     * @param snakeTarget The target snake that will interact with the booster.
     */
    public SnakeCherryBoosterModel(final ISnakeBoosterTarget snakeTarget) {
        super("CherryBooster");
        this.snakeTarget = snakeTarget;
        loadSoundEffect();
    }

    /**
     * {@inheritDoc}
     * If the new booster position is occupied by the snake we do nothing.
     * @param newPosition The position where the booster should appear.
     */
    @Override
    public void activate(final Point newPosition) {
        if (snakeTarget.getSnake().contains(newPosition)) { return; }
        super.activate(newPosition);
    }

    @Override
    void spawnCoolDown() {
        try {
            Thread.sleep(randomizer.nextLong(AppConfig.BOOSTER_SPAWN_MIN_DELAY, AppConfig.BOOSTER_SPAWN_MAX_DELAY));
        } catch (final InterruptedException e) {
            System.out.println("interrupted");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Applies the effect of the cherry booster, growing the snake and increasing its speed.
     */
    @Override
    void applyEffect() {
        snakeTarget.setGrow(true);
        snakeTarget.increaseSpeed(AppConfigSnake.SPEED_MULTIPLIER);
    }

    @Override
    void loadSoundEffect() {
        AudioManager.INSTANCE.queueSoundEffect(soundEffect);
    }

    @Override
    void playSoundEffect() {
        AudioManager.INSTANCE.playSoundEffect(soundEffect);
    }

    @Override
    public Color getBoosterColor() {
        return new Color(color.getRGB());
    }
}
