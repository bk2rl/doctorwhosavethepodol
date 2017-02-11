package com.b2r.main.model;

import java.util.ArrayList;

public class Quest {

    public enum QuestDifficulty {EASY, NORMAL, HARD}

    public enum QuestMode {SINGLE, MULTIPLAYER}

    private int id;

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

    private Quest() {
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

    public String getEndText() {
        return endText;
    }

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

    public void addProgress(int addProgress) {
        this.progress += addProgress;
    }

    public void addScore(int addScore) {
        this.score += addScore;
    }

    public boolean isTimeAdded() {
        return isTimeAdded;
    }

    public long getAddTime() {
        return addTime;
    }

    public String getPskAddTime() {
        return pskAddTime;
    }

    public void setIsTimeAdded(boolean isTimeAdded) {
        this.isTimeAdded = isTimeAdded;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }

    public void setIsEnded(boolean isEnded) {
        this.isEnded = isEnded;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public void setQuestStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public static class QuestBuilder {

        private Quest quest;

        public QuestBuilder() {
            this.quest = new Quest();
        }

        public QuestBuilder setTitle(String title) {
            quest.title = title;
            return this;
        }

        public QuestBuilder setShortDescription(String shortDescription) {
            quest.shortDescription = shortDescription;
            return this;
        }

        public QuestBuilder setLongDescription(String longDescription) {
            quest.longDescription = longDescription;
            return this;
        }

        public QuestBuilder setQuestLogoSrc(String questLogoSrc) {
            quest.questLogoSrc = questLogoSrc;
            return this;
        }

        public QuestBuilder setImgSrc(String imgSrc) {
            quest.imgSrc = imgSrc;
            return this;
        }

        public QuestBuilder setEndText(String endText) {
            quest.endText = endText;
            return this;
        }

        public QuestBuilder setPskAddTime(String pskAddTime) {
            quest.pskAddTime = pskAddTime;
            return this;
        }

        public QuestBuilder setQuestMode(QuestMode questMode) {
            quest.questMode = questMode;
            return this;
        }

        public QuestBuilder setQuestDifficulty(QuestDifficulty questDifficulty) {
            quest.questDifficulty = questDifficulty;
            return this;
        }

        public QuestBuilder setExp(int exp) {
            quest.exp = exp;
            return this;
        }

        public QuestBuilder setScore(int score) {
            quest.score = score;
            return this;
        }

        public QuestBuilder setStarted(boolean started) {
            quest.isStarted = started;
            return this;
        }

        public QuestBuilder setCurrent(boolean current) {
            quest.isCurrent = current;
            return this;
        }

        public QuestBuilder setEnded(boolean ended) {
            quest.isEnded = ended;
            return this;
        }

        public QuestBuilder setHasBindToMap(boolean hasBindToMap) {
            quest.hasBindToMap = hasBindToMap;
            return this;
        }

        public QuestBuilder setFavorite(boolean favorite) {
            quest.isFavorite = favorite;
            return this;
        }

        public QuestBuilder setTimeAdded(boolean timeAdded) {
            quest.isTimeAdded = timeAdded;
            return this;
        }

        public QuestBuilder setHasBindToAgent(boolean hasBindToAgent) {
            quest.hasBindToAgent = hasBindToAgent;
            return this;
        }

        public QuestBuilder setIsTimeAdded(boolean isTimeAdded) {
            quest.isTimeAdded = isTimeAdded;
            return this;
        }

        public QuestBuilder setAddTime(long addTime) {
            quest.addTime = addTime;
            return this;
        }

        public QuestBuilder setDurationTime(long durationTime) {
            quest.durationTime = durationTime;
            return this;
        }

        public QuestBuilder setIsEnded(boolean isEnded) {
            quest.isEnded = isEnded;
            return this;
        }

        public QuestBuilder setIsCurrent(boolean isCurrent) {
            quest.isCurrent = isCurrent;
            return this;
        }

        public QuestBuilder setIsStarted(boolean isStarted) {
            quest.isStarted = isStarted;
            return this;
        }

        public QuestBuilder setProgress(int progress) {
            quest.progress = progress;
            return this;
        }

        public QuestBuilder setStartTime(long startTime) {
            quest.startTime = startTime;
            return this;
        }

        public QuestBuilder setId(int id) {
            quest.id = id;
            return this;
        }

        public QuestBuilder setTaskList(ArrayList<Task> taskList) {
            quest.taskList = taskList;
            return this;
        }

        public Quest build() {
            return quest;
        }
    }
}
