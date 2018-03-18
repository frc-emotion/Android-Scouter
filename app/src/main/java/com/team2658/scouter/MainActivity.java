package com.team2658.scouter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String[] HEADERS = {"Scouter Name", "Team Number", "Passed autonomous line?", "Autonomous cube on home switch?",
            "Autonomous cube on opponent switch?", "Autonomous cube on scale?",
            "Number of cubes on home switch?", "Number of cubes on opponent switch?",
            "Number of cubes on scale?", "Number of cubes dropped?", "Number of cubes in exchange?",
            "Climb process?"};

    private EditText scouter;   //Scouter name
    private EditText teamNumber;    //Shows team number
    private CheckBox autoLine;
    private CheckBox autoHome;
    private CheckBox autoOpp;
    private CheckBox autoScale;
    private TextView telNumHomeCubes;  //shows number of vubes in home during auto
    private TextView telNumEnemySwitch;
    private TextView telNumScale;
    private TextView telNumDropped;
    private TextView telNumExchange;
    private Button inc1;    //increment buttons
    private Button dec1;    //decrement buttons
    private Button inc2;    //increment buttons
    private Button dec2;    //decrement buttons
    private Button inc3;    //increment buttons
    private Button dec3;    //decrement buttons
    private Button inc4;    //increment buttons
    private Button dec4;    //decrement buttons
    private Button inc5;    //increment buttons
    private Button dec5;    //decrement

    private Spinner climbSpin;

    private int telHomeCubes = 0;  //counter of cubes in home during auto
    private int telOppCubes = 0;  //counter of cubes in home during auto
    private int telScaleCubes = 0;  //counter of cubes in home during auto
    private int telDropCubes = 0;  //counter of cubes in home during auto
    private int telEx = 0;  //counter of cubes in home during auto

    private String spinnerChoice;

    @Override   //Method that holds what to do once the page creates
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init stuff
        initUI();
    }

    private void initUI() {
        scouter = (EditText) findViewById(R.id.editName);

        autoLine = (CheckBox) findViewById(R.id.autoLineCheck);
        autoHome = (CheckBox) findViewById(R.id.autoHomeCheck);
        autoOpp = (CheckBox) findViewById(R.id.autoOppCheck);
        autoScale = (CheckBox) findViewById(R.id.autoScaleCheck);

        teamNumber = (EditText) findViewById(R.id.editTeamNumber);  //init text edit for team number
        telNumHomeCubes = (TextView) findViewById(R.id.integer_number_home);   //init text view for autonomous home cubes
        telNumEnemySwitch = (TextView) findViewById(R.id.integer_number_opp);
        telNumScale = (TextView) findViewById(R.id.integer_number_scale);
        telNumDropped = (TextView) findViewById(R.id.integer_number_drop);
        telNumExchange = (TextView) findViewById(R.id.integer_number_ex);

        inc1 = (Button) findViewById(R.id.incCubesHome);    //init increment button
        inc1.setOnClickListener(this);  //set the click listener for the method onClick

        dec1 = (Button) findViewById(R.id.decCubesHome);    //init decrement button
        dec1.setOnClickListener(this);  //set the click listener for the method onClick

        inc2 = (Button) findViewById(R.id.incCubesOpp);    //init increment button
        inc2.setOnClickListener(this);  //set the click listener for the method onClick

        dec2 = (Button) findViewById(R.id.decCubesOpp);    //init decrement button
        dec2.setOnClickListener(this);  //set the click listener for the method onClick

        inc3 = (Button) findViewById(R.id.incCubesScale);    //init increment button
        inc3.setOnClickListener(this);  //set the click listener for the method onClick

        dec3 = (Button) findViewById(R.id.decCubesScale);    //init decrement button
        dec3.setOnClickListener(this);  //set the click listener for the method onClick

        inc4 = (Button) findViewById(R.id.incCubesDrop);    //init increment button
        inc4.setOnClickListener(this);  //set the click listener for the method onClick

        dec4 = (Button) findViewById(R.id.decCubesDrop);    //init decrement button
        dec4.setOnClickListener(this);  //set the click listener for the method onClick

        inc5 = (Button) findViewById(R.id.incEx);    //init increment button
        inc5.setOnClickListener(this);  //set the click listener for the method onClick

        dec5 = (Button) findViewById(R.id.decEx);    //init decrement button
        dec5.setOnClickListener(this);  //set the click listener for the method onClick

        climbSpin = (Spinner) findViewById(R.id.spinnerClimb);
        climbSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerChoice = parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void submitData(View v) {
        String scouterName = scouter.getText().toString();    //convert data to string
        String teamNum = teamNumber.getText().toString();   //convert data in text edit to string
        String autoLineS = Utils.checkBoxToString(autoLine);
        String autoHomeS = Utils.checkBoxToString(autoHome);
        String autoOppS = Utils.checkBoxToString(autoOpp);
        String autoScaleS = Utils.checkBoxToString(autoScale);
        String telHome = "" + telHomeCubes;
        String telOpp = "" + telOppCubes;
        String telScale = "" + telScaleCubes;
        String telDropped = "" + telDropCubes;
        String exchange = "" + telEx;
        String climb = "" + spinnerChoice;

        String[] dataToSubmit = {scouterName, teamNum, autoLineS, autoHomeS, autoOppS, autoScaleS,
                telHome, telOpp, telScale, telDropped, exchange, climb};

        try {
            writeCSV(dataToSubmit);
            Utils.showToast("Successfully saved as scouter_data.csv", Toast.LENGTH_SHORT, getApplicationContext());
            clear();
        } catch (IOException e) {
            Utils.showToast("Could not write to .csv", Toast.LENGTH_SHORT, getApplicationContext());
        }
    }

    //on click method for counters
    public void onClick(View v) {
        boolean showTextHomeCubes = false;
        boolean showTextOppCubes = false;
        boolean showTextScaleCubes = false;
        boolean showTextDropCubes = false;
        boolean showTextEx = false;
        switch (v.getId()) {
            case R.id.incCubesHome:
                telHomeCubes++;
                showTextHomeCubes = true;
                break;   //increment
            case R.id.decCubesHome:
                telHomeCubes--;
                showTextHomeCubes = true;
                break;   //decrement
            case R.id.incCubesOpp:
                telOppCubes++;
                showTextOppCubes = true;
                break;   //increment
            case R.id.decCubesOpp:
                telOppCubes--;
                showTextOppCubes = true;
                break;   //decrement

            case R.id.incCubesScale:
                telScaleCubes++;
                showTextScaleCubes = true;
                break;   //increment
            case R.id.decCubesScale:
                telScaleCubes--;
                showTextScaleCubes = true;
                break;   //decrement

            case R.id.incCubesDrop:
                telDropCubes++;
                showTextDropCubes = true;
                break;   //increment
            case R.id.decCubesDrop:
                telDropCubes--;
                showTextDropCubes = true;
                break;   //decrement

            case R.id.incEx:
                telEx++;
                showTextEx = true;
                break;   //increment
            case R.id.decEx:
                telEx--;
                showTextEx = true;
                break;   //decrement
        }
        if (showTextHomeCubes)
            telNumHomeCubes.setText("" + telHomeCubes);   //refresh view

        if (showTextOppCubes)
            telNumEnemySwitch.setText("" + telOppCubes);   //refresh view

        if (showTextScaleCubes)
            telNumScale.setText("" + telScaleCubes);   //refresh view

        if (showTextDropCubes)
            telNumDropped.setText("" + telDropCubes);   //refresh view

        if (showTextEx)
            telNumExchange.setText("" + telEx);   //refresh view
    }

    public void clearData(View V) {
        clear();
    }

    private void clear() {
        scouter.setText("");
        teamNumber.setText("");
        autoLine.setChecked(false);
        autoHome.setChecked(false);
        autoOpp.setChecked(false);
        autoScale.setChecked(false);
        telHomeCubes = 0;
        telOppCubes = 0;
        telScaleCubes = 0;
        telDropCubes = 0;
        telEx = 0;
        updateCounters();
    }

    private void updateCounters() {
        telNumHomeCubes.setText("" + telHomeCubes);
        telNumEnemySwitch.setText("" + telOppCubes);
        telNumScale.setText("" + telScaleCubes);
        telNumDropped.setText("" + telDropCubes);
        telNumExchange.setText("" + telEx);
    }

    private void writeCSV(String[] data) throws IOException {
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "scouter_data.csv";
        String filePath = baseDir + File.separator + fileName;
        File f = new File(filePath);
        CSVWriter writer;

        // File exist
        if (f.exists() && !f.isDirectory()) {
            FileWriter mFileWriter = new FileWriter(filePath, true);
            writer = new CSVWriter(mFileWriter);
        } else {
            writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(HEADERS);
        }

        writer.writeNext(data);

        writer.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent aboutIntent = new Intent(this,AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
