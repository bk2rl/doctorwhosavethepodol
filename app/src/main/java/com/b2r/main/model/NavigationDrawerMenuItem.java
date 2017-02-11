package com.b2r.main.model;

public class NavigationDrawerMenuItem {
    private int drawableResource;
    private String title;

    public int getDrawableResource() {
        return drawableResource;
    }

    public NavigationDrawerMenuItem setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NavigationDrawerMenuItem setTitle(String title) {
        this.title = title;
        return this;
    }
}
