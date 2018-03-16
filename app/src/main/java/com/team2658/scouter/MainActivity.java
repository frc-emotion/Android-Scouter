package com.team2658.scouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText teamNumber;    //Shows team number
    private TextView autoNumHomeCubes;  //shows number of vubes in home during auto
    private Button inc1;    //increment buttons
    private Button dec1;    //decrement buttons

    private int autoHomeCubes = 0;  //counter of cubes in home during auto

    private Retrofit retrofit;  //Retrofit object
    private ForumsWebService spreadsheetWebService; //Interface of field entries

    @Override   //Method that holds what to do once the page creates
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init stuff
        initWidgets();
        initRetro();
    }

    private void initWidgets()
    {
        teamNumber = (EditText) findViewById(R.id.editTeamNumber);  //init text edit for team number
        autoNumHomeCubes = (TextView) findViewById(R.id.integer_number_home);   //init text view for autonomous home cubes

        inc1 = (Button) findViewById(R.id.incCubesHome);    //init increment button
        inc1.setOnClickListener(this);  //set the click listener for the method onClick

        dec1 = (Button) findViewById(R.id.decCubesHome);    //init decrement button
        dec1.setOnClickListener(this);  //set the click listener for the method onClick
    }

    private void initRetro()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/")    //base url for all forums
                .build();
        spreadsheetWebService = retrofit.create(ForumsWebService.class);
    }

    public void submitData(View V)
    {
        String teamNum = teamNumber.getText().toString();   //convert data in text edit to string

        //Call<Void> completeQuestionnaireCall = spreadsheetWebService.completeForum(, teamNum, , , , , , , , , , );
        //completeQuestionnaireCall.enqueue(callCallback);
    }

    private final Callback<Void> callCallback = new Callback<Void>() {
        @Override   //stuff to do of successful
        public void onResponse(Response<Void> response) {
            Log.d("XXX", "Submitted" + response);
            clear();
            Utils.showToast("Added to forum", Toast.LENGTH_SHORT, getApplicationContext());
        }

        @Override   //stuff to do if failed
        public void onFailure(Throwable t) {
            Log.e("XXX", "Failed", t);
            Utils.showToast("Submission Failed", Toast.LENGTH_SHORT, getApplicationContext());
        }
    };

    //on click method for counters
    public void onClick(View v)
    {
        boolean showTextHomeCubes = false;
        switch(v.getId())
        {
            case R.id.incCubesHome: autoHomeCubes++; showTextHomeCubes = true; break;   //increment
            case R.id.decCubesHome: autoHomeCubes--; showTextHomeCubes = true; break;   //decrement
        }
        if(showTextHomeCubes)
            autoNumHomeCubes.setText("" + autoHomeCubes);   //refresh view
    }

    public void clearData(View V)
    {
        clear();
    }

    private void clear()
    {

    }
}
