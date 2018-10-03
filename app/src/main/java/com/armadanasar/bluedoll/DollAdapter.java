package com.armadanasar.bluedoll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.armadanasar.bluedoll.Doll;
import com.armadanasar.bluedoll.R;

import java.util.ArrayList;

public class DollAdapter extends ArrayAdapter<Doll>{

    public DollAdapter(@NonNull Context context, ArrayList<Doll> dolls) {
        super(context, 0, dolls);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Doll doll = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dolls_list_item_entry_view, parent, false);
        }

        TextView dolls_list_item_dollName = convertView.findViewById(R.id.dolls_list_item_dollName);
        TextView dolls_list_item_dollDescription = convertView.findViewById(R.id.dolls_list_item_dollDescription);
        ImageView dolls_list_item_dollImage = convertView.findViewById(R.id.dolls_list_item_dollImage);

        dolls_list_item_dollName.setText(doll.name);
        dolls_list_item_dollDescription.setText(doll.description);
        dolls_list_item_dollImage.setImageResource(doll.imageResId);

        return convertView;
    }
}

