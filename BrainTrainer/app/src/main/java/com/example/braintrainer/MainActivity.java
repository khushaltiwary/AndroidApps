package com.example.braintrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
TextView timerTextView,scoreTextView,questionTextView;
TextView timerValueTextView,scoreValueTextView,questionValueTextValue;
Button button1,button2,button3,button4;
Button restartButton,quitButton;

String TimeVar,LevelVar;//intent lene ke liye
int timeValueIntent,levelValueIntent;//intent ke string ko int me change krne ke liye.

    int a;
    int b;
    int result;

    ArrayList<Integer> answers = new ArrayList<Integer>(4);
    int locationOfCorrectAnswer;int Score=0;int TotalNoOfQuestions=1;
    int milis = 60000;
    int START_TIME_IN_MILLIS = 30000;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        timerTextView = findViewById(R.id.TimertextView);
        questionTextView = findViewById(R.id.QuestionstextView);
        scoreTextView = findViewById(R.id.ScoretextView);
        timerValueTextView = findViewById(R.id.timeValueTextView);
        scoreValueTextView = findViewById(R.id.scoreValueTextView);
        questionValueTextValue= findViewById(R.id.questionValueTextView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        restartButton = findViewById(R.id.restartButtonView);
        quitButton = findViewById(R.id.quitButtonView);

        gameStarter();timer();
        restart(findViewById(R.id.timeTextView));

        TimeVar = getIntent().getExtras().getString("userSelectedTime");
        LevelVar = getIntent().getExtras().getString("userSelectedLevel");

      //  Log.i("timeValue",TimeVar);
      //  Log.i("level value ",LevelVar);

        timeValueIntent = Integer.valueOf((TimeVar));
        levelValueIntent = Integer.valueOf(LevelVar);
       /* Log.i("timeValue after conversion", String.valueOf(timeValueIntent));
        Log.i("level value after conversion", String.valueOf(levelValueIntent));
        Log.i("value of START_TIME_IN_MILLIS",(String.valueOf(START_TIME_IN_MILLIS)));*/


    }//onCreate end

    public void gameStarter() {
        Random random = new Random();

        a = random.nextInt(100);
        b = random.nextInt(100);
        result = a + b;

        questionValueTextValue.setText(Integer.toString(a) + " + " + Integer.toString(b));

        //Logic for storing correct ans at random positions inside array and matching it with result variable.
        locationOfCorrectAnswer = random.nextInt(4);//Generate random btw 0-3

        answers.clear();

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) { //Check if locationOfCorrectAnswer equals to i then only add it inside arrayList.
                //Since correctAnswerLocation is always randomly generated.
                //It get stored always at random position is arrayList
                answers.add(result);
            } else {
                int wrongAnswer = random.nextInt(200);
                while (wrongAnswer == result) {
                    wrongAnswer = random.nextInt(200);
                }
                answers.add(wrongAnswer);
            }
        }
        button1.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));

    }

    public void restart(View view){
        Score = 0;
        TotalNoOfQuestions = 0;
        restartButton.setEnabled(false);
        scoreValueTextView.setText(Integer.toString(Score) + "/" + Integer.toString(TotalNoOfQuestions));
        setBtnCond(false);
        gameStarter();
        timer();
    }



    public void quit(View view){
        Intent intent = new Intent(getApplicationContext(),Result.class);
        intent.putExtra("UserScore",Score);
        startActivity(intent);
        finish();
    }

    public void chooseAns(View view) {
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            Toast.makeText(getApplicationContext(),"Correct Answer :)",Toast.LENGTH_SHORT).show();
            Score++;
        } else {
            Toast.makeText(getApplicationContext(),"Wrong Answer :/",Toast.LENGTH_SHORT).show();
        }
        scoreValueTextView.setText(Score +"/"+TotalNoOfQuestions);
        TotalNoOfQuestions++;
        gameStarter();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.custom_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.help:
                Log.i("help pressed","ok");
            case R.id.about:
                Log.i("help","ok");
            default:
                Log.i("error","ok");
        }
        return super.onOptionsItemSelected(item);
    }



    //****************CountDownTimer ***************************
    public void timer() {
        MediaPlayer mediaPlayer =MediaPlayer.create(this,R.raw.funnysong);
        mCountDownTimer = new CountDownTimer(300000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                mediaPlayer.setLooping(true);
                mediaPlayer.start();

            }
            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Timed out", Toast.LENGTH_SHORT).show();
                restartButton.setVisibility(View.VISIBLE);
                restartButton.setEnabled(true);
                quitButton.setVisibility(View.VISIBLE);
                setBtnCond(true);
                mediaPlayer.stop();

            }
        }.start();
    }

    public void setBtnCond(boolean isBtnEnabled){
        if(isBtnEnabled == true) {
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
        }else{
            button1.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            button4.setEnabled(true);
        }
    }
    public void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerValueTextView.setText(String.valueOf(timeLeftFormatted));
    }

}//Main