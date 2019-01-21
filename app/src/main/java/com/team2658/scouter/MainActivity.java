package com.team2658.scouter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.os.Environment.DIRECTORY_DOWNLOADS;


/**
 * Created by Gokul Swaminathan on 3/14/2018.
 * <p>
 * This class is the main class that runs the main page.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String CSV_FILE = "scouter_data.csv";
    private final String EXCEL_FILE = "scouter_data_excel.xls";

    //contains the titles for the csv file
    private String[] HEADERS ;

    private EditText teamNumber;    //Shows team number
    private EditText hatchMech;    //Shows hatch mechanism
    private EditText cargoMech;    //Shows cargo mechanism
    private EditText lifttMech;    //Shows lift mechanism
    private EditText notesBlock;    //Notes block
    private TextView telNumPreHatch;  //shows number of hatch panels installed before tel
    private TextView telNumPreCargo; //shows number of cargo installed before tel
    private TextView telNumHatch;   //shows number of hatch installed during tel
    private TextView telNumCargo; //shows number of cargo installed during tel
    private Button hatchPreInc;    //increment button
    private Button hatchPreDec;    //decrement button
    private Button cargoPreInc;    //increment button
    private Button cargoPreDec;    //decrement button
    private Button hatchInc;    //increment button
    private Button hatchDec;    //decrement button
    private Button cargoInc;    //increment button
    private Button cargoDec;    //decrement button

    private Button nuke;

    private Spinner startSpin; //spinner for
    private Spinner climbSpin; //spinner to explain climb process

    private int telHatchPre = 0;  //counter of cubes in home during auto
    private int telCargoPre = 0;  //counter of cubes in home during auto
    private int telHatch = 0;  //counter of cubes in home during auto
    private int telCargo = 0;  //counter of cubes in home during auto

    private String startChoice;
    private String liftChoice;//spinner selection made by user

    private String baseDir = android.os.Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).toString();
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy 'at' h:mm z");
    private String date = df.format(Calendar.getInstance().getTime());

    @Override   //Method that holds what to do once the page creates
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HEADERS = getResources().getStringArray(R.array.headers);
        //stuff to do only on first install

        checkPermissions();

        //init stuff
        initUI();
    }

    private void checkPermissions() {
        int permission1 = PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission2 = PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission1 == PermissionChecker.PERMISSION_GRANTED || permission2 == PermissionChecker.PERMISSION_GRANTED) {
            //good to go
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Utils.showToast("Permission denied to access your files", Toast.LENGTH_LONG, this);
                }
                return;
            }
        }
    }


    private void initUI() {
        //init all items

        teamNumber = findViewById(R.id.editTeamNumber);
        notesBlock = findViewById(R.id.editNotes);
        hatchMech = findViewById(R.id.mechHatch);
        cargoMech = findViewById(R.id.mechCargo);
        lifttMech = findViewById(R.id.mechLift);
        telNumPreHatch = findViewById(R.id.integer_number_hatch_pre);
        telNumPreCargo = findViewById(R.id.integer_number_cargo_pre);
        telNumHatch = findViewById(R.id.integer_number_hatch_post);
        telNumCargo = findViewById(R.id.integer_number_cargo_post);

        hatchPreInc = findViewById(R.id.incHatchPre);    //init increment button
        hatchPreInc.setOnClickListener(this);  //set the click listener for the method onClick

        hatchPreDec = findViewById(R.id.decHatchPre);    //init decrement button
        hatchPreDec.setOnClickListener(this);  //set the click listener for the method onClick

        cargoPreInc = findViewById(R.id.incCargoPre);    //init increment button
        cargoPreInc.setOnClickListener(this);  //set the click listener for the method onClick

        cargoPreDec = findViewById(R.id.decCargoPre);    //init decrement button
        cargoPreDec.setOnClickListener(this);  //set the click listener for the method onClick

        hatchInc = findViewById(R.id.incHatchPost);    //init increment button
        hatchInc.setOnClickListener(this);  //set the click listener for the method onClick

        hatchDec = findViewById(R.id.decHatchPost);    //init decrement button
        hatchDec.setOnClickListener(this);  //set the click listener for the method onClick

        cargoInc = findViewById(R.id.incCargoPost);    //init increment button
        cargoInc.setOnClickListener(this);  //set the click listener for the method onClick

        cargoDec = findViewById(R.id.decCargoPost);    //init decrement button
        cargoDec.setOnClickListener(this);  //set the click listener for the method onClick

        nuke = findViewById(R.id.button_clearCsv);

        climbSpin = findViewById(R.id.spinnerClimb);
        startSpin = findViewById(R.id.spinnerStart);

        //spinnerchoice becomes the selected on the spinner
        climbSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                liftChoice = parent.getItemAtPosition(position).toString();
            }


            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        startSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startChoice = parent.getItemAtPosition(position).toString();
            }


            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

    public void submitData(View v) {
        //convert all data to strings
        String teamNum = teamNumber.getText().toString();
        String HatchMech = hatchMech.getText().toString();
        String CargoMech = cargoMech.getText().toString();
        String LiftMech= lifttMech.getText().toString();

        if (teamNum.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.emptyTeam), Toast.LENGTH_LONG).show();
        } else
        if (HatchMech.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.emptyHatchMech), Toast.LENGTH_LONG).show();
        } else
        if (CargoMech.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.emptyCargoMech), Toast.LENGTH_LONG).show();
        } else
        if (LiftMech.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.emptyLiftMech), Toast.LENGTH_LONG).show();
        }


        String notes = notesBlock.getText().toString();

        if (notes.isEmpty()) {
            notes = "No notes";
        }

        //array of one team scouted
        String[] dataToSubmit = {teamNum, startChoice,HatchMech,CargoMech ,LiftMech,
                telHatchPre+"", telCargoPre+"", telHatch+"", telCargo+"", liftChoice, notes};

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
        switch (v.getId()) {
            case R.id.incHatchPre:
                telHatchPre++;
                telNumPreHatch.setText(telHatchPre+"");
                break;   //increment
            case R.id.decHatchPre:
                if (telHatchPre > 0)
                    telHatchPre--;
                telNumPreHatch.setText(telHatchPre+"");
                break;   //decrement
            case R.id.incCargoPre:
                telCargoPre++;
                telNumPreCargo.setText(telCargoPre+"");
                break;   //increment
            case R.id.decCargoPre:
                if (telCargoPre > 0)
                    telCargoPre--;
                telNumPreCargo.setText(telCargoPre+"");
                break;   //decrement
            case R.id.incHatchPost:
                telHatch++;
                telNumHatch.setText(telHatch+"");
                break;   //increment
            case R.id.decHatchPost:
                if (telHatch > 0)
                    telHatch--;
                telNumHatch.setText(telHatch+"");
                break;   //decrement
            case R.id.incCargoPost:
                telCargo++;
                telNumCargo.setText(telCargo+"");
                break;   //increment
            case R.id.decCargoPost:
                if (telCargo > 0)
                    telCargo--;
                telNumCargo.setText(telCargo+"");
                break;   //decrement

        }

    }

    public void clearData(View V) {
        clear();
    }

    private void clear() {
        //clear/reset all data entries

        telNumCargo.setText("0");
        telNumHatch.setText("0");
        telNumPreHatch.setText("0");
        telNumPreCargo.setText("0");
    }



    private void writeCSV(String[] data) throws IOException {
        String filePath = baseDir + File.separator + CSV_FILE;
        File f = new File(filePath);

        //csv writer object
        CSVWriter writer;

        // if file exists
   //     if (f.exists() && !f.isDirectory()) {
     //       FileWriter mFileWriter = new FileWriter(filePath, true);
       //     writer = new CSVWriter(mFileWriter);
        //}
        // else create a file with the headers
       // else {
            writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(HEADERS);  //Write line by line (Array)
        //}

        writer.writeNext(data);
        writer.close();
    }

    //method to empty the file, incase if messup. This method is the on click for the button.
    public void clearFile(View v) {
        try {
            clearCsv();
            Utils.showToast("Clear successful", Toast.LENGTH_LONG, getApplicationContext());
        } catch (IOException e) {
            Utils.showToast("Clear failed", Toast.LENGTH_LONG, getApplicationContext());
        }
    }

    //this is the implementation of method above^
    private void clearCsv() throws IOException {
        String filePath = baseDir + File.separator + CSV_FILE;
        File f = new File(filePath);

        //csv writer object
        CSVWriter writer;

        writer = new CSVWriter(new FileWriter(filePath));
        writer.writeNext(HEADERS);  //Write line by line (Array)
        writer.close();

    }

    //this method is the onclick of the export method below
    public void exportExcel(View v) {
        try {
            String filePath = baseDir + File.separator + CSV_FILE;
            saveExcel(filePath);
            Utils.showToast("Export Success", Toast.LENGTH_LONG, getApplicationContext());
        } catch (IOException e) {
            Utils.showToast("Export failed", Toast.LENGTH_LONG, getApplicationContext());
        }
    }

    //this method exports the .csv to .xls. It will overwrite any existing xls files.
    private void saveExcel(String csvFilePath) throws IOException {
        String excelFilePath = baseDir + File.separator + EXCEL_FILE;

        //converter object
        Converter convert = new Converter(csvFilePath, excelFilePath);
        convert.convertCsvToExcel();
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
            case R.id.action_view: //open view page when about menu is clicked
                Intent viewIntent = new Intent(this, ExcelViewActivity.class);
                startActivity(viewIntent);
                return true;
            case R.id.action_share: //shares excel file with scout leader
                try {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();    //strictmode policies
                    StrictMode.setVmPolicy(builder.build());
                    File attachment = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), EXCEL_FILE);
                    Intent emailIntent = Utils.emailIntent("gokulswamilive@gmail.com", "Scouting Data", date, attachment);
                    startActivity(emailIntent);
                } catch (Exception e) {
                    Utils.showToast("Error, could not send email", Toast.LENGTH_SHORT, getApplicationContext());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
