package com.gsnathan.android_scouter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.divyanshu.draw.activity.DrawingActivity;

public class MainActivity extends AppCompatActivity {

    private static final int DRAW_CODE = 2658;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null && resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case DRAW_CODE:
                    byte[] byteArray = data.getByteArrayExtra("bitmap");
                    saveImage(byteArray);
            }
        }
    }

    public void drawStormPath(View v){
        startActivityForResult(new Intent(this, DrawingActivity.class), DRAW_CODE);
    }

    private void saveImage(byte[] byteArray){

    }
}
