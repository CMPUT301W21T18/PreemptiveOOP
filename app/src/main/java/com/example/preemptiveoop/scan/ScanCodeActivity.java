package com.example.preemptiveoop.scan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.preemptiveoop.R;
import com.example.preemptiveoop.scan.model.Barcode;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;

import org.json.JSONObject;

/**
 * The ScanCodeActivity class is the activity class that builds and manages the UI for the user to
 * scan a QR code or barcode.
 */
public class ScanCodeActivity extends AppCompatActivity {
    private final int CHILD_CONFIRM_EXP = 1;

    private CodeScanner cScanner;
    private CodeScannerView cSView;

    private User user;
    private String resultArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(".user");

        cSView = findViewById(R.id.scanner_view);
        cScanner = new CodeScanner(this, cSView);

        cScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) { runOnUiThread(getDecodedRunnable(result)); }
        });

        cSView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { cScanner.startPreview(); }
        });
    }

    private void startScanConfirmActivity(String experimentId, String result, User user) {
        Intent intent = new Intent(this, AfterScanActivity.class);
        intent.putExtra(".experimentId", resultArray[0]);
        intent.putExtra(".result", resultArray[1]);
        intent.putExtra(".user", user);
        startActivityForResult(intent, CHILD_CONFIRM_EXP);
    }
    
    private Runnable getDecodedRunnable(Result result) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String scanResult = result.getText();
                if (scanResult.isEmpty())
                    return;

                resultArray = jsonDecode(scanResult);

                // if result is QR code
                if (resultArray[0] != null) {
                    startScanConfirmActivity(resultArray[0], resultArray[1], user);
                    return;
                }

                // if result is barcode
                CollectionReference barcodeCol = FirebaseFirestore.getInstance().collection("Barcodes");
                barcodeCol.whereEqualTo("fuzzyString", scanResult).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshot) {
                                if (querySnapshot.size() == 0) {
                                    Log.d("BARCODE ANALYSIS", "Cannot find experiment");
                                    return;
                                }
                                Log.d("BARCODE ANALYSIS", "Found the experiment");

                                Barcode barcode = querySnapshot.iterator().next().toObject(Barcode.class);
                                resultArray = jsonDecode(barcode.getJsonString());

                                if (resultArray[0] == null) return;
                                startScanConfirmActivity(resultArray[0], resultArray[1], user);
                            }
                        });
            }
        };
        return runnable;
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
            Log.d("BARCODE ANALYSIS", "cannot fetch data from json");
        }
        return result;
    }
}