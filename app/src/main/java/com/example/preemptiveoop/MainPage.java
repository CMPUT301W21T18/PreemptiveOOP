package com.example.preemptiveoop;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.user.activity.UserLogin;

public class MainPage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        Intent intent = new Intent(this, UserLogin.class);
        startActivity(intent);
    }
}
