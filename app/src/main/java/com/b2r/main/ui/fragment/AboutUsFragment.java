package com.b2r.main.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.chooser.ChooserTarget;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b2r.main.R;
import com.b2r.main.ui.activity.MainActivity;
import com.balysv.materialmenu.MaterialMenuDrawable;


public class AboutUsFragment extends Fragment {

    private MainActivity mActivity;

    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AboutUsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.about_us, container, false);
        convertView.findViewById(R.id.www).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bk2rl.com")));
            }
        });
        convertView.findViewById(R.id.fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Bk2Rl/")));
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
