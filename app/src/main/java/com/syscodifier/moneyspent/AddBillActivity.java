package com.syscodifier.moneyspent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBillActivity extends AppCompatActivity {

    Button addData;
    EditText date, item, price;
    String dateData, itemData, priceData;

    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        addData = findViewById(R.id.add_data);
        date = findViewById(R.id.date);
        item = findViewById(R.id.itemPurchased);
        price = findViewById(R.id.price);

        database = FirebaseFirestore.getInstance();

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateData = date.getText().toString();
                priceData = price.getText().toString();
                itemData = item.getText().toString();

                Map<String, Object> addItem = new HashMap<>();
                addItem.put("date", dateData);
                addItem.put("price", priceData);
                addItem.put("item", itemData);
                addItem.put("display", true);

                database.collection("money_spent").add(addItem)
                        .addOnSuccessListener(AddBillActivity.this, new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Successfully added item", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AddBillActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed added item !!! " + e , Toast.LENGTH_LONG).show();
                            }
                        });
                Toast.makeText(getApplicationContext(), "Please Wait !!!", Toast.LENGTH_LONG).show();


            }
        });



    }

}
