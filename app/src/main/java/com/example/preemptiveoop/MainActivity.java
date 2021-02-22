package com.example.preemptiveoop;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // simple test to our database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, String> test = new HashMap<>();
        test.put("description", "A simple test to our Firestore database.");

        db.collection("Test").document("Test #1")
                .set(test)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) { Log.d("DB", "Test data successfully added."); }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) { Log.d("DB", "Test failed. " + e.toString()); }
                });
    }
}