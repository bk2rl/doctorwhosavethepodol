package com.b2r.main;

import java.util.ArrayList;

class Quest {
    public Quest(String title, String aboutQuest) {

        this.title = title;
        this.aboutQuest = aboutQuest;
        this.imgSrc = null;
        this.progress = 0;
        this.backgroundColor = 0;
        this.isFavorite = false;
        this.hasBindToMap = false;
        this.hasBindToContacts = false;
        this.isMultiplayer = false;
        this.questDifficulty = QuestDifficulty.EASY;
        this.count = 1;
        this.questLanguage = QuestLanguage.UKRAINIAN;
        this.questType = QuestType.USER;
        this.questReward = QuestReward.EXP;
        this.questPremiumState = QuestPremiumState.LEGENDARY;
        taskArrayList = new ArrayList<>();
    }

    private enum QuestType {USER, B2R, COMMUNITY, FRIEND, COMMERCIAL}
    private enum QuestDifficulty {EASY, NORMAL, HARD}
    private enum QuestLanguage {RUSSIAN,UKRAINIAN,ENGLISH}
    private enum QuestReward {EXP, ACHIEVEMENT}
    private enum QuestPremiumState {NONE, LEGENDARY}

    public ArrayList<Task> getTaskArrayList() {
        return taskArrayList;
    }

    public void addTask(Task task){
        taskArrayList.add(task);
    }

    private ArrayList<Task> taskArrayList;

    String title;
    String aboutQuest;
    String imgSrc;
    byte progress;
    int backgroundColor;


    boolean isFavorite;
    boolean hasBindToMap;
    boolean hasBindToContacts;
    boolean isMultiplayer;
    QuestDifficulty questDifficulty;
    int count;
    QuestLanguage questLanguage;
    QuestType questType;
    QuestReward questReward;
    QuestPremiumState questPremiumState;
}
