package com.b2r.main.model;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.LinkedList;

public class Task {
    public enum State {PASSED, BRONZE, SILVER, GOLD, ACTIVE}

    public enum TaskDifficulty {EASY, NORMAL, HARD}

    public static LinkedList<OverlayItem> mapMarkers = new LinkedList<>();

    public static LinkedList<OverlayItem> getMapMarkers() {
        return mapMarkers;
    }

    private OverlayItem mapItem;

    public OverlayItem getMapItem() {
        return mapItem;
    }

    private int taskId;
    private int questId;

    private String title;
    private String shortDescription;
    private String longDescription;
    private State state;
    private TaskDifficulty difficultyLevel;
    private String pskPass;

    private ArrayList<String> psksUnlock;
    private String pskBronze;
    private String pskSilver;
    private String pskGold;
    private boolean isTaskVisible;
    private boolean hasBindToMap;
    private boolean hasBindToAgent;
    private double latitude;
    private double longitude;
    private String imgSrc;
    private int bronzeScore;
    private int silverScore;
    private int goldScore;

    private Task() {
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public State getState() {
        return state;
    }

    public TaskDifficulty getDifficultyLevel() {
        return difficultyLevel;
    }

    public String getPskPass() {
        return pskPass;
    }

    public ArrayList<String> getPsksUnlock() {
        return psksUnlock;
    }

    public String getPskBronze() {
        return pskBronze;
    }

    public String getPskSilver() {
        return pskSilver;
    }

    public String getPskGold() {
        return pskGold;
    }

    public boolean isTaskVisible() {
        return isTaskVisible;
    }

    public boolean isHasBindToMap() {
        return hasBindToMap;
    }

    public boolean isHasBindToAgent() {
        return hasBindToAgent;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public int getBronzeScore() {
        return bronzeScore;
    }

    public int getSilverScore() {
        return silverScore;
    }

    public int getGoldScore() {
        return goldScore;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setTaskVisible(boolean taskVisibility) {
        if (taskVisibility) {
            isTaskVisible = true;
            if (hasBindToMap) {
                mapItem = new OverlayItem(title, shortDescription, new GeoPoint(latitude, longitude));
                mapMarkers.add(mapItem);
            }
        } else {
            isTaskVisible = false;
        }
    }

    public static class TaskBuilder {
        private Task task;

        public TaskBuilder() {
            this.task = new Task();
        }

        public TaskBuilder setTaskId(int id){
            task.taskId = id;
            return this;
        }

        public TaskBuilder setQuestId(int id){
            task.questId = id;
            return this;
        }

        public TaskBuilder setDifficultyLevel(TaskDifficulty difficultyLevel) {
            task.difficultyLevel = difficultyLevel;
            return this;
        }

        public TaskBuilder setShortDescription(String shortDescription) {
            task.shortDescription = shortDescription;
            return this;
        }

        public TaskBuilder setLongDescription(String longDescription) {
            task.longDescription = longDescription;
            return this;
        }

        public TaskBuilder setPskPass(String pskPass) {
            task.pskPass = pskPass;
            return this;
        }

        public TaskBuilder setPsksUnlock(ArrayList<String> psksUnlock) {
            task.psksUnlock = psksUnlock;
            return this;
        }

        public TaskBuilder setPskBronze(String pskBronze) {
            task.pskBronze = pskBronze;
            return this;
        }

        public TaskBuilder setPskSilver(String pskSilver) {
            task.pskSilver = pskSilver;
            return this;
        }

        public TaskBuilder setPskGold(String pskGold) {
            task.pskGold = pskGold;
            return this;
        }

        public TaskBuilder setIsTaskVisible(boolean isTaskVisible) {
            task.isTaskVisible = isTaskVisible;
            return this;
        }

        public TaskBuilder setHasBindToMap(boolean hasBindToMap) {
            task.hasBindToMap = hasBindToMap;
            return this;
        }

        public TaskBuilder setHasBindToAgent(boolean hasBindToAgent) {
            task.hasBindToAgent = hasBindToAgent;
            return this;
        }

        public TaskBuilder setLatitude(double latitude) {
            task.latitude = latitude;
            return this;
        }

        public TaskBuilder setLongitude(double longitude) {
            task.longitude = longitude;
            return this;
        }

        public TaskBuilder setImgSrc(String imgSrc) {
            task.imgSrc = imgSrc;
            return this;
        }

        public TaskBuilder setBronzeScore(int bronzeScore) {
            task.bronzeScore = bronzeScore;
            return this;
        }

        public TaskBuilder setSilverScore(int silverScore) {
            task.silverScore = silverScore;
            return this;
        }

        public TaskBuilder setGoldScore(int goldScore) {
            task.goldScore = goldScore;
            return this;
        }

        public TaskBuilder setTitle(String title) {
            task.title = title;
            return this;
        }

        public TaskBuilder setState(State state) {
            task.state = state;
            return this;
        }

        public Task build() {
            task.setTaskVisible(task.isTaskVisible);
            return task;
        }

    }

}
