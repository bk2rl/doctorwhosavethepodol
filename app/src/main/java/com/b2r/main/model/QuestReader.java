package com.b2r.main.model;

import android.util.JsonReader;
import android.util.Log;

import com.b2r.main.Constants;

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
                Log.d(Constants.DEBUG, "Begining reading Quest");
                quests.add(readQuest(reader));
                Log.d(Constants.DEBUG, "Ending reading from JSON");
            }
            reader.endArray();
        }
        reader.endObject();
        return quests;
    }

    private Quest readQuest(JsonReader reader) throws IOException {
        Quest.QuestBuilder questBuilder = new Quest.QuestBuilder();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    questBuilder.setId(reader.nextInt());
                    break;
                case "questTitle":
                    questBuilder.setTitle(reader.nextString());
                    break;
                case "questShortDescription":
                    questBuilder.setShortDescription(reader.nextString());
                    break;
                case "questLongDescription":
                    questBuilder.setLongDescription(reader.nextString());
                    break;
                case "endText":
                    questBuilder.setEndText(reader.nextString());
                    break;
                case "questLogoSrc":
                    questBuilder.setQuestLogoSrc(reader.nextString());
                    break;
                case "progress":
                    questBuilder.setProgress(reader.nextInt());
                    break;
                case "imgSrc":
                    questBuilder.setImgSrc(reader.nextString());
                    break;
                case "mode":
                    questBuilder.setQuestMode(Quest.QuestMode.valueOf(reader.nextString()));
                    break;
                case "durationTime":
                    questBuilder.setDurationTime(reader.nextLong());
                    break;
                case "pskAddTime":
                    questBuilder.setPskAddTime(reader.nextString());
                    break;
                case "addTime":
                    questBuilder.setAddTime(reader.nextLong());
                    break;
                case "isTimeAdded":
                    questBuilder.setTimeAdded(reader.nextBoolean());
                    break;
                case "startTime":
                    questBuilder.setStartTime(reader.nextLong());
                    break;
                case "isStarted":
                    questBuilder.setStarted(reader.nextBoolean());
                    break;
                case "exp":
                    questBuilder.setExp(reader.nextInt());
                    break;
                case "hasBindToMap":
                    questBuilder.setHasBindToMap(reader.nextBoolean());
                    break;
                case "hasBindToAgent":
                    questBuilder.setHasBindToAgent(reader.nextBoolean());
                    break;
                case "isFavorite":
                    questBuilder.setFavorite(reader.nextBoolean());
                    break;
                case "difficultyLevel":
                    questBuilder.setQuestDifficulty(Quest.QuestDifficulty.valueOf(reader.nextString()));
                    break;
                case "score":
                    questBuilder.setScore(reader.nextInt());
                    break;
                case "isCurrent":
                    questBuilder.setCurrent(reader.nextBoolean());
                    break;
                case "isEnded":
                    questBuilder.setEnded(reader.nextBoolean());
                    break;
                case "tasks":
                    questBuilder.setTaskList(readTaskArray(reader));
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return questBuilder.build();
    }

    private ArrayList<Task> readTaskArray(JsonReader reader) throws IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();

        reader.beginArray();
        while (reader.hasNext()) {
            Log.d(Constants.DEBUG, "Begining reading Task");
            tasks.add(readTask(reader));
            Log.d(Constants.DEBUG, "Ending reading Task");
        }
        reader.endArray();

        return tasks;
    }

    private Task readTask(JsonReader reader) throws IOException {

        Task.TaskBuilder taskBuilder = new Task.TaskBuilder();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "taskId":
                    taskBuilder.setTaskId(reader.nextInt());
                    break;
                case "questId":
                    taskBuilder.setQuestId(reader.nextInt());
                    break;
                case "taskTitle":
                    taskBuilder.setTitle(reader.nextString());
                    break;
                case "taskShortDescription":
                    taskBuilder.setShortDescription(reader.nextString());
                    break;
                case "taskLongDescription":
                    taskBuilder.setLongDescription(reader.nextString());
                    break;
                case "pskPass":
                    taskBuilder.setPskPass(reader.nextString());
                    break;
                case "psksUnlock":
                    taskBuilder.setPsksUnlock(readUnlockKeys(reader));
                    break;
                case "pskBronze":
                    taskBuilder.setPskBronze(reader.nextString());
                    break;
                case "pskSilver":
                    taskBuilder.setPskSilver(reader.nextString());
                    break;
                case "pskGold":
                    taskBuilder.setPskGold(reader.nextString());
                    break;
                case "state":
                    taskBuilder.setState(Task.State.valueOf(reader.nextString()));
                    break;
                case "isTaskVisible":
                    taskBuilder.setIsTaskVisible(reader.nextBoolean());
                    break;
                case "imgSrc":
                    taskBuilder.setImgSrc(reader.nextString());
                    break;
                case "difficultyLevel":
                    taskBuilder.setDifficultyLevel(Task.TaskDifficulty.valueOf(reader.nextString()));
                    break;
                case "hasBindToMap":
                    taskBuilder.setHasBindToMap(reader.nextBoolean());
                    break;
                case "hasBindToAgent":
                    taskBuilder.setHasBindToAgent(reader.nextBoolean());
                    break;
                case "bronzeScore":
                    taskBuilder.setBronzeScore(reader.nextInt());
                    break;
                case "silverScore":
                    taskBuilder.setSilverScore(reader.nextInt());
                    break;
                case "goldScore":
                    taskBuilder.setGoldScore(reader.nextInt());
                    break;
                case "latitude":
                    taskBuilder.setLatitude(reader.nextDouble());
                    break;
                case "longitude":
                    taskBuilder.setLongitude(reader.nextDouble());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }

        reader.endObject();

        return taskBuilder.build();

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
