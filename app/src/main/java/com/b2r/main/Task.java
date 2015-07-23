package com.b2r.main;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.LinkedList;

public class Task {
    public enum State{PASSED,BRONZE,SILVER,GOLD,ACTIVE};
    public enum TaskDifficulty{EASY, NORMAL, HARD};

    public OverlayItem getMapItem() {
        return mapItem;
    }

    private OverlayItem mapItem;

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

    public static LinkedList<OverlayItem> mapMarkers = new LinkedList<>();

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

    public static LinkedList<OverlayItem> getMapMarkers() {
        return mapMarkers;
    }

    public static void setMapMarkers(LinkedList<OverlayItem> mapMarkers) {
        Task.mapMarkers = mapMarkers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setDifficultyLevel(TaskDifficulty difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void setPskPass(String pskPass) {
        this.pskPass = pskPass;
    }

    public void setPsksUnlock(ArrayList<String> psksUnlock) {
        this.psksUnlock = psksUnlock;
    }

    public void setPskBronze(String pskBronze) {
        this.pskBronze = pskBronze;
    }

    public void setPskSilver(String pskSilver) {
        this.pskSilver = pskSilver;
    }

    public void setPskGold(String pskGold) {
        this.pskGold = pskGold;
    }

    public void setIsTaskVisible(boolean isTaskVisible) {
        this.isTaskVisible = isTaskVisible;
    }

    public void setHasBindToMap(boolean hasBindToMap) {
        this.hasBindToMap = hasBindToMap;
    }

    public void setHasBindToAgent(boolean hasBindToAgent) {
        this.hasBindToAgent = hasBindToAgent;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public void setBronzeScore(int bronzeScore) {
        this.bronzeScore = bronzeScore;
    }

    public void setSilverScore(int silverScore) {
        this.silverScore = silverScore;
    }

    public void setGoldScore(int goldScore) {
        this.goldScore = goldScore;
    }

    public Task(String title, String shortDescription, String longDescription,
                String imgSrc, String state, String difficultyLevel,
                String pskPass, ArrayList<String> psksUnlock,
                String pskBronze, String pskSilver, String pskGold,
                boolean isTaskVisible, boolean hasBindToMap, boolean hasBindToAgent,
                double latitude, double longitude, int bronzeScore, int silverScore, int goldScore
    )  {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imgSrc = imgSrc;
        this.state = State.valueOf(state);
        this.difficultyLevel = TaskDifficulty.valueOf(difficultyLevel);
        this.pskPass = pskPass;
        this.psksUnlock = psksUnlock;
        this.pskBronze = pskBronze;
        this.pskSilver = pskSilver;
        this.pskGold = pskGold;
        this.hasBindToMap = hasBindToMap;
        this.hasBindToAgent = hasBindToAgent;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bronzeScore = bronzeScore;
        this.silverScore = silverScore;
        this.goldScore = goldScore;
        setTaskVisible(isTaskVisible);
    }

    public void setTaskVisible(boolean taskVisibility){
        if (taskVisibility){
            isTaskVisible = true;
            if (hasBindToMap) {
                mapItem = new OverlayItem(title, shortDescription, new GeoPoint(latitude, longitude));
                mapMarkers.add(mapItem);
            }
        }else {
            isTaskVisible = false;
        }
    }

}
