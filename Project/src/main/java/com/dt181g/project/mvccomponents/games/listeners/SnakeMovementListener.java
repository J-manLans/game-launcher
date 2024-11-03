package com.dt181g.project.mvccomponents.games.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.dt181g.project.mvccomponents.games.snake.model.SnakeModel;
import com.dt181g.project.support.AppConfigProject;

public class SnakeMovementListener extends KeyAdapter {
    private final SnakeModel snakeModel;

    public SnakeMovementListener(final SnakeModel snakeModel) {
        this.snakeModel = snakeModel;
    }

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
