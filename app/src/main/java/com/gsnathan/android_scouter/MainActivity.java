package com.gsnathan.android_scouter;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.raed.drawingview.DrawingView;

import java.io.File;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private static final int DRAW_CODE = 2658;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.field_view);
       updateSandstormIMage();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateSandstormIMage();
    }

    private void updateSandstormIMage(){
        File img = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/DeepScouterApp/field_image.png");
        if (!img.exists())
            imageView.setImageResource(R.drawable.field_image);
        else {
            Bitmap myBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
    }


    public void drawStormPath(View v) {
        startActivity(new Intent(this, DrawActivity.class));
    }

    private void saveImage(byte[] byteArray) {

    }
}
