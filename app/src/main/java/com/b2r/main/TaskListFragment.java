package com.b2r.main;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class TaskListFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TASKS = "param1";

    // TODO: Rename and change types of parameters
    private ArrayList<Task> mTasks;

    private OnFragmentInteractionListener mListener;
    private TaskListAdapter mAdapter;
    private View mFooter;

    // TODO: Rename and change types of parameters
    public static TaskListFragment newInstance(ArrayList<Task> tasks) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASKS, tasks);
        fragment.setArguments(args);
        return fragment;
    }

    public TaskListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTasks = (ArrayList<Task>) getArguments().getSerializable(ARG_TASKS);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new TaskListAdapter(mTasks, R.layout.task_item, new int[]{R.id.task_item_head_text, R.id.task_item_secondary_text, R.id.map_button});
        mFooter = View.inflate(getActivity(),R.layout.task_list_footer,null);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(null);
        getListView().addFooterView(mFooter);
        setListAdapter(mAdapter);
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
        public void onFragmentInteraction(String id);
    }

    private class TaskListAdapter extends BaseAdapter implements View.OnClickListener {

        ArrayList<Task> mTasks;
        int mLayoutId;
        int[] mViewIds;

        TaskListAdapter(ArrayList<Task> tasks, int layoutId, int[] viewIds) {
            //TODO: check parametrs count
            mTasks = tasks;
            mLayoutId = layoutId;
            mViewIds = viewIds;
        }

        @Override
        public int getCount() {
            return mTasks.size();
        }

        @Override
        public Object getItem(int position) {
            return mTasks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getActivity(), mLayoutId, null);
            Task task = mTasks.get(position);
            ((TextView)convertView.findViewById(mViewIds[0])).setText(task.getTitle());
            ((TextView)convertView.findViewById(mViewIds[1])).setText(task.getDescription());
            convertView.findViewById(mViewIds[2]).setOnClickListener(this);

            //((ImageView)convertView.findViewById(mViewIds[2])).setImageBitmap(BitmapFactory.decodeFile(quest.imgSrc));

            //((ImageView)convertView.findViewById(mViewIds[3])).setImageResource(R.attr);

            // convertView.setBackgroundColor(quest.backgroundColor);
            return convertView;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == mViewIds[2])
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new MapFragment()).addToBackStack(null).commit();
        }
    }

}
