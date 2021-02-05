package com.audiokontroller.homework.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audiokontroller.homework.R;
import com.audiokontroller.homework.data.models.Snack;

import java.util.ArrayList;

//Simple ArrayAdapter to populate the ReviewOrderFragment ListView
public class ReviewMenuListAdapter extends ArrayAdapter<Snack> {

    ArrayList<Snack> data;

    public ReviewMenuListAdapter(@NonNull Context context, int resource, ArrayList<Snack> list) {
        super(context, resource, list);
        this.data = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Snack currentSnack = data.get(position);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.review_list_item, parent, false);
        }

        TextView snackNameTV = convertView.findViewById(R.id.review_item_name);
        snackNameTV.setText(currentSnack.getName());

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
