package com.example.preemptiveoop.barcode;
import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.preemptiveoop.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Scanner;

//https://github.com/yuriy-budiyev/code-scanner
//https://github.com/Karumi/Dexter

public class ScannerActivity extends AppCompatActivity {

    CodeScanner scsanner;
    CodeScannerView scvscanview;
    TextView tvexperimenttype;
    TextView tvresult;
    public String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


        scvscanview = findViewById(R.id.scannerView);
        tvexperimenttype = findViewById(R.id.Textview_scanresulttype);
        tvresult = findViewById(R.id.Textview_scanresult);
        scsanner = new CodeScanner(this,scvscanview);

        scsanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        data = result.getText().toString();

                        // !!!!!!!!
                        // not finished yet

                        tvexperimenttype.setText(data);

                    }
                });
            }
        });

        scvscanview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scsanner.startPreview();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
    }

    @Override
    protected void onPause() {
        scsanner.releaseResources();
        super.onPause();
    }

    public void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                scsanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(ScannerActivity.this, "Camera Permission is Required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).check();
    }


}