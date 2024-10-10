package com.dt181g.laboration_3.model;

public class FakeTicTacToeModel extends GameModel {
    private final String title;

    public FakeTicTacToeModel(String title, String iconPath){
        super(title, iconPath);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
