package com.example.preemptiveoop.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.user.model.DeviceId;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * The UserLogin class is the activity class that builds and manages the UI for the login page.
 */
public class UserLogin extends AppCompatActivity {
    private final int CHILD_USER_REGISTER = 1;

    private User user = null;

    private EditText etUserName;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etUserName = findViewById(R.id.EditText_username);
        btLogin = findViewById(R.id.Button_login);

        String deviceId = DeviceId.getDeviceId(getApplicationContext());

        CollectionReference userCol = FirebaseFirestore.getInstance().collection("Users");
        // perform query
        userCol.whereEqualTo("bondDeviceId", deviceId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot query) {
                        if (query.isEmpty()) {                   // query returned no document
                            Intent intent = new Intent(UserLogin.this, UserRegister.class);
                            startActivityForResult(intent, CHILD_USER_REGISTER);
                            return;
                        }

                        DocumentSnapshot doc = query.getDocuments().get(0);
                        user = doc.toObject(User.class);
                        etUserName.setText(user.getUsername());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        MyDialog.messageDialog(UserLogin.this, "Firestore Access Failed", e.toString());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHILD_USER_REGISTER:
                if (resultCode == Activity.RESULT_OK) {
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void btLoginOnClick(View v) {
        if (user == null)
            return;

        // return our user object through an intent
        Intent intent = new Intent();
        intent.putExtra(".user", user);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void btRegisterOnClick(View v) {
        Intent intent = new Intent(this, UserRegister.class);
        startActivityForResult(intent, CHILD_USER_REGISTER);
    }
}
