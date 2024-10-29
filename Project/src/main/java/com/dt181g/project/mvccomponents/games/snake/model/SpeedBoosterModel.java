package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.support.AppConfigProject;

public class SpeedBoosterModel extends SnakeBoostersModel {
    private int[][] speedBooster = new int[1][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];;

    public SpeedBoosterModel() {
        super.setBooster(this);
    }

    @Override
    protected void eatBooster(SnakeModel snakeModel) {
        super.addSpeed(snakeModel, 0);
    }

    @Override
    public int[][] getBooster() {
        return speedBooster;
    }
}
