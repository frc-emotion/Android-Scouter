package com.gsnathan.android_scouter;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText teamText, notesText;
    Spinner sandStartPosition, sandStartPiece, climb;
    ElegantNumberButton sandCargo, sandHatch, shipCargo, shipHatch, rocketCargo, rocketHatch, dropCargo, dropHatch;
    CheckBox level1, level2, level3;
    ArrayList<ElegantNumberButton> counters;
    int[] counterValues;
    CSVWriterTool writer;
    Button deleteButton;
    public static StringBuilder ultimate;
    int saves = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teamText = findViewById(R.id.editTeamNumber);
        notesText = findViewById(R.id.editNotes);

        sandStartPosition = findViewById(R.id.spin_sand_start);
        sandStartPiece = findViewById(R.id.spin_sand_piece);
        climb = findViewById(R.id.spin_climb);

        sandCargo = findViewById(R.id.sand_cargo_count);
        sandHatch = findViewById(R.id.sand_hatch_count);
        shipCargo = findViewById(R.id.game_ship_cargo_count);
        shipHatch = findViewById(R.id.game_ship_hatch_count);
        rocketCargo = findViewById(R.id.game_rocket_cargo_count);
        rocketHatch = findViewById(R.id.game_rocket_hatch_count);
        dropCargo = findViewById(R.id.drop_cargo_count);
        dropHatch = findViewById(R.id.drop_hatch_count);

        counters = new ArrayList<ElegantNumberButton>() {
            {
                add(sandCargo);
                add(sandHatch);
                add(shipCargo);
                add(shipHatch);
                add(rocketCargo);
                add(rocketHatch);
                add(dropCargo);
                add(dropHatch);
            }
        };
        counterValues = new int[counters.size()];

        level1 = findViewById(R.id.rocket_level1);
        level2 = findViewById(R.id.rocket_level2);
        level3 = findViewById(R.id.rocket_level3);
        deleteButton = findViewById(R.id.clearButton);
        deleteButton.setOnClickListener(v -> clearView());
        deleteButton.setOnLongClickListener(v -> {

            AlertDialog.Builder deleteLog = new AlertDialog.Builder(MainActivity.this);
            deleteLog.setMessage("Are you sure you want to delete the whole file? You will lose all data!");
            deleteLog.setCancelable(false);

            deleteLog.setPositiveButton(
                    "Yes",
                    (dialog, id) -> {
                        try {
                            writer.clearCSV();
                            saves = 0;
                            clearView();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dialog.cancel();
                    });

            deleteLog.setNegativeButton(
                    "No",
                    (dialog, id) -> dialog.cancel());

            AlertDialog deleteCSV = deleteLog.create();
            deleteCSV.show();

            return false;
        });

        writer = new CSVWriterTool();

        updateCounterValues();
    }

    private void updateCounterValues() {
        for(ElegantNumberButton e : counters){
            e.setOnValueChangeListener((view, oldValue, newValue) -> {
                counterValues[counters.indexOf(e)] = newValue;
                Log.d("Counts", Arrays.toString(counterValues));
            });
        }
    }

    public void saveData(View v) {
        String teamNum = teamText.getText().toString();
        int startSpinnerChoice = sandStartPosition.getSelectedItemPosition();
        int startSpinnerPiece = sandStartPiece.getSelectedItemPosition();
        int sandCargo = counterValues[0];
        int sandHatch = counterValues[1];
        int shipCargo = counterValues[2];
        int shipHatch = counterValues[3];
        int rocketCargo = counterValues[4];
        int rocketHatches = counterValues[5];
        int ifLevel1 = level1.isChecked() ? 1 : 0;
        int ifLevel2 = level2.isChecked() ? 1 : 0;
        int ifLevel3 = level3.isChecked() ? 1 : 0;
        int dropCargo = counterValues[6];
        int dropHatch = counterValues[7];
        int climbPosition = climb.getSelectedItemPosition();
        String notes = notesText.getText().toString().isEmpty() ? "No Notes" : notesText.getText().toString();

        String[] data = {teamNum, String.valueOf(startSpinnerChoice), String.valueOf(startSpinnerPiece), String.valueOf(sandCargo), String.valueOf(sandHatch), String.valueOf(shipCargo), String.valueOf(shipHatch), String.valueOf(rocketCargo), String.valueOf(rocketHatches),
                String.valueOf(ifLevel1), String.valueOf(ifLevel2), String.valueOf(ifLevel3), String.valueOf(dropCargo), String.valueOf(dropHatch), String.valueOf(climbPosition), notes};
        try {
            writer.writeLineToCSV(data);
        } catch (IOException e) {
            Snackbar mySnackBar = Snackbar.make(findViewById(android.R.id.content), "IOEXCEPTION", Snackbar.LENGTH_LONG);
            mySnackBar.show();
        }
        clearView();
        Snackbar mySnackBar = Snackbar.make(findViewById(android.R.id.content), "Saved data!", Snackbar.LENGTH_LONG);
        mySnackBar.show();

        saves++;
        if(saves == 5){
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Please scan with the master app! (10 saves)", Snackbar.LENGTH_LONG);
            snack.show();
        }
    }

    private void clearView() {
        teamText.getText().clear();
        sandStartPosition.setSelection(0);
        sandStartPiece.setSelection(0);
        for (ElegantNumberButton e : counters){
            e.setNumber("0");
        }
        Arrays.fill(counterValues, 0);
        level1.setChecked(false);
        level2.setChecked(false);
        level3.setChecked(false);
        climb.setSelection(0);
        notesText.getText().clear();
    }

    public void genQR(View v) {
        ArrayList<String[]> dataList = null;
        try {
            dataList = writer.readCsvAsList();
            ultimate = new StringBuilder();
            ultimate.append("");
            for (int x = 0; x < dataList.size(); x++) {
                for (String s : dataList.get(x)) {
                    ultimate.append(s).append(",");
                }
                if (x != dataList.size() - 1)
                    ultimate.append("br,");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        QRDialog dialog = new QRDialog();
        dialog.show(getSupportFragmentManager(), "h");
    }
}
