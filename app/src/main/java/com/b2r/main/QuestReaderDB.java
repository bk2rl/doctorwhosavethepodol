package com.b2r.main;

import android.util.JsonReader;
import android.util.Log;

import com.b2r.main.database.B2RDB;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuestReaderDB {

    B2RDB mDB;

    public void readJsonStream(InputStream in, B2RDB db) throws IOException {
        Log.d(Constants.DEBUG,"Begining reading from JSON");
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        mDB = db;
        try {
            readQuestArray(reader);
        } finally {
            reader.close();
            Log.d(Constants.DEBUG, "Ending reading from JSON");
        }
    }

    public void readQuestArray(JsonReader reader) throws IOException {
        ArrayList<Quest> quests = null;
        reader.beginObject();
        if (reader.nextName().equals("quests")) {
            reader.beginArray();
            while (reader.hasNext()) {
                Log.d(Constants.DEBUG,"Begining reading Quest");
                readQuest(reader);
                Log.d(Constants.DEBUG, "Ending reading from JSON");
            }
            reader.endArray();
        }
        reader.endObject();
    }

    public void readQuest(JsonReader reader) throws IOException {
        int id = -1;
        String questTitle = null;
        String questShortDescription = null;
        String questLongDescription = null;
        String questLogoSrc = null;
        int progress = 0;
        String imgSrc = null;
        String mode = null;
        int durationTime = 0;
        boolean isStarted = false;
        int exp = 0;
        boolean hasBindToMap = false;
        boolean isFavorite = false;
        boolean hasBindToAgent = false;
        String difficultyLevel = null;

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
                    durationTime = reader.nextInt();
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
                case "tasks":
                    readTaskArray(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        mDB.addQuest(id, questTitle, questShortDescription, questLongDescription,
                questLogoSrc, progress, imgSrc, mode, durationTime,
                isStarted, exp, hasBindToMap, isFavorite, hasBindToAgent, difficultyLevel);
    }

    public void readTaskArray(JsonReader reader) throws IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();

        reader.beginArray();
        while (reader.hasNext()) {
            Log.d(Constants.DEBUG,"Begining reading Task");
            readTask(reader);
            Log.d(Constants.DEBUG, "Ending reading Task");
        }
        reader.endArray();
    }

    public void readTask(JsonReader reader) throws IOException {

        int taskId = -1;
        int questId = -1;
        String taskTitle = null;
        String taskShortDescription = null;
        String taskLongDescription = null;
        String pskPass = null;
        String psksUnlock = null;
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

        mDB.addTask(taskId,questId,taskTitle,taskShortDescription,taskLongDescription,pskPass,
                psksUnlock,pskBronze,pskSilver,pskGold,state,isTaskVisible,imgSrc,difficultyLevel,
                hasBindToMap,hasBindToAgent,bronzeScore,silverScore,goldScore,latitude,longitude);

    }

    private static final String strSeparator = "__,__";

    private static String convertArrayListToString(ArrayList<String> array){
        String str = "";
        for (int i = 0;i<array.size(); i++) {
            str = str+ array.get(i);
            // Do not append comma at the end of last element
            if(i<array.size()-1){
                str = str+strSeparator;
            }
        }
        return str;
    }

    private String readUnlockKeys(JsonReader reader) throws IOException {
        ArrayList<String> keys = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            keys.add(reader.nextString());
        }
        reader.endArray();
        return convertArrayListToString(keys);
    }

    private Task.State readState(JsonReader reader) throws IOException {
        Task.State state = Task.State.ACTIVE;
        String stState = reader.nextString();
        if (stState.equals(Task.State.ACTIVE.name())) {
            state = Task.State.ACTIVE;
        } else if (stState.equals(Task.State.PASSED.name())) {
            state = Task.State.PASSED;
        } else if (stState.equals(Task.State.BRONZE.name())) {
            state = Task.State.BRONZE;
        } else if (stState.equals(Task.State.SILVER.name())) {
            state = Task.State.SILVER;
        } else if (stState.equals(Task.State.GOLD.name())) {
            state = Task.State.GOLD;
        }
        return state;
    }
}
