package com.team2658.scouter;

import android.Manifest;
import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

/**
 * Created by Gokul Swaminathan on 3/18/2018.
 *
 * This class shows the intro at the startup of the app.
 */

public class MainIntroActivity extends IntroActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setFullscreen(true);
        super.onCreate(savedInstanceState);

        //slide builder --> builds a slide of the intro

        //main slide
        addSlide(new SimpleSlide.Builder()
                .title(R.string.app_name)
                .description(R.string.description__intro)
                .image(R.mipmap.ic_launcher)
                .background(R.color.yellowPrim)
                .backgroundDark(R.color.yellowDark)
                .scrollable(false)  //disable scrolling
                .build());

        //storage permission slide
        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_permission_write)
                .description(R.string.description__permission_write)
                .image(R.drawable.readstorage_icon)
                .background(R.color.yellowPrim)
                .backgroundDark(R.color.yellowDark)
                .scrollable(false)  //disable scrolling
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE) //permission to read/write storage
                .build());

        // Add slides, edit configuration...
    }
}