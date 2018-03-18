package com.team2658.scouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView versionView;
    private final String APP_VERSION = "Version " + BuildConfig.VERSION_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initUI();
    }

    private void initUI()
    {
        versionView = (TextView) findViewById(R.id.text_version);
        versionView.setText(APP_VERSION);
    }


    public void openLink(View v)
    {
        startActivity(Utils.webIntent("http://opencsv.sourceforge.net/"));
    }
}
