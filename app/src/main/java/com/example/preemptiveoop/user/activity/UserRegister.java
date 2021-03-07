package com.example.preemptiveoop.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRegister extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etContact;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        etUsername = findViewById(R.id.EditText_username);
        etPassword = findViewById(R.id.EditText_password);
        etContact = findViewById(R.id.EditText_contact);
        btRegister = findViewById(R.id.Button_register);
    }

    public void btRegisterOnClick(View v) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String contact = etContact.getText().toString();

        if (username.equals("") || password.equals("") || contact.equals("")) {
            new AlertDialog.Builder(UserRegister.this)
                    .setTitle("Empty Fields")
                    .setMessage("Please provide username, password, and contact info.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        // add our new user to database
        User user = new User(username, password, contact);
        FirebaseFirestore.getInstance().collection("Users")
                .document(username)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // return our user object through an intent
                        Intent intent = new Intent();
                        intent.putExtra("UserRegister.user", user);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) { Log.d("UserRegister.DB", "Failed to add the new user to db. Detail: ", e); }
                });
    }
}