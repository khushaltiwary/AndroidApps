package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

Button playButton;
Spinner levelSpinner,timeSpinner;
TextView welcomeTextView;
String timeVar,levelVar;

ArrayList<Integer> timeArrayList;
ArrayAdapter<Integer>  timeArrayAdapter;

ArrayList<Integer> levelArrayList;
ArrayAdapter<Integer> levelArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Select Level & Time");


        playButton =findViewById(R.id.playButton);

        //timeSpinner ***************
        timeSpinner = findViewById(R.id.timeSpinner);
        timeArrayList = new ArrayList<Integer>();
        timeArrayList.add(5);timeArrayList.add(10);timeArrayList.add(15);timeArrayList.add(20);timeArrayList.add(25);timeArrayList.add(30);
        timeArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_selectable_list_item,timeArrayList);
        timeSpinner.setAdapter(timeArrayAdapter);
        timeSpinner.setEnabled(false);


        //leveSpinner *************
        levelSpinner = findViewById(R.id.levelSpinner);
        levelArrayList = new ArrayList<Integer>();
        levelArrayList.add(1);levelArrayList.add(2);levelArrayList.add(3);levelArrayList.add(4);
        levelArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_selectable_list_item,levelArrayList);
        levelSpinner.setAdapter(levelArrayAdapter);
        levelSpinner.setEnabled(false);

        //levelSpinner itemClickListener ***************
        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                levelVar = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               Toast.makeText(getApplicationContext(),"Please select something",Toast.LENGTH_SHORT).show();
            }
        });


        //timeSpinner itemOnClickListener **************
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeVar = parent.getItemAtPosition(position).toString();
                Log.i("time value :",timeVar);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"Please select something",Toast.LENGTH_SHORT).show();
            }
        });


    }//end onCreate
    
    public void setPlayButton(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("userSelectedTime",timeVar);
        intent.putExtra("userSelectedLevel",levelVar);
        startActivity(intent);
        finish();
    }

}//end Main