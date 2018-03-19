package com.team2658.scouter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
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

/**
 * Created by Gokul Swaminathan on 3/14/2018.
 *
 * This class is the main class that runs the main page.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //contains the titles for the csv file
    private final String[] HEADERS = {"Scouter Name", "Team Number", "Passed autonomous line?", "Autonomous cube on home switch?",
            "Autonomous cube on opponent switch?", "Autonomous cube on scale?",
            "Number of cubes on home switch?", "Number of cubes on opponent switch?",
            "Number of cubes on scale?", "Number of cubes dropped?", "Number of cubes in exchange?",
            "Climb process?"};

    private EditText scouter;   //Scouter name
    private EditText teamNumber;    //Shows team number
    private CheckBox autoLine;  //checkbox for auto line
    private CheckBox autoHome;  //checkbox for auto home switch
    private CheckBox autoOpp;   //checkbox for auto enemy switch
    private CheckBox autoScale; //checkbox for auto scale
    private TextView telNumHomeCubes;  //shows number of cubes in home during tel
    private TextView telNumEnemySwitch; //shows number of cubes in enemy switch during tel
    private TextView telNumScale;   //shows number of cubes in scale during tel
    private TextView telNumDropped; //shows number of cubes dropped during tel
    private TextView telNumExchange;    //shows number of cubes in exchange during tel
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

    private Spinner climbSpin;  //spinner to explain climb process

    private int telHomeCubes = 0;  //counter of cubes in home during auto
    private int telOppCubes = 0;  //counter of cubes in home during auto
    private int telScaleCubes = 0;  //counter of cubes in home during auto
    private int telDropCubes = 0;  //counter of cubes in home during auto
    private int telEx = 0;  //counter of cubes in home during auto

    private String spinnerChoice;   //spinner selection made by user

    @Override   //Method that holds what to do once the page creates
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //stuff to do only on first install
        onStartUp();

        //init stuff
        initUI();
    }

    private void onStartUp() {
        //only show material intro on first install of the app
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = prefs.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            startActivity(Utils.actIntent(this, MainIntroActivity.class));
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        }
    }

    private void initUI() {
        //init all items
        scouter = (EditText) findViewById(R.id.editName);

        autoLine = (CheckBox) findViewById(R.id.autoLineCheck);
        autoHome = (CheckBox) findViewById(R.id.autoHomeCheck);
        autoOpp = (CheckBox) findViewById(R.id.autoOppCheck);
        autoScale = (CheckBox) findViewById(R.id.autoScaleCheck);

        teamNumber = (EditText) findViewById(R.id.editTeamNumber);
        telNumHomeCubes = (TextView) findViewById(R.id.integer_number_home);
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

        //spinnerchoice becomes the selected on the spinner
        climbSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerChoice = parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }


    public void submitData(View v) {
        //convert all data to strings
        String scouterName = scouter.getText().toString();
        String teamNum = teamNumber.getText().toString();
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

        //array of one team scouted
        String[] dataToSubmit = {scouterName, teamNum, autoLineS, autoHomeS, autoOppS, autoScaleS,
                telHome, telOpp, telScale, telDropped, exchange, climb};

        //write the data
        try {
            writeCSV(dataToSubmit);
            Utils.showToast("Successfully saved", Toast.LENGTH_SHORT, getApplicationContext());
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
        //clear/reset all data entries
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
        //update counters after reset
        telNumHomeCubes.setText("" + telHomeCubes);
        telNumEnemySwitch.setText("" + telOppCubes);
        telNumScale.setText("" + telScaleCubes);
        telNumDropped.setText("" + telDropCubes);
        telNumExchange.setText("" + telEx);
    }

    private void writeCSV(String[] data) throws IOException {
        String baseDir = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();  //Downloads folder directory
        String fileName = "scouter_data.csv";
        String filePath = baseDir + File.separator + fileName;
        File f = new File(filePath);
        CSVWriter writer;

        // if file exists
        if (f.exists() && !f.isDirectory()) {
            FileWriter mFileWriter = new FileWriter(filePath, true);
            writer = new CSVWriter(mFileWriter);
        }
        // else create a file with the headers
        else {
            writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(HEADERS);  //Write line by line (Array)
        }

        writer.writeNext(data);
        writer.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //display the menu when clicked
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about: //open about page when about menu is clicked
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
