package com.b2r.main.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.b2r.main.R;
import com.b2r.main.ui.activity.MainActivity;
import com.b2r.main.ui.adapter.ImageViewAdapater;
import com.balysv.materialmenu.MaterialMenuDrawable;

import java.io.IOException;
import java.util.Arrays;


public class AboutAppFragment extends Fragment {

    private MainActivity mActivity;
    private RecyclerView mRecyclerView;
    private int mColumnCount;

    public static AboutAppFragment newInstance() {
        AboutAppFragment fragment = new AboutAppFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AboutAppFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mColumnCount = 3;
    }
    

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.about_app, container, false);
        mRecyclerView = (RecyclerView) convertView.findViewById(R.id.about_up_photos_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, mColumnCount));
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                try {
                    int width = mRecyclerView.getMeasuredWidth()/mColumnCount;
                    mRecyclerView.setAdapter(new ImageViewAdapater(mActivity, Arrays.asList(mActivity.getAssets().list("about_app")),width,width));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mRecyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            }
        });

        convertView.findViewById(R.id.urban_climbing_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.urban_climbing_fb))));
            }
        });
        return convertView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.getDrawerLayout().closeDrawer(GravityCompat.START);
        mActivity.getMaterialMenuDrawable().animateIconState(MaterialMenuDrawable.IconState.ARROW);
    }
}
