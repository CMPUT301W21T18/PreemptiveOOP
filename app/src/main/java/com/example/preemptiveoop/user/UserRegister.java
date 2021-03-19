package com.example.preemptiveoop.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.user.model.User;
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
            MyDialog.errorDialog(UserRegister.this,
                    "Empty Fields",
                    "Please provide username, password, and contact info."
            );
            return;
        }

        // add our new user to database
        User user = new User(username, password, contact);
        user.writeToDatabase();
    }
}