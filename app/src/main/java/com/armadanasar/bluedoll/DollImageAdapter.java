package com.armadanasar.bluedoll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DollImageAdapter extends ArrayAdapter<Doll> {

    public DollImageAdapter(@NonNull Context context, @NonNull ArrayList<Doll> dolls) {
        super(context, 0, dolls);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Doll doll = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_spinner_item, parent, false);
        }

        ImageView image_spinner_item_imageView = convertView.findViewById(R.id.image_spinner_item_imageView);
        image_spinner_item_imageView.setImageResource(doll.imageResId);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
        //return super.getDropDownView(position, convertView, parent);
    }
}
