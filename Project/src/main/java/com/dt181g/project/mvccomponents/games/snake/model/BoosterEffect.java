package com.dt181g.project.mvccomponents.games.snake.model;

public interface BoosterEffect {
    public void setSpeedBoosterEffectTimeout(int countDown);
    public int getSpeedBoosterEffectTimeout();
    public void reset(SnakeModel snakeModel);
}
