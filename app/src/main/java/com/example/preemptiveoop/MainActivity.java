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
import com.example.preemptiveoop.scan.ScanCodeActivity;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.user.RetrieveProfileFragment;
import com.example.preemptiveoop.user.UserProfileFragment;
import com.example.preemptiveoop.user.model.User;
import com.example.preemptiveoop.user.UserLogin;

/**
 * The MainActivity class is the activity class that builds and manages the UI for the main menu.
 */
public class MainActivity extends AppCompatActivity {
    private final int CHILD_USER_LOGIN = 1;
    private final int CHILD_QR_SCAN = 2;

    private User user;

    private TextView tvUsername;
    private Button btExperiment, btSearch, btQrcode, btPost, btLogout, btUsrProfile, btRetrieveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //tvUsername        = findViewById(R.id.TextView_username);
        btExperiment      = findViewById(R.id.Button_experiment);
        btSearch          = findViewById(R.id.Button_search);
        btQrcode          = findViewById(R.id.Button_qrcode);
        //btPost            = findViewById(R.id.Button_post);
        //btLogout          = findViewById(R.id.Button_logout);
        btUsrProfile      = findViewById(R.id.Button_usrprofile);
        btRetrieveProfile = findViewById(R.id.Button_retrieve_profile);

        btExperiment.setOnClickListener(this::btExperimentOnClick);
        btSearch.setOnClickListener(this::btSearchOnClick);
        btUsrProfile.setOnClickListener(this::btUsrProfileOnClick);
        btRetrieveProfile.setOnClickListener((this::btRetrieveProfileOnClick));
        btQrcode.setOnClickListener(this::btQrcodeOnClick);

        Intent intent = new Intent(this, UserLogin.class);
        startActivityForResult(intent, CHILD_USER_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHILD_USER_LOGIN:
                if (resultCode == Activity.RESULT_OK)
                    user = (User) data.getSerializableExtra(".user");
                break;
            case CHILD_QR_SCAN:
                if (resultCode == Activity.RESULT_OK)
                    MyDialog.messageDialog(MainActivity.this, "Record Successfully", "New trial has been recorded");
                break;
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
        Intent intent = new Intent(this, ScanCodeActivity.class);
        intent.putExtra(".user", user);
        startActivityForResult(intent, CHILD_QR_SCAN);
    }

    public void btLogoutOnClick(View v) {}
}
