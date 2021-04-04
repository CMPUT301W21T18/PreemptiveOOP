package com.example.preemptiveoop.scan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.BinomialExp;
import com.example.preemptiveoop.experiment.model.CountExp;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.GenericExperiment;
import com.example.preemptiveoop.experiment.model.MeasurementExp;
import com.example.preemptiveoop.experiment.model.NonNegativeExp;
import com.example.preemptiveoop.trial.ExecuteTrial;
import com.example.preemptiveoop.trial.model.BinomialTrial;
import com.example.preemptiveoop.trial.model.CountTrial;
import com.example.preemptiveoop.trial.model.MeasurementTrial;
import com.example.preemptiveoop.trial.model.NonNegativeTrial;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.uiwidget.model.MyLocation;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

import java.util.Date;

public class CaptureActivity extends AppCompatActivity {
    private CodeScanner cScanner;
    private CodeScannerView cSView;

    private DocumentReference expDoc;

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

                        String resultArray[] = qrDecode(scanResult);
//                        Toast.makeText(CaptureActivity.this, resultArray[0], Toast.LENGTH_SHORT).show();
                        if (resultArray[0].isEmpty()|| resultArray[1].isEmpty()) return;
                        updateInfo(resultArray);
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
    protected void onResume() {
        super.onResume();
        cScanner.startPreview();
    }

    @Override
    protected void onPause() {
        cScanner.releaseResources();
        super.onPause();
    }

    private String[] qrDecode(String s) {
        String[] result = s.split("@@", 0);
        return result;
    }

    private void updateInfo(String resultArray[]) {
        expDoc = FirebaseFirestore.getInstance()
                .collection("Experiments").document(resultArray[0]);

        // verify the QR info to DB
        expDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        experiment = document.toObject(GenericExperiment.class).toCorrespondingExp();
                        Log.d("CAPTURE QR", "Find experiment");
//                        Toast.makeText(CaptureActivity.this, experiment.getOwner(), Toast.LENGTH_SHORT).show();

                        // Auto participation
                        if (!user.getPartiExpIdList().contains(experiment.getDatabaseId())) {
                            experiment.addExperimenter(user.getUsername());
                            experiment.writeToDatabase();

                            user.participateExp(experiment);
                            user.writeToDatabase();
                        }

                        // execute the trial in experiment
                        Number result;
                        try {
                            if (experiment instanceof MeasurementExp) {
                                result = Double.parseDouble(resultArray[1]);
                            } else {
                                result = Integer.parseInt(resultArray[1]);
                            }
                        } catch (NumberFormatException e){
                            MyDialog.errorDialog(CaptureActivity.this, "Invalid Input", "Please scan again.");
                            return;
                        }

                        if (experiment.isRequireLocation()) {
                            double la;
                            double lo;
                            try {
                                la = Double.parseDouble(resultArray[2]);
                                lo = Double.parseDouble(resultArray[3]);

                            } catch (NumberFormatException e){
                                MyDialog.errorDialog(CaptureActivity.this, "Invalid Input", "Please scan again.");
                                return;
                            }
                            myLocation = new MyLocation(la, lo);
                        }

                        if (experiment instanceof BinomialExp) {
                            expDoc.update("trials", FieldValue.arrayUnion(
                                    new BinomialTrial(user.getUsername(), new Date(), myLocation, (Integer) result, false)
                            ));
                            setResult(Activity.RESULT_OK);
                            finish();
                        }

                        if (experiment instanceof CountExp) {
                            expDoc.update("trials", FieldValue.arrayUnion(
                                    new CountTrial(user.getUsername(), new Date(), myLocation, (Integer) result, false)
                            ));
                            setResult(Activity.RESULT_OK);
                            finish();
                        }

                        if (experiment instanceof MeasurementExp) {
                            expDoc.update("trials", FieldValue.arrayUnion(
                                    new MeasurementTrial(user.getUsername(), new Date(), myLocation, (Double)result, false)
                            ));
                            setResult(Activity.RESULT_OK);
                            finish();
                        }

                        if (experiment instanceof NonNegativeExp) {
                            expDoc.update("trials", FieldValue.arrayUnion(
                                    new NonNegativeTrial(user.getUsername(), new Date(), myLocation, (Integer) result, false)
                            ));
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    } else {
                        Log.d("CAPTURE QR", "The experiment Id doesn't exist");
                        return;
                    }
                } else {
                    Log.d("CAPTURE QR", "Fail to connect to DB");
                    return;
                }
            }
        });
    }
}