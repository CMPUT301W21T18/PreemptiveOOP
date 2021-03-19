package com.example.preemptiveoop.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.user.User;

public class ProfileActivity extends AppCompatActivity {


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_retrive);
        //button set later
        user = (User) getIntent().getSerializableExtra("info");
        TextView tvUserName = findViewById(R.id.tv_username_retrieve);
        TextView tvEmail = findViewById(R.id.tv_email_after_retrive);
        tvUserName.setText(user.getUsername());
        tvEmail.setText(user.getContact());
        findViewById(R.id.button_back_editprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}