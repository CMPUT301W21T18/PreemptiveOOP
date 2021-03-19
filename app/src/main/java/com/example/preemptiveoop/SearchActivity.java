package com.example.preemptiveoop.user.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchActivity extends AppCompatActivity {


    private TextView tvUserName;
    private TextView tvEmail;
    private EditText editTextTextPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        tvUserName = findViewById(R.id.tv_username);
        tvEmail = findViewById(R.id.tv_emailadd);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);

        findViewById(R.id.btn_retrieve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = editTextTextPersonName.getText().toString();
                search(inputName);
            }


        });
    }



    private void search(String inputName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("Users");
        users.document(inputName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {
                            Toast.makeText(SearchActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        User user = documentSnapshot.toObject(User.class);
                        tvUserName.setText(user.getUsername());
                        tvEmail.setText(user.getContact());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SearchActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}