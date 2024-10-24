package com.dt181g.project.mvccomponents.games.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.dt181g.project.mvccomponents.games.snake.controller.SnakeController;
import com.dt181g.project.support.AppConfigProject;

public class SnakeMovementListener extends KeyAdapter {
    private final SnakeController controller;
    private final boolean restart = false;

    public SnakeMovementListener(final SnakeController controller) {
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.controller.getSnakeGridModel().getSnakeModel().setDirection(AppConfigProject.UP, restart);
            case KeyEvent.VK_S -> this.controller.getSnakeGridModel().getSnakeModel().setDirection(AppConfigProject.DOWN, restart);
            case KeyEvent.VK_A -> this.controller.getSnakeGridModel().getSnakeModel().setDirection(AppConfigProject.LEFT, restart);
            case KeyEvent.VK_D -> this.controller.getSnakeGridModel().getSnakeModel().setDirection(AppConfigProject.RIGHT, restart);
        }
    }
}
