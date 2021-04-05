package com.example.preemptiveoop.scan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.BinomialExp;
import com.example.preemptiveoop.experiment.model.CountExp;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.MeasurementExp;
import com.example.preemptiveoop.experiment.model.NonNegativeExp;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class QRcodeActivity extends AppCompatActivity {
    private final int CHILD_LOCATION_PICKER = 1;
    private ImageView ivQRcode;
    private EditText etResult;
    private Button btSuccess;
    private Button btFailure;
    private Button btGenerate;

    private HashMap hashMap;
    private Bitmap bitmap;
    private Experiment experiment;
    private int resultInt;
    private double resultDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_rcode);

        Intent intent = getIntent();
        experiment = (Experiment) intent.getSerializableExtra(".experiment");

        initView();
        setViewsPerExpType();
        resultInt = -1; // set the initial value for non-input detection
    }

    private void initView() {
        hashMap = new HashMap();
        ivQRcode = findViewById(R.id.ImageView_qr);
        etResult = findViewById(R.id.EditText_qr_result);
        btSuccess = findViewById(R.id.Button_qr_success);
        btFailure = findViewById(R.id.Button_qr_failure);
        btGenerate = findViewById(R.id.Button_qr_generate);

        btGenerate.setOnClickListener(this::btGenerateOnClick);
        btSuccess.setOnClickListener(this::btSuccessOnClick);
        btFailure.setOnClickListener(this::btFailureOnClick);

    }

    private void setViewsPerExpType() {
        if (experiment instanceof BinomialExp) {
            etResult.setHint("Choose result below");
            etResult.setEnabled(false);
            return;
        }

        btSuccess.setVisibility(View.GONE);
        btFailure.setVisibility(View.GONE);

        if (experiment instanceof MeasurementExp) {
            etResult.setHint("Enter mockup measurement here");
            return;
        }

        if (experiment instanceof NonNegativeExp) {
            etResult.setHint("Enter mockup non-negative number here");
            return;
        }

        if (experiment instanceof CountExp) {
            etResult.setHint("No result input required");
            etResult.setEnabled(false);
            return;
        }
    }

    public void btSuccessOnClick(View v) {
        etResult.setText("Success");
        resultInt = 1;

    }

    public void btFailureOnClick(View v) {
        etResult.setText("Failure");
        resultInt = 0;
    }

    public void btGenerateOnClick(View v) {
        if (experiment instanceof BinomialExp) {
            if (resultInt == -1) {
                MyDialog.errorDialog(QRcodeActivity.this, "Invalid Input", "Please choose the valid result.");
                return;
            }
            create_QRcode(jsonEncode(experiment.getDatabaseId(), resultInt));
            return;
        }

        if (experiment instanceof CountExp) {
            create_QRcode(jsonEncode(experiment.getDatabaseId(), 1));
            return;
        }

        if (experiment instanceof MeasurementExp) {
            String resultStr = etResult.getText().toString();
            if (resultStr.isEmpty()) {
                MyDialog.errorDialog(QRcodeActivity.this, "Invalid Input", "Please enter the valid result.");
                return;
            }
            try { resultDouble = Double.parseDouble(resultStr); }
            catch (NumberFormatException e) {
                MyDialog.errorDialog(QRcodeActivity.this, "Invalid Result", "Please enter a decimal number.");
                return;
            }
            create_QRcode(jsonEncode(experiment.getDatabaseId(), resultDouble));
            return;
        }

        if (experiment instanceof NonNegativeExp) {
            String resultStr = etResult.getText().toString();
            if (resultStr.isEmpty()) {
                MyDialog.errorDialog(QRcodeActivity.this, "Invalid Input", "Please enter the valid result.");
                return;
            }
            try { resultInt = Integer.parseInt(resultStr); }
            catch (NumberFormatException e) {
                MyDialog.errorDialog(QRcodeActivity.this, "Invalid Result", "Please enter an integer number.");
                return;
            }
            create_QRcode(jsonEncode(experiment.getDatabaseId(), resultInt));
            return;
        }
    }

    private JSONObject jsonEncode(String expId, Number result) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("experimentId", expId);
            jsonObject.put("result", result.toString());
        } catch (JSONException e) {
            MyDialog.errorDialog(QRcodeActivity.this, "Invalid Result", "Please choose the valid experiment.");
            return null;
        }
        return jsonObject;
    }

    private void create_QRcode(JSONObject content) {
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content.toString(), BarcodeFormat.QR_CODE, 250, 250, hashMap);
        } catch (WriterException e) {
            MyDialog.errorDialog(QRcodeActivity.this, "Invalid Result", "Please choose the valid experiment.");
            return;
        }

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //set the color
                if (bitMatrix.get(i, j)) {
                    pixels[i * width + j] = Color.BLACK;

                } else {
                    pixels[i * width + j] = Color.WHITE;
                }
            }
        }

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        ivQRcode.setImageBitmap(bitmap);
    }
}