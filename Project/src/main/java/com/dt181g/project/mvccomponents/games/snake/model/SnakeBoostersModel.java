package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.mvccomponents.BaseModel;

public abstract class SnakeBoostersModel implements BaseModel {

    protected abstract void eatBooster(SnakeModel snakeModel);
    public abstract int[][] getBooster();
    protected abstract int getBoosterColor();
    protected abstract boolean isActive();
}
