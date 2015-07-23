package com.b2r.main;

import android.util.JsonReader;
import android.util.Log;

import com.b2r.main.database.B2RDB;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuestReader {

    public ArrayList<Quest> readJsonStream(InputStream in) throws IOException {
        Log.d(Constants.DEBUG, "Begining reading from JSON");
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readQuestArray(reader);
        } finally {
            reader.close();
            Log.d(Constants.DEBUG, "Ending reading from JSON");
        }
    }

    public ArrayList<Quest> readQuestArray(JsonReader reader) throws IOException {
        ArrayList<Quest> quests = new ArrayList<>();
        reader.beginObject();
        if (reader.nextName().equals("quests")) {
            reader.beginArray();
            while (reader.hasNext()) {
                Log.d(Constants.DEBUG,"Begining reading Quest");
                quests.add(readQuest(reader));
                Log.d(Constants.DEBUG, "Ending reading from JSON");
            }
            reader.endArray();
        }
        reader.endObject();
        return quests;
    }

    public Quest readQuest(JsonReader reader) throws IOException {
        int id = -1;
        String questTitle = null;
        String questShortDescription = null;
        String questLongDescription = null;
        String questLogoSrc = null;
        int progress = 0;
        int score = 0;
        String imgSrc = null;
        String mode = null;
        long durationTime = 0;
        long startTime = 0;
        boolean isStarted = false;
        int exp = 0;
        boolean hasBindToMap = false;
        boolean isCurrent = false;
        boolean isFavorite = false;
        boolean hasBindToAgent = false;
        String difficultyLevel = null;
        ArrayList<Task> taskList = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "questTitle":
                    questTitle = reader.nextString();
                    break;
                case "questShortDescription":
                    questShortDescription = reader.nextString();
                    break;
                case "questLongDescription":
                    questLongDescription = reader.nextString();
                    break;
                case "questLogoSrc":
                    questLogoSrc = reader.nextString();
                    break;
                case "progress":
                    progress = reader.nextInt();
                    break;
                case "imgSrc":
                    imgSrc = reader.nextString();
                    break;
                case "mode":
                    mode = reader.nextString();
                    break;
                case "durationTime":
                    durationTime = reader.nextLong();
                    break;
                case "startTime":
                    durationTime = reader.nextLong();
                    break;
                case "isStarted":
                    isStarted = reader.nextBoolean();
                    break;
                case "exp":
                    exp = reader.nextInt();
                    break;
                case "hasBindToMap":
                    hasBindToMap = reader.nextBoolean();
                    break;
                case "hasBindToAgent":
                    hasBindToAgent = reader.nextBoolean();
                    break;
                case "isFavorite":
                    isFavorite = reader.nextBoolean();
                    break;
                case "difficultyLevel":
                    difficultyLevel = reader.nextString();
                    break;
                case "score":
                    score = reader.nextInt();
                    break;
                case "isCurrent":
                    isCurrent = reader.nextBoolean();
                    break;
                case "tasks":
                    taskList = readTaskArray(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Quest(questTitle, questShortDescription, questLongDescription,
                questLogoSrc, imgSrc,  mode, difficultyLevel, progress, durationTime, startTime,
                exp, isStarted, hasBindToMap, isFavorite, hasBindToAgent, taskList, score, isCurrent);
    }

    public ArrayList<Task> readTaskArray(JsonReader reader) throws IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();

        reader.beginArray();
        while (reader.hasNext()) {
            Log.d(Constants.DEBUG,"Begining reading Task");
            tasks.add(readTask(reader));
            Log.d(Constants.DEBUG, "Ending reading Task");
        }
        reader.endArray();

        return tasks;
    }

    public Task readTask(JsonReader reader) throws IOException {

        int taskId = -1;
        int questId = -1;
        String taskTitle = null;
        String taskShortDescription = null;
        String taskLongDescription = null;
        String pskPass = null;
        ArrayList<String> psksUnlock = null;
        String pskBronze = null;
        String pskSilver = null;
        String pskGold = null;
        String state = null;
        boolean isTaskVisible = false;
        String imgSrc = null;
        String difficultyLevel = null;
        boolean hasBindToMap = false;
        boolean hasBindToAgent = false;
        int bronzeScore = 0;
        int silverScore = 0;
        int goldScore = 0;
        double latitude = 0.0;
        double longitude = 0.0;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("taskId")) {
                taskId = reader.nextInt();
            } else if (name.equals("questId")) {
                questId = reader.nextInt();
            } else if (name.equals("taskTitle")) {
                taskTitle = reader.nextString();
            } else if (name.equals("taskShortDescription")) {
                taskShortDescription = reader.nextString();
            } else if (name.equals("taskLongDescription")) {
                taskLongDescription = reader.nextString();
            } else if (name.equals("pskPass")) {
                pskPass = reader.nextString();
            } else if (name.equals("psksUnlock")) {
                psksUnlock = readUnlockKeys(reader);
            } else if (name.equals("pskBronze")) {
                pskBronze = reader.nextString();
            } else if (name.equals("pskSilver")) {
                pskSilver = reader.nextString();
            } else if (name.equals("pskGold")) {
                pskGold = reader.nextString();
            } else if (name.equals("state")) {
                state = reader.nextString();
            } else if (name.equals("isTaskVisible")) {
                isTaskVisible = reader.nextBoolean();
            } else if (name.equals("imgSrc")) {
                imgSrc = reader.nextString();
            } else if (name.equals("difficultyLevel")) {
                difficultyLevel = reader.nextString();
            } else if (name.equals("hasBindToMap")) {
                hasBindToMap = reader.nextBoolean();
            } else if (name.equals("hasBindToAgent")) {
                hasBindToAgent = reader.nextBoolean();
            } else if (name.equals("bronzeScore")) {
                bronzeScore = reader.nextInt();
            } else if (name.equals("silverScore")) {
                silverScore = reader.nextInt();
            } else if (name.equals("goldScore")) {
                goldScore = reader.nextInt();
            } else if (name.equals("latitude")) {
                latitude = reader.nextDouble();
            } else if (name.equals("longitude")) {
                longitude = reader.nextDouble();
            } else {
                reader.skipValue();
            }
        }

        reader.endObject();

        return new Task(taskTitle,taskShortDescription,taskLongDescription, imgSrc, state, difficultyLevel, pskPass,
                psksUnlock,pskBronze,pskSilver,pskGold,isTaskVisible,hasBindToMap,hasBindToAgent,latitude,longitude,bronzeScore,silverScore,goldScore);

    }

    private ArrayList<String> readUnlockKeys(JsonReader reader) throws IOException {
        ArrayList<String> keys = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            keys.add(reader.nextString());
        }
        reader.endArray();
        return keys;
    }

}
