package com.syscodifier.moneyspent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<ListItem> {
    public MyListAdapter(Context context, ArrayList<ListItem> items) {
        super(context, 0, items);
    }


    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        ListItem listItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_adapter, parent, false);

        }

        // Lookup view for data population

        TextView date = (TextView) convertView.findViewById(R.id.date);

        TextView item = (TextView) convertView.findViewById(R.id.item);
        TextView price = (TextView) convertView.findViewById(R.id.cost);

        // Populate the data into the template view using the data object

        date.setText(listItem.date);
        item.setText(listItem.item);
        price.setText(listItem.price);

        // Return the completed view to render on screen

        return convertView;

    }

}
