package com.example.preemptiveoop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLogin extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etUsername = findViewById(R.id.EditText_username);
        etPassword = findViewById(R.id.EditText_password);
        btLogin = findViewById(R.id.Button_login);
        btRegister = findViewById(R.id.Button_register);
    }

    public void btLoginOnClick(View v) {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            // perform our database query
            // Note: This is a guide, so every object reference is explicitly written. When writing for real, the reference can be cascaded.

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference usersCol = db.collection("Users");

            usersCol.document(username)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {      // query failed!
                            if (!task.isSuccessful()) {
                                Log.d("UserLogin.DB", "Failed to perform query. Detail: ", task.getException());
                                return;
                            }

                            DocumentSnapshot document = task.getResult();
                            if (!document.exists()) {                                       // query returned no document!
                                new AlertDialog.Builder(UserLogin.this)
                                        .setTitle("Invalid Username")
                                        .setMessage("The provided username is not a registered User. Please register.")
                                        .setPositiveButton("OK", null)
                                        .show();
                                return;
                            }

                            User user = document.toObject(User.class);
                            if (!user.getPassword().equals(password)) {
                                new AlertDialog.Builder(UserLogin.this)
                                        .setTitle("Invalid Username")
                                        .setMessage("The provided username is not a registered User. Please register.")
                                        .setPositiveButton("OK", null)
                                        .show();
                                return;
                            }

                            // return our user object through an intent
                            Intent intent = new Intent();
                            intent.putExtra("UserLogin.userObject", user);
                            setResult(0, intent);
                            finish();
                        }
                    });
        }
}