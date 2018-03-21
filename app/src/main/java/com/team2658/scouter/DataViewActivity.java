package com.team2658.scouter;

import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Gokul Swaminathan on 3/20/2018.
 *
 * This class shows the csv file data in a grid layout. Not pretty, but does the job. Might need more help to improve view.
 */

public class DataViewActivity extends AppCompatActivity {

    GridView gridView;  //gridview
    String[] data = {}; //array that holds all the data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataview);

        //force landscape
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Utils.showToast("Data can only be shown in landscape", Toast.LENGTH_LONG, getApplicationContext());

        //try to load the data of file to array
        try {
            loadData();
        } catch (IOException e) {
            Utils.showToast(e.getMessage(), Toast.LENGTH_SHORT, getApplicationContext());
        }

        //init gridview and set it's array adapter
        gridView = (GridView) findViewById(R.id.gridview);
        DataAdapter adapter = new DataAdapter(getApplicationContext(), data);
        gridView.setAdapter(adapter);

    }

    private void loadData() throws IOException {
        //reader object
        CSVReader reader = new CSVReader(new FileReader(fileDirectory()));

        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {

            //nextLine[] is an array of values from the line
            //merge arrays
            data = Utils.mergeArrays(data, nextLine);

        }

    }

    //get the file directory
    private String fileDirectory() {
        String baseDir = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();  //Downloads folder directory
        String fileName = "scouter_data.csv";
        String filePath = baseDir + File.separator + fileName;
        return filePath;
    }
}
