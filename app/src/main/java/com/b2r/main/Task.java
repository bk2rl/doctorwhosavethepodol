package com.b2r.main;

public class Task {
    private long latitude;
    private long longtitude;
    private String psk;
    private boolean isPlaceVisible;
    private String title;
    private String description;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
