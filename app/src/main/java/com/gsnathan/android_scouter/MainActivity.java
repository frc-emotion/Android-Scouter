package com.gsnathan.android_scouter;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.raed.drawingview.DrawingView;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText teamText, notesText;
    Spinner sandStartPosition, sandStartPiece, climb;
    ElegantNumberButton sandCargo, sandHatch, shipCargo, shipHatch, rocketCargo, rocketHatch, dropCargo, dropHatch;
    CheckBox level1, level2, level3;
    ArrayList<ElegantNumberButton> counters;
    int[] counterValues;


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

        updateCounterValues();
    }

    private void updateCounterValues(){
        counters.forEach((e) -> e.setOnValueChangeListener((view, oldValue, newValue) -> {
            counterValues[counters.indexOf(e)] = newValue;
            Log.d("Counts", Arrays.toString(counterValues));
        }));
    }

    public void saveData(View v) {

    }
}
