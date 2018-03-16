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

    private EditText teamNumber;
    private TextView numHomeCubes;
    private Button inc1;
    private Button dec1;

    private int homeCubes = 0;

    private Retrofit retrofit;
    private ForumsWebService spreadsheetWebService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init stuff
        initWidgets();
        initRetro();
    }

    private void initWidgets()
    {
        teamNumber = (EditText) findViewById(R.id.editTeamNumber);
        numHomeCubes = (TextView) findViewById(R.id.integer_number1);

        inc1 = (Button) findViewById(R.id.incCubesHome);
        inc1.setOnClickListener(this);

        dec1 = (Button) findViewById(R.id.decCubesHome);
        dec1.setOnClickListener(this);
    }

    private void initRetro()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/")
                .build();
        spreadsheetWebService = retrofit.create(ForumsWebService.class);
    }

    private void submitData()
    {
        String teamNum = teamNumber.getText().toString();

        //Call<Void> completeQuestionnaireCall = spreadsheetWebService.completeForum(, teamNum, , , , , , , , , , );
        //completeQuestionnaireCall.enqueue(callCallback);
    }

    private final Callback<Void> callCallback = new Callback<Void>() {
        @Override
        public void onResponse(Response<Void> response) {
            Log.d("XXX", "Submitted" + response);
            clear();
            Utils.showToast("Added to forum", Toast.LENGTH_SHORT, getApplicationContext());
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e("XXX", "Failed", t);
            Utils.showToast("Submission Failed", Toast.LENGTH_SHORT, getApplicationContext());
        }
    };

    public void onClick(View v)
    {
        boolean showTextHomeCubes = false;
        switch(v.getId())
        {
            case R.id.incCubesHome: homeCubes++; showTextHomeCubes = true; break;
            case R.id.decCubesHome: homeCubes--; showTextHomeCubes = true; break;
        }
        if(showTextHomeCubes)
            numHomeCubes.setText("" + homeCubes);
    }

    private void clear()
    {

    }
}
