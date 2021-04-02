package com.example.preemptiveoop.barcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.experiment.model.BinomialExp;
import com.example.preemptiveoop.experiment.model.Experiment;

import com.google.zxing.WriterException;
import com.example.preemptiveoop.R;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

//https://github.com/androidmads/QRGenerator

public class QRgeneratorActivity extends AppCompatActivity {

    private static final String TAG = "QRcodeGenerator";

    ImageView ivQRimage;
    Button btQRgenerator;

    Experiment template = new BinomialExp("testDatabaseID", "test", new Date(), "Not good", null, false, 11);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);

        ivQRimage = findViewById(R.id.ImageView_qrcode);
        btQRgenerator = findViewById(R.id.Button_qr_generator);

        btQRgenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = template.getDatabaseId();

                QRGEncoder qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,500);
                try {
                    Bitmap qrmap = qrgEncoder.getBitmap();
                    ivQRimage.setImageBitmap(qrmap);
                } catch (Exception e) {
                    Log.v(TAG, e.toString());
                }
            }
        });
    }


}