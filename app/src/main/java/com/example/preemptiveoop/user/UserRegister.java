package com.example.preemptiveoop.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.user.model.DeviceId;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * The UserRegister class is the activity class that builds and manages the UI for the new user to
 * register itself to the database.
 */
public class UserRegister extends AppCompatActivity {
    private EditText etUsername;
    private EditText etContact;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        etUsername = findViewById(R.id.EditText_username);
        etContact = findViewById(R.id.EditText_contact);
        btRegister = findViewById(R.id.Button_register);

        btRegister.setOnClickListener(this::btRegisterOnClick);

        MyDialog.messageDialog(this,
                "New User",
                "Your profile is not found on DB. Please register."
        );
    }

    public void btRegisterOnClick(View v) {
        String username = etUsername.getText().toString();
        String contact = etContact.getText().toString();

        if (username.isEmpty() || contact.isEmpty()) {
            MyDialog.messageDialog(UserRegister.this,
                    "Empty Fields",
                    "Please provide username and contact info."
            );
            return;
        }

       FirebaseFirestore.getInstance().collection("Users")
               .document(username).get()
               .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            MyDialog.messageDialog(UserRegister.this,
                                    "Invalid Username",
                                    "This username already exists in the DB. Please try another."
                            );
                            return;
                        }

                       String deviceId = DeviceId.getDeviceId(getApplicationContext());

                       // add our new user to database
                       User user = new User(deviceId, username, contact);
                       user.writeToDatabase();

                       // return user object through intent
                       Intent intent = new Intent();
                       intent.putExtra(".user", user);
                       setResult(Activity.RESULT_OK, intent);
                       finish();
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {Log.d("UserRegister.DB", e.toString()); }
               });
    }
}
