package com.dt181g.laboration_3.model;

public abstract class GameModel {
    private final String title;
    private final String iconPath;

    public GameModel(String title, String iconPath) {
        this.title = title;
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getTitle() {
        return title;
    }
}
