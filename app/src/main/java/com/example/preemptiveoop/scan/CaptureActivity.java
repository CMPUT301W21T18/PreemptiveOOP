package com.example.preemptiveoop.scan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.uiwidget.model.MyLocation;
import com.example.preemptiveoop.user.model.User;

import com.google.zxing.Result;

import org.json.*;

public class CaptureActivity extends AppCompatActivity {
    private final int CHILD_CONFIRM_EXP = 1;
    private CodeScanner cScanner;
    private CodeScannerView cSView;

    private String expId;
    private Experiment experiment;
    private User user;
    private MyLocation myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(".user");

        initView();
    }

    private void initView() {
        cSView = findViewById(R.id.scanner_view);
        cScanner = new CodeScanner(this, cSView);

        cScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String scanResult = result.getText();
                        if (scanResult.isEmpty()) return;
                        String resultArray[] = jsonDecode(scanResult);
                        if (resultArray[0].isEmpty()|| resultArray[1].isEmpty()) return;
                        Intent intent = new Intent(CaptureActivity.this, ScanConfirmActivity.class);
                        intent.putExtra(".experimentId", resultArray[0]);
                        intent.putExtra(".result", resultArray[1]);
                        intent.putExtra(".user", user);
                        startActivityForResult(intent, CHILD_CONFIRM_EXP);
                    }
                });
            }
        });

        cSView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cScanner.startPreview();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHILD_CONFIRM_EXP:
                if (resultCode == Activity.RESULT_OK)
                    setResult(Activity.RESULT_OK);
                    finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cScanner.startPreview();
    }

    @Override
    protected void onPause() {
        cScanner.releaseResources();
        super.onPause();
    }

    private String[] jsonDecode(String jsonString) {
        String[] result = new String[2];
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            result[0] = jsonObject.getString("experimentId");
            result[1] = jsonObject.getString("result");
        } catch (Exception e) {
            Toast.makeText(CaptureActivity.this, "JSON Fail", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}