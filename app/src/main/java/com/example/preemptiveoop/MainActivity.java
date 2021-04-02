package com.example.preemptiveoop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.barcode.ScannerActivity;
import com.example.preemptiveoop.experiment.ExperimentList;
import com.example.preemptiveoop.user.UserLogin;
import com.example.preemptiveoop.user.model.User;

public class MainActivity extends AppCompatActivity {
    private final int CHILD_USER_LOGIN = 1;

    private TextView tvUsername;
    private Button btProfile, btExperiment, btQrcode, btPost, btLogout;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        tvUsername  = findViewById(R.id.TextView_username);
        btProfile   = findViewById(R.id.Button_profile);
        btExperiment= findViewById(R.id.Button_experiment);
        btQrcode    = findViewById(R.id.Button_qrcode);
        btPost      = findViewById(R.id.Button_post);
        btLogout    = findViewById(R.id.Button_logout);

        btExperiment.setOnClickListener(this::btExperimentOnClick);

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

    public void btProfileOnClick(View v) {}
    public void btExperimentOnClick(View v) {
        Intent intent = new Intent(this, ExperimentList.class);
        intent.putExtra("MainActivity.user", user);
        startActivity(intent);
    }
<<<<<<< Updated upstream
    public void btQrcodeOnClick(View v) {}
=======
    public void btSearchOnClick(View v) {
        Intent intent = new Intent(this, ExperimentList.class);
        intent.putExtra(".user", user);
        intent.putExtra(".searchMode", true);
        startActivity(intent);
    }
    public void btUsrProfileOnClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(),"USER_PROFILE");
    }
    public void btRetrieveProfileOnClick(View v) {
        RetrieveProfileFragment fragment = new RetrieveProfileFragment();
        fragment.show(getSupportFragmentManager(),"RETRIEVE_USER_PROFILE");
    }
    public void btQrcodeOnClick(View v) {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivity(intent);
    }
>>>>>>> Stashed changes
    public void btPostOnClick(View v) {}
    public void btLogoutOnClick(View v) {}
}