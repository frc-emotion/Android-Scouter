package com.team2658.scouter;

import android.Manifest;
import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

/**
 * Created by Gokul Swaminathan on 3/18/2018.
 */

public class MainIntroActivity extends IntroActivity {
    @Override protected void onCreate(Bundle savedInstanceState){
        //setFullscreen(true);
        super.onCreate(savedInstanceState);

        addSlide(new SimpleSlide.Builder()
                .title(R.string.app_name)
                .description(R.string.description__intro)
                .image(R.mipmap.ic_launcher)
                .background(R.color.yellowPrim)
                .backgroundDark(R.color.yellowDark)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_permission_write)
                .description(R.string.description__permission_write)
                .image(R.drawable.readstorage_icon)
                .background(R.color.yellowPrim)
                .backgroundDark(R.color.yellowDark)
                .scrollable(false)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .build());

        // Add slides, edit configuration...
    }
}