package com.b2r.main.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageViewAdapater extends RecyclerView.Adapter {
    private Context context;
    private List<String> pathList;
    private final int width;
    private final int height;

    public ImageViewAdapater(Context context, List<String> pathList, int width, int height) {
        this.context = context;
        this.pathList = pathList;
        this.width = width;
        this.height = height;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ImageView(context), width, height);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(Uri.parse("file:///android_asset/about_app/" + pathList.get(position))).asBitmap().into(((ViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return pathList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView, int width, int height) {
            super(itemView);
            imageView = ((ImageView) itemView);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(width,height));
//            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
}
