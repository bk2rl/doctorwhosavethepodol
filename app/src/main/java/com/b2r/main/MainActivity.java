package com.b2r.main;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.b2r.main.database.B2RDB;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity implements QuestListFragment.OnFragmentInteractionListener, TaskFragment.OnFragmentInteractionListener,
ComicsFragment.OnFragmentInteractionListener {

    private static final String IS_FIRST_LOAD = "is_first_load";
    private ArrayList<Quest> mQuests;
    private Timer timer;
    private Fragment[] fragments;
    private TextView scoreView;
    private TextView timeView;
    private B2RDB mDB;
    private SharedPreferences sPref;
    private Quest mCurrentQuest;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.DEBUG, "Main Activity onCreate start");
        setContentView(R.layout.activity_main);
        fragments = new Fragment[6];
        fragments[Constants.QUEST_FRAGMENT_IDX] = QuestListFragment.newInstance("1", "2");
        fragments[Constants.TASK_FRAGMENT_IDX] = null;
        fragments[Constants.MAP_FRAGMENT_IDX] = null;
        fragments[Constants.COMICS_FRAGMENT_IDX] = new ComicsFragment();
        fragments[Constants.HELP_FRAGMENT_IDX] = null;

        mDrawerLayout = (DrawerLayout) (findViewById(R.id.drawer_layout));
        mDrawerLayout.setDrawerListener(new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close));



        Toolbar toolbar = (Toolbar) findViewById(R.id.top_tool_bar);
        scoreView = (TextView)toolbar.findViewById(R.id.scoreView);
        timeView = (TextView)toolbar.findViewById(R.id.timeView);
        setSupportActionBar(toolbar);

//        Log.d(Constants.DEBUG, "Main Activity onCreate db instance");
//        mDB = new B2RDB(this);

        Log.d(Constants.DEBUG, "Main Activity onStart start");
//        mDB.open();
        Log.d(Constants.DEBUG, "Checking for first load");
        File file = new File(getFilesDir(), "data.json");
        firstLoad = !file.exists();

        QuestReader questReader;
        questReader = new QuestReader();
        try {
            if (firstLoad) {
                firstLoad(questReader);
            } else {
                defaultLoad(questReader);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        for (Quest quest: mQuests){
            if (quest.isCurrent()) {
                scoreView.setText(String.valueOf(quest.getScore()));
            }
        }

        Log.d(Constants.DEBUG,"Main Activity onCreate end");
    }

    private void defaultLoad(QuestReader questReader) throws IOException {

        Log.d(Constants.DEBUG, "MainActivity begin reading from local quest file");
        mQuests = questReader.readJsonStream(openFileInput("data.json"));
        Log.d(Constants.DEBUG, "MainActivity end reading from local quest file");

        for (Quest quest:mQuests){
            if (quest.isCurrent()) {
                mCurrentQuest = quest;
            }
        }


        if (mCurrentQuest.isStarted()) {

            onFragmentInteraction(Constants.START_TIMER,null);

            Log.d(Constants.DEBUG, "Main Activity QuestList begin fragment transaction");
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[Constants.QUEST_FRAGMENT_IDX]).commit();
            Log.d(Constants.DEBUG, "Main Activity QuestList end fragment transaction");

        } else {

            Log.d(Constants.DEBUG, "Main Activity Comics begin fragment transaction");
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[Constants.COMICS_FRAGMENT_IDX]).commit();
            Log.d(Constants.DEBUG, "Main Activity Comics end fragment transaction");

        }
    }

    private void firstLoad(QuestReader questReader) throws IOException {

        Log.d(Constants.DEBUG, "MainActivity begin reading origin quest file");
        mQuests = questReader.readJsonStream(getAssets().open("quest.json"));
        Log.d(Constants.DEBUG, "MainActivity end reading origin quest file");

        for (Quest quest:mQuests){
            if (quest.isCurrent()) {
                mCurrentQuest = quest;
            }
        }

        Log.d(Constants.DEBUG, "Main Activity QuestList begin fragment transaction");
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[Constants.COMICS_FRAGMENT_IDX]).commit();
        Log.d(Constants.DEBUG, "Main Activity QuestList end fragment transaction");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        QuestWriter writer = null;
        OutputStream out = null;
        try {
            writer = new QuestWriter();
            out = openFileOutput("data.json", Context.MODE_PRIVATE);
            writer.writeJsonStream(out, mQuests);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int id, Bundle args) {
        switch (id) {
            case Constants.SWITCH_TO_TASK:
                int questPosition = args.getInt(Constants.QUEST_POSITION);
                int taskPosition = args.getInt(Constants.TASK_POSITION);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, TaskFragment.newInstance(questPosition,taskPosition)).addToBackStack(null).commit();
                break;
            case Constants.SWITCH_TO_MAP:
                if (fragments[com.b2r.main.Constants.MAP_FRAGMENT_IDX] == null)
                    fragments[com.b2r.main.Constants.MAP_FRAGMENT_IDX] = new MapFragment();
                fragments[com.b2r.main.Constants.MAP_FRAGMENT_IDX].setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[com.b2r.main.Constants.MAP_FRAGMENT_IDX]).addToBackStack(null).commit();
                break;
            case Constants.SWITCH_TO_LIST:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[Constants.QUEST_FRAGMENT_IDX]).commit();
                break;
            case Constants.START_TIMER:
                timer = new Timer(this, mCurrentQuest.getStartTime(), mCurrentQuest.getDurationTime(), timeView);
                timer.start();
                break;
        }
    }


    public boolean isFirstLoad() {
        return firstLoad;
    }

    public Timer getTimer(){
        return timer;
    }

    private boolean firstLoad;

    public B2RDB getDB() {
        return mDB;
    }

    public ArrayList<Quest> getQuests() {
        return mQuests;
    }

    public TextView getScoreView() {
        return scoreView;
    }

    public Quest getCurrentQuest() {
        return mCurrentQuest;
    }
}
