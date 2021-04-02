package com.example.preemptiveoop.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLogin extends AppCompatActivity {
    private final int CHILD_USER_REGISTER = 1;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHILD_USER_REGISTER: {
                if (resultCode == Activity.RESULT_OK) {
                    User user = (User) data.getSerializableExtra("UserRegister.user");
                    etUsername.setText(user.getUsername());
                    etPassword.setText(user.getPassword());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void btLoginOnClick(View v) {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                MyDialog.errorDialog(UserLogin.this,
                        "Empty Fields",
                        "Please provide both username and password."
                );
                return;
            }

            FirebaseFirestore.getInstance().collection("Users")
                    .document(username)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (!documentSnapshot.exists()) {                   // query returned no document!
                                MyDialog.errorDialog(UserLogin.this,
                                        "Invalid Username",
                                        "The provided username is not a registered user. Please register."
                                );
                                return;
                            }

                            User user = documentSnapshot.toObject(User.class);
                            if (!user.getPassword().equals(password)) {
                                MyDialog.errorDialog(UserLogin.this,
                                        "Incorrect Password",
                                        "The provided password is incorrect. Please retry."
                                );
                                return;
                            }

                            // return our user object through an intent
                            Intent intent = new Intent();
                            intent.putExtra("UserLogin.user", user);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {           // query failed!
                        @Override
                        public void onFailure(@NonNull Exception e) { Log.d("UserLogin.DB", "Failed to perform query.", e); }
                    });
        }

        public void btRegisterOnClick(View v) {
            Intent intent = new Intent(this, UserRegister.class);
            startActivityForResult(intent, CHILD_USER_REGISTER);
        }

        public void btGuestOnClick(View v) {
            CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Users");
            String new_id = collectionReference.document().getId();
            User user = new User(new_id, null, null);
            collectionReference.document(new_id).set(user);

            // return our user object through an intent
            Intent intent = new Intent();
            intent.putExtra("UserLogin.user", user);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
}