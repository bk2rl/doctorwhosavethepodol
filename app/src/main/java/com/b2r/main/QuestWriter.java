package com.b2r.main;


import android.util.JsonWriter;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class QuestWriter {

    public void writeJsonStream(OutputStream out, List<Quest> mQuestList) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeQuestArray(writer, mQuestList);
        writer.close();
    }


    public void writeQuestArray(JsonWriter writer, List<Quest> mQuestList) throws IOException {
        writer.beginObject();
        writer.name("quests");
        Log.d(Constants.DEBUG, "Begin writing quest");
        writer.beginArray();
        for (Quest quest : mQuestList) {
            writeQuest(writer, quest);
        }
        writer.endArray();
        writer.endObject();
        Log.d(Constants.DEBUG, "End writing quest");
    }

    public void writeQuest(JsonWriter writer, Quest mQuest) throws IOException {
        writer.beginObject();
        writer.name("questTitle").value(mQuest.getTitle());
        writer.name("questShortDescription").value(mQuest.getShortDescription());
        writer.name("questLongDescription").value(mQuest.getLongDescription());
        writer.name("endText").value(mQuest.getEndText());
        writer.name("questLogoSrc").value(mQuest.getQuestLogoSrc());
        writer.name("progress").value(mQuest.getProgress());
        writer.name("imgSrc").value(mQuest.getImgSrc());
        writer.name("mode").value(mQuest.getQuestMode().name());
        writer.name("durationTime").value(mQuest.getDurationTime());
        writer.name("startTime").value(mQuest.getStartTime());
        writer.name("isStarted").value(mQuest.isStarted());
        writer.name("pskAddTime").value(mQuest.getPskAddTime());
        writer.name("addTime").value(mQuest.getAddTime());
        writer.name("isTimeAdded").value(mQuest.isTimeAdded());
        writer.name("exp").value(mQuest.getExp());
        writer.name("hasBindToMap").value(mQuest.isHasBindToMap());
        writer.name("hasBindToAgent").value(mQuest.isHasBindToAgent());
        writer.name("isFavorite").value(mQuest.isFavorite());
        writer.name("difficultyLevel").value(mQuest.getQuestDifficulty().name());
        writer.name("score").value(mQuest.getScore());
        writer.name("isEnded").value(mQuest.isEnded());
        writer.name("isCurrent").value(mQuest.isCurrent());
        writer.name("tasks");
        writeTaskArray(writer, mQuest.getTaskList());
        writer.endObject();
    }

    public void writeTaskArray(JsonWriter writer, List<Task> taskList) throws IOException{
        writer.beginArray();
        for (Task task : taskList) {
            writeTask(writer, task);
        }
        writer.endArray();
    }

    public void writeTask(JsonWriter writer, Task task) throws IOException {
        writer.beginObject();
        writer.name("taskTitle").value(task.getTitle());
        writer.name("taskShortDescription").value(task.getShortDescription());
        writer.name("taskLongDescription").value(task.getLongDescription());
        writer.name("pskPass").value(task.getPskPass());
        writer.name("psksUnlock");
        writePsksUnlock(writer, task.getPsksUnlock());
        writer.name("pskBronze").value(task.getPskBronze());
        writer.name("pskSilver").value(task.getPskSilver());
        writer.name("pskGold").value(task.getPskGold());
        writer.name("state").value(task.getState().name());
        writer.name("isTaskVisible").value(task.isTaskVisible());
        writer.name("imgSrc").value(task.getImgSrc());
        writer.name("difficultyLevel").value(task.getDifficultyLevel().name());
        writer.name("hasBindToMap").value(task.isHasBindToMap());
        writer.name("hasBindToAgent").value(task.isHasBindToAgent());
        writer.name("bronzeScore").value(task.getBronzeScore());
        writer.name("silverScore").value(task.getSilverScore());
        writer.name("goldScore").value(task.getGoldScore());
        writer.name("latitude").value(task.getLatitude());
        writer.name("longitude").value(task.getLongitude());
        writer.endObject();
    }

    public void writePsksUnlock(JsonWriter writer, ArrayList<String> psksUnlock) throws IOException {
        writer.beginArray();
        for (String psk:psksUnlock){
            writer.value(psk);
        }
        writer.endArray();
    }

}

