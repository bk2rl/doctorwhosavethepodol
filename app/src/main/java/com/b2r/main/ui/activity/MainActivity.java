package com.b2r.main.ui.activity;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2r.main.Constants;
import com.b2r.main.model.NavigationDrawerMenuItem;
import com.b2r.main.model.Quest;
import com.b2r.main.ui.fragment.AboutAppFragment;
import com.b2r.main.ui.fragment.AboutUsFragment;
import com.b2r.main.ui.fragment.QuestListFragment;
import com.b2r.main.model.QuestReader;
import com.b2r.main.model.QuestWriter;
import com.b2r.main.R;
import com.b2r.main.ui.Timer;
import com.b2r.main.ui.adapter.NavigationDrawerListAdapter;
import com.b2r.main.ui.fragment.ComicsFragment;
import com.b2r.main.ui.fragment.HelpFragment;
import com.b2r.main.ui.fragment.MapFragment;
import com.b2r.main.ui.fragment.TaskFragment;
import com.balysv.materialmenu.MaterialMenuDrawable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements QuestListFragment.OnFragmentInteractionListener, TaskFragment.OnFragmentInteractionListener,
        ComicsFragment.OnFragmentInteractionListener, HelpFragment.OnFragmentInteractionListener {

    private ArrayList<Quest> mQuests;
    private Timer timer = null;
    private Fragment[] fragments;
    private TextView scoreView;
    private TextView timeView;
    private Quest mCurrentQuest;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton floatingActionButton;
    private ImageView comicButton;
    private ExpandableListView drawerListView;
    private MaterialMenuDrawable materialMenuDrawable;
    private boolean firstLoad;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.DEBUG, "Main Activity onCreate start");
        setContentView(R.layout.activity_main);
        fragments = new Fragment[10];
        fragments[Constants.QUEST_FRAGMENT_IDX] = QuestListFragment.newInstance();
        fragments[Constants.TASK_FRAGMENT_IDX] = null;
        fragments[Constants.MAP_FRAGMENT_IDX] = null;
        fragments[Constants.COMICS_FRAGMENT_IDX] = null;
        fragments[Constants.HELP_FRAGMENT_IDX] = null;

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        toolbar = (Toolbar) findViewById(R.id.top_tool_bar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);

        materialMenuDrawable = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.REGULAR);
        timeView = (TextView) toolbar.findViewById(R.id.timeView);
        scoreView = (TextView) toolbar.findViewById(R.id.scoreView);
        comicButton = (ImageView) toolbar.findViewById(R.id.comicButton);
        comicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentInteraction(Constants.SWITCH_TO_COMICS_WITH_BACKSTACK, null);
            }
        });

        findViewById(R.id.helpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentInteraction(Constants.SWITCH_TO_HELP, null);
            }
        });

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(materialMenuDrawable);

        Log.d(Constants.DEBUG, "Main Activity onStart start");
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Quest quest : mQuests) {
            if (quest.isCurrent()) {
                scoreView.setText(String.valueOf(quest.getScore()));
            }
        }

        setNavigationDrawer();

        Log.d(Constants.DEBUG, "Main Activity onCreate end");
    }

    private void setNavigationDrawer() {

        mDrawerLayout = (DrawerLayout) (findViewById(R.id.drawer_layout));
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            public boolean isDrawerOpened;

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (getFragmentManager().getBackStackEntryCount() < 1) {
                    materialMenuDrawable.setTransformationOffset(
                            MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                            isDrawerOpened ? 2 - slideOffset : slideOffset
                    );
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpened = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_IDLE) {
                    if (isDrawerOpened) {
                        materialMenuDrawable.setIconState(MaterialMenuDrawable.IconState.ARROW);
                    } else {
                        if (getFragmentManager().getBackStackEntryCount() < 1) {
                            materialMenuDrawable.setIconState(MaterialMenuDrawable.IconState.BURGER);
                        }
                    }
                }
            }
        });

        ArrayList<NavigationDrawerMenuItem> navigationDrawerMenuItems = new ArrayList<>();
        navigationDrawerMenuItems.add(new NavigationDrawerMenuItem().setTitle(getResources().getString(R.string.passwords)).setDrawableResource(R.drawable.key));
        navigationDrawerMenuItems.add(new NavigationDrawerMenuItem().setTitle(getResources().getString(R.string.about_app)).setDrawableResource(R.drawable.about_app));
        navigationDrawerMenuItems.add(new NavigationDrawerMenuItem().setTitle(getResources().getString(R.string.about_us)).setDrawableResource(R.drawable.about_bk2rl));

        int groupTo[] = {R.id.navigation_drawer_menu_item_title_text, R.id.navigation_drawer_menu_item_image};
        int childTo[] = {R.id.navigation_drawer_menu_child_item_title_text, R.id.navigation_drawer_menu_child_item_password};

        drawerListView = (ExpandableListView) findViewById(R.id.navList);
        drawerListView.setGroupIndicator(null);
        drawerListView.setAdapter(new NavigationDrawerListAdapter(this, navigationDrawerMenuItems, mCurrentQuest.getTaskList(), R.layout.navigation_drawer_menu_item_group_layout, groupTo,
                R.layout.navigation_drawer_menu_item_child_layout, childTo));

        drawerListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                final AlertDialog.Builder builder;
                switch (i) {
                    case 1:
                        onFragmentInteraction(Constants.SWITCH_TO_ABOUT_APP, null);
                        return true;
                    case 2:
                        onFragmentInteraction(Constants.SWITCH_TO_ABOUT_US, null);
                        return true;
                }
                return false;
            }
        });
    }

    private void defaultLoad(QuestReader questReader) throws IOException {

        Log.d(Constants.DEBUG, "MainActivity begin reading from local quest file");
        mQuests = questReader.readJsonStream(openFileInput("data.json"));
        Log.d(Constants.DEBUG, "MainActivity end reading from local quest file");

        for (Quest quest : mQuests) {
            if (quest.isCurrent()) {
                mCurrentQuest = quest;
            }
        }


        if (mCurrentQuest.isStarted()) {

            if (!mCurrentQuest.isEnded()) {
                onFragmentInteraction(Constants.START_TIMER, null);
            }
            onFragmentInteraction(Constants.SWITCH_TO_LIST, null);

        } else {

            onFragmentInteraction(Constants.SWITCH_TO_COMICS, null);

        }
    }

    private void firstLoad(QuestReader questReader) throws IOException {

        Log.d(Constants.DEBUG, "MainActivity begin reading origin quest file");
        mQuests = questReader.readJsonStream(getAssets().open("quest.json"));
        Log.d(Constants.DEBUG, "MainActivity end reading origin quest file");

        for (Quest quest : mQuests) {
            if (quest.isCurrent()) {
                mCurrentQuest = quest;
            }
        }

        onFragmentInteraction(Constants.SWITCH_TO_COMICS, null);
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
            materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.BURGER);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
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
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.BURGER);
                } else if (getFragmentManager().getBackStackEntryCount() > 1) {
                    getFragmentManager().popBackStack();
                } else if (getFragmentManager().getBackStackEntryCount() == 1) {
                    getFragmentManager().popBackStack();
                    materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.BURGER);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);
                }
                return true;
        }
        return false;
    }


    @Override
    public void onFragmentInteraction(int id, Bundle args) {
        switch (id) {
            case Constants.SWITCH_TO_TASK:
                int questPosition = args.getInt(Constants.QUEST_POSITION);
                int taskPosition = args.getInt(Constants.TASK_POSITION);
                materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);
                getFragmentManager().beginTransaction().setCustomAnimations(R.animator.task_in, R.animator.quest_out, R.animator.quest_in, R.animator.task_out).replace(R.id.fragmentContainer, TaskFragment.newInstance(questPosition, taskPosition)).addToBackStack(null).commit();
                break;
            case Constants.SWITCH_TO_MAP:
                if (fragments[com.b2r.main.Constants.MAP_FRAGMENT_IDX] == null)
                    fragments[com.b2r.main.Constants.MAP_FRAGMENT_IDX] = new MapFragment();
                fragments[com.b2r.main.Constants.MAP_FRAGMENT_IDX].setArguments(args);
                materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[com.b2r.main.Constants.MAP_FRAGMENT_IDX]).addToBackStack(null).commit();
                break;
            case Constants.SWITCH_TO_LIST:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[Constants.QUEST_FRAGMENT_IDX]).commit();
                break;
            case Constants.SWITCH_TO_COMICS:
                if (fragments[Constants.COMICS_FRAGMENT_IDX] == null)
                    fragments[Constants.COMICS_FRAGMENT_IDX] = new ComicsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[Constants.COMICS_FRAGMENT_IDX]).commit();
                break;
            case Constants.SWITCH_TO_HELP:
                if (fragments[Constants.HINT_FRAGMENT_IDX] == null)
                    fragments[Constants.HINT_FRAGMENT_IDX] = new HelpFragment();
                materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[Constants.HINT_FRAGMENT_IDX]).addToBackStack(null).commit();
                break;
            case Constants.SWITCH_TO_COMICS_WITH_BACKSTACK:
                if (fragments[Constants.COMICS_FRAGMENT_IDX] == null)
                    fragments[Constants.COMICS_FRAGMENT_IDX] = new ComicsFragment();
                materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[Constants.COMICS_FRAGMENT_IDX]).addToBackStack(null).commit();
                break;
            case Constants.SWITCH_TO_ABOUT_APP:
                if (fragments[Constants.SWITCH_TO_ABOUT_APP] == null)
                    fragments[Constants.SWITCH_TO_ABOUT_APP] = AboutAppFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[Constants.SWITCH_TO_ABOUT_APP]).addToBackStack(null).commit();
                break;
            case Constants.SWITCH_TO_ABOUT_US:
                if (fragments[Constants.SWITCH_TO_ABOUT_US] == null)
                    fragments[Constants.SWITCH_TO_ABOUT_US] = AboutUsFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragments[Constants.SWITCH_TO_ABOUT_US]).addToBackStack(null).commit();
                break;
            case Constants.START_TIMER:
                if (timer != null) {
                    timer.interrupt();
                }
                timer = new Timer(this, mCurrentQuest.getStartTime(), mCurrentQuest.getDurationTime(), timeView);
                timer.start();
                break;
            case Constants.STOP_TIMER:
                if (timer != null) {
                    timer.interrupt();
                    long timeLeft = mCurrentQuest.getDurationTime() -
                            (GregorianCalendar.getInstance().getTimeInMillis() - mCurrentQuest.getStartTime());
                    mCurrentQuest.addScore((int) TimeUnit.MILLISECONDS.toMinutes(timeLeft));
                    mCurrentQuest.setIsEnded(true);
                    timeView.setText(String.format("%d:%02d:%02d", 0, 0, 0));
                    scoreView.setText(String.valueOf(mCurrentQuest.getScore()));
                }
                break;
        }
    }

    public boolean onFragmentInteraction(int id) {
        switch (id) {
            case Constants.CHANGE_FOOTER_TO_END_STATE:
                if (fragments[Constants.QUEST_FRAGMENT_IDX] != null) {
                    mCurrentQuest.setIsEnded(true);
                    return true;
                } else {
                    return false;
                }
        }
        return false;
    }

    public FloatingActionButton getFloatingActionButton() {
        return floatingActionButton;
    }

    public AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }

    public ArrayList<Quest> getQuests() {
        return mQuests;
    }

    public TextView getScoreView() {
        return scoreView;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public MaterialMenuDrawable getMaterialMenuDrawable() {
        return materialMenuDrawable;
    }

    public Quest getCurrentQuest() {
        return mCurrentQuest;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

}
