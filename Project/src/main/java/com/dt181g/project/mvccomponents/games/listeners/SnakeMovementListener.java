package com.dt181g.project.mvccomponents.games.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.dt181g.project.mvccomponents.games.snake.model.SnakeModel;
import com.dt181g.project.support.AppConfigProject;

/**
 * Listens for keyboard input to control the movement of the snake in the game.
 * It updates the direction of the snake model based on key presses for movement
 * in all four cardinal directions.
 *
 * @author Joel Lansgren
 */
public class SnakeMovementListener extends KeyAdapter {
    private final SnakeModel snakeModel;

    /**
     * Constructs a SnakeMovementListener for the specified snake model.
     *
     * @param snakeModel The model of the snake to control.
     */
    public SnakeMovementListener(final SnakeModel snakeModel) {
        this.snakeModel = snakeModel;
    }

    /**
     * Invoked when a key is pressed. Updates the snake's direction based on the
     * key code of the pressed key.
     *
     * @param e The KeyEvent containing information about the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.snakeModel.setDirection(AppConfigProject.UP, false);
            case KeyEvent.VK_S -> this.snakeModel.setDirection(AppConfigProject.DOWN, false);
            case KeyEvent.VK_A -> this.snakeModel.setDirection(AppConfigProject.LEFT, false);
            case KeyEvent.VK_D -> this.snakeModel.setDirection(AppConfigProject.RIGHT, false);
            case KeyEvent.VK_UP -> this.snakeModel.setDirection(AppConfigProject.UP, false);
            case KeyEvent.VK_DOWN -> this.snakeModel.setDirection(AppConfigProject.DOWN, false);
            case KeyEvent.VK_LEFT -> this.snakeModel.setDirection(AppConfigProject.LEFT, false);
            case KeyEvent.VK_RIGHT -> this.snakeModel.setDirection(AppConfigProject.RIGHT, false);
        }
    }
}
