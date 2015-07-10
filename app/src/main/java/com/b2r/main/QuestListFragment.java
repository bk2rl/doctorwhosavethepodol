package com.b2r.main;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.AbstractList;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestListFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static QuestListFragment newInstance(String param1, String param2) {
        QuestListFragment fragment = new QuestListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View returnView = inflater.inflate(R.layout.quest_list_fragment, container);
        ListView questListView = new ListView(getActivity());
        //TODO set quests data on external storage or database
        ArrayList<Quest> questArrayList = new ArrayList<>();
        final Quest quest = new Quest(
                "Doctor Quest", "Save the Podol!"
        );
        quest.addTask(new Task("Title 1","Description 1"));
        quest.addTask(new Task("Title 2","Description 2"));
        quest.addTask(new Task("Title 3","Description 3"));
        quest.addTask(new Task("Title 4","Description 4"));
        questArrayList.add(quest);
        questListView.setAdapter(new QuestListAdapter(questArrayList, R.layout.quest_item, new int[]{R.id.quest_item_head_text, R.id.quest_item_secondary_text}));
        questListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, TaskListFragment.newInstance(quest.getTaskArrayList())).addToBackStack(null).commit();
            }
        });
        return questListView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    private class QuestListAdapter extends BaseAdapter {

        ArrayList<Quest> mQuests;
        int mLayoutId;
        int[] mViewIds;

        QuestListAdapter(ArrayList<Quest> quests, int layoutId, int[] viewIds) {
            //TODO: check parametrs count
            mQuests = quests;
            mLayoutId = layoutId;
            mViewIds = viewIds;
        }

        @Override
        public int getCount() {
            return mQuests.size();
        }

        @Override
        public Object getItem(int position) {
            return mQuests.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getActivity(), mLayoutId, null);
            Quest quest = mQuests.get(position);
            ((TextView)convertView.findViewById(mViewIds[0])).setText(quest.title);
            ((TextView)convertView.findViewById(mViewIds[1])).setText(quest.aboutQuest);

            //((ImageView)convertView.findViewById(mViewIds[2])).setImageBitmap(BitmapFactory.decodeFile(quest.imgSrc));

            //((ImageView)convertView.findViewById(mViewIds[3])).setImageResource(R.attr);

           // convertView.setBackgroundColor(quest.backgroundColor);
            return convertView;
        }
    }

}
