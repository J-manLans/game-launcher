package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.mvccomponents.IBaseModel;

public interface ISnakeBoostersModel extends IBaseModel {

    void eatBooster(SnakeModel snakeModel);
    int[][] getBooster();
    int getBoosterColor();
    boolean isActive();
}
