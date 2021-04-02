
package com.example.preemptiveoop.barcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.preemptiveoop.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


//https://github.com/journeyapps/zxing-android-embedded
public class BarcodegeneratorActivity extends AppCompatActivity {

    private static final String TAG = "QRcodeGenerator";

    ImageView ivBarimage;
    Button btBargenerator;

    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);

        ivBarimage = findViewById(R.id.ImageView_barcode);
        btBargenerator = findViewById(R.id.Button_bar_generator);

        btBargenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //!!!!!!!!
                // need string as id to access Trial
                String template = "template";

                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(template, BarcodeFormat.CODE_128, 400, 170, null);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap barmap = barcodeEncoder.createBitmap(bitMatrix);
                    ivBarimage.setImageBitmap(barmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
