package com.syscodifier.moneyspent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class ResetDBActivity extends AppCompatActivity {

    Button resetDBConfirm;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_db);

        database = FirebaseFirestore.getInstance();
        final WriteBatch writeBatch = database.batch();

        resetDBConfirm = findViewById(R.id.reset_db_confirm);
        resetDBConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.collection("money_spent").whereEqualTo("display", true).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        DocumentReference documentReference = database.collection("money_spent").document(document.getId());
                                        writeBatch.update(documentReference, "display", false);
                                    }
                                    writeBatch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(), "Done!!!", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(ResetDBActivity.this, HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });

                                } else {
                                    Log.d("kko", "Error getting documents: ", task.getException());
                                }
                            }
                        });


            }
        });

    }
}
