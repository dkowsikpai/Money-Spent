package com.syscodifier.moneyspent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Button addBill, resetBill;
    Integer totalPrice = 0;
    TextView total_display;
    private FirebaseFirestore database;

    boolean temp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseFirestore.getInstance();

        total_display = findViewById(R.id.display_cost);

        addBill = findViewById(R.id.add_bill);
        addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddBillActivity.class);
                startActivity(intent);

            }
        });


        resetBill = findViewById(R.id.reset_db_activity);
        resetBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ResetDBActivity.class);
                startActivity(intent);
            }
        });


        database.collection("money_spent").whereEqualTo("display", true).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<ListItem> arrayOfItems = new ArrayList<ListItem>();

                            // Create the adapter to convert the array to views

                            MyListAdapter adapter = new MyListAdapter(getApplicationContext(), arrayOfItems);

                            // Attach the adapter to a ListView

                            ListView listView = (ListView) findViewById(R.id.listView);

                            listView.setAdapter(adapter);

                            // Add item to adapter
                            /* for (int i=0; i< 100; i++) {
                                ListItem listItem = new ListItem("Nathan", "San Diego", "10");
                                adapter.add(listItem);
                            } */

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ListItem listItem = new ListItem(document.getData().get("date").toString(), document.getData().get("item").toString(), document.getData().get("price").toString());
                                adapter.add(listItem);
                                totalPrice += Integer.parseInt(document.getData().get("price").toString());
                                total_display.setText(totalPrice.toString() + " Rs");
                                temp = true;
                            }

                            if (!temp){
                                Toast.makeText(getApplicationContext(), "Empty !!!", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Log.d("kko", "Error getting documents: ", task.getException());
                        }
                    }
                });

        Toast.makeText(getApplicationContext(), "Please wait while database loading !!!", Toast.LENGTH_LONG).show();

    }
}
