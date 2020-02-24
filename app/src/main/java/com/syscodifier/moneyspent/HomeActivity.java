package com.syscodifier.moneyspent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Button addBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addBill = findViewById(R.id.add_bill);
        addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddBillActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        ArrayList<ListItem> arrayOfUsers = new ArrayList<ListItem>();

// Create the adapter to convert the array to views

        MyListAdapter adapter = new MyListAdapter(this, arrayOfUsers);

// Attach the adapter to a ListView

        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);

        // Add item to adapter
        for (int i=0; i< 100; i++) {
            ListItem listItem = new ListItem("Nathan", "San Diego", "10");
            adapter.add(listItem);
        }

// Fetching some data, data has now returned

// If data was JSON, convert to ArrayList of User objects.

//        JSONArray jsonArray = ...;

//        ArrayList<User> newUsers = User.fromJson(jsonArray)

//        adapter.addAll(listItem);

    }
}
