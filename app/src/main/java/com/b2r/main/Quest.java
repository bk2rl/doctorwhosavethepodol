package com.b2r.main;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Quest {

    public enum QuestDifficulty {EASY, NORMAL, HARD}

    public enum QuestMode {SINGLE, MULTIPLAYER}

    private String title;
    private String shortDescription;
    private String longDescription;
    private String questLogoSrc;
    private String imgSrc;
    private String endText;
    private String pskAddTime;

    private QuestMode questMode;
    private QuestDifficulty questDifficulty;

    private int progress;
    private int exp;
    private int score;
    private long durationTime;
    private long addTime;
    private long startTime;

    private boolean isStarted;
    private boolean isCurrent;
    private boolean isEnded;
    private boolean hasBindToMap;
    private boolean isFavorite;
    private boolean isTimeAdded;
    private boolean hasBindToAgent;

    private ArrayList<Task> taskList;

    public Quest(String title, String shortDescription, String longDescription, String questLogoSrc,
                 String imgSrc, String questMode, String questDifficulty,
                 int progress, long durationTime, long startTime, int exp,
                 boolean isStarted, boolean hasBindToMap, boolean isFavorite, boolean hasBindToAgent,
                 ArrayList<Task> taskList, int score, boolean isCurrent, String endText,
                 boolean isEnded, String pskAddTime, long addTime, boolean isTimeAdded) {

        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.questLogoSrc = questLogoSrc;
        this.imgSrc = imgSrc;
        this.questMode = QuestMode.valueOf(questMode);
        this.questDifficulty = QuestDifficulty.valueOf(questDifficulty);
        this.progress = progress;
        this.durationTime = durationTime;
        this.addTime = addTime;
        this.startTime = startTime;
        this.exp = exp;
        this.isStarted = isStarted;
        this.hasBindToMap = hasBindToMap;
        this.isFavorite = isFavorite;
        this.hasBindToAgent = hasBindToAgent;
        this.taskList = taskList;
        this.score = score;
        this.pskAddTime = pskAddTime;
        this.isCurrent = isCurrent;
        this.endText = endText;
        this.isEnded = isEnded;
        this.isTimeAdded = isTimeAdded;
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

    public String getEndText() { return endText;}

    public String getQuestLogoSrc() {
        return questLogoSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public QuestMode getQuestMode() {
        return questMode;
    }

    public QuestDifficulty getQuestDifficulty() {
        return questDifficulty;
    }

    public int getProgress() {
        return progress;
    }

    public int getExp() {
        return exp;
    }

    public long getDurationTime() {
        return durationTime;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isHasBindToMap() {
        return hasBindToMap;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isHasBindToAgent() {
        return hasBindToAgent;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getScore() {
        return score;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setIsEnded(boolean isEnded) {
        this.isEnded = isEnded;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void addProgress(int addProgress) {
        this.progress += addProgress;
    }

    public void addScore(int addScore) {
        this.score += addScore;
    }

    public boolean isTimeAdded() {
        return isTimeAdded;
    }

    public void setIsTimeAdded(boolean isTimeAdded) {
        this.isTimeAdded = isTimeAdded;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getPskAddTime() {
        return pskAddTime;
    }

    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }
}
