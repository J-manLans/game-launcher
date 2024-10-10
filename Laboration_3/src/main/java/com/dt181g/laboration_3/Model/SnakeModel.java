package com.dt181g.laboration_3.model;

public class SnakeModel extends GameModel {
    private final String title;
    private int snake = 3;
    private int apples = 0;

    public SnakeModel(String title, String iconPath){
        super(iconPath);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
