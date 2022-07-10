package com.cs5520.cs5520_a8;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

// Code referred from https://www.viralandroid.com/2016/03/display-images-in-android-gridview.html
public class StickerGridViewAdapter extends BaseAdapter {

    private final Context context;
    private final String[] imageIds;

    public StickerGridViewAdapter(Context context, String[] imageIds) {
        this.context = context;
        this.imageIds = imageIds;
    }

    @Override
    public int getCount() {
        return this.imageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(this.context);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(16, 16, 16, 16);
        } else {
            imageView = (ImageView) convertView;
        }
        int resourceId = context.getResources().getIdentifier(this.imageIds[position] , "drawable", context.getPackageName());
        imageView.setImageResource(resourceId);
        return imageView;
    }
}
