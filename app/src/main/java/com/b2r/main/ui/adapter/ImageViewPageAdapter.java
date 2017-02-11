package com.b2r.main.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


public class ImageViewPageAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Drawable> mDrawables;

    public ImageViewPageAdapter(Context context, ArrayList<Drawable> drawables) {
        mContext = context;
        mDrawables = drawables;
    }

    @Override
    public int getCount() {
        return mDrawables.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setImageDrawable(mDrawables.get(position));
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
