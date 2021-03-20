package com.example.preemptiveoop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.experiment.ExperimentList;
import com.example.preemptiveoop.user.model.User;
import com.example.preemptiveoop.user.UserLogin;

public class MainActivity extends AppCompatActivity {
    private final int CHILD_USER_LOGIN = 1;

    private TextView tvUsername;
    private Button btExperiment, btSearch, btQrcode, btPost, btLogout;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        tvUsername  = findViewById(R.id.TextView_username);
        btExperiment= findViewById(R.id.Button_experiment);
        btSearch    = findViewById(R.id.Button_search);
        btQrcode    = findViewById(R.id.Button_qrcode);
        btPost      = findViewById(R.id.Button_post);
        btLogout    = findViewById(R.id.Button_logout);

        btExperiment.setOnClickListener(this::btExperimentOnClick);
        btSearch.setOnClickListener(this::btSearchOnClick);

        /*CollectionReference trialCol = FirebaseFirestore.getInstance().collection("Trials");
        String newId = trialCol.document().getId();

        BinomialTrial t1 = new BinomialTrial("TestCreator", new Date(), null, 10);
        trialCol.document(newId).set(t1);

        trialCol.document(newId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        GenericTrial  t2 = documentSnapshot.toObject(GenericTrial.class);
                        BinomialTrial t3 = t2.toBinomialTrial();

                        Log.d("MainActivity.DB", "Trial read successful.");
                    }
                })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Log.d("MainActivity.DB", "Trial read failed.", e);
                   }
               });*/

        Intent intent = new Intent(this, UserLogin.class);
        startActivityForResult(intent, CHILD_USER_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHILD_USER_LOGIN: {
                if (resultCode == Activity.RESULT_OK) {
                    user = (User) data.getSerializableExtra("UserLogin.user");
                    tvUsername.setText(user.getUsername());
                }
            }
        }
    }

    public void btExperimentOnClick(View v) {
        Intent intent = new Intent(this, ExperimentList.class);
        intent.putExtra(".user", user);
        startActivity(intent);
    }

    public void btSearchOnClick(View v) {
        Intent intent = new Intent(this, ExperimentList.class);
        intent.putExtra(".user", user);
        intent.putExtra(".searchMode", true);
        startActivity(intent);
    }

    public void btQrcodeOnClick(View v) {}
    public void btPostOnClick(View v) {}
    public void btLogoutOnClick(View v) {}
}